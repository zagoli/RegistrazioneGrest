package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Response.FreemarkerTemplate;
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

public class ControllerRegistraUtente implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerRegistraUtente.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            if (request.getSession().getAttribute("tipoUtente") != null && request.getSession().getAttribute("tipoUtente").equals(0)) {
                template.setView("ammseg/registrasegretario.html");
            } else {
                template.setView("user/registrautente.html");
            }
            if (request.getParameterMap().containsKey("nome")) {
                if (Checker.checkMail(DAOUtils.normalizeText(request.getParameter("mail")))) {
                    Registrato r = new Registrato();
                    r.setPassword(request.getParameter("password"));
                    RequestFields.account(request, r);
                    if (request.getSession().getAttribute("tipoUtente") != null && request.getSession().getAttribute("tipoUtente").equals(0)) {
                        if (request.getParameterMap().containsKey("consentiModifica")) {
                            r.setTipoUt(1);
                        } else {
                            r.setTipoUt(2);
                        }
                    } else {
                        r.setTipoUt(3);
                    }
                    DAOMan.registratoDAO.insert(r);
                    logger.info("Utente {} {} registrato. Tipo utente {}", r.getNome(), r.getCognome(), r.getTipoUt());
                    template.setTitoloPagina("Utente Registrato");
                    template.addObject("DONE", true);
                } else {
                    template.addObject("INVALIDMAIL", true);
                    template.setTitoloPagina("Registrazione Utente");
                    template.addObject("DONE", false);
                }
            } else {
                template.setTitoloPagina("Registrazione Utente");
                template.addObject("DONE", false);
            }
        } catch (final RuntimeException | SQLException | ConfigPropertyException | IOException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraUtente.class.getName());
        }
        return template;
    }

}
