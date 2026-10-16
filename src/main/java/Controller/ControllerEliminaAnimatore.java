package Controller;

import Controller.Utils.IscrizioniUtils;
import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerEliminaAnimatore implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerEliminaAnimatore.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.animatoreDAO.delete(id);
            logger.info("Animatore con id {} eliminato", id);
            return IscrizioniUtils.getPaginaRedirectDopoModifica(request, "an");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaAnimatore.class.getName());
        }
        return template;
    }

}
