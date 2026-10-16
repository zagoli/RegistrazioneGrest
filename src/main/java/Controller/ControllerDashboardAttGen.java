package Controller;

import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Collections;

public class ControllerDashboardAttGen implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Attività genitori");
            template.setView("user/dashboardattgen.html");
            int idUt = (int) request.getSession().getAttribute("idUtente");
            var collaborazioneGen = DAOMan.attivitaGenDAO.findCollaborazioniByRegistratoId(idUt);
            if (!collaborazioneGen.isEmpty()) {
                Collections.sort(collaborazioneGen);
                template.addObject("attivita", collaborazioneGen);
            }
            template.addObject("allattivita", DAOMan.attivitaGenDAO.findAll());
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerDashboardAttGen.class.getName());
        }
        return template;
    }

}
