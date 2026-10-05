package Controller;

import DAOManager.DAOMan;
import Domain.AttivitaGen;
import Domain.RelCollabora;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerDashboardAttGen implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Attività genitori");
            template.setView("user/dashboardattgen.html");
            int idUt = (int) request.getSession().getAttribute("idUtente");
            Map<AttivitaGen, Object[]> mapAttivitaGen = new HashMap<>();
            List<RelCollabora> listRelCollabora = DAOMan.relCollaboraDAO.findByRegistratoId(idUt);
            if (!listRelCollabora.isEmpty()) {
                for (RelCollabora rc : listRelCollabora) {
                    AttivitaGen ag = DAOMan.attivitaGenDAO.findById(rc.getAttivitaGenId());
                    Object[] o = {rc.getData(), rc.getId()};
                    mapAttivitaGen.put(ag, o);
                }
                template.addObject("attivita", mapAttivitaGen);
            }
            template.addObject("allattivita", DAOMan.attivitaGenDAO.findAll());
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerDashboardAttGen.class.getName());
        }
        return template;
    }

}
