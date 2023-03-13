package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerModificaPassword implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Modifica la password");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        if (!request.getParameterMap().containsKey("password")) {
            mv.setView("user/modificapassword.html");
        } else {
            try {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                String password = request.getParameter("password");
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                r.setPassword(password);
                DAOMan.registratoDAO.updatePassword(r);
                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");       
            } catch (NullPointerException | SQLException | IOException ex) {

                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerModificaPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
    
}
