package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerModificaPassword implements ControllerInterface {

    Logger logger = LoggerFactory.getLogger(ControllerModificaPassword.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica la password");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            if (!request.getParameterMap().containsKey("password")) {
                template.setView("user/modificapassword.html");
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
            template = Utils.getErrorPageAndLogException(e, ControllerModificaPassword.class.getName());
        }
        return template;
    }

}
