package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
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
import java.util.Map;
import java.util.TreeMap;

public class ControllerModificaTerzamedia implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica terza media");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            int idTerzamedia = Integer.parseInt(request.getParameter("id"));
            Terzamedia t = DAOMan.terzamediaDAO.findById(idTerzamedia);
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                //INSERISCO IL TERZAMEDIA E FACCIO UNA REDIRECT
                t.setNome(request.getParameter("nome"));
                t.setCognome(request.getParameter("cognome"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascita = sdf.parse(request.getParameter("dataNascita"));
                t.setDataNascita(dataNascita);
                t.setPresenza(request.getParameter("presenza"));
                t.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
                t.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
                t.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
                t.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                t.setSezione(request.getParameter("sezione"));
                t.setMail(request.getParameter("mail"));
                t.setSaNuotare(request.getParameterMap().containsKey("saNuotare"));
                String cellulare = request.getParameter("cellulare");
                if (!cellulare.isEmpty()) {
                    t.setCellulare(cellulare);
                }
                t.setFestaPassaggio(request.getParameterMap().containsKey("festaPassaggio"));
                String richieste = request.getParameter("richieste");
                if (!richieste.isEmpty()) {
                    t.setRichieste(richieste);
                }
                String noteAlimentari = request.getParameter("noteAlimentari");
                if (!noteAlimentari.isEmpty()) {
                    t.setNoteAlimentari(noteAlimentari);
                }
                String nTessera = request.getParameter("nTessera");
                if (!nTessera.isEmpty()) {
                    t.setnTessera(nTessera);
                }
                DAOMan.terzamediaDAO.update(t);
                List<RelPresenzaTer> calToDelete = DAOMan.relPresenzaTerDAO.findByTerzamediaId(idTerzamedia);
                for (RelPresenzaTer relPresenzaTer : calToDelete) {
                    DAOMan.relPresenzaTerDAO.delete(relPresenzaTer);
                }
                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaTer rpt = new RelPresenzaTer(Integer.parseInt(calId), idTerzamedia);
                    DAOMan.relPresenzaTerDAO.insert(rpt);
                }
                if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                    return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
                } else {
                    return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=ter");
                }
            } else {
                //PREPARO I DATI PER LA PAGINA
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findAll();
                template.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                template.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                template.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                template.addObject("scuole", listScuola);
                //
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataNascita = sdf.format(t.getDataNascita());
                template.addObject("dataNascita", dataNascita);
                template.addObject("terzamedia", t);
                List<Calendario> listaCalendari = DAOMan.calendarioDAO.findAll();
                List<Calendario> listaCalendariTerzamedia = DAOMan.calendarioDAO.findByTerzamediaId(idTerzamedia);
                Map<Calendario, Boolean> calendari = new TreeMap<>();
                listaCalendari.forEach((calendario) -> calendari.put(calendario, listaCalendariTerzamedia.contains(calendario)));
                template.addObject("calendari", calendari);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                template.setView("user/modificaterzamedia.html");
            }
        } catch (final RuntimeException | SQLException | IOException | ParseException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaTerzamedia.class.getName());
        }
        return template;
    }

}
