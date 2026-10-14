package Controller;

import DAOManager.DAOMan;
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

public class ControllerPagamentiRagazzi implements ControllerInterface {

    private final static Logger logger = LoggerFactory.getLogger(ControllerPagamentiRagazzi.class);

    private static void renderPage(FreemarkerTemplate template) throws SQLException, ConfigPropertyException, IOException {
        PagamentiSupport.render(template, DAOMan.ragazzoDAO.findAllIscrittoConPagamento(),
                "ragazzi", "ammseg/gestiscipagamenti.html", ControllerPagamentiRagazzi::calcolaQuota);
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

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
            if (request.getParameterMap().isEmpty()) {
                renderPage(template);
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                PagamentiSupport.PaymentRequest payment = PagamentiSupport.PaymentRequest.read(request);
                float quota = payment.quota();
                int idRagazzo = payment.id();
                int ordineArrivo = payment.ordineArrivo();
                logger.info("Inserimento pagamento ragazzo richiesto per ragazzo con id {}, ordine arrivo {}, quota {}", idRagazzo, ordineArrivo, quota);
                if (PagamentiSupport.insert(template,
                        () -> DAOMan.pagamentoDAO.insert(ordineArrivo, quota, idRagazzo, payment.userId()),
                        "Pagamento già presente per il ragazzo, o ordine arrivo " + ordineArrivo + " già inserito.",
                        logger, ControllerPagamentiRagazzi::renderPage)) {
                    logger.info("Pagamento di {} euro aggiunto per il ragazzo con id {}. Ordine arrivo: {}", quota, idRagazzo, request.getParameter("ordineArrivo"));
                    return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamenti");
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

}
