package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerEliminaCU implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.contattoUrgenzeDAO.delete(id);
            response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerEliminaCU.class.getName());
        }
        return mv;
    }

}
