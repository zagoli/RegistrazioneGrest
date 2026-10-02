package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ControllerResult;
import ModelAndView.ModelAndView;
import ModelAndView.RedirectResult;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerModificaPassword implements ControllerInterface {

    Logger logger = LoggerFactory.getLogger(ControllerModificaPassword.class);

    @Override
    public ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Modifica la password");
            mv.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            if (!request.getParameterMap().containsKey("password")) {
                mv.setView("user/modificapassword.html");
            } else {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                String password = request.getParameter("password");
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                r.setPassword(password);
                DAOMan.registratoDAO.updatePassword(r);
                logger.info("Registrato {} ha modificato la password", r.getMail());
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            }
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerModificaPassword.class.getName());
        }
        return mv;
    }

}
