package Controller;

import DAOManager.DAOMan;
import Domain.Laboratorio;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.sql.SQLException;
import java.util.List;

public class ControllerGestisciLaboratori implements ControllerInterface {
    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate("ammseg/gestiscilaboratori.html");

        try {
            if (request.getParameterMap().isEmpty()) {
                template.addObject("TITOLOPAGINA", "Gestisci laboratori");
                template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
                List<Laboratorio> laboratori = DAOMan.laboratorioDAO.findAll();

            } else if (request.getParameterMap().containsKey("add")) {
            } else if (request.getParameterMap().containsKey("delete")) {

            }
        } catch (SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerGestisciLaboratori.class.getName());
        }


        return template;
    }

    private record LaboratorioConConteggioIscritti(Laboratorio laboratorio, int numeroIscritti) {
    }

}
