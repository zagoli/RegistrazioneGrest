package Controller;

import DAOManager.DAOMan;
import Domain.ContattoUrgenze;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerModificaCU implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Modifica contatto telefonico urgenze");
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            int idCU = Integer.parseInt(request.getParameter("id"));
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
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerModificaCU.class.getName());
        }
        return mv;
    }

}
