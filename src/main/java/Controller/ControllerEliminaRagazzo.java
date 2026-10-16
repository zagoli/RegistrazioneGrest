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

public class ControllerEliminaRagazzo implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerEliminaRagazzo.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.ragazzoDAO.delete(id);
            logger.info("Ragazzo con id {} eliminato", id);
            return IscrizioniUtils.getPaginaRedirectDopoModifica(request, "rag");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaRagazzo.class.getName());
        }
        return template;
    }

}
