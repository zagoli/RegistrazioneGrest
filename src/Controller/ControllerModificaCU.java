package Controller;

import DAOManager.DAOMan;
import Domain.ContattoUrgenze;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerModificaCU implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Modifica contatto telefonico urgenze");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idCU = Integer.parseInt(request.getParameter("id"));
        try {
            if (!request.getParameterMap().containsKey("nome")) {
                mv.setView("acccu/modificacu.html");
                ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
                mv.addObject("contatto", cu);
            } else {
                ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
                cu.setNome(request.getParameter("nome"));
                cu.setCognome(request.getParameter("cognome"));
                cu.setCellulare(request.getParameter("cellulare"));
                cu.setFisso(request.getParameter("fisso"));
                cu.setRelazione(request.getParameter("relazione"));
                DAOMan.contattoUrgenzeDAO.update(cu);
                response.sendRedirect("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (NullPointerException | IOException | SQLException ex) {

            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerInserisciAccompagnatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
