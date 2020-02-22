package Controller;

import DAOManager.DAOMan;
import Domain.ContattoUrgenze;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerInserisciCU implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        if (!request.getParameterMap().containsKey("nome")) {
            mv.setView("acccu/inseriscicu.html");
            mv.addObject("TITOLOPAGINA", "Inserisci contatto telefonico urgenze");
        } else {
            try {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                ContattoUrgenze cu = new ContattoUrgenze();
                cu.setNome(request.getParameter("nome"));
                cu.setCognome(request.getParameter("cognome"));
                cu.setRelazione(request.getParameter("relazione"));
                cu.setCellulare(request.getParameter("cellulare"));
                cu.setFisso(request.getParameter("fisso"));
                cu.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.contattoUrgenzeDAO.insert(cu);
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
