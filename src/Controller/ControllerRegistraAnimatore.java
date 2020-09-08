package Controller;

import DAOManager.DAOMan;
import Domain.Animatore;
import Domain.Calendario;
import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Parrocchia;
import Domain.Registrato;
import Domain.RelPresenzaAn;
import ModelAndView.*;
import Utility.Checker;
import com.mashape.unirest.http.exceptions.UnirestException;
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

public class ControllerRegistraAnimatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Registra Animatore");
        mv.setView("user/registraanimatore.html");
        try {
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                Animatore animatore = new Animatore();
                animatore.setNome(request.getParameter("nome"));
                animatore.setCognome(request.getParameter("cognome"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascita = sdf.parse(request.getParameter("dataNascita"));
                animatore.setDataNascita(dataNascita);
                animatore.setPresenza(request.getParameter("presenza"));
                animatore.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
                animatore.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
                animatore.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                animatore.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                animatore.setCellulare(request.getParameter("cellulare"));
                animatore.setFasciaEtaRagazzi(request.getParameter("fasciaEtaRagazzi"));
                animatore.setMail(request.getParameter("mail"));
                animatore.setCodiceFiscale(request.getParameter("codiceFiscale").toUpperCase());
                String nTessera = request.getParameter("nTessera");
                if (!nTessera.isEmpty()) {
                    animatore.setnTessera(nTessera);
                }
                DAOMan.animatoreDAO.insert(animatore);
                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaAn rpa = new RelPresenzaAn(animatore.getId(), Integer.parseInt(calId));
                    DAOMan.relPresenzaAnDAO.insert(rpa);
                }
                response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
            } else {
                //preparo i dati necessari per l'iscrizione
                List<Laboratorio> listLab = DAOMan.laboratorioDAO.findAll();
                mv.addObject("laboratori", listLab);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                mv.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                mv.addObject("circoli", listCircolo);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                mv.addObject("calendari", listaCalendario);
                Registrato reg = DAOMan.registratoDAO.findById((int) request.getSession().getAttribute("idUtente"));
                mv.addObject("registrato", reg);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    mv.addObject("INVALIDMAIL", true);
                }
                //iscrizioni aperte o chiuse
                Properties properties = new Properties();
                InputStream in = new FileInputStream("C:/conf/RegistrazioneGrest/config.properties");
                properties.load(in);
                mv.addObject("ISCRAN", properties.getProperty("ISCRAN").equals("true"));
                in.close();
            }
        } catch (UnirestException | NullPointerException | IOException | NumberFormatException | SQLException | ParseException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerRegistraAnimatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
