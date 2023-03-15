package Controller;

import DAOManager.DAOMan;
import Domain.AttivitaGen;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerDashboardAttGen implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Attività genitori");
            mv.setView("user/dashboardattgen.html");
            int idUt = (int) request.getSession().getAttribute("idUtente");
            Map<AttivitaGen, Object[]> mapAttivitaGen = new HashMap<>();
            List<RelCollabora> listRelCollabora = DAOMan.relCollaboraDAO.findByRegistratoId(idUt);
            if (!listRelCollabora.isEmpty()) {
                for (RelCollabora rc : listRelCollabora) {
                    AttivitaGen ag = DAOMan.attivitaGenDAO.findById(rc.getAttivitaGenId());
                    Object[] o = {rc.getData(), rc.getId()};
                    mapAttivitaGen.put(ag, o);
                }
                mv.addObject("attivita", mapAttivitaGen);
            }
            mv.addObject("allattivita", DAOMan.attivitaGenDAO.findAll());
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerDashboardAttGen.class.getName());
        }
        return mv;
    }

}
