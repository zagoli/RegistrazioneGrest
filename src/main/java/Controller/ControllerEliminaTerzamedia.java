package Controller;

import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerEliminaTerzamedia implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.terzamediaDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=ter");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaTerzamedia.class.getName());
        }
        return template;
    }

}
