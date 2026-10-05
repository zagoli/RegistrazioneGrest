package Controller;

import DAOManager.DAOMan;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerEliminaCU implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.contattoUrgenzeDAO.delete(id);
            return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaCU.class.getName());
        }
        return template;
    }

}
