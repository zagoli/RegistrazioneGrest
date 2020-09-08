package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerInserisciAccompagnatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        if (!request.getParameterMap().containsKey("nome")) {
            mv.setView("acccu/inserisciaccompagnatore.html");
            mv.addObject("TITOLOPAGINA", "Inserisci accompagnatore");
        } else {
            try {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                Accompagnatore a = new Accompagnatore();
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                a.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.accompagnatoreDAO.insert(a);
                response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
            } catch (NullPointerException | IOException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerInserisciAccompagnatore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
