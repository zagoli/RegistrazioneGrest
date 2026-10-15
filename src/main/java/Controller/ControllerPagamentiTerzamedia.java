package Controller;

import DAOManager.DAOMan;
import Domain.DatiPagamento;
import Domain.IscrittoPagamento;
import Domain.PagamentoRiepilogo;
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
import java.util.ArrayList;
import java.util.List;

public class ControllerPagamentiTerzamedia implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerPagamentiTerzamedia.class);

    private static void renderPage(FreemarkerTemplate template) throws SQLException, ConfigPropertyException, IOException {
        List<DatiPagamento<IscrittoPagamento, PagamentoRiepilogo>> iscritti = DAOMan.terzamediaDAO.findAllIscrittoConPagamento();
        List<Object[]> dati = new ArrayList<>(iscritti.size());
        if (!iscritti.isEmpty()) {
            for (DatiPagamento<IscrittoPagamento, PagamentoRiepilogo> dato : iscritti) {
                PagamentoRiepilogo pagamento = dato.getPagamento();
                dati.add(new Object[]{dato.getIscritto(), pagamento != null,
                        pagamento != null ? pagamento : calcolaQuota(dato.getIscritto().getLocalita(), dato.getSettimane())});
            }
            template.addObject("terzamedia", dati);
        }
        template.setView("ammseg/gestiscipagamentiterzamedia.html");
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
                var quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                var idTerzamedia = Integer.parseInt(request.getParameter("addPagamento"));
                var idUt = (int) request.getSession().getAttribute("idUtente");
                var ordineArrivo = Integer.parseInt(request.getParameter("ordineArrivo"));
                logger.info("Inserimento pagamento terzamedia richiesto per ragazzo con id {}, ordine arrivo {}, quota {}", idTerzamedia, ordineArrivo, quota);
                boolean result = true;
                String duplicateMessage = "Pagamento già presente per il ragazzo di terza media, o ordine arrivo " + ordineArrivo + " già inserito.";
                try {
                    DAOMan.pagamentoTerzamediaDAO.insert(ordineArrivo, quota, idTerzamedia, idUt);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505")) {
                        logger.warn(duplicateMessage);
                        template.addObject("errorePagamento", duplicateMessage);
                        renderPage(template);
                        result = false;
                    } else {
                        throw e;
                    }
                }
                if (result) {
                    logger.info("Pagamento di {} euro aggiunto per il ragazzo di terza media con id {}. Ordine arrivo: {}", quota, idTerzamedia, ordineArrivo);
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
