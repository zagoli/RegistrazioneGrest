package Utility;

import Domain.Animatore;
import Domain.ContattoUrgenze;
import Domain.Iscritto;
import Domain.Ragazzo;
import Domain.Registrato;
import Domain.RelCollabora;
import Domain.TerzaMedia;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RequestFieldsOptionalClearingTest {
    @FunctionalInterface
    private interface Mapper<T> {
        void apply(HttpServletRequest request, T bean) throws Exception;
    }

    private record Field<T>(String name, BiConsumer<T, String> setter, Function<T, String> getter) {
    }

    private static HttpServletRequest request(Map<String, String> parameters) {
        return (HttpServletRequest) Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class}, (proxy, method, arguments) -> switch (method.getName()) {
                    case "getParameter" -> parameters.get(arguments[0]);
                    case "getParameterMap" -> parameters;
                    default -> throw new UnsupportedOperationException(method.getName());
                });
    }

    private static Map<String, String> requiredParameters() {
        return new HashMap<>(Map.of("nome", "Nome", "cognome", "Cognome",
                "dataNascita", "2012-02-31", "presenza", "giornata", "sezione", "A",
                "classe", "3", "mail", "required@example.test"));
    }

    private static <T> Stream<DynamicTest> cases(String group, Supplier<T> factory, Mapper<T> mapper, List<Field<T>> fields) {
        return fields.stream().flatMap(field -> Stream.of("missing", "empty", "value", "whitespace").map(mode ->
                DynamicTest.dynamicTest(group + "." + field.name() + "." + mode, () -> {
                    T bean = factory.get();
                    field.setter().accept(bean, "saved value");
                    Map<String, String> parameters = requiredParameters();
                    String submitted = switch (mode) {
                        case "empty" -> "";
                        case "value" -> "  replacement value  ";
                        case "whitespace" -> " ";
                        default -> null;
                    };
                    if (submitted != null) {
                        parameters.put(field.name(), submitted);
                    }
                    mapper.apply(request(parameters), bean);
                    assertEquals(submitted == null ? "saved value" : submitted.isEmpty() ? null : submitted,
                            field.getter().apply(bean));
                })));
    }

    private static <T extends Iscritto> Stream<DynamicTest> tessera(String group, Supplier<T> factory) {
        return cases(group, factory, RequestFields::iscritto,
                List.of(new Field<>("nTessera", Iscritto::setNTessera, Iscritto::getNTessera)));
    }

    @TestFactory
    Stream<DynamicTest> optionalFieldsClearOnlyWhenSubmitted() {
        return Stream.of(
                tessera("Ragazzo", Ragazzo::new),
                tessera("TerzaMedia", TerzaMedia::new),
                tessera("Animatore", Animatore::new),
                cases("Ragazzo", Ragazzo::new, RequestFields::ragazzo, List.of(
                        new Field<>("richieste", Ragazzo::setRichieste, Ragazzo::getRichieste),
                        new Field<>("noteAlimentari", Ragazzo::setNoteAlimentari, Ragazzo::getNoteAlimentari))),
                cases("TerzaMedia", TerzaMedia::new, RequestFields::terzaMedia, List.of(
                        new Field<>("richieste", TerzaMedia::setRichieste, TerzaMedia::getRichieste),
                        new Field<>("noteAlimentari", TerzaMedia::setNoteAlimentari, TerzaMedia::getNoteAlimentari),
                        new Field<>("cellulare", TerzaMedia::setCellulare, TerzaMedia::getCellulare))),
                cases("Contatto", ContattoUrgenze::new, RequestFields::contatto, List.of(
                        new Field<>("cellulare", ContattoUrgenze::setCellulare, ContattoUrgenze::getCellulare),
                        new Field<>("fisso", ContattoUrgenze::setFisso, ContattoUrgenze::getFisso))),
                cases("AttivitaGen", RelCollabora::new,
                        (request, bean) -> RequestFields.optionalText(request, "data", bean::setData),
                        List.of(new Field<>("data", RelCollabora::setData, RelCollabora::getData)))
        ).flatMap(Function.identity());
    }

    @TestFactory
    Stream<DynamicTest> notNullAccountFieldsRejectMissingEmptyAndBlankValues() {
        return Stream.of("telefono", "localita", "via", "civico").flatMap(field ->
                Stream.of("missing", "empty", "blank").map(mode -> DynamicTest.dynamicTest(field + "." + mode, () -> {
                    Map<String, String> parameters = requiredParameters();
                    parameters.putAll(Map.of("telefono", "0000000000", "localita", "Balconi",
                            "via", "Via di prova", "civico", "1"));
                    if (mode.equals("missing")) {
                        parameters.remove(field);
                    } else {
                        parameters.put(field, mode.equals("empty") ? "" : " ");
                    }
                    Registrato account = new Registrato();
                    account.setNome("Saved name");
                    IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                            () -> RequestFields.account(request(parameters), account));
                    assertTrue(error.getMessage().contains(field));
                    assertEquals("Saved name", account.getNome());
                })));
    }

    @Test
    void notNullAccountFieldsAreSavedWhenPresent() {
        Map<String, String> parameters = requiredParameters();
        parameters.putAll(Map.of("telefono", "0000000000", "localita", "Balconi",
                "via", "Via di prova", "civico", "1"));
        Registrato account = new Registrato();
        RequestFields.account(request(parameters), account);
        assertEquals("0000000000", account.getTelefono());
        assertEquals("Balconi", account.getLocalita());
        assertEquals("Via di prova", account.getVia());
        assertEquals("1", account.getCivico());
    }

    @Test
    void iscrittoKeepsLenientDateParsingAndDoesNotChangeRegistrationOwnership() throws Exception {
        Ragazzo bean = new Ragazzo();
        Registrato owner = new Registrato();
        bean.setRegistrato(owner);
        Map<String, String> parameters = requiredParameters();
        RequestFields.iscritto(request(parameters), bean);
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-02-31"), bean.getDataNascita());
        assertEquals("Nome", bean.getNome());
        assertEquals("Cognome", bean.getCognome());
        assertEquals("giornata", bean.getPresenza());
        assertSame(owner, bean.getRegistrato());
    }

    @Test
    void uncheckedCheckboxesStillClearBooleanFlags() {
        Ragazzo ragazzo = new Ragazzo();
        ragazzo.setMensa(true);
        ragazzo.setEntrataAnticipata(true);
        ragazzo.setSaNuotare(true);
        RequestFields.ragazzo(request(requiredParameters()), ragazzo);
        assertFalse(ragazzo.getMensa());
        assertFalse(ragazzo.getEntrataAnticipata());
        assertFalse(ragazzo.getSaNuotare());
        TerzaMedia terzaMedia = new TerzaMedia();
        terzaMedia.setSaNuotare(true);
        terzaMedia.setFestaPassaggio(true);
        RequestFields.terzaMedia(request(requiredParameters()), terzaMedia);
        assertFalse(terzaMedia.getSaNuotare());
        assertFalse(terzaMedia.getFestaPassaggio());
    }
}
