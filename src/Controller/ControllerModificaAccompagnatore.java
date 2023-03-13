package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerModificaAccompagnatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
        if (!request.getParameterMap().containsKey("nome")) {
            try {
                mv.setView("acccu/modificaaccompagnatore.html");
                mv.addObject("TITOLOPAGINA", "Modifica accompagnatore");
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                mv.addObject("accompagnatore", a);
            } catch (NullPointerException | SQLException ex) {
                mv.setView("errore.hmtl");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerModificaAccompagnatore.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                DAOMan.accompagnatoreDAO.update(a);
                response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
            } catch (NullPointerException | IOException | SQLException ex) {

                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerInserisciAccompagnatore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
