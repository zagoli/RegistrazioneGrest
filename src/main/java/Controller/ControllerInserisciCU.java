package Controller;

import DAOManager.DAOMan;
import Domain.ContattoUrgenze;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerInserisciCU implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/inseriscicu.html");
                template.addObject("TITOLOPAGINA", "Inserisci contatto telefonico urgenze");
            } else {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                ContattoUrgenze cu = new ContattoUrgenze();
                RequestFields.contatto(request, cu);
                cu.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.contattoUrgenzeDAO.insert(cu);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerInserisciCU.class.getName());
        }
        return template;
    }

}
