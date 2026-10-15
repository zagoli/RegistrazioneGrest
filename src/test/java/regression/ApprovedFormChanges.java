package regression;

import java.util.Set;
import java.util.regex.Pattern;

/** Explicit user-approved UI changes applied only to the frozen expected form output. */
final class ApprovedFormChanges {
    private static final Set<String> PARTICIPANTS = Set.of(
            "user/registraragazzo.html.ftl", "user/modificaragazzo.html.ftl",
            "user/registraterzamedia.html.ftl", "user/modificaterzamedia.html.ftl",
            "user/registraanimatore.html.ftl", "user/modificaanimatore.html.ftl");
    private static final String YEAR = "<b>\n<script type=\"text/javascript\">\n"
            + "var theDate = new Date();\n"
            + "                                document.write(theDate.getFullYear());</script>\n</b>";

    private ApprovedFormChanges() {}

    static String apply(String name, String original) {
        if (PARTICIPANTS.contains(name)) {
            return participant(original);
        }
        if (name.equals("ammseg/registrasegretario.html.ftl") && original.contains("id=\"email\"")) {
            String extraFields = TemplateRegressionTest.normalize("""
                    <div class="form-group">
                        <label for="telefono">Numero di telefono</label>
                        <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]"
                               required id="telefono" name="telefono" placeholder="Cellulare personale">
                    </div>
                    <div class="form-group">
                        <label for="localita">Località</label>
                        <input class="form-control" type="text" required id="localita" name="localita" placeholder="Localit&agrave">
                    </div>
                    <div class="form-group">
                        <label for="via">Via</label>
                        <input class="form-control" type="text" required id="via" name="via" placeholder="Via">
                    </div>
                    <div class="form-group">
                        <label for="civico">Numero civico</label>
                        <input class="form-control" type="text" required id="civico" name="civico" placeholder="Numero Civico">
                    </div>
                    """);
            return original.replace("<div class=\"form-group\">\n<label for=\"pswd\">",
                    extraFields + "\n<div class=\"form-group\">\n<label for=\"pswd\">");
        }
        if (name.equals("user/modificautente.html.ftl")) {
            String result = original.replace("<label for=\"localita\">\nLocalita\n",
                    "<label for=\"localita\">\nLocalità\n")
                    .replace("data-parsley-length=\"[10,10]\"", "data-parsley-length=\"[10, 10]\"");
            return Pattern.compile("<input[^>]*>").matcher(result).replaceAll(match -> {
                String tag = match.group();
                boolean required = Set.of("telefono", "localita", "via", "civico").stream()
                        .anyMatch(field -> tag.contains("id=\"" + field + "\""));
                if (required && !tag.contains(" required")) {
                    return TemplateRegressionTest.normalizeTag(tag.substring(0, tag.length() - 1) + " required>");
                }
                return tag;
            });
        }
        return original;
    }

    private static String participant(String original) {
        String result = Pattern.compile(
                "(<div class=\"form-group\">\\n<label for=\"cognome\">.*?</div>)\\n"
                        + "(<div class=\"form-group\">\\n<label for=\"nome\">.*?</div>)", Pattern.DOTALL)
                .matcher(original).replaceFirst("$2\n$1");
        result = result.replace("<label>\nPresenza giornaliera\n",
                        "<label>\nPresenza giornaliera al Grest\n")
                .replace("<label class=\"form-check-label\" for=\"completo\">\nCompleto\n",
                        "<label class=\"form-check-label\" for=\"completo\">\nMattina e pomeriggio\n")
                .replace("<label>\nPartecipazione\n</label>", "<label>\nPeriodo di partecipazione\n</label>")
                .replace("<label>\nPeriodo di partecipazione\n</label>\n<br>",
                        "<label>\nPeriodo di partecipazione\n</label>")
                .replace("height=600, status=yes", "height=900, status=yes")
                .replace("<label class=\"sr-only\" for=\"laboratorio\">", "<label for=\"laboratorio\">")
                .replace("<label for=\"laboratorio\">\nLaboratorio\n",
                        "<label for=\"laboratorio\">\nSelezionare un laboratorio\n")
                .replace("<select aria-describedby=\"helpLaboratorio\" ", "<select ")
                .replace("<label for=\"sezione\">\nfrequentante la classe terza media , sezione\n",
                        "<label for=\"sezione\">\nSezione\n");
        result = Pattern.compile("<label for=\"circolo\">.*?</label>", Pattern.DOTALL).matcher(result)
                .replaceFirst("<label for=\"circolo\">\nCircolo di tesseramento NOI\n</label>");
        result = Pattern.compile("<label for=\"nTessera\">.*?</label>", Pattern.DOTALL).matcher(result)
                .replaceFirst("<label for=\"nTessera\">\nNumero tessera Circolo NOI\n" + YEAR + "\n</label>");
        result = Pattern.compile("(<small[^>]*id=\"helpNTessera\">\\n).*?(\\n</small>)", Pattern.DOTALL)
                .matcher(result).replaceFirst("$1Inserire il numero di tessera del Circolo NOI "
                        + "se non si è tesserati al Circolo NOI di Balconi.$2");
        int circoloStart = result.indexOf("<select class=\"form-control\" id=\"circolo\"");
        int circoloEnd = result.indexOf("</select>", circoloStart);
        String circoli = result.substring(circoloStart, circoloEnd).replace(" )", ")");
        result = result.substring(0, circoloStart) + circoli + result.substring(circoloEnd);
        return Pattern.compile("<input[^>]*id=\"sezione\"[^>]*>").matcher(result)
                .replaceAll(match -> match.group().replace("placeholder=\"Classe\"", "placeholder=\"A\"")
                        .replace("placeholder=\"sezione\"", "placeholder=\"A\""));
    }
}
