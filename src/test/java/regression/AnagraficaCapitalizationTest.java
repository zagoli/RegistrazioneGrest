package regression;

import Domain.Animatore;
import Domain.Iscritto;
import Domain.Ragazzo;
import Domain.TerzaMedia;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AnagraficaCapitalizationTest {
    @Test
    void capitalizesNamesForEveryParticipantTypeWithoutAnOptionalFlag() throws Exception {
        Template template = new Template("anagrafica", new StringReader("""
                <#import "struct/dettaglioIscritto.html.ftl" as dettaglio>
                <@dettaglio.anagraficaIscritto iscritto=iscritto/>
                """), TemplateRegressionTest.configuration());
        for (Iscritto iscritto : List.of(new Ragazzo(), new TerzaMedia(), new Animatore())) {
            iscritto.setNome("mARIO luIGI");
            iscritto.setCognome("rOSSI");
            iscritto.setDataNascita(java.sql.Date.valueOf("2012-01-01"));
            StringWriter output = new StringWriter();
            template.process(Map.of("iscritto", iscritto), output);
            assertTrue(output.toString().contains("<p>Mario Luigi</p>"));
            assertTrue(output.toString().contains("<p>Rossi</p>"));
        }
    }
}
