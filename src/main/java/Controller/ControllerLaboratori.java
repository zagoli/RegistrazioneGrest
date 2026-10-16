package Controller;

import Controller.Utils.SalvataggioLaboratoriSquadreUtils;
import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerLaboratori implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLaboratori.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            template.addObject("TITOLOPAGINA", "Assegnazione Laboratori");
            if (!request.getParameterMap().containsKey("submitted")) {
                //preparo gli oggetti per la pagina
                switch (request.getParameter("target")) {
                    case "rag":
                        template.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        template.addObject("laboratori", DAOMan.laboratorioDAO.findNonRiservato());
                        template.setView("ammseg/labragazzi.html");
                        break;
                    case "an":
                        template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                        template.setView("ammseg/labanimatori.html");
                        break;
                    case "ter":
                        template.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                        template.setView("ammseg/labterzamedia.html");
                        break;
                }
            } else {
                //salvo le modifiche
                switch (request.getParameter("target")) {
                    case "rag": {
                        SalvataggioLaboratoriSquadreUtils.salvataggio(request.getParameterValues("laboratorio"), false, DAOMan.ragazzoDAO::updateLaboratorio);
                        logger.info("Laboratori ragazzi salvati");
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=rag");
                    }
                    case "ter": {
                        SalvataggioLaboratoriSquadreUtils.salvataggio(request.getParameterValues("laboratorio"), false, DAOMan.terzamediaDAO::updateLaboratorio);
                        logger.info("Laboratori terzamedia salvati");
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=ter");
                    }
                    case "an": {
                        SalvataggioLaboratoriSquadreUtils.salvataggioAnimatori(request, false, DAOMan.animatoreDAO::updateLaboratorio);
                        logger.info("Laboratori animatori salvati");
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=an");
                    }
                }
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerLaboratori.class.getName());
        }
        return template;
    }
}
