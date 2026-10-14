package Controller;

import Domain.AnimatoreExport;
import Domain.RagazzoExport;
import Domain.TerzaMediaExport;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import regression.Fixtures;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExportPreservationTest {
    @Test
    void exportKeepsOriginalSheetsColumnsCellTypesAndParentData() throws Exception {
        RagazzoExport ragazzo = new RagazzoExport("Ragazzo", "Export", Fixtures.BIRTH, "C",
                Fixtures.laboratorio(), Fixtures.parrocchia(), Fixtures.registrato(), Fixtures.circolo(),
                "Si", "Richiesta", "Nota", "No", "Si", "No", Fixtures.scuola(), "R-123", "B", "4",
                Fixtures.squadra(), List.of(3, 1), "Si", new BigDecimal("123.50"));
        TerzaMediaExport terzaMedia = new TerzaMediaExport("Terza", "Export", Fixtures.BIRTH, "C",
                Fixtures.laboratorio(), Fixtures.scuola(), Fixtures.parrocchia(), Fixtures.circolo(), "0000000022",
                "Nota terza", "Richiesta terza", "Si", "No", "C", "T-456", "terza@example.invalid",
                Fixtures.squadra(), List.of(2, 1), Fixtures.registrato(), "No", new BigDecimal("98.75"));
        AnimatoreExport animatore = new AnimatoreExport("Animatore", "Export", Fixtures.BIRTH,
                "SNTTST00A00A000A", "0000000023", "animatore@example.invalid", Fixtures.parrocchia(), Fixtures.circolo(),
                "A-789", List.of(1, 3), "C", "PIC", Fixtures.laboratorio(), "No", Fixtures.squadra(),
                "Si", Fixtures.registrato());
        try (Workbook workbook = new XSSFWorkbook()) {
            createSheet("creaFoglioRagazzi", workbook, List.of(ragazzo));
            createSheet("creaFoglioRagazziTerzaMedia", workbook, List.of(terzaMedia));
            createSheet("creaFoglioAnimatori", workbook, List.of(animatore));
            assertEquals(3, workbook.getNumberOfSheets());
            verify(workbook.getSheetAt(0), "Ragazzi", new String[]{
                    "Nome", "Cognome", "Data di nascita", "Scuola", "Grado", "Classe", "Parrocchia",
                    "Circolo NOI", "Numero tessera Circolo NOI", "Settimane di Presenza", "Presenza giornaliera",
                    "Laboratorio", "Squadra", "Mensa", "Sa nuotare", "Entrata Anticipata", "Richieste",
                    "Note alimentari", "Fratello iscritto", "Ha pagato", "Importo pagamento", "Nome genitore",
                    "Cognome genitore", "Email genitore", "Telefono genitore", "Indirizzo"
            }, new Object[]{
                    "Ragazzo", "Export", "02/01/2010", "Scuola sintetica", "primaria", "4B",
                    "Parrocchia test, Localita test", "Circolo test, Paese test", "R-123", "3, 1", "C",
                    "Laboratorio sintetico", "Squadra test", "No", "Si", "Si", "Richiesta", "Nota", "No",
                    "Si", 123.5d, "Ada", "Esempio", "fixture@example.invalid", "0000000041",
                    "Via sintetica 12B, Localita test"
            });
            verify(workbook.getSheetAt(1), "Ragazzi Terza Media", new String[]{
                    "Nome", "Cognome", "Data di nascita", "Scuola", "Classe", "Parrocchia", "Circolo NOI",
                    "Numero tessera Circolo NOI", "Settimane di Presenza", "Presenza giornaliera", "Laboratorio",
                    "Squadra", "Sa nuotare", "Festa del passaggio", "Richieste", "Note alimentari", "Ha pagato",
                    "Importo pagamento", "Nome genitore", "Cognome genitore", "Email genitore", "Telefono genitore", "Indirizzo"
            }, new Object[]{
                    "Terza", "Export", "02/01/2010", "Scuola sintetica", "3C",
                    "Parrocchia test, Localita test", "Circolo test, Paese test", "T-456", "2, 1", "C",
                    "Laboratorio sintetico", "Squadra test", "Si", "No", "Richiesta terza", "Nota terza",
                    "No", 98.75d, "Ada", "Esempio", "fixture@example.invalid", "0000000041",
                    "Via sintetica 12B, Localita test"
            });
            verify(workbook.getSheetAt(2), "Animatori", new String[]{
                    "Nome", "Cognome", "Data di nascita", "Codice Fiscale", "Cellulare", "E-Mail", "Parrocchia",
                    "Circolo NOI", "Numero tessera Circolo NOI", "Settimane di Presenza", "Presenza giornaliera",
                    "Fascia d'età ragazzi preferita", "Laboratorio", "Responsabile laboratorio", "Squadra",
                    "Responsabile squadra", "Indirizzo"
            }, new Object[]{
                    "Animatore", "Export", "02/01/2010", "SNTTST00A00A000A", "0000000023", "animatore@example.invalid",
                    "Parrocchia test, Localita test", "Circolo test, Paese test", "A-789", "1, 3", "C", "PIC",
                    "Laboratorio sintetico", "No", "Squadra test", "Si", "Via sintetica 12B, Localita test"
            });
        }
    }

    private static void createSheet(String name, Workbook workbook, List<?> rows) throws Exception {
        var method = ControllerEsportazioneMassiva.class.getDeclaredMethod(name, Workbook.class, List.class);
        method.setAccessible(true);
        method.invoke(null, workbook, rows);
    }

    private static void verify(Sheet sheet, String name, String[] headers, Object[] values) {
        assertEquals(name, sheet.getSheetName());
        assertEquals(2, sheet.getPhysicalNumberOfRows());
        Row heading = sheet.getRow(0);
        Row row = sheet.getRow(1);
        assertEquals(headers.length, heading.getLastCellNum());
        assertEquals(values.length, row.getLastCellNum());
        for (int column = 0; column < headers.length; column++) {
            assertEquals(headers[column], heading.getCell(column).getStringCellValue());
            assertTrue(sheet.getWorkbook().getFontAt(heading.getCell(column).getCellStyle().getFontIndex()).getBold());
            if (values[column] instanceof Double number) {
                assertEquals(CellType.NUMERIC, row.getCell(column).getCellType());
                assertEquals(number.doubleValue(), row.getCell(column).getNumericCellValue());
            } else {
                assertEquals(CellType.STRING, row.getCell(column).getCellType());
                assertEquals(values[column], row.getCell(column).getStringCellValue());
            }
        }
    }
}
