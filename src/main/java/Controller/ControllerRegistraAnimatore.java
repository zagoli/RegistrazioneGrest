package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
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

public class ControllerRegistraAnimatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Registra Animatore");
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
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                template.setView("user/registraanimatore.html");
                //preparo i dati necessari per l'iscrizione
                List<Laboratorio> listLab = DAOMan.laboratorioDAO.findAll();
                template.addObject("laboratori", listLab);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                template.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                template.addObject("circoli", listCircolo);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                Registrato reg = DAOMan.registratoDAO.findById((int) request.getSession().getAttribute("idUtente"));
                template.addObject("registrato", reg);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                //iscrizioni aperte o chiuse
                template.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
            }
        } catch (final RuntimeException | SQLException | IOException | ConfigPropertyException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraAnimatore.class.getName());
        }
        return template;
    }

}
