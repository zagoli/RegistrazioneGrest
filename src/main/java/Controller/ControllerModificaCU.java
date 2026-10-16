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

public class ControllerModificaCU implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerModificaCU.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Modifica contatto telefonico urgenze");
            int idCU = Integer.parseInt(request.getParameter("id"));
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/modificacu.html");
                ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
                template.addObject("contatto", cu);
            } else {
                ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
                logger.info("Modifica contatto telefonico urgenze {} {} con id {}", cu.getNome(), cu.getCognome(), idCU);
                RequestFields.contatto(request, cu);
                DAOMan.contattoUrgenzeDAO.update(cu);
                logger.info("Contatto telefonico urgenze con id {} modificato", idCU);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaCU.class.getName());
        }
        return template;
    }

}
