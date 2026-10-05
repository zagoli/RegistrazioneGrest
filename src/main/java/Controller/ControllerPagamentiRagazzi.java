package Controller;

import DAOManager.DAOMan;
import Domain.DatiPagamento;
import Domain.PagamentoRiepilogo;
import Domain.Ragazzo;
import Domain.RagazzoPagamento;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerPagamentiRagazzi implements ControllerInterface {

    private final static Logger logger = LoggerFactory.getLogger(ControllerPagamentiRagazzi.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
            if (request.getParameterMap().isEmpty()) {
                renderPage(template);
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                float quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                int idRagazzo = Integer.parseInt(request.getParameter("addPagamento"));
                int idUt = (int) request.getSession().getAttribute("idUtente");
                int ordineArrivo = Integer.parseInt(request.getParameter("ordineArrivo"));
                logger.info("Inserimento pagamento ragazzo richiesto per ragazzo con id {}, ordine arrivo {}, quota {}", idRagazzo, ordineArrivo, quota);
                try {
                    DAOMan.pagamentoDAO.insert(ordineArrivo, quota, idRagazzo, idUt);
                    logger.info("Pagamento di {} euro aggiunto per il ragazzo con id {}. Ordine arrivo: {}", quota, idRagazzo, request.getParameter("ordineArrivo"));
                    return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamenti");
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505")) {
                        String message = "Pagamento già presente per il ragazzo, o ordine arrivo " + ordineArrivo + " già inserito.";
                        logger.warn(message);
                        template.addObject("errorePagamento", message);
                        renderPage(template);
                    } else {
                        throw e;
                    }
                }
            } else if (request.getParameterMap().containsKey("deletePagamento")) {
                int id = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoDAO.delete(id);
                logger.info("Pagamento con id {} eliminato.", id);
                return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamenti");
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerPagamentiRagazzi.class.getName());
        }
        return template;
    }

    private static void renderPage(FreemarkerTemplate template) throws SQLException, ConfigPropertyException, IOException {
        List<DatiPagamento<RagazzoPagamento, PagamentoRiepilogo>> iscritti = DAOMan.ragazzoDAO.findAllConPagamenti();
        List<Object[]> datiRagazzi = new ArrayList<>(iscritti.size());
        if (!iscritti.isEmpty()) {
            for (DatiPagamento<RagazzoPagamento, PagamentoRiepilogo> dato : iscritti) {
                PagamentoRiepilogo pagamento = dato.getPagamento();
                datiRagazzi.add(new Object[]{dato.getIscritto(), pagamento != null, pagamento != null ? pagamento : calcolaQuota(dato.getIscritto(), dato.getSettimane())});
            }
            template.addObject("ragazzi", datiRagazzi);
        }
        template.setView("ammseg/gestiscipagamenti.html");
    }

    protected static float calcolaQuota(Ragazzo r) throws SQLException, ConfigPropertyException, IOException {
        return calcolaQuota(r.getFratelloIscritto(), r.getMensa(), r.getEntrataAnticipata(), r.getRegistrato().getLocalita(), DAOMan.relPresenzaRagDAO.findByRagazzoId(r.getId()).size());
    }

    private static float calcolaQuota(RagazzoPagamento r, int nSettimane) throws ConfigPropertyException, IOException {
        return calcolaQuota(r.isFratelloIscritto(), r.isMensa(), r.isEntrataAnticipata(), r.getLocalita(), nSettimane);
    }

    private static float calcolaQuota(boolean fratelloIscritto, boolean mensa, boolean entrataAnticipata, String localita, int nSettimane) throws ConfigPropertyException, IOException {
        assert nSettimane > 0;
        int[][][] tabellaQuotaBase = new int[][][]{
                // normale
                {   //senza mensa   |   con mensa
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_MENSA_RAGAZZI"))},    // una settimana
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_MENSA_RAGAZZI"))},   // due settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_MENSA_RAGAZZI"))},   // tre settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_MENSA_RAGAZZI"))}    // quattro settimane
                },
                // fratello iscritto
                {   //senza mensa   |   con mensa
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_FRATELLI_MENSA_RAGAZZI"))},    // una settimana
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_FRATELLI_MENSA_RAGAZZI"))},   // due settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_FRATELLI_MENSA_RAGAZZI"))},   // tre settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_FRATELLI_MENSA_RAGAZZI"))}    // quattro settimane
                }};
        int supplementoFuoriComune = Integer.parseInt(ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_RAGAZZI"));
        int supplementoEntrataAnticipataRagazzi = Integer.parseInt(ConfigProperties.getProperty("SUPPLEMENTO_ENTRATA_ANTICIPATA_RAGAZZI"));
        return tabellaQuotaBase[fratelloIscritto ? 1 : 0][nSettimane - 1][mensa ? 1 : 0] + nSettimane * (entrataAnticipata ? supplementoEntrataAnticipataRagazzi : 0) + nSettimane * (Checker.checkIsFromPescantina(localita) ? 0 : supplementoFuoriComune);
    }

}
