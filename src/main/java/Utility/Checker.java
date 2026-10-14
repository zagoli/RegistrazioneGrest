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
        boolean result = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        int idCU = Integer.parseInt(request.getParameter("id"));
        try {
            ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
            if (cu.getRegistrato().getId() == idRegistrato) {
                result = true;
            }
        } catch (final NullPointerException | SQLException ex) {
            logger.error("Errore nella verifica dei permessi sul contatto urgenze", ex);
        }
        return result;
    }

    public static Boolean checkActionAccompagnatore(HttpServletRequest request) {
        boolean result = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
        try {
            Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
            if (a.getRegistrato().getId() == idRegistrato) {
                result = true;
            }
        } catch (final NullPointerException | SQLException ex) {
            logger.error("Errore nella verifica dei permessi sull'accompagnatore", ex);
        }
        return result;
    }

    public static Boolean checkActionRagazzo(HttpServletRequest request) {
        boolean result = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idRagazzo = Integer.parseInt(request.getParameter("id"));
        try {
            Ragazzo r = DAOMan.ragazzoDAO.findById(idRagazzo);
            if (tipoUt.equals(3)) {
                if (r.getRegistrato().getId() == idRegistrato) {
                    result = true;
                }
            } else if (tipoUt <= 1) {
                result = true;
            }
        } catch (final NullPointerException | SQLException ex) {
            logger.error("Errore nella verifica dei permessi sul ragazzo", ex);
        }
        return result;
    }

    public static Boolean checkActionTerzamedia(HttpServletRequest request) {
        boolean result = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idTerzamedia = Integer.parseInt(request.getParameter("id"));
        try {
            TerzaMedia t = DAOMan.terzamediaDAO.findById(idTerzamedia);
            if (tipoUt.equals(3)) {
                if (t.getRegistrato().getId() == idRegistrato) {
                    result = true;
                }
            } else if (tipoUt <= 1) {
                result = true;
            }
        } catch (final NullPointerException | SQLException ex) {
            logger.error("Errore nella verifica dei permessi sul ragazzo di terza media", ex);
        }
        return result;
    }

    public static Boolean checkActionAnimatore(HttpServletRequest request) {
        boolean result = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idAnimatore = Integer.parseInt(request.getParameter("id"));
        try {
            Animatore a = DAOMan.animatoreDAO.findById(idAnimatore);
            if (tipoUt.equals(3)) {
                if (a.getRegistrato().getId() == idRegistrato) {
                    result = true;
                }
            } else if (tipoUt <= 1) {
                result = true;
            }
        } catch (final NullPointerException | SQLException ex) {
            logger.error("Errore nella verifica dei permessi sull'animatore", ex);
        }
        return result;
    }

    public static boolean checkIsFromPescantina(String localita) {
        String[] dintorni = {"pescantina", "settimo", "balconi", "ospedaletto", "arce", "arcè", "arcé", "santa lucia"};
        localita = localita.trim().toLowerCase();
        return Arrays.stream(dintorni).anyMatch(localita::contains);
    }

}
