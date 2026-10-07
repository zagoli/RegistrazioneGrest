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

            row.createCell(0).setCellValue(animatore.getNome());
            row.createCell(1).setCellValue(animatore.getCognome());
            row.createCell(2).setCellValue(sdf.format(animatore.getDataNascita()));
            row.createCell(3).setCellValue(animatore.getCodiceFiscale());
            row.createCell(4).setCellValue(animatore.getCellulare());
            row.createCell(5).setCellValue(animatore.getMail());

            row.createCell(6).setCellValue(animatore.getParrocchia().getNome() + ", " + animatore.getParrocchia().getLuogo());
            row.createCell(7).setCellValue(animatore.getCircolo().getNome() + ", " + animatore.getCircolo().getLuogo());
            row.createCell(8).setCellValue(animatore.getNTessera());

            row.createCell(9).setCellValue(animatore.getSettimanePresenza().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            row.createCell(10).setCellValue(animatore.getPresenza());

            row.createCell(11).setCellValue(animatore.getFasciaEtaRagazzi());
            row.createCell(12).setCellValue(animatore.getLaboratorio().getDescrizione());
            row.createCell(13).setCellValue(animatore.getResponsabileLaboratorio());
            row.createCell(14).setCellValue(animatore.getSquadra().getNome());
            row.createCell(15).setCellValue(animatore.getResponsabileSquadra());

            row.createCell(16).setCellValue(animatore.getRegistrato().getVia() + " " + animatore.getRegistrato().getCivico() + ", " + animatore.getRegistrato().getLocalita());
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

            row.createCell(0).setCellValue(terzaMedia.getNome());
            row.createCell(1).setCellValue(terzaMedia.getCognome());
            row.createCell(2).setCellValue(sdf.format(terzaMedia.getDataNascita()));

            row.createCell(3).setCellValue(terzaMedia.getScuola().getDescrizione());
            row.createCell(4).setCellValue("3" + terzaMedia.getSezione());
            row.createCell(5).setCellValue(terzaMedia.getParrocchia().getNome() + ", " + terzaMedia.getParrocchia().getLuogo());
            row.createCell(6).setCellValue(terzaMedia.getCircolo().getNome() + ", " + terzaMedia.getCircolo().getLuogo());
            row.createCell(7).setCellValue(terzaMedia.getNTessera());

            row.createCell(8).setCellValue(terzaMedia.getSettimanePresenza().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            row.createCell(9).setCellValue(terzaMedia.getPresenza());

            row.createCell(10).setCellValue(terzaMedia.getLaboratorio().getDescrizione());
            row.createCell(11).setCellValue(terzaMedia.getSquadra().getNome());

            row.createCell(12).setCellValue(terzaMedia.getSaNuotare());
            row.createCell(13).setCellValue(terzaMedia.getFestaPassaggio());

            row.createCell(14).setCellValue(terzaMedia.getRichieste());
            row.createCell(15).setCellValue(terzaMedia.getNoteAlimentari());

            row.createCell(16).setCellValue(terzaMedia.getPagato());
            row.createCell(17).setCellValue(terzaMedia.getImportoPagamento().doubleValue());

            row.createCell(18).setCellValue(terzaMedia.getRegistrato().getNome());
            row.createCell(19).setCellValue(terzaMedia.getRegistrato().getCognome());
            row.createCell(20).setCellValue(terzaMedia.getRegistrato().getMail());
            row.createCell(21).setCellValue(terzaMedia.getRegistrato().getTelefono());
            row.createCell(22).setCellValue(terzaMedia.getRegistrato().getVia() + " " + terzaMedia.getRegistrato().getCivico() + ", " + terzaMedia.getRegistrato().getLocalita());
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

            row.createCell(0).setCellValue(ragazzo.getNome());
            row.createCell(1).setCellValue(ragazzo.getCognome());
            row.createCell(2).setCellValue(dateFormat.format(ragazzo.getDataNascita()));

            row.createCell(3).setCellValue(ragazzo.getScuola().getDescrizione());
            row.createCell(4).setCellValue(ragazzo.getScuola().getGrado());
            row.createCell(5).setCellValue(ragazzo.getClasse() + ragazzo.getSezione());
            row.createCell(6).setCellValue(ragazzo.getParrocchia().getNome() + ", " + ragazzo.getParrocchia().getLuogo());
            row.createCell(7).setCellValue(ragazzo.getCircolo().getNome() + ", " + ragazzo.getCircolo().getLuogo());
            row.createCell(8).setCellValue(ragazzo.getNTessera());

            row.createCell(9).setCellValue(ragazzo.getSettimanePresenza().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            row.createCell(10).setCellValue(ragazzo.getPresenza());

            row.createCell(11).setCellValue(ragazzo.getLaboratorio().getDescrizione());
            row.createCell(12).setCellValue(ragazzo.getSquadra().getNome());

            row.createCell(13).setCellValue(ragazzo.getMensa());
            row.createCell(14).setCellValue(ragazzo.getSaNuotare());
            row.createCell(15).setCellValue(ragazzo.getEntrataAnticipata());

            row.createCell(16).setCellValue(ragazzo.getRichieste());
            row.createCell(17).setCellValue(ragazzo.getNoteAlimentari());

            row.createCell(18).setCellValue(ragazzo.getFratelloIscritto());
            row.createCell(19).setCellValue(ragazzo.getPagato());
            row.createCell(20).setCellValue(ragazzo.getImportoPagamento().doubleValue());

            row.createCell(21).setCellValue(ragazzo.getRegistrato().getNome());
            row.createCell(22).setCellValue(ragazzo.getRegistrato().getCognome());
            row.createCell(23).setCellValue(ragazzo.getRegistrato().getMail());
            row.createCell(24).setCellValue(ragazzo.getRegistrato().getTelefono());
            row.createCell(25).setCellValue(ragazzo.getRegistrato().getVia() + " " + ragazzo.getRegistrato().getCivico() + ", " + ragazzo.getRegistrato().getLocalita());
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
