package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerEliminaTerzamedia implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Terzamedia eliminato");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.terzamediaDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)){
                    response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
                } else {
                    response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=ter");
                }
        } catch (NullPointerException | IOException | NumberFormatException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerEliminaTerzamedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }
    
}
