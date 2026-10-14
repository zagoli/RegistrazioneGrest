package Utility;

import DAOManager.DAOMan;
import Domain.*;
import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import kong.unirest.core.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Checker {
    private static final Logger logger = LoggerFactory.getLogger(Checker.class);

    public static boolean checkMail(String mail) throws UnirestException, ConfigPropertyException, IOException {
        if (!ConfigProperties.getProperty("EMAIL_CHECKER_ENABLED").equals("true")) {
            return true;
        }
        // http://172.16.0.8:8085/v0/check_email
        // usare self-hosted https://reacher.email/
        HttpResponse<JsonNode> response = Unirest.post(ConfigProperties.getProperty("EMAIL_CHECKER_ENDPOINT"))
                .header("Content-Type", "application/json")
                .body("""
                        {
                            "to_email": "%s",
                            "from_email": "assistenzatecnica@parrocchiadibalconi.it"
                        }
                        """.formatted(mail)).asJson();
        if (response.getStatus() == 200) {
            JSONObject responsedata = response.getBody().getObject();
            return !responsedata.getString("is_reachable").equals("invalid")
                    && !responsedata.getString("is_reachable").equals("risky");
        }
        return false;
    }

    public static boolean checkActionAnnotazione(HttpServletRequest request) {
        int tipoUt = (int) request.getSession().getAttribute("tipoUtente");
        if (request.getParameterMap().containsKey("add")) {
            return tipoUt < 2;
        } else if (request.getParameterMap().containsKey("del")) {
            if (tipoUt == 0) return true;
            try {
                var idSegretario = DAOMan.annotazioneSegreteriaDAO.getIdSegretario(Integer.parseInt(request.getParameter("id")));
                var idUtente = (int) request.getSession().getAttribute("idUtente");
                return idSegretario == idUtente;
            } catch (final NullPointerException | SQLException e) {
                logger.error("Errore nella verifica dell'identificativo del segretario", e);
            }
        }
        return false;
    }

    public static Boolean checkActionCU(HttpServletRequest request) {
        return checkOwnership(request, id -> DAOMan.contattoUrgenzeDAO.findById(id).getRegistrato(),
                "Errore nella verifica dei permessi sul contatto urgenze");
    }

    public static Boolean checkActionAccompagnatore(HttpServletRequest request) {
        return checkOwnership(request, id -> DAOMan.accompagnatoreDAO.findById(id).getRegistrato(),
                "Errore nella verifica dei permessi sull'accompagnatore");
    }

    private static boolean checkOwnership(HttpServletRequest request, RecordFinder<Registrato> finder,
                                           String errorMessage) {
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            return finder.findById(id).getId() == idRegistrato;
        } catch (final NullPointerException | SQLException ex) {
            logger.error(errorMessage, ex);
        }
        return false;
    }

    public static Boolean checkActionRagazzo(HttpServletRequest request) {
        return checkActionIscritto(request, id -> DAOMan.ragazzoDAO.findById(id),
                "Errore nella verifica dei permessi sul ragazzo");
    }

    public static Boolean checkActionTerzamedia(HttpServletRequest request) {
        return checkActionIscritto(request, id -> DAOMan.terzamediaDAO.findById(id),
                "Errore nella verifica dei permessi sul ragazzo di terza media");
    }

    public static Boolean checkActionAnimatore(HttpServletRequest request) {
        return checkActionIscritto(request, id -> DAOMan.animatoreDAO.findById(id),
                "Errore nella verifica dei permessi sull'animatore");
    }

    private static boolean checkActionIscritto(HttpServletRequest request, RecordFinder<Iscritto> finder,
                                               String errorMessage) {
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idIscritto = Integer.parseInt(request.getParameter("id"));
        try {
            return canModifyIscritto(finder.findById(idIscritto), idRegistrato, tipoUt);
        } catch (final NullPointerException | SQLException ex) {
            logger.error(errorMessage, ex);
        }
        return false;
    }

    static boolean canModifyIscritto(Iscritto iscritto, int idRegistrato, Integer tipoUt) {
        if (tipoUt.equals(3)) {
            return iscritto.getRegistrato().getId() == idRegistrato;
        }
        return tipoUt <= 1;
    }

    @FunctionalInterface
    private interface RecordFinder<T> {
        T findById(int id) throws SQLException;
    }

    public static boolean checkIsFromPescantina(String localita) {
        if (localita == null) {
            return false;
        }
        String[] dintorni = {"pescantina", "settimo", "balconi", "ospedaletto", "arce", "arcè", "arcé", "santa lucia"};
        localita = localita.trim().toLowerCase();
        return Arrays.stream(dintorni).anyMatch(localita::contains);
    }

}
