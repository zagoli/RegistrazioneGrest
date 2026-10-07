package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ControllerRegistraRagazzo implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerRegistraRagazzo.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Registrazione Ragazzo");

            int idRegistrato = (int) request.getSession().getAttribute("idUtente");
            boolean haAltroRagazzoIscritto = DAOMan.ragazzoDAO.countByRegistratoId(idRegistrato) > 0;

            if (!request.getParameterMap().containsKey("nome")) {
                template.setView("user/registraragazzo.html");
                //Recupera dati per la registrazione
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findNonRiservato();
                template.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                template.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                template.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                template.addObject("scuole", listScuola);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                //iscrizioni aperte o chiuse
                template.addObject("ISCRRAG", ConfigProperties.getProperty("ISCRRAG").equals("true"));
                template.addObject("haAltroRagazzoIscritto", haAltroRagazzoIscritto);
            } else {
                Ragazzo ragazzo = new Ragazzo();
                ragazzo.setNome(request.getParameter("nome"));
                ragazzo.setCognome(request.getParameter("cognome"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascita = sdf.parse(request.getParameter("dataNascita"));
                ragazzo.setDataNascita(dataNascita);
                ragazzo.setPresenza(request.getParameter("presenza"));
                ragazzo.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
                ragazzo.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
                ragazzo.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
                ragazzo.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                ragazzo.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                ragazzo.setSezione(request.getParameter("sezione"));
                ragazzo.setClasse(request.getParameter("classe"));

                ragazzo.setEntrataAnticipata(request.getParameterMap().containsKey("entrataAnticipata"));
                ragazzo.setMensa(request.getParameterMap().containsKey("mensa"));
                ragazzo.setSaNuotare(request.getParameterMap().containsKey("saNuotare"));

                if (request.getParameterMap().containsKey("fratelloIscritto")) {
                    ragazzo.setFratelloIscritto(true);
                    if (!haAltroRagazzoIscritto) {
                        logger.warn("Il registrato con id {} ha dichiarato di avere un fratello già iscritto, ma non risultano altri ragazzi registrati a suo carico.", idRegistrato);
                    }
                } else {
                    ragazzo.setFratelloIscritto(false);
                }

                String richieste = request.getParameter("richieste");
                if (!richieste.isEmpty()) {
                    ragazzo.setRichieste(richieste);
                }

                String noteAlimentari = request.getParameter("noteAlimentari");
                if (!noteAlimentari.isEmpty()) {
                    ragazzo.setNoteAlimentari(noteAlimentari);
                }

                String nTessera = request.getParameter("nTessera");
                if (!nTessera.isEmpty()) {
                    ragazzo.setNTessera(nTessera);
                }

                DAOMan.ragazzoDAO.insert(ragazzo);

                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaRag rpr = new RelPresenzaRag(Integer.parseInt(calId), ragazzo.getId());
                    DAOMan.relPresenzaRagDAO.insert(rpr);
                }

                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            }
        } catch (final RuntimeException | IOException | SQLException | ParseException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraRagazzo.class.getName());
        }
        return template;
    }
}
