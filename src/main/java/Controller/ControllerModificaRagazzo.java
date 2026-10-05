package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControllerModificaRagazzo implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica ragazzo");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            int idRagazzo = Integer.parseInt(request.getParameter("id"));
            Ragazzo r = DAOMan.ragazzoDAO.findById(idRagazzo);
            if (!request.getParameterMap().containsKey("nome")) {
                //PREPARO I DATI PER LA PAGINA
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataNascita = sdf.format(r.getDataNascita());
                template.addObject("dataNascita", dataNascita);
                template.addObject("ragazzo", r);
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findNonRiservato();
                template.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                template.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                template.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                template.addObject("scuole", listScuola);
                List<Calendario> listaCalendari = DAOMan.calendarioDAO.findAll();
                List<Calendario> listaCalendariRagazzo = DAOMan.calendarioDAO.findByRagazzoId(idRagazzo);
                Map<Calendario, Boolean> calendari = new TreeMap<>();
                listaCalendari.forEach((calendario) -> calendari.put(calendario, listaCalendariRagazzo.contains(calendario)));
                template.addObject("calendari", calendari);
                template.setView("user/modificaragazzo.html");
            } else {
                //INSERISCO IL RAGAZZO E FACCIO UNA REDIRECT
                r.setId(Integer.parseInt(request.getParameter("id")));
                r.setNome(request.getParameter("nome"));
                r.setCognome(request.getParameter("cognome"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascita = sdf.parse(request.getParameter("dataNascita"));
                r.setDataNascita(dataNascita);
                r.setPresenza(request.getParameter("presenza"));
                r.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
                r.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
                r.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
                r.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                r.setSezione(request.getParameter("sezione"));
                r.setClasse(request.getParameter("classe"));

                r.setEntrataAnticipata(request.getParameterMap().containsKey("entrataAnticipata"));

                r.setMensa(request.getParameterMap().containsKey("mensa"));

                r.setSaNuotare(request.getParameterMap().containsKey("saNuotare"));

                r.setFratelloIscritto(request.getParameterMap().containsKey("fratelloIscritto"));

                String richieste = request.getParameter("richieste");
                if (!richieste.isEmpty()) {
                    r.setRichieste(richieste);
                } else {
                    r.setRichieste(null);
                }

                String noteAlimentari = request.getParameter("noteAlimentari");
                if (!noteAlimentari.isEmpty()) {
                    r.setNoteAlimentari(noteAlimentari);
                } else {
                    r.setNoteAlimentari(null);
                }

                String nTessera = request.getParameter("nTessera");
                if (!nTessera.isEmpty()) {
                    r.setnTessera(nTessera);
                }

                DAOMan.ragazzoDAO.update(r);

                List<RelPresenzaRag> calToDelete = DAOMan.relPresenzaRagDAO.findByRagazzoId(Integer.parseInt(request.getParameter("id")));
                for (RelPresenzaRag relPresenzaRag : calToDelete) {
                    DAOMan.relPresenzaRagDAO.delete(relPresenzaRag);
                }

                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaRag rpr = new RelPresenzaRag(Integer.parseInt(calId), r.getId());
                    DAOMan.relPresenzaRagDAO.insert(rpr);
                }

                if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                    return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
                } else {
                    return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=rag");
                }

            }
        } catch (final RuntimeException | SQLException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaRagazzo.class.getName());
        }
        return template;
    }

}
