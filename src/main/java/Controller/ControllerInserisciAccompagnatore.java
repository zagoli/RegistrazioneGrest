package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerInserisciAccompagnatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("acccu/inserisciaccompagnatore.html");
                template.addObject("TITOLOPAGINA", "Inserisci accompagnatore");
            } else {
                int idUt = (int) request.getSession().getAttribute("idUtente");
                Accompagnatore a = new Accompagnatore();
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                a.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.accompagnatoreDAO.insert(a);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerInserisciAccompagnatore.class.getName());
        }
        return template;
    }

}
