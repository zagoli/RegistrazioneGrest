package Controller;

import DAOManager.DAOMan;
import Domain.TerzaMedia;
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

public class ControllerPagamentiTerzamedia implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerPagamentiTerzamedia.class);

    private static void renderPage(FreemarkerTemplate template) throws SQLException, ConfigPropertyException, IOException {
        PagamentiSupport.render(template, DAOMan.terzamediaDAO.findAllIscrittoConPagamento(),
                "terzamedia", "ammseg/gestiscipagamentiterzamedia.html",
                (iscritto, settimane) -> calcolaQuota(iscritto.getLocalita(), settimane));
    }

    protected static float calcolaQuota(TerzaMedia t) throws SQLException, ConfigPropertyException, IOException {
        return calcolaQuota(t.getRegistrato().getLocalita(), DAOMan.relPresenzaTerDAO.findByTerzamediaId(t.getId()).size());
    }

    private static float calcolaQuota(String localita, int nSettimane) throws ConfigPropertyException, IOException {
        assert nSettimane > 0;
        int supplementoFuoriComune = Integer.parseInt(ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_TERZAMEDIA"));
        int[] tabellaQuotaBase = new int[]{
                Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_TERZAMEDIA")),
                Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_TERZAMEDIA")),
                Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_TERZAMEDIA")),
                Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_TERZAMEDIA"))
        };
        return tabellaQuotaBase[nSettimane - 1] +
                nSettimane * (Checker.checkIsFromPescantina(localita) ? 0 : supplementoFuoriComune);
    }

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Gestisci pagamenti terzamedia");
            if (request.getParameterMap().isEmpty()) {
                renderPage(template);
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                PagamentiSupport.PaymentRequest payment = PagamentiSupport.PaymentRequest.read(request);
                float quota = payment.quota();
                int idTerzamedia = payment.id();
                int ordineArrivo = payment.ordineArrivo();
                logger.info("Inserimento pagamento terzamedia richiesto per ragazzo con id {}, ordine arrivo {}, quota {}", idTerzamedia, ordineArrivo, quota);
                if (PagamentiSupport.insert(template,
                        () -> DAOMan.pagamentoTerzamediaDAO.insert(ordineArrivo, quota, idTerzamedia, payment.userId()),
                        "Pagamento già presente per il ragazzo di terza media, o ordine arrivo " + ordineArrivo + " già inserito.",
                        logger, ControllerPagamentiTerzamedia::renderPage)) {
                    logger.info("Pagamento di {} euro aggiunto per il ragazzo di terza media con id {}. Ordine arrivo: {}", quota, idTerzamedia, request.getParameter("ordineArrivo"));
                    return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamentiTerzamedia");
                }
            } else if (request.getParameterMap().containsKey("deletePagamento")) {
                int id = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoTerzamediaDAO.delete(id);
                logger.info("Pagamento con id {} eliminato.", id);
                return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamentiTerzamedia");
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerPagamentiTerzamedia.class.getName());
        }
        return template;
    }

}
