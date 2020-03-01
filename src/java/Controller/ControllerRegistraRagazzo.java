package Controller;

import DAOManager.DAOMan;
import Domain.Calendario;
import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Parrocchia;
import Domain.Ragazzo;
import Domain.RelPresenzaRag;
import Domain.Scuola;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerRegistraRagazzo implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Registrazione Ragazzo");
        mv.setView("user/registraragazzo.html");
        if (!request.getParameterMap().containsKey("nome")) {
            try {
                //Recupera dati per la registrazione
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findNonRiservato();
                mv.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                mv.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                mv.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                mv.addObject("scuole", listScuola);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                mv.addObject("calendari", listaCalendario);
                //iscrizioni aperte o chiuse
                Properties properties = new Properties();
                InputStream in = new FileInputStream("C:/conf/RegistrazioneGrest/config.properties");
                properties.load(in);
                mv.addObject("ISCRRAG", properties.getProperty("ISCRRAG").equals("true"));
                in.close();
            } catch (IOException | NullPointerException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerRegistraRagazzo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
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

                if (request.getParameterMap().containsKey("entrataAnticipata")) {
                    ragazzo.setEntrataAnticipata(Boolean.TRUE);
                } else {
                    ragazzo.setEntrataAnticipata(Boolean.FALSE);
                }

                if (request.getParameterMap().containsKey("mensa")) {
                    ragazzo.setMensa(Boolean.TRUE);
                } else {
                    ragazzo.setMensa(Boolean.FALSE);
                }

                if (request.getParameterMap().containsKey("saNuotare")) {
                    ragazzo.setSaNuotare(Boolean.TRUE);
                } else {
                    ragazzo.setSaNuotare(Boolean.FALSE);
                }

                if (request.getParameterMap().containsKey("fratelloIscritto")) {
                    ragazzo.setFratelloIscritto(Boolean.TRUE);
                } else {
                    ragazzo.setFratelloIscritto(Boolean.FALSE);
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
                    ragazzo.setnTessera(nTessera);
                }

                DAOMan.ragazzoDAO.insert(ragazzo);

                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaRag rpr = new RelPresenzaRag(Integer.parseInt(calId), ragazzo.getId());
                    DAOMan.relPresenzaRagDAO.insert(rpr);
                }

                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");

            } catch (NullPointerException | IOException | NumberFormatException | SQLException | ParseException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerRegistraRagazzo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
}