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

public class ControllerEliminaTerzamedia implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerEliminaTerzamedia.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.terzamediaDAO.delete(id);
            logger.info("Terzamedia con id {} eliminato", id);
            return IscrizioniUtils.getPaginaRedirectDopoModifica(request, "ter");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaTerzamedia.class.getName());
        }
        return template;
    }

}
