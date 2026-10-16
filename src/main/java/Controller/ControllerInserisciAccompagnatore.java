package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerInserisciAccompagnatore implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerInserisciAccompagnatore.class);

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
                logger.info("Accompagnatore {} {} inserito con successo per registrato con id {}", a.getNome(), a.getCognome(), idUt);
                return new RedirectResult("/RegistrazioneGrest/App/AccompagnatoriContatti");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerInserisciAccompagnatore.class.getName());
        }
        return template;
    }

}
