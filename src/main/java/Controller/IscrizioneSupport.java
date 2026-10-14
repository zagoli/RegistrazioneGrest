package Controller;

import DAOManager.DAOMan;
import Domain.Calendario;
import Domain.Iscritto;
import Domain.Laboratorio;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

final class IscrizioneSupport {
    private IscrizioneSupport() {
    }

    static void readIscritto(HttpServletRequest request, Iscritto iscritto) throws SQLException, ParseException {
        RequestFields.iscritto(request, iscritto);
        iscritto.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
        iscritto.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
        iscritto.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
    }

    static void catalogs(FreemarkerTemplate template, List<Laboratorio> laboratori, boolean scuole) throws SQLException {
        template.addObject("laboratori", laboratori);
        template.addObject("parrocchie", DAOMan.parrocchiaDAO.findAll());
        template.addObject("circoli", DAOMan.circoloDAO.findAll());
        if (scuole) {
            template.addObject("scuole", DAOMan.scuolaDAO.findAll());
        }
    }

    static void editIdentity(FreemarkerTemplate template, String key, Iscritto iscritto) {
        template.addObject("dataNascita", new SimpleDateFormat("yyyy-MM-dd").format(iscritto.getDataNascita()));
        template.addObject(key, iscritto);
    }

    static Map<Calendario, Boolean> selectedCalendars(List<Calendario> all, List<Calendario> selected) {
        Map<Calendario, Boolean> calendari = new TreeMap<>();
        all.forEach(calendario -> calendari.put(calendario, selected.contains(calendario)));
        return calendari;
    }

    static RedirectResult afterChange(HttpServletRequest request, String target) {
        if (request.getSession().getAttribute("tipoUtente").equals(3)) {
            return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
        }
        return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=" + target);
    }
}
