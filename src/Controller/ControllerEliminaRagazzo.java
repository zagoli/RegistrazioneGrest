package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerEliminaRagazzo implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.ragazzoDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
            } else {
                response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=rag");
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            Utils.getErrorPageAndLogException(e, ControllerEliminaRagazzo.class.getName());
        }
        return mv;
    }

}
