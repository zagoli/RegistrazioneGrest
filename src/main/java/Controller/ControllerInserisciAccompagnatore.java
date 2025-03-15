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

public class ControllerInserisciAccompagnatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
            if (!request.getParameterMap().containsKey("nome")) {
                mv.setView("acccu/inserisciaccompagnatore.html");
                mv.addObject("TITOLOPAGINA", "Inserisci accompagnatore");
            } else {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                Accompagnatore a = new Accompagnatore();
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                a.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.accompagnatoreDAO.insert(a);
                response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerInserisciAccompagnatore.class.getName());
        }
        return mv;
    }

}
