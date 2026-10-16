package Controller;

import DAOManager.DAOMan;
import Domain.ContattoUrgenze;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.RequestFields;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerInserisciCU implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerInserisciCU.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/inseriscicu.html");
                template.setTitoloPagina("Inserisci contatto telefonico urgenze");
            } else {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                ContattoUrgenze cu = new ContattoUrgenze();
                RequestFields.contatto(request, cu);
                cu.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.contattoUrgenzeDAO.insert(cu);
                logger.info("Contatto telefonico urgenze {} {} inserito con successo per registrato con id {}", cu.getNome(), cu.getCognome(), idUt);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerInserisciCU.class.getName());
        }
        return template;
    }

}
