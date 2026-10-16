package Controller;

import Controller.Utils.IscrizioniUtils;
import Controller.Utils.PresenzeUtils;
import DAOManager.DAOMan;
import Domain.Calendario;
import Domain.Laboratorio;
import Domain.Ragazzo;
import Domain.RelPresenzaRag;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.RequestFields;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ControllerRegistraRagazzo implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerRegistraRagazzo.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Registrazione Ragazzo");

            int idRegistrato = (int) request.getSession().getAttribute("idUtente");
            boolean haAltroRagazzoIscritto = DAOMan.ragazzoDAO.countByRegistratoId(idRegistrato) > 0;

            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("user/registraragazzo.html");
                //Recupera dati per la registrazione
                List<Laboratorio> laboratori = DAOMan.laboratorioDAO.findNonRiservato();
                template.addObject("laboratori", laboratori);
                template.addObject("parrocchie", DAOMan.parrocchiaDAO.findAll());
                template.addObject("circoli", DAOMan.circoloDAO.findAll());
                template.addObject("scuole", DAOMan.scuolaDAO.findAll());
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                //iscrizioni aperte o chiuse
                template.addObject("ISCRRAG", ConfigProperties.getProperty("ISCRRAG").equals("true"));
                template.addObject("haAltroRagazzoIscritto", haAltroRagazzoIscritto);
            } else {
                Ragazzo ragazzo = new Ragazzo();
                IscrizioniUtils.populateIscrittoFromRequest(request, ragazzo);
                ragazzo.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                ragazzo.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                RequestFields.ragazzo(request, ragazzo);

                if (request.getParameterMap().containsKey("fratelloIscritto")) {
                    ragazzo.setFratelloIscritto(true);
                    if (!haAltroRagazzoIscritto) {
                        logger.warn("Il registrato con id {} ha dichiarato di avere un fratello già iscritto, ma non risultano altri ragazzi registrati a suo carico.", idRegistrato);
                    }
                } else {
                    ragazzo.setFratelloIscritto(false);
                }

                DAOMan.ragazzoDAO.insert(ragazzo);

                PresenzeUtils.insert(request, calId -> new RelPresenzaRag(calId, ragazzo.getId()),
                        DAOMan.relPresenzaRagDAO::insert);

                logger.info("Ragazzo {} {} registrato", ragazzo.getNome(), ragazzo.getCognome());

                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            }
        } catch (final RuntimeException | IOException | SQLException | ParseException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraRagazzo.class.getName());
        }
        return template;
    }
}
