package Controller;

import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerEliminaAnimatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.animatoreDAO.delete(id);
            return IscrizioneSupport.afterChange(request, "an");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaAnimatore.class.getName());
        }
        return template;
    }

}
