package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
import Utility.ConfigPropertyException;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerModificaUtente implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica account");
            int idUt = (int) request.getSession().getAttribute("idUtente");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                Registrato r = DAOMan.registratoDAO.findById(idUt);
                r.setMail(request.getParameter("mail"));
                r.setNome(request.getParameter("nome"));
                r.setCognome(request.getParameter("cognome"));
                r.setTelefono(request.getParameter("telefono"));
                r.setLocalita(request.getParameter("localita"));
                r.setVia(request.getParameter("via"));
                r.setCivico(request.getParameter("civico"));
                DAOMan.registratoDAO.update(r);
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
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
