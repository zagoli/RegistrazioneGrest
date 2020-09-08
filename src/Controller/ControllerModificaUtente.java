package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerModificaUtente implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Modifica account");
        int idUt = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        try {
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                r.setMail(request.getParameter("mail"));
                r.setNome(request.getParameter("nome"));
                r.setCognome(request.getParameter("cognome"));
                r.setTelefono(request.getParameter("telefono"));
                r.setLocalita(request.getParameter("localita"));
                r.setVia(request.getParameter("via"));
                r.setCivico(request.getParameter("civico"));
                DAOMan.registratoDAO.update(r);
                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
            } else {
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    mv.addObject("INVALIDMAIL", true);
                }
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                mv.addObject("registrato", r);
                mv.setView("user/modificautente.html");
            }
        } catch (UnirestException | IOException | NullPointerException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerModificaUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
}
