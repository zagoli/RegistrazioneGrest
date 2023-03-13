package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerEliminaRagazzo implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Ragazzo eliminato");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DAOMan.ragazzoDAO.delete(id);
            if (request.getSession().getAttribute("tipoUtente").equals(3)){
                    response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
                } else {
                    response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=rag");
                }
        } catch (NullPointerException | IOException | NumberFormatException | SQLException ex) {

            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerEliminaRagazzo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }
    
}
