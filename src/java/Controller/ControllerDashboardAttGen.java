package Controller;

import DAOManager.DAOMan;
import Domain.AttivitaGen;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerDashboardAttGen implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Attivit&agrave; genitori");
        mv.setView("user/dashboardattgen.html");
        try {
            int idUt = (int) request.getSession().getAttribute("idUtente");
            Map<AttivitaGen,Object[]> mapAttivitaGen = new HashMap<>();
            List<RelCollabora> listRelCollabora = DAOMan.relCollaboraDAO.findByRegistratoId(idUt);
            for (RelCollabora rc : listRelCollabora) {
                AttivitaGen ag = DAOMan.attivitaGenDAO.findById(rc.getAttivitaGenId());
                Object[] o = {rc.getData(),rc.getId()};
                mapAttivitaGen.put(ag, o);
            }
            mv.addObject("attivita", mapAttivitaGen);
            mv.addObject("allattivita", DAOMan.attivitaGenDAO.findAll());
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerDashboardAttGen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
