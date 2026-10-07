package Controller;

import DAOManager.DAOMan;
import Domain.ContattoUrgenze;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerModificaCU implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica contatto telefonico urgenze");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            int idCU = Integer.parseInt(request.getParameter("id"));
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/modificacu.html");
                ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
                template.addObject("contatto", cu);
            } else {
                ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
                cu.setNome(request.getParameter("nome"));
                cu.setCognome(request.getParameter("cognome"));
                cu.setCellulare(request.getParameter("cellulare"));
                cu.setFisso(request.getParameter("fisso"));
                cu.setRelazione(request.getParameter("relazione"));
                DAOMan.contattoUrgenzeDAO.update(cu);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaCU.class.getName());
        }
        return template;
    }

}
