package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerModificaAccompagnatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
            int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
            if (!request.getParameterMap().containsKey("nome")) {
                mv.setView("acccu/modificaaccompagnatore.html");
                mv.addObject("TITOLOPAGINA", "Modifica accompagnatore");
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                mv.addObject("accompagnatore", a);
            } else {
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                DAOMan.accompagnatoreDAO.update(a);
                response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerModificaAccompagnatore.class.getName());
        }
        return mv;
    }

}
