package Utility;

import Controller.ControllerLoginEPasswordReset;
import DAOManager.DAOMan;
import Domain.*;

import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import kong.unirest.core.json.JSONObject;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Checker {

    public static boolean checkMail(String mail) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://172.16.0.8:8085/v0/check_email")
                .header("Content-Type", "application/json")
                .body("""
                        {
                            "to_email": "%s",
                            "from_email": "assistenzatecnica@parrocchiadibalconi.it"
                        }
                        """.formatted(mail)).asJson();
        if (response.getStatus() == 200) {
            JSONObject responsedata = response.getBody().getObject();
            return !responsedata.getString("is_reachable").equals("invalid") && !responsedata.getString("is_reachable").equals("risky");
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
