package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerRegistraAttGen implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerRegistraAttGen.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "errore");
            RelCollabora rc = new RelCollabora();
            int idUt = (int) request.getSession().getAttribute("idUtente");
            int idAttivita = Integer.parseInt(request.getParameter("attivita"));
            RequestFields.optionalText(request, "data", rc::setData);
            rc.setRegistratoId(idUt);
            rc.setAttivitaGenId(idAttivita);
            DAOMan.relCollaboraDAO.insert(rc);
            logger.info("Collaborazione genitore con id {} aggiunta a registrato {}", idAttivita, idUt);
            return new RedirectResult("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraAttGen.class.getName());
        }
        return template;
    }
}
