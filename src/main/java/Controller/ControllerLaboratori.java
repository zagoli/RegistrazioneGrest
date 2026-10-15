package Controller;

import Controller.Utils.SalvataggioLaboratoriSquadreUtils;
import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerLaboratori implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            template.addObject("TITOLOPAGINA", "Assegnazione Laboratori");
            //preparo gli oggetti per la pagina
            if (!request.getParameterMap().containsKey("submitted")) {
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
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=rag");
                    }
                    case "ter": {
                        SalvataggioLaboratoriSquadreUtils.salvataggio(request.getParameterValues("laboratorio"), false, DAOMan.terzamediaDAO::updateLaboratorio);
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=ter");
                    }
                    case "an": {
                        SalvataggioLaboratoriSquadreUtils.salvataggioAnimatori(request, false, DAOMan.animatoreDAO::updateLaboratorio);
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
