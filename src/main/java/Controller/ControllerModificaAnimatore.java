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

public class ControllerModificaAnimatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            int idAnimatore = Integer.parseInt(request.getParameter("id"));
            template.addObject("TITOLOPAGINA", "Modifica animatore");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
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
                    a.setNTessera(nTessera);
                }
                DAOMan.animatoreDAO.update(a);
                List<RelPresenzaAn> calToDelete = DAOMan.relPresenzaAnDAO.findByAnimatoreId(Integer.parseInt(request.getParameter("id")));
                for (RelPresenzaAn relPresenzaAn : calToDelete) {
                    DAOMan.relPresenzaAnDAO.delete(relPresenzaAn);
                }
                String[] cal = request.getParameterValues("cal");
                for (String calId : cal) {
                    RelPresenzaAn rpa = new RelPresenzaAn(a.getId(), Integer.parseInt(calId));
                    DAOMan.relPresenzaAnDAO.insert(rpa);
                }
                if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                    return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
                } else {
                    return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=an");
                }
            } else {
                //PREPARO I DATI PER LA PAGINA
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataNascita = sdf.format(a.getDataNascita());
                template.addObject("dataNascita", dataNascita);
                template.addObject("animatore", a);
                List<Laboratorio> listLab = DAOMan.laboratorioDAO.findAll();
                template.addObject("laboratori", listLab);
                List<Parrocchia> listParrocchia = DAOMan.parrocchiaDAO.findAll();
                template.addObject("parrocchie", listParrocchia);
                List<Circolo> listCircolo = DAOMan.circoloDAO.findAll();
                template.addObject("circoli", listCircolo);
                List<Calendario> listaCalendari = DAOMan.calendarioDAO.findAll();
                List<Calendario> listaCalendariAnimatore = DAOMan.calendarioDAO.findByAnimatoreId(idAnimatore);
                Map<Calendario, Boolean> calendari = new TreeMap<>();
                listaCalendari.forEach((calendario) -> calendari.put(calendario, listaCalendariAnimatore.contains(calendario)));
                template.addObject("calendari", calendari);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                template.setView("user/modificaanimatore.html");
            }
        } catch (final RuntimeException | SQLException | IOException | ParseException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaAnimatore.class.getName());
        }
        return template;
    }

}
