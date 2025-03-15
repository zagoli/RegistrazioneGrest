package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerEliminaAnimatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.animatoreDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
            } else {
                response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=an");
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerEliminaAnimatore.class.getName());
        }
        return mv;
    }

}
