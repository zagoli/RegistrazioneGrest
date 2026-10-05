package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.ContattoUrgenze;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class ControllerDashboardAccCu implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Accompagnatori e Contatti telefonici Urgenze");
            template.setView("acccu/dashboardacccu.html");
            int idUtente = (int) request.getSession().getAttribute("idUtente");
            List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idUtente);
            if (!listAccompagnatore.isEmpty()) {
                template.addObject("accompagnatori", listAccompagnatore);
            }
            List<ContattoUrgenze> listContattoUrgenze = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idUtente);
            if (!listContattoUrgenze.isEmpty()) {
                template.addObject("contatti", listContattoUrgenze);
            }
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerDashboardAccCu.class.getName());
        }
        return template;
    }

}
