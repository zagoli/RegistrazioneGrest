package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerModificaAccompagnatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/modificaaccompagnatore.html");
                template.addObject("TITOLOPAGINA", "Modifica accompagnatore");
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                template.addObject("accompagnatore", a);
            } else {
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                DAOMan.accompagnatoreDAO.update(a);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaAccompagnatore.class.getName());
        }
        return template;
    }

}
