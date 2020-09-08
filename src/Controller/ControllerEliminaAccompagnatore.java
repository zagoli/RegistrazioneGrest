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

public class ControllerEliminaAccompagnatore implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.accompagnatoreDAO.delete(id);
            response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
        } catch (NullPointerException | IOException | NumberFormatException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerEliminaRagazzo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }
    
}
