package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerRegistraAttGen implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            mv.addObject("TITOLOPAGINA", "errore");
            RelCollabora rc = new RelCollabora();
            int idUt = (int) request.getSession().getAttribute("idUtente");
            int idAttivita = Integer.parseInt(request.getParameter("attivita"));
            String data = request.getParameter("data");
            if (data != null) {
                rc.setData(data);
            }
            rc.setRegistratoId(idUt);
            rc.setAttivitaGenId(idAttivita);
            DAOMan.relCollaboraDAO.insert(rc);
            response.sendRedirect("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerRegistraAttGen.class.getName());
        }
        return mv;
    }
}
