package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerModificaAccompagnatore implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerModificaAccompagnatore.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/modificaaccompagnatore.html");
                template.setTitoloPagina("Modifica accompagnatore");
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                template.addObject("accompagnatore", a);
            } else {
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                logger.info("Modifica accompagnatore {} {} con id {}", a.getNome(), a.getCognome(), idAccompagnatore);
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                DAOMan.accompagnatoreDAO.update(a);
                logger.info("Accompagnatore con id {} salvato come {} {}", idAccompagnatore, a.getNome(), a.getCognome());
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaAccompagnatore.class.getName());
        }
        return template;
    }

}
