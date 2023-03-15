package Utility;

import Controller.ControllerLoginEPasswordReset;
import DAOManager.DAOMan;
import Domain.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Checker {

    public static boolean checkMail(String mail) throws UnirestException, ConfigPropertyException, IOException {
        HttpResponse<JsonNode> response = Unirest.get("https://emailverification.whoisxmlapi.com/api/v2")
                .queryString("apiKey", ConfigProperties.getProperty("XMLAPI_MAIL_CHECK_API_KEY"))
                .queryString("emailAddress", mail)
                .asJson();
        //controllo se la richiesta è andata a buon fine
        if (response.getStatus() == 200) {
            JSONObject responsedata = response.getBody().getObject();
            //prima controllo se la mail è in formato valido, altrimenti se splitto e non è valido magari da NullPointer
            if (responsedata.getBoolean("formatCheck")
                    && responsedata.getString("emailAddress").equals(mail)
                    && responsedata.getBoolean("dnsCheck")
                    // && responsedata.getBoolean("smtpCheck")
                    && !responsedata.getBoolean("disposableCheck")) {
                return true;
            }
        }
        return false;
    }

    public static Boolean checkActionCU(HttpServletRequest request) {
        boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idCU = Integer.parseInt(request.getParameter("id"));
        try {
            ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
            if (tipoUt.equals(3) && cu.getRegistrato().getId() == idRegistrato) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLoginEPasswordReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionAccompagnatore(HttpServletRequest request) {
        boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
        try {
            Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
            if (tipoUt.equals(3) && a.getRegistrato().getId() == idRegistrato) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLoginEPasswordReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionRagazzo(HttpServletRequest request) {
        boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idRagazzo = Integer.parseInt(request.getParameter("id"));
        try {
            Ragazzo r = DAOMan.ragazzoDAO.findById(idRagazzo);
            if (tipoUt.equals(3)) {
                if (r.getRegistrato().getId() == idRegistrato) {
                    flag = true;
                }
            } else if (tipoUt <= 1) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLoginEPasswordReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionTerzamedia(HttpServletRequest request) {
        boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idTerzamedia = Integer.parseInt(request.getParameter("id"));
        try {
            Terzamedia t = DAOMan.terzamediaDAO.findById(idTerzamedia);
            if (tipoUt.equals(3)) {
                if (t.getRegistrato().getId() == idRegistrato) {
                    flag = true;
                }
            } else if (tipoUt <= 1) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLoginEPasswordReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionAnimatore(HttpServletRequest request) {
        boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idAnimatore = Integer.parseInt(request.getParameter("id"));
        try {
            Animatore a = DAOMan.animatoreDAO.findById(idAnimatore);
            if (tipoUt.equals(3)) {
                if (a.getRegistrato().getId() == idRegistrato) {
                    flag = true;
                }
            } else if (tipoUt <= 1) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLoginEPasswordReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static boolean checkIsFromPescantina(String localita) {
        String[] dintorni = {"pescantina", "settimo", "balconi", "ospedaletto", "arce", "arcè", "arcé", "santa lucia"};
        localita = localita.trim().toLowerCase();
        return Arrays.stream(dintorni).parallel().anyMatch(localita::contains);
    }

}
