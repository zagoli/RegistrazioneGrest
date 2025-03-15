package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
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
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            mv.addObject("TITOLOPAGINA", "Registrazione ragazzo di Terzamedia");
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                Terzamedia terzamedia = new Terzamedia();
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

                if (request.getParameterMap().containsKey("saNuotare")) {
                    terzamedia.setSaNuotare(Boolean.TRUE);
                } else {
                    terzamedia.setSaNuotare(Boolean.FALSE);
                }

                String cellulare = request.getParameter("cellulare");
                if (!cellulare.isEmpty()) {
                    terzamedia.setCellulare(cellulare);
                }

                if (request.getParameterMap().containsKey("festaPassaggio")) {
                    terzamedia.setFestaPassaggio(Boolean.TRUE);
                } else {
                    terzamedia.setFestaPassaggio(Boolean.FALSE);
                }

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
                    terzamedia.setnTessera(nTessera);
                }

                DAOMan.terzamediaDAO.insert(terzamedia);

                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaTer rpt = new RelPresenzaTer(Integer.parseInt(calId), terzamedia.getId());
                    DAOMan.relPresenzaTerDAO.insert(rpt);
                }

                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
            } else {
                mv.setView("user/registraterzamedia.html");
                //Recupera dati per la registrazione
                List<Laboratorio> listLabGiusti = DAOMan.laboratorioDAO.findAll();
                mv.addObject("laboratori", listLabGiusti);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                mv.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                mv.addObject("circoli", listCircolo);
                List<Scuola> listScuola = DAOMan.scuolaDAO.findAll();
                mv.addObject("scuole", listScuola);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                mv.addObject("calendari", listaCalendario);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    mv.addObject("INVALIDMAIL", true);
                }
                //iscrizioni aperte o chiuse
                mv.addObject("ISCRTER", ConfigProperties.getProperty("ISCRTER").equals("true"));
            }
        } catch (final RuntimeException | IOException | ConfigPropertyException | SQLException | ParseException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerRegistraTerzamedia.class.getName());
        }
        return mv;
    }
}
