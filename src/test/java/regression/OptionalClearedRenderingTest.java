package regression;

import Domain.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OptionalClearedRenderingTest {
    @TestFactory
    Stream<DynamicTest> clearedProfileAndContactValuesRenderWithoutOldText() throws Exception {
        var cfg = TemplateRegressionTest.configuration();
        List<String> templates = List.of(
                "user/modificautente.html.ftl", "user/registraanimatore.html.ftl",
                "acccu/modificacu.html.ftl", "acccu/dashboardacccu.html.ftl",
                "ammseg/dettaglioragazzo.html.ftl", "ammseg/dettaglioterzamedia.html.ftl",
                "ammseg/dettaglioanimatore.html.ftl", "ammseg/visualizzaragazzi.html.ftl",
                "ammseg/visualizzaterzamedia.html.ftl", "ammseg/visualizzaanimatori.html.ftl",
                "stampe/schedaragazzo.html.ftl", "stampe/schedaterzamedia.html.ftl",
                "stampe/schedaanimatore.html.ftl");
        return templates.stream().map(path -> DynamicTest.dynamicTest(path, () -> {
            Map<String, Object> model = TemplateRegressionTest.model(path, "complete-C");
            clearOptionalValues(model);
            StringWriter output = new StringWriter();
            cfg.getTemplate(path).process(model, output);
            assertFalse(output.toString().isBlank());
            assertFalse(output.toString().contains("Via sintetica"));
            assertFalse(output.toString().contains("Localita test</i>"));
            assertFalse(output.toString().contains("0000000041"));
            assertFalse(output.toString().contains("0000000031"));
            assertFalse(output.toString().contains("0000000032"));
        }));
    }

    private static void clearOptionalValues(Object value) {
        if (value instanceof Registrato account) {
            account.setTelefono(null);
            account.setVia(null);
            account.setCivico(null);
            account.setLocalita(null);
        } else if (value instanceof Iscritto participant) {
            clearOptionalValues(participant.getRegistrato());
            participant.setNTessera(null);
            if (participant instanceof Ragazzo boy) {
                boy.setRichieste(null);
                boy.setNoteAlimentari(null);
            } else if (participant instanceof TerzaMedia thirdYear) {
                thirdYear.setRichieste(null);
                thirdYear.setNoteAlimentari(null);
                thirdYear.setCellulare(null);
            }
        } else if (value instanceof ContattoUrgenze contact) {
            contact.setCellulare(null);
            contact.setFisso(null);
        } else if (value instanceof Map<?, ?> map) {
            map.forEach((key, item) -> {
                clearOptionalValues(key);
                clearOptionalValues(item);
            });
        } else if (value instanceof Iterable<?> list) {
            list.forEach(OptionalClearedRenderingTest::clearOptionalValues);
        }
    }
}
