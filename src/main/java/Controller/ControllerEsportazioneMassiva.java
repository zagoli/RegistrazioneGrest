package Controller;

import DAOManager.DAOMan;
import Domain.AnimatoreExport;
import Domain.RagazzoExport;
import Domain.TerzaMediaExport;
import Response.BinaryResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerEsportazioneMassiva implements ControllerInterface {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerEsportazioneMassiva.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {

        logger.info("Inizio esportazione massiva");

        try (Workbook workbook = new XSSFWorkbook()) {
            List<RagazzoExport> ragazzi = DAOMan.ragazzoExportDAO.findAll();
            List<TerzaMediaExport> ragazziTerzaMedia = DAOMan.terzamediaExportDAO.findAll();
            List<AnimatoreExport> animatori = DAOMan.animatoreExportDAO.findAll();

            creaFoglioRagazzi(workbook, ragazzi);
            creaFoglioRagazziTerzaMedia(workbook, ragazziTerzaMedia);
            creaFoglioAnimatori(workbook, animatori);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            workbook.write(output);

            logger.info("Foglio excel salvato in memoria. Invio la risposta");

            return new BinaryResult("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "export_grest.xlsx",
                    output.toByteArray());
        } catch (SQLException | IOException e) {
            return Utils.getErrorPageAndLogException(e, ControllerEsportazioneMassiva.class.getSimpleName());
        }


    }

    private static void creaFoglioAnimatori(Workbook workbook, List<AnimatoreExport> animatori) {
        String nomeFoglio = WorkbookUtil.createSafeSheetName("Animatori");
        Sheet sheet = workbook.createSheet(nomeFoglio);
        creaIntestazioneAnimatore(sheet);
        popolaFoglioAnimatori(animatori, sheet);
        autoSizeColumns(sheet);
        logger.info("Creato foglio Animatori");
    }

    private static void popolaFoglioAnimatori(List<AnimatoreExport> animatori, Sheet sheet) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 1; i <= animatori.size(); i++) {
            AnimatoreExport animatore = animatori.get(i - 1);
            Row row = sheet.createRow(i);

            row.createCell(0).setCellValue(animatore.nome());
            row.createCell(1).setCellValue(animatore.cognome());
            row.createCell(2).setCellValue(sdf.format(animatore.dataNascita()));
            row.createCell(3).setCellValue(animatore.codiceFiscale());
            row.createCell(4).setCellValue(animatore.cellulare());
            row.createCell(5).setCellValue(animatore.mail());

            row.createCell(6).setCellValue(animatore.parrocchia().getNome() + ", " + animatore.parrocchia().getLuogo());
            row.createCell(7).setCellValue(animatore.circolo().getNome() + ", " + animatore.circolo().getLuogo());
            row.createCell(8).setCellValue(animatore.nTessera());

            row.createCell(9).setCellValue(animatore.settimanePresenza().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            row.createCell(10).setCellValue(animatore.presenza());

            row.createCell(11).setCellValue(animatore.fasciaEtaRagazzi());
            row.createCell(12).setCellValue(animatore.laboratorio().getDescrizione());
            row.createCell(13).setCellValue(animatore.responsabileLaboratorio());
            row.createCell(14).setCellValue(animatore.squadra().getNome());
            row.createCell(15).setCellValue(animatore.responsabileSquadra());

            row.createCell(16).setCellValue(animatore.registrato().getVia() + " " + animatore.registrato().getCivico() + ", " + animatore.registrato().getLocalita());
        }
    }

    private static void creaIntestazioneAnimatore(Sheet sheet) {
        creaRigaIntestazione(sheet, new String[]{
                "Nome",
                "Cognome",
                "Data di nascita",
                "Codice Fiscale",
                "Cellulare",
                "E-Mail",
                "Parrocchia",
                "Circolo NOI",
                "Numero tessera Circolo NOI",
                "Settimane di Presenza",
                "Presenza giornaliera",
                "Fascia d'età ragazzi preferita",
                "Laboratorio",
                "Responsabile laboratorio",
                "Squadra",
                "Responsabile squadra",
                "Indirizzo"
        });
    }

    private static void creaFoglioRagazziTerzaMedia(Workbook workbook, List<TerzaMediaExport> ragazziTerzaMedia) {
        String nomeFoglio = WorkbookUtil.createSafeSheetName("Ragazzi Terza Media");
        Sheet sheet = workbook.createSheet(nomeFoglio);
        creaIntestazioneRagazzoTerzaMedia(sheet);
        popolaFoglioRagazziTerzaMedia(ragazziTerzaMedia, sheet);
        autoSizeColumns(sheet);
        logger.info("Creato foglio Ragazzi Terza Media");
    }

    private static void popolaFoglioRagazziTerzaMedia(List<TerzaMediaExport> ragazziTerzaMedia, Sheet sheet) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 1; i <= ragazziTerzaMedia.size(); i++) {
            TerzaMediaExport terzaMedia = ragazziTerzaMedia.get(i - 1);
            Row row = sheet.createRow(i);

            row.createCell(0).setCellValue(terzaMedia.nome());
            row.createCell(1).setCellValue(terzaMedia.cognome());
            row.createCell(2).setCellValue(sdf.format(terzaMedia.dataNascita()));

            row.createCell(3).setCellValue(terzaMedia.scuola().getDescrizione());
            row.createCell(4).setCellValue("3" + terzaMedia.sezione());
            row.createCell(5).setCellValue(terzaMedia.parrocchia().getNome() + ", " + terzaMedia.parrocchia().getLuogo());
            row.createCell(6).setCellValue(terzaMedia.circolo().getNome() + ", " + terzaMedia.circolo().getLuogo());
            row.createCell(7).setCellValue(terzaMedia.nTessera());

            row.createCell(8).setCellValue(terzaMedia.settimanePresenza().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            row.createCell(9).setCellValue(terzaMedia.presenza());

            row.createCell(10).setCellValue(terzaMedia.laboratorio().getDescrizione());
            row.createCell(11).setCellValue(terzaMedia.squadra().getNome());

            row.createCell(12).setCellValue(terzaMedia.saNuotare());
            row.createCell(13).setCellValue(terzaMedia.festaPassaggio());

            row.createCell(14).setCellValue(terzaMedia.richieste());
            row.createCell(15).setCellValue(terzaMedia.noteAlimentari());

            row.createCell(16).setCellValue(terzaMedia.pagato());
            row.createCell(17).setCellValue(terzaMedia.importoPagamento().doubleValue());

            row.createCell(18).setCellValue(terzaMedia.registrato().getNome());
            row.createCell(19).setCellValue(terzaMedia.registrato().getCognome());
            row.createCell(20).setCellValue(terzaMedia.registrato().getMail());
            row.createCell(21).setCellValue(terzaMedia.registrato().getTelefono());
            row.createCell(22).setCellValue(terzaMedia.registrato().getVia() + " " + terzaMedia.registrato().getCivico() + ", " + terzaMedia.registrato().getLocalita());
        }
    }

    private static void creaIntestazioneRagazzoTerzaMedia(Sheet sheet) {
        creaRigaIntestazione(sheet, new String[] {
                "Nome",
                "Cognome",
                "Data di nascita",
                "Scuola",
                "Classe",
                "Parrocchia",
                "Circolo NOI",
                "Numero tessera Circolo NOI",
                "Settimane di Presenza",
                "Presenza giornaliera",
                "Laboratorio",
                "Squadra",
                "Sa nuotare",
                "Festa del passaggio",
                "Richieste",
                "Note alimentari",
                "Ha pagato",
                "Importo pagamento",
                "Nome genitore",
                "Cognome genitore",
                "Email genitore",
                "Telefono genitore",
                "Indirizzo"
        });
    }

    private static void creaFoglioRagazzi(Workbook workbook, List<RagazzoExport> ragazzi) {
        String nomeFoglio = WorkbookUtil.createSafeSheetName("Ragazzi");
        Sheet sheet = workbook.createSheet(nomeFoglio);
        creaIntestazioneRagazzo(sheet);
        popolaFoglioRagazzi(ragazzi, sheet);
        autoSizeColumns(sheet);
        logger.info("Creato foglio Ragazzi");
    }

    private static void popolaFoglioRagazzi(List<RagazzoExport> ragazzi, Sheet sheet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 1; i <= ragazzi.size(); i++) {
            RagazzoExport ragazzo = ragazzi.get(i - 1);
            Row row = sheet.createRow(i);

            row.createCell(0).setCellValue(ragazzo.nome());
            row.createCell(1).setCellValue(ragazzo.cognome());
            row.createCell(2).setCellValue(dateFormat.format(ragazzo.dataNascita()));

            row.createCell(3).setCellValue(ragazzo.scuola().getDescrizione());
            row.createCell(4).setCellValue(ragazzo.scuola().getGrado());
            row.createCell(5).setCellValue(ragazzo.classe() + ragazzo.sezione());
            row.createCell(6).setCellValue(ragazzo.parrocchia().getNome() + ", " + ragazzo.parrocchia().getLuogo());
            row.createCell(7).setCellValue(ragazzo.circolo().getNome() + ", " + ragazzo.circolo().getLuogo());
            row.createCell(8).setCellValue(ragazzo.nTessera());

            row.createCell(9).setCellValue(ragazzo.settimanePresenza().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            row.createCell(10).setCellValue(ragazzo.presenza());

            row.createCell(11).setCellValue(ragazzo.laboratorio().getDescrizione());
            row.createCell(12).setCellValue(ragazzo.squadra().getNome());

            row.createCell(13).setCellValue(ragazzo.mensa());
            row.createCell(14).setCellValue(ragazzo.saNuotare());
            row.createCell(15).setCellValue(ragazzo.entrataAnticipata());

            row.createCell(16).setCellValue(ragazzo.richieste());
            row.createCell(17).setCellValue(ragazzo.noteAlimentari());

            row.createCell(18).setCellValue(ragazzo.fratelloIscritto());
            row.createCell(19).setCellValue(ragazzo.pagato());
            row.createCell(20).setCellValue(ragazzo.importoPagamento().doubleValue());

            row.createCell(21).setCellValue(ragazzo.registrato().getNome());
            row.createCell(22).setCellValue(ragazzo.registrato().getCognome());
            row.createCell(23).setCellValue(ragazzo.registrato().getMail());
            row.createCell(24).setCellValue(ragazzo.registrato().getTelefono());
            row.createCell(25).setCellValue(ragazzo.registrato().getVia() + " " + ragazzo.registrato().getCivico() + ", " + ragazzo.registrato().getLocalita());
        }
    }

    private static void creaIntestazioneRagazzo(Sheet sheet) {
        creaRigaIntestazione(sheet,
                new String[] {
                        "Nome",
                        "Cognome",
                        "Data di nascita",
                        "Scuola",
                        "Grado",
                        "Classe",
                        "Parrocchia",
                        "Circolo NOI",
                        "Numero tessera Circolo NOI",
                        "Settimane di Presenza",
                        "Presenza giornaliera",
                        "Laboratorio",
                        "Squadra",
                        "Mensa",
                        "Sa nuotare",
                        "Entrata Anticipata",
                        "Richieste",
                        "Note alimentari",
                        "Fratello iscritto",
                        "Ha pagato",
                        "Importo pagamento",
                        "Nome genitore",
                        "Cognome genitore",
                        "Email genitore",
                        "Telefono genitore",
                        "Indirizzo"
        });
    }

    private static void creaRigaIntestazione(Sheet foglio, String[] nomiColonne) {
        Row row = foglio.createRow(0);
        for (int i = 0; i < nomiColonne.length; i++) {
            creaCellaIntestazione(row, i, nomiColonne[i]);
        }
    }

    private static void creaCellaIntestazione(Row riga, int indice, String intestazione) {
        Cell cell = riga.createCell(indice);
        cell.setCellValue(intestazione);
        cell.setCellStyle(boldFont(riga.getSheet().getWorkbook()));
    }

    private static void autoSizeColumns(Sheet sheet) {
        int maxNumColumns = 0;
        for (Row row : sheet) {
            if (row.getLastCellNum() > maxNumColumns) {
                maxNumColumns = row.getLastCellNum();
            }
        }
        for (int i = 0; i < maxNumColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static CellStyle boldFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

}
