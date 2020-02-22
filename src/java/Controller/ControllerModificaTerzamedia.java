package Controller;

import DAOManager.DAOMan;
import Domain.Calendario;
import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Parrocchia;
import Domain.RelPresenzaTer;
import Domain.Scuola;
import Domain.Terzamedia;
import ModelAndView.*;
import Utility.Checker;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerModificaTerzamedia implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Modifica terza media");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idTerzamedia = Integer.parseInt(request.getParameter("id"));
        try {
            Terzamedia t = DAOMan.terzamediaDAO.findById(idTerzamedia);
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                //INSERISCO IL TERZAMEDIA E FACCIO UNA REDIRECT
                int idUtente = (int) request.getSession().getAttribute("idUtente");
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

                if (request.getParameterMap().containsKey("saNuotare")) {
                    t.setSaNuotare(Boolean.TRUE);
                } else {
                    t.setSaNuotare(Boolean.FALSE);
                }

                String cellulare = request.getParameter("cellulare");
                if (!cellulare.isEmpty()) {
                    t.setCellulare(cellulare);
                }

                if (request.getParameterMap().containsKey("festaPassaggio")) {
                    t.setFestaPassaggio(Boolean.TRUE);
                } else {
                    t.setFestaPassaggio(Boolean.FALSE);
                }

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
                    response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
                } else {
                    response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=ter");
                }

            } else {
                //PREPARO I DATI PER LA PAGINA
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findAll();
                mv.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                mv.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                mv.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                mv.addObject("scuole", listScuola);
                //
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataNascita = sdf.format(t.getDataNascita());
                mv.addObject("dataNascita", dataNascita);
                mv.addObject("terzamedia", t);
                List<Calendario> listaCalendari = DAOMan.calendarioDAO.findAll();
                List<Calendario> listaCalendariTerzamedia = DAOMan.calendarioDAO.findByTerzamediaId(idTerzamedia);
                Map calendari = new TreeMap();
                listaCalendari.forEach((calendario) -> {
                    if (listaCalendariTerzamedia.contains(calendario)) {
                        calendari.put(calendario, true);
                    } else {
                        calendari.put(calendario, false);
                    }
                });
                mv.addObject("calendari", calendari);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    mv.addObject("INVALIDMAIL", true);
                }
                mv.setView("user/modificaterzamedia.html");
            }
        } catch (NullPointerException | IOException | NumberFormatException | SQLException | ParseException | UnirestException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerModificaTerzamedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
