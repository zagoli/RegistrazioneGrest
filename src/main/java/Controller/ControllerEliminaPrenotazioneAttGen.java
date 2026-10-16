package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerEliminaPrenotazioneAttGen implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerEliminaPrenotazioneAttGen.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            RelCollabora toDeleteCollabora = DAOMan.relCollaboraDAO.findById(id);
            DAOMan.relCollaboraDAO.delete(toDeleteCollabora);
            logger.info("Collaborazione genitore con id {} eliminata", id);
            return new RedirectResult("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaPrenotazioneAttGen.class.getName());
        }
        return template;
    }

}
