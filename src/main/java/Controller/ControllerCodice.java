package Controller;

import DAOManager.DAOMan;
import Domain.CodiceSbloccoIscrizione;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ControllerCodice implements ControllerInterface {

    private final static Logger logger = LoggerFactory.getLogger(ControllerCodice.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate("user/rispostacodice.json");
        boolean success = false;
        try {
            if (request.getParameter("scope").equals("verifica")) {
                String codiceString = request.getParameter("codice");
                logger.info("Verifica codice sblocco iscrizione {}", codiceString);
                CodiceSbloccoIscrizione codice = DAOMan.codiceSbloccoIscrizioneDAO.findByCodice(codiceString);
                if (codice != null && codice.getUtilizzato() == 0) {
                    Integer idoInteger = (Integer) request.getSession().getAttribute("idUtente");
                    Short id = idoInteger.shortValue();
                    codice.setUtilizzato(id);
                    codice.setDataUtilizzo(new Timestamp(System.currentTimeMillis()));
                    DAOMan.codiceSbloccoIscrizioneDAO.update(codice);
                    success = true;
                    logger.info("Iscrizione sbloccata con successo.");
                } else {
                    logger.warn("Codice non trovato o già utilizzato.");
                }
            }
        } catch (final RuntimeException | SQLException e) {
            Utils.logException(e, ControllerCodice.class.getName());
        } finally {
            template.addObject("result", success);
        }
        return template;
    }

}
