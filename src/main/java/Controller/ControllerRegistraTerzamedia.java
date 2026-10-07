package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ControllerRegistraTerzamedia implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Registrazione ragazzo di Terzamedia");
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                TerzaMedia terzamedia = new TerzaMedia();
                terzamedia.setNome(request.getParameter("nome"));
                terzamedia.setCognome(request.getParameter("cognome"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascita = sdf.parse(request.getParameter("dataNascita"));
                terzamedia.setDataNascita(dataNascita);
                terzamedia.setPresenza(request.getParameter("presenza"));
                terzamedia.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
                terzamedia.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
                terzamedia.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
                terzamedia.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                terzamedia.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                terzamedia.setSezione(request.getParameter("sezione"));
                terzamedia.setMail(request.getParameter("mail"));

                terzamedia.setSaNuotare(request.getParameterMap().containsKey("saNuotare"));

                String cellulare = request.getParameter("cellulare");
                if (!cellulare.isEmpty()) {
                    terzamedia.setCellulare(cellulare);
                }

                terzamedia.setFestaPassaggio(request.getParameterMap().containsKey("festaPassaggio"));

                String richieste = request.getParameter("richieste");
                if (!richieste.isEmpty()) {
                    terzamedia.setRichieste(richieste);
                }

                String noteAlimentari = request.getParameter("noteAlimentari");
                if (!noteAlimentari.isEmpty()) {
                    terzamedia.setNoteAlimentari(noteAlimentari);
                }

                String nTessera = request.getParameter("nTessera");
                if (!nTessera.isEmpty()) {
                    terzamedia.setNTessera(nTessera);
                }

                DAOMan.terzamediaDAO.insert(terzamedia);

                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaTer rpt = new RelPresenzaTer(Integer.parseInt(calId), terzamedia.getId());
                    DAOMan.relPresenzaTerDAO.insert(rpt);
                }

                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                template.setView("user/registraterzamedia.html");
                //Recupera dati per la registrazione
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findAll();
                template.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                template.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                template.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                template.addObject("scuole", listScuola);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                //iscrizioni aperte o chiuse
                template.addObject("ISCRTER", ConfigProperties.getProperty("ISCRTER").equals("true"));
            }
        } catch (final RuntimeException | IOException | ConfigPropertyException | SQLException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraTerzamedia.class.getName());
        }
        return template;
    }
}
