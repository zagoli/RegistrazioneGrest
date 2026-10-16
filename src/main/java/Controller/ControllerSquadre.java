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

public class ControllerSquadre implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerSquadre.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Assegnazione Squadre");
            if (!request.getParameterMap().containsKey("submitted")) {
                template.addObject("squadre", DAOMan.squadraDAO.findAll());
            }
            switch (request.getParameter("target")) {
                case "rag":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        template.setView("ammseg/squadreragazzi.html");
                    } else {
                        //salvo le modifiche
                        SalvataggioLaboratoriSquadreUtils.salvataggio(request.getParameterValues("squadra"), true, DAOMan.ragazzoDAO::updateSquadra);
                        logger.info("Squadre ragazzi salvate");
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=rag");
                    }
                    break;
                case "ter":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        template.setView("ammseg/squadreterzamedia.html");
                    } else {
                        //salvo le modifiche
                        SalvataggioLaboratoriSquadreUtils.salvataggio(request.getParameterValues("squadra"), true, DAOMan.terzamediaDAO::updateSquadra);
                        logger.info("Squadre terzamedia salvate");
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=ter");
                    }
                    break;
                case "an":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        template.setView("ammseg/squadreanimatori.html");
                    } else {
                        //salvo le modifiche
                        SalvataggioLaboratoriSquadreUtils.salvataggioAnimatori(request, true, DAOMan.animatoreDAO::updateSquadra);
                        logger.info("Squadre animatori salvate");
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=an");
                    }
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerSquadre.class.getName());
        }
        return template;
    }
}
