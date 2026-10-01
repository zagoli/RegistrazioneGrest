package Controller;

import DAOManager.DAOMan;
import ModelAndView.ControllerResult;
import ModelAndView.ModelAndView;
import ModelAndView.RedirectResult;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ControllerEliminaCU implements ControllerInterface {

    @Override
    public ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.contattoUrgenzeDAO.delete(id);
            return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerEliminaCU.class.getName());
        }
        return mv;
    }

}
