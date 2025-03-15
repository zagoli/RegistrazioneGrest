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

public class ControllerInserisciCU implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
            if (!request.getParameterMap().containsKey("nome")) {
                mv.setView("acccu/inseriscicu.html");
                mv.addObject("TITOLOPAGINA", "Inserisci contatto telefonico urgenze");
            } else {
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
            }
        } catch (final RuntimeException | SQLException | IOException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerInserisciCU.class.getName());
        }
        return mv;
    }

}
