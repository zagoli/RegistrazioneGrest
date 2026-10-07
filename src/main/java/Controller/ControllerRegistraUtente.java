package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Checker;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerRegistraUtente implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            if (request.getSession().getAttribute("tipoUtente") != null && request.getSession().getAttribute("tipoUtente").equals(0)) {
                template.setView("ammseg/registrasegretario.html");
            } else {
                template.setView("user/registrautente.html");
            }
            if (request.getParameterMap().containsKey("nome")) {
                if (Checker.checkMail(request.getParameter("mail"))) {
                    Registrato r = new Registrato();
                    r.setMail(request.getParameter("mail"));
                    r.setPassword(request.getParameter("password"));
                    r.setNome(request.getParameter("nome"));
                    r.setCognome(request.getParameter("cognome"));
                    r.setTelefono(request.getParameter("telefono"));
                    r.setLocalita(request.getParameter("localita"));
                    r.setVia(request.getParameter("via"));
                    r.setCivico(request.getParameter("civico"));
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
                    template.addObject("TITOLOPAGINA", "Utente Registrato");
                    template.addObject("DONE", true);
                } else {
                    template.addObject("INVALIDMAIL", true);
                    template.addObject("TITOLOPAGINA", "Registrazione Utente");
                    template.addObject("DONE", false);
                }
            } else {
                template.addObject("TITOLOPAGINA", "Registrazione Utente");
                template.addObject("DONE", false);
            }
        } catch (final RuntimeException | SQLException | ConfigPropertyException | IOException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraUtente.class.getName());
        }
        return template;
    }

}
