package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
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

public class ControllerModificaRagazzo implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Modifica ragazzo");
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            int idRagazzo = Integer.parseInt(request.getParameter("id"));
            Ragazzo r = DAOMan.ragazzoDAO.findById(idRagazzo);
            if (!request.getParameterMap().containsKey("nome")) {
                //PREPARO I DATI PER LA PAGINA
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataNascita = sdf.format(r.getDataNascita());
                mv.addObject("dataNascita", dataNascita);
                mv.addObject("ragazzo", r);
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findNonRiservato();
                mv.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                mv.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                mv.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                mv.addObject("scuole", listScuola);
                List<Calendario> listaCalendari = DAOMan.calendarioDAO.findAll();
                List<Calendario> listaCalendariRagazzo = DAOMan.calendarioDAO.findByRagazzoId(idRagazzo);
                Map<Calendario, Boolean> calendari = new TreeMap<>();
                listaCalendari.forEach((calendario) -> calendari.put(calendario, listaCalendariRagazzo.contains(calendario)));
                mv.addObject("calendari", calendari);
                mv.setView("user/modificaragazzo.html");
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

                if (request.getParameterMap().containsKey("entrataAnticipata")) {
                    r.setEntrataAnticipata(Boolean.TRUE);
                } else {
                    r.setEntrataAnticipata(Boolean.FALSE);
                }

                if (request.getParameterMap().containsKey("mensa")) {
                    r.setMensa(Boolean.TRUE);
                } else {
                    r.setMensa(Boolean.FALSE);
                }

                if (request.getParameterMap().containsKey("saNuotare")) {
                    r.setSaNuotare(Boolean.TRUE);
                } else {
                    r.setSaNuotare(Boolean.FALSE);
                }

                if (request.getParameterMap().containsKey("fratelloIscritto")) {
                    r.setFratelloIscritto(Boolean.TRUE);
                } else {
                    r.setFratelloIscritto(Boolean.FALSE);
                }

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
                    response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
                } else {
                    response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=rag");
                }

            }
        } catch (final RuntimeException | IOException | SQLException | ParseException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerModificaRagazzo.class.getName());
        }
        return mv;
    }

}
