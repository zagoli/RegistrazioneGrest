package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.ContattoUrgenze;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerDashboardAccCu implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Accompagnatori e Contatti telefonici Urgenze");
        mv.setView("acccu/dashboardacccu.html");
        try {
            int idUtente = (int) request.getSession().getAttribute("idUtente");
            List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idUtente);
            if (!listAccompagnatore.isEmpty()) {
                mv.addObject("accompagnatori", listAccompagnatore);
            }
            List<ContattoUrgenze> listContattoUrgenze = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idUtente);
            if (!listContattoUrgenze.isEmpty()) {
                mv.addObject("contatti", listContattoUrgenze);
            }
        } catch (NullPointerException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
    
}
