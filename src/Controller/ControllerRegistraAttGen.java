package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRegistraAttGen implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "errore");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        try {
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
        } catch (NullPointerException | SQLException | IOException ex) {

            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerRegistraAttGen.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
}
