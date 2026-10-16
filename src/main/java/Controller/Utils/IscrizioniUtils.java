package Controller.Utils;

import DAOManager.DAOMan;
import Domain.Calendario;
import Domain.Iscritto;
import Response.RedirectResult;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public final class IscrizioniUtils {

    private IscrizioniUtils() {
    }

    public static void populateIscrittoFromRequest(HttpServletRequest request, Iscritto iscritto) throws SQLException, ParseException {
        RequestFields.iscritto(request, iscritto);
        iscritto.setLaboratorio(DAOMan.laboratorioDAO.findById(Integer.parseInt(request.getParameter("laboratorio"))));
        iscritto.setParrocchia(DAOMan.parrocchiaDAO.findById(Integer.parseInt(request.getParameter("parrocchia"))));
        iscritto.setCircolo(DAOMan.circoloDAO.findById(Integer.parseInt(request.getParameter("circolo"))));
    }

    public static SortedMap<Calendario, Boolean> getSettimaneSelezionate(List<Calendario> all, List<Calendario> selected) {
        SortedMap<Calendario, Boolean> calendari = new TreeMap<>();
        all.forEach(calendario -> calendari.put(calendario, selected.contains(calendario)));
        return calendari;
    }

    public static RedirectResult getPaginaRedirectDopoModifica(HttpServletRequest request, String target) {
        if (request.getSession().getAttribute("tipoUtente").equals(3)) {
            return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
        }
        return new RedirectResult("/RegistrazioneGrest/App/VisualizzaIscritti?target=" + target);
    }
}
