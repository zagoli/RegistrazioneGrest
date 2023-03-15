package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.ContattoUrgenze;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class ControllerDashboardAccCu implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Accompagnatori e Contatti telefonici Urgenze");
            mv.setView("acccu/dashboardacccu.html");
            int idUtente = (int) request.getSession().getAttribute("idUtente");
            List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idUtente);
            if (!listAccompagnatore.isEmpty()) {
                mv.addObject("accompagnatori", listAccompagnatore);
            }
            List<ContattoUrgenze> listContattoUrgenze = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idUtente);
            if (!listContattoUrgenze.isEmpty()) {
                mv.addObject("contatti", listContattoUrgenze);
            }
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerDashboardAccCu.class.getName());
        }
        return mv;
    }
    
}
