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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ControllerGestisciLaboratori implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerGestisciLaboratori.class);

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
                logger.info("Aggiunto nuovo laboratorio {} da registrato con id {}", laboratorio.getDescrizione(), request.getSession().getAttribute("idUtente"));
                return new RedirectResult("/RegistrazioneGrest/App/GestisciLaboratori");
            } else if (request.getParameterMap().containsKey("delete")) {
                int idLaboratorio = Integer.parseInt(request.getParameter("idLaboratorio"));
                DAOMan.laboratorioDAO.delete(idLaboratorio);
                logger.info("Eliminato laboratorio con id {} da registrato con id {}", idLaboratorio, request.getSession().getAttribute("idUtente"));
                return new RedirectResult("/RegistrazioneGrest/App/GestisciLaboratori");
            }
        } catch (SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerGestisciLaboratori.class.getName());
        }

        return template;
    }

}
