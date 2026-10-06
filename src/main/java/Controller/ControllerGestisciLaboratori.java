package Controller;

import DAOManager.DAOMan;
import Domain.Laboratorio;
import Domain.LaboratorioConNumeroIscritti;
import Response.FreemarkerTemplate;
import Response.Response;
import Response.RedirectResult;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
                List<LaboratorioConNumeroIscritti> laboratorioConNumeroIscritti = DAOMan.laboratorioDAO.findAllConNumeroIscritti();
                template.addObject("laboratori", laboratorioConNumeroIscritti);
            } else if (request.getParameterMap().containsKey("add")) {
                Laboratorio laboratorio = new Laboratorio();
                laboratorio.setDescrizione(request.getParameter("nome"));
                laboratorio.setRiservato(request.getParameterMap().containsKey("riservato"));
                DAOMan.laboratorioDAO.insert(laboratorio);
                return new RedirectResult("/RegistrazioneGrest/App/GestisciLaboratori");
            } else if (request.getParameterMap().containsKey("delete")) {
                int idLaboratorio = Integer.parseInt(request.getParameter("idLaboratorio"));
                DAOMan.laboratorioDAO.delete(idLaboratorio);
                return new RedirectResult("/RegistrazioneGrest/App/GestisciLaboratori");
            }
        } catch (SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerGestisciLaboratori.class.getName());
        }

        return template;
    }

}
