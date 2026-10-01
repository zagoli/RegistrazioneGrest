package Controller;

import DAOManager.DAOMan;
import ModelAndView.ControllerResult;
import ModelAndView.ModelAndView;
import ModelAndView.RedirectResult;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ControllerEliminaAnimatore implements ControllerInterface {

    @Override
    public ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.animatoreDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=an");
            }
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerEliminaAnimatore.class.getName());
        }
        return mv;
    }

}
