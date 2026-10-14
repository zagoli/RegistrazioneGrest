package Controller;

import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerSquadre implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Assegnazione Squadre");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            switch (request.getParameter("target")) {
                case "rag":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        template.addObject("squadre", DAOMan.squadraDAO.findAll());
                        template.setView("ammseg/squadreragazzi.html");
                    } else {
                        //salvo le modifiche
                        AssegnazioniSupport.pairs(request.getParameterValues("squadra"), true, DAOMan.ragazzoDAO::updateSquadra);
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=rag");
                    }
                    break;
                case "an":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        template.addObject("squadre", DAOMan.squadraDAO.findAll());
                        template.setView("ammseg/squadreanimatori.html");
                    } else {
                        //salvo le modifiche
                        AssegnazioniSupport.animators(request, true, DAOMan.animatoreDAO::updateSquadra);
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=an");
                    }
                    break;
                case "ter":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        template.addObject("squadre", DAOMan.squadraDAO.findAll());
                        template.setView("ammseg/squadreterzamedia.html");
                    } else {
                        //salvo le modifiche
                        AssegnazioniSupport.pairs(request.getParameterValues("squadra"), true, DAOMan.terzamediaDAO::updateSquadra);
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=ter");
                    }
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerSquadre.class.getName());
        }
        return template;
    }
}
