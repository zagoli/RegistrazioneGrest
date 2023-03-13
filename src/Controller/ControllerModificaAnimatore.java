package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

public class ControllerModificaAnimatore implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Modifica animatore");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idAnimatore = Integer.parseInt(request.getParameter("id"));
        try {
            Animatore a = DAOMan.animatoreDAO.findById(idAnimatore);
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                //INSERISCO ANIMATORE E FACCIO UNA REDIRECT
                a.setId(Integer.parseInt(request.getParameter("id")));
                a.setNome(request.getParameter("nome"));
                a.setCognome(request.getParameter("cognome"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascita = sdf.parse(request.getParameter("dataNascita"));
                a.setDataNascita(dataNascita);
                a.setPresenza(request.getParameter("presenza"));
                a.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
                a.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
                a.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
                a.setCellulare(request.getParameter("cellulare"));
                a.setFasciaEtaRagazzi(request.getParameter("fasciaEtaRagazzi"));
                a.setMail(request.getParameter("mail"));
                a.setCodiceFiscale(request.getParameter("codiceFiscale").toUpperCase());
                String nTessera = request.getParameter("nTessera");
                if (!nTessera.isEmpty()) {
                    a.setnTessera(nTessera);
                }

                DAOMan.animatoreDAO.update(a);
                
                List<RelPresenzaAn> calToDelete = DAOMan.relPresenzaAnDAO.findByAnimatoreId(Integer.parseInt(request.getParameter("id")));
                for (RelPresenzaAn relPresenzaAn : calToDelete) {
                    DAOMan.relPresenzaAnDAO.delete(relPresenzaAn);
                }

                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaAn rpa = new RelPresenzaAn(a.getId(),Integer.parseInt(calId));
                    DAOMan.relPresenzaAnDAO.insert(rpa);
                }
                if (request.getSession().getAttribute("tipoUtente").equals(3)){
                    response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
                } else {
                    response.sendRedirect("/RegistrazioneGrest/App/VisualizzaIscritti?target=an");
                }
            } else {
                //PREPARO I DATI PER LA PAGINA
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataNascita = sdf.format(a.getDataNascita());
                mv.addObject("dataNascita", dataNascita);
                mv.addObject("animatore", a);
                List<Laboratorio> listLab = DAOMan.laboratorioDAO.findAll();
                mv.addObject("laboratori", listLab);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                mv.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                mv.addObject("circoli", listCircolo);
                List<Calendario> listaCalendari = DAOMan.calendarioDAO.findAll();
                List<Calendario> listaCalendariAnimatore = DAOMan.calendarioDAO.findByAnimatoreId(idAnimatore);
                Map<Calendario, Boolean> calendari = new TreeMap<>();
                listaCalendari.forEach((calendario) -> calendari.put(calendario, listaCalendariAnimatore.contains(calendario)));
                mv.addObject("calendari", calendari);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    mv.addObject("INVALIDMAIL", true);
                }
                mv.setView("user/modificaanimatore.html");
            }
        } catch (UnirestException | NullPointerException | IOException | NumberFormatException | SQLException | ParseException ex) {

            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerModificaAnimatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
