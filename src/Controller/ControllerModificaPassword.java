package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerModificaPassword implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Modifica la password");
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            if (!request.getParameterMap().containsKey("password")) {
                mv.setView("user/modificapassword.html");
            } else {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                String password = request.getParameter("password");
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                r.setPassword(password);
                DAOMan.registratoDAO.updatePassword(r);
                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerModificaPassword.class.getName());
        }
        return mv;
    }

}
