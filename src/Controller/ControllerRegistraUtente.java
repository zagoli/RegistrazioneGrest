package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRegistraUtente implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        if (request.getSession().getAttribute("tipoUtente") != null && request.getSession().getAttribute("tipoUtente").equals(0)) {
            mv.setView("ammseg/registrasegretario.html");
        } else {
            mv.setView("user/registrautente.html");
        }
        //richiesta di registrazione proveniente dal form, quindi contiene almeno un parametro, in questo caso il nome
        try {
            if (request.getParameterMap().containsKey("nome")) {
                if (Checker.checkMail(request.getParameter("mail"))) {
                    Registrato r = new Registrato();
                    r.setMail(request.getParameter("mail"));
                    String pswd = request.getParameter("password");
                    r.setPassword(pswd);
                    r.setNome(request.getParameter("nome"));
                    r.setCognome(request.getParameter("cognome"));
                    r.setTelefono(request.getParameter("telefono"));
                    r.setLocalita(request.getParameter("localita"));
                    r.setVia(request.getParameter("via"));
                    r.setCivico(request.getParameter("civico"));
                    if (request.getSession().getAttribute("tipoUtente") != null && request.getSession().getAttribute("tipoUtente").equals(0)) {
                        if (request.getParameterMap().containsKey("consentiModifica")) {
                            r.setTipoUt(1);
                        } else {
                            r.setTipoUt(2);
                        }
                    } else {
                        r.setTipoUt(3);                        
                    }
                    DAOMan.registratoDAO.insert(r);
                    mv.addObject("TITOLOPAGINA", "Utente Registrato");
                    mv.addObject("DONE", true);
                } else {
                    mv.addObject("INVALIDMAIL", true);
                    mv.addObject("TITOLOPAGINA", "Registrazione Utente");
                    mv.addObject("DONE", false);
                }
            } //se non ci sono parametri si rimanda al form
            else {
                mv.addObject("TITOLOPAGINA", "Registrazione Utente");
                mv.addObject("DONE", false);
            }
        } catch (NullPointerException | SQLException | UnirestException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            mv.addObject("TITOLOPAGINA", "Errore");
            Logger.getLogger(ControllerRegistraUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
