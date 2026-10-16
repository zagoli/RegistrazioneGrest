package Controller;

import DAOManager.DAOMan;
import Domain.*;
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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ControllerEsportazioneMassiva implements ControllerInterface {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerEsportazioneMassiva.class);

    private static void creaFoglioAnimatori(Workbook workbook, List<AnimatoreExport> animatori) {
        creaFoglio(workbook, "Animatori", animatori,
                ControllerEsportazioneMassiva::creaIntestazioneAnimatore, ControllerEsportazioneMassiva::popolaFoglioAnimatori);
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

            affiliazioneEPresenza(row, 6, animatore.parrocchia(), animatore.circolo(), animatore.nTessera(),
                    animatore.settimanePresenza(), animatore.presenza());

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
        creaFoglio(workbook, "Ragazzi Terza Media", ragazziTerzaMedia,
                ControllerEsportazioneMassiva::creaIntestazioneRagazzoTerzaMedia, ControllerEsportazioneMassiva::popolaFoglioRagazziTerzaMedia);
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
            affiliazioneEPresenza(row, 5, terzaMedia.parrocchia(), terzaMedia.circolo(), terzaMedia.nTessera(),
                    terzaMedia.settimanePresenza(), terzaMedia.presenza());

            row.createCell(10).setCellValue(terzaMedia.laboratorio().getDescrizione());
            row.createCell(11).setCellValue(terzaMedia.squadra().getNome());

            row.createCell(12).setCellValue(terzaMedia.saNuotare());
            row.createCell(13).setCellValue(terzaMedia.festaPassaggio());

            row.createCell(14).setCellValue(terzaMedia.richieste());
            row.createCell(15).setCellValue(terzaMedia.noteAlimentari());

            row.createCell(16).setCellValue(terzaMedia.pagato());
            row.createCell(17).setCellValue(terzaMedia.importoPagamento().doubleValue());

            datiGenitore(row, 18, terzaMedia.registrato());
        }
    }

    private static void creaIntestazioneRagazzoTerzaMedia(Sheet sheet) {
        creaRigaIntestazione(sheet, new String[]{
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
        creaFoglio(workbook, "Ragazzi", ragazzi,
                ControllerEsportazioneMassiva::creaIntestazioneRagazzo, ControllerEsportazioneMassiva::popolaFoglioRagazzi);
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
            affiliazioneEPresenza(row, 6, ragazzo.parrocchia(), ragazzo.circolo(), ragazzo.nTessera(),
                    ragazzo.settimanePresenza(), ragazzo.presenza());

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

            datiGenitore(row, 21, ragazzo.registrato());
        }
    }

    private static void creaIntestazioneRagazzo(Sheet sheet) {
        creaRigaIntestazione(sheet,
                new String[]{
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

    private static <T> void creaFoglio(Workbook workbook, String name, List<T> dati,
                                       Consumer<Sheet> header, BiConsumer<List<T>, Sheet> populate) {
        Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(name));
        header.accept(sheet);
        populate.accept(dati, sheet);
        autoSizeColumns(sheet);
        logger.info("Creato foglio " + name);
    }

    private static void affiliazioneEPresenza(Row row, int firstColumn, Parrocchia parrocchia, Circolo circolo,
                                              String tessera, List<Integer> settimane, String presenza) {
        row.createCell(firstColumn).setCellValue(parrocchia.getNome() + ", " + parrocchia.getLuogo());
        row.createCell(firstColumn + 1).setCellValue(circolo.getNome() + ", " + circolo.getLuogo());
        row.createCell(firstColumn + 2).setCellValue(tessera);
        row.createCell(firstColumn + 3).setCellValue(settimane.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        row.createCell(firstColumn + 4).setCellValue(presenza);
    }

    private static void datiGenitore(Row row, int firstColumn, Registrato registrato) {
        row.createCell(firstColumn).setCellValue(registrato.getNome());
        row.createCell(firstColumn + 1).setCellValue(registrato.getCognome());
        row.createCell(firstColumn + 2).setCellValue(registrato.getMail());
        row.createCell(firstColumn + 3).setCellValue(registrato.getTelefono());
        row.createCell(firstColumn + 4).setCellValue(registrato.getVia() + " " + registrato.getCivico() + ", " + registrato.getLocalita());
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

}
