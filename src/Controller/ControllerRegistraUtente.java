package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ControllerRegistraUtente implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            if (request.getSession().getAttribute("tipoUtente") != null && request.getSession().getAttribute("tipoUtente").equals(0)) {
                mv.setView("ammseg/registrasegretario.html");
            } else {
                mv.setView("user/registrautente.html");
            }
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
            } else {
                mv.addObject("TITOLOPAGINA", "Registrazione Utente");
                mv.addObject("DONE", false);
            }
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerRegistraUtente.class.getName());
        }
        return mv;
    }

}
