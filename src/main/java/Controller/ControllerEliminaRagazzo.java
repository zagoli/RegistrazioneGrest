package Controller;

import DAOManager.DAOMan;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerEliminaRagazzo implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.ragazzoDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=rag");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaRagazzo.class.getName());
        }
        return template;
    }

}
