package regression;

import Domain.*;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateRegressionTest {
    private static final Path ROOT = Path.of("src", "main", "webapp", "WEB-INF");
    private static final List<String> SCENARIOS = List.of("complete-C", "morning-M", "afternoon-P",
            "optional-absent", "empty-lists", "errors-and-closed");
    private static final Pattern TOKENS = Pattern.compile(
            "(?is)<!--.*?-->|<script\\b(?:[^>\"']|\"[^\"]*\"|'[^']*')*>.*?</script\\s*>"
                    + "|<(textarea|pre)\\b(?:[^>\"']|\"[^\"]*\"|'[^']*')*>.*?</\\1\\s*>"
                    + "|<p\\b[^>]*style=\"[^\"]*white-space:\\s*pre-wrap[^\"]*\"[^>]*>.*?</p\\s*>"
                    + "|<(?:[^>\"']|\"[^\"]*\"|'[^']*')+>|[^<]+|<");
    private static final Pattern ATTRIBUTES = Pattern.compile(
            "([^\\s=/>]+)(?:\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+))?");

    static Configuration configuration() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_35);
        cfg.setDirectoryForTemplateLoading(ROOT.toFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.ITALY);
        cfg.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        return cfg;
    }

    private static List<String> templates() throws Exception {
        try (Stream<Path> paths = Files.walk(ROOT)) {
            return paths.filter(p -> p.toString().endsWith(".ftl"))
                    .map(p -> ROOT.relativize(p).toString().replace('\\', '/')).sorted().toList();
        }
    }

    @TestFactory
    Stream<DynamicTest> everyTemplateParses() throws Exception {
        Configuration cfg = configuration();
        List<String> names = templates();
        if (Boolean.getBoolean("dry.record")) {
            Baseline.verify("template-inventory", String.join("\n", names));
        } else {
            for (String original : Baseline.originalTemplates()) {
                assertTrue(names.contains(original), "Original template removed: " + original);
            }
        }
        return names.stream().map(name -> DynamicTest.dynamicTest(name, () -> assertNotNull(cfg.getTemplate(name))));
    }

    @TestFactory
    Stream<DynamicTest> everyTemplateRendersAgainstFrozenOracle() throws Exception {
        Configuration cfg = configuration();
        List<String> names = Boolean.getBoolean("dry.record") ? templates() : Baseline.originalTemplates();
        return names.stream().flatMap(name -> SCENARIOS.stream().map(scenario ->
                DynamicTest.dynamicTest(name + " [" + scenario + "]", () -> {
                    StringWriter output = new StringWriter();
                    cfg.getTemplate(name).process(model(name, scenario), output);
                    String canonical = name.endsWith(".json.ftl") ? output.toString().trim()
                            : normalize(output.toString());
                    Baseline.verify("templates/" + name + "/" + scenario, canonical);
                })));
    }

    /*
     * Lexical HTML normalization avoids a forgiving HTML parser repairing invalid legacy markup.
     * Text/value content, duplicate attributes, script source and attribute values remain visible.
     * Only formatting whitespace and attribute ordering are ignored.
     */
    static String normalize(String html) {
        List<String> result = new ArrayList<>();
        Matcher matcher = TOKENS.matcher(html.replace("\r\n", "\n"));
        while (matcher.find()) {
            String token = matcher.group();
            if (token.startsWith("<!--")) continue;
            if (token.regionMatches(true, 0, "<script", 0, 7)) {
                int end = token.indexOf('>');
                result.add(normalizeTag(token.substring(0, end + 1)));
                result.add(token.substring(end + 1));
            } else if (token.regionMatches(true, 0, "<textarea", 0, 9)
                    || token.regionMatches(true, 0, "<pre", 0, 4)
                    || token.startsWith("<p") && token.contains("pre-wrap")) {
                int end = token.indexOf('>');
                int close = token.lastIndexOf("</");
                result.add(normalizeTag(token.substring(0, end + 1)));
                if (close > end + 1) result.add(token.substring(end + 1, close));
                result.add(token.substring(close));
            } else if (token.startsWith("<")) {
                result.add(normalizeTag(token));
            } else if (!token.isBlank()) {
                result.add(token.strip().replaceAll("[ \\t]*\\n[ \\t]*", " "));
            }
        }
        return String.join("\n", result);
    }

    private static String normalizeTag(String token) {
        if (token.startsWith("</") || token.startsWith("<!") || token.equals("<")) return token.strip();
        Matcher attrs = ATTRIBUTES.matcher(token.substring(1, token.length() - 1));
        if (!attrs.find()) return token;
        String name = attrs.group(1);
        List<String> attributes = new ArrayList<>();
        while (attrs.find()) {
            String value = attrs.group(2);
            if (value != null && value.length() >= 2
                    && (value.charAt(0) == '"' || value.charAt(0) == '\'')) {
                value = value.substring(1, value.length() - 1);
            }
            attributes.add(attrs.group(1) + (value == null ? "" : "=\"" + value + "\""));
        }
        Collections.sort(attributes);
        return "<" + name + (attributes.isEmpty() ? "" : " " + String.join(" ", attributes)) + ">";
    }

    @Test
    void normalizationDoesNotHideBehavioralChanges() {
        assertEquals(normalize("<input name=\"x\" required value=\"A\">"),
                normalize("<input value=\"A\" required name=\"x\">"));
        for (String changed : List.of("<input name=\"y\" required value=\"A\">",
                "<input name=\"x\" value=\"A\">", "<input name=\"x\" required value=\"B\">",
                "<input name=\"x\" required value=\"A  B\">")) {
            assertNotEquals(normalize("<input name=\"x\" required value=\"A\">"), normalize(changed));
        }
        assertNotEquals(normalize("<p>Alpha beta</p>"), normalize("<p>Alpha  beta</p>"));
        assertNotEquals(normalize("<script>f(1);</script>"), normalize("<script>f(2);</script>"));
        assertNotEquals(normalize("<textarea>A\nB</textarea>"), normalize("<textarea>A\nC</textarea>"));
        assertNotEquals(normalize("<textarea>A\nB</textarea>"), normalize("<textarea>A B</textarea>"));
        assertNotEquals(normalize("<textarea>A</textarea>"), normalize("<textarea> A </textarea>"));
        assertNotEquals(normalize("<p style=\"white-space: pre-wrap;\">A\nB</p>"),
                normalize("<p style=\"white-space: pre-wrap;\">A B</p>"));
    }

    static Map<String, Object> model(String path, String scenario) {
        String file = path.substring(path.lastIndexOf('/') + 1);
        boolean absent = scenario.equals("optional-absent");
        boolean empty = scenario.equals("empty-lists");
        boolean errors = scenario.equals("errors-and-closed");
        boolean morning = scenario.equals("morning-M");
        boolean afternoon = scenario.equals("afternoon-P");
        boolean enabled = !morning && !errors;
        Ragazzo rag = Fixtures.ragazzo();
        TerzaMedia ter = Fixtures.terzamedia();
        Animatore ani = Fixtures.animatore();
        Registrato reg = Fixtures.registrato();
        String presence = morning ? "M" : afternoon ? "P" : "C";
        rag.setPresenza(presence);
        ter.setPresenza(presence);
        ani.setPresenza(presence);
        rag.setEntrataAnticipata(enabled);
        rag.setMensa(enabled);
        rag.setSaNuotare(enabled);
        rag.setFratelloIscritto(enabled);
        ter.setSaNuotare(enabled);
        ter.setFestaPassaggio(enabled);
        ani.setResponsabileLaboratorio(enabled);
        ani.setResponsabileSquadra(enabled);
        ani.setFasciaEtaRagazzi(morning ? "GRA" : afternoon ? "TER" : "PIC");
        if (absent) {
            rag.setNTessera(null); rag.setRichieste(null); rag.setNoteAlimentari(null);
            ter.setNTessera(null); ter.setRichieste(null); ter.setNoteAlimentari(null); ter.setCellulare(null);
            ani.setNTessera(null);
            rag.setSquadra(new Squadra(0, "", ""));
            ter.setSquadra(new Squadra(0, "", ""));
            ani.setSquadra(new Squadra(0, "", ""));
            if (file.equals("modificautente.html.ftl")) {
                reg.setTelefono(null); reg.setLocalita(null); reg.setVia(null); reg.setCivico(null);
            }
        }
        Map<String, Object> m = new HashMap<>();
        m.put("TITOLOPAGINA", "Pagina sintetica");
        m.put("tipoUt", morning ? 2 : afternoon || absent ? 3 : 0);
        m.put("idUtente", morning ? 99 : 41);
        for (String flag : List.of("ISCRRAG", "ISCRAN", "ISCRTER")) m.put(flag, !errors);
        m.put("DONE", errors);
        m.put("result", !errors); m.put("error", errors);
        m.put("haAltroRagazzoIscritto", enabled);
        m.put("altroFratelloNonTrovato", morning);
        m.put("dataNascita", "2010-01-02");
        m.put("registrato", reg); m.put("ragazzo", rag); m.put("animatore", ani); m.put("terzamedia", ter);
        m.put("accompagnatore", Fixtures.accompagnatore()); m.put("contatto", Fixtures.contatto());
        m.put("cu", empty ? List.of() : List.of(Fixtures.contatto()));
        m.put("contatti", empty ? List.of() : List.of(Fixtures.contatto()));
        m.put("accompagnatori", empty ? List.of() : List.of(Fixtures.accompagnatore()));
        m.put("laboratori", empty ? List.of() : List.of(Fixtures.laboratorio(), new Laboratorio(91, "Riservato test", true)));
        m.put("parrocchie", empty ? List.of() : List.of(Fixtures.parrocchia(), new Parrocchia(92, "Altra parrocchia", "Altro luogo")));
        m.put("circoli", empty ? List.of() : List.of(Fixtures.circolo(), new Circolo(93, "Altro circolo", "Altro luogo")));
        m.put("scuole", empty ? List.of() : List.of(Fixtures.scuola(), new Scuola(94, "secondaria", "Altra scuola")));
        m.put("squadre", empty ? List.of() : List.of(Fixtures.squadra(), new Squadra(95, "Altra squadra", "#abcd12")));
        m.put("laboratorio", Fixtures.laboratorio()); m.put("squadra", Fixtures.squadra());
        m.put("settimane", empty ? List.of() : Fixtures.calendari());
        m.put("settimana", Fixtures.calendario()); m.put("set", Fixtures.calendario());
        m.put("calendari", empty ? List.of() : Fixtures.calendari());
        if (file.matches("modifica(ragazzo|terzamedia|animatore)\\.html\\.ftl")) {
            Map<Calendario, Boolean> selected = new LinkedHashMap<>();
            if (!empty) { selected.put(Fixtures.calendario(), true); selected.put(Fixtures.secondoCalendario(), false); }
            m.put("calendari", selected);
        }
        m.put("annotazioni", empty ? List.of() : List.of(Fixtures.annotazione()));
        m.put("attgen", empty ? List.of() : List.of(Fixtures.attivita()));
        m.put("allattivita", empty ? List.of() : List.of(Fixtures.attivita()));
        m.put("attivita", empty ? List.of() : List.of(List.of("Ada", "Esempio", "Lunedi", "Attivita sintetica")));
        if (file.equals("dashboardattgen.html.ftl")) {
            m.put("attivita", empty ? Map.of() : Map.of(Fixtures.attivita(), List.of("Lunedi", 33)));
        }
        m.put("ragazzi", empty ? List.of() : List.of(rag));
        m.put("animatori", empty ? List.of() : List.of(ani));
        if (!file.matches("(modifica|dettaglio|scheda)terzamedia\\.html\\.ftl")) {
            m.put("terzamedia", empty ? List.of() : List.of(ter));
        }
        if (!absent) m.put("pagamento", Fixtures.pagamento());
        m.put("quotaIscrizione", 123.5);
        m.put("amministratori", empty ? List.of() : List.of(reg));
        Registrato seg = Fixtures.registrato(); seg.setTipoUt(morning ? 2 : 1);
        m.put("segretari", empty ? List.of() : List.of(seg));
        for (String count : List.of("nreg", "nrag", "npag", "nani", "nter", "nmensatot", "eanttot")) m.put(count, empty ? 0 : 7);
        for (String key : List.of("mapragclassi", "mapanieta", "mapraglab", "mapanilab")) {
            m.put(key, empty ? Map.of() : Map.of("Gruppo test", 7));
        }
        for (String key : List.of("anisett", "ragsett", "mensasett", "eansett")) {
            m.put(key, empty ? List.of() : List.of(List.of(1, 7), List.of(2, 3)));
        }
        for (int i = 1; i <= 4; i++) for (String suffix : List.of("", "_mensa", "_fratelli", "_fratelli_mensa")) {
            m.put("prezzo_" + i + suffix, i * 25);
        }
        m.put("supplemento_fuoricomune", 15); m.put("supplemento_anticipo", 20);
        if (errors) {
            for (String flag : List.of("INVALIDMAIL", "ERRATO", "UTENTENONTROVATO", "FATTO", "NOCU")) m.put(flag, true);
            m.put("errorePagamento", "Errore pagamento sintetico");
            m.put("codiceErrore", "TEST-0001"); m.put("dataOraErrore", "01/06/2026 10:00");
        }
        if (file.equals("dashboardutente.html.ftl")) {
            m.put("ragazzi", empty ? Map.of() : Map.of(rag, enabled));
            m.put("terzamedia", empty ? Map.of() : Map.of(ter, enabled));
        }
        if (file.equals("gestiscilaboratori.html.ftl")) {
            m.put("laboratori", empty ? List.of() : List.of(
                    new LaboratorioConNumeroIscritti(Fixtures.laboratorio(), 7),
                    new LaboratorioConNumeroIscritti(new Laboratorio(91, "Riservato test", true), 0)));
        }
        for (String kind : List.of("ragazzi", "terzamedia")) {
            Object participant = kind.equals("ragazzi") ? rag : ter;
            if (file.equals("visualizza" + kind + ".html.ftl")) {
                m.put(kind, empty ? List.of() : Collections.singletonList(Arrays.asList(participant, absent ? null : Fixtures.riepilogo())));
            }
            if (file.equals("gestiscipagamenti" + (kind.equals("ragazzi") ? "" : "terzamedia") + ".html.ftl")) {
                m.put(kind, empty ? List.of() : List.of(List.of(participant, enabled, enabled ? Fixtures.riepilogo() : 140.0)));
            }
        }
        List<Object> ragRow = List.of(rag, "1 2", List.of(Fixtures.contatto()));
        List<Object> terRow = List.of(ter, "1 2", List.of(Fixtures.contatto()));
        List<Object> aniRow = List.of(ani, "1 2");
        m.put("dati", empty ? List.of() : List.of(ragRow));
        if (file.equals("collaborazione.html.ftl")) {
            m.put("dati", empty ? List.of() : Collections.singletonList(Arrays.asList(
                    new RelCollabora(36, 41, 33, "Lunedi"), Fixtures.attivita(), reg,
                    absent ? null : rag, absent ? null : Fixtures.calendari())));
        }
        if (file.matches("(elesintrag|pressetsqu)\\.html\\.ftl")) m.put("ragazzi", empty ? List.of() : List.of(ragRow));
        if (file.equals("elesintter.html.ftl")) m.put("terzamedia", empty ? List.of() : List.of(terRow));
        if (file.matches("(elesintan|presgiornani|pressetsqu)\\.html\\.ftl")) m.put("animatori", empty ? List.of() : List.of(aniRow));
        return m;
    }
}
