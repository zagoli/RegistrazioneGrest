package Controller;

import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerDashboardAccCu implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Accompagnatori e Contatti telefonici Urgenze");
            template.setView("acccu/dashboardacccu.html");
            int idUtente = (int) request.getSession().getAttribute("idUtente");
            DettaglioSupport.accompagnatori(template, idUtente);
            DettaglioSupport.emergencyContacts(template, idUtente, "contatti");
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerDashboardAccCu.class.getName());
        }
        return template;
    }

}
