package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
import Utility.DAOUtils;
import Utility.ConfigPropertyException;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerModificaUtente implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerModificaUtente.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Modifica account");
            int idUt = (int) request.getSession().getAttribute("idUtente");
            String mail = DAOUtils.normalizeText(request.getParameter("mail"));
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(mail)) {
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                logger.info("Modifica utente con id {}", r.getId());
                RequestFields.account(request, r);
                DAOMan.registratoDAO.update(r);
                logger.info("Utente con id {} modificato", r.getId());
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(mail)) {
                    template.addObject("INVALIDMAIL", true);
                }
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                template.addObject("registrato", r);
                template.setView("user/modificautente.html");
            }
        } catch (final RuntimeException | SQLException | IOException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaUtente.class.getName());
        }
        return template;
    }
}
