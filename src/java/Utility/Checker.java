package Utility;

import Controller.ControllerLogin;
import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.Animatore;
import Domain.ContattoUrgenze;
import Domain.Ragazzo;
import Domain.Terzamedia;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class Checker {

    public static boolean checkMail(String mail) throws IOException, UnirestException {
        boolean flag = false;
        String key = /*assistenza*//*"bc92b98f15fbd6039d7d9437dea21d4c";*/ /*segreteria*/"99eb8b3fcc4698a5f33129569761e3af";
        HttpResponse<JsonNode> response = Unirest.get("http://apilayer.net/api/check").queryString("access_key", key).queryString("email", mail).asJson();
        //controllo se la richiesta è andata a buon fine
        if (response.getStatus() == 200) {
            JSONArray array = response.getBody().getArray();
            JSONObject responsedata = array.getJSONObject(0);
            //controllo se ho esaurito la quota delle mail
            if (responsedata.has("success") && responsedata.getBoolean("success") == false) {
                JSONObject errore = responsedata.getJSONObject("error");
                if (errore.getInt("code") == 104) {
                    flag = true;
                }
            //prima controllo se la mail è in formato valido, altrimenti se splitto e non è valido magari da NullPointer
            } else if (responsedata.getBoolean("format_valid") == true) {
                String dominio = mail.split("@")[1];
                if (dominio.equalsIgnoreCase("alice.it") || dominio.equalsIgnoreCase("tim.it") || dominio.equalsIgnoreCase("tin.it")|| dominio.equalsIgnoreCase("tiscali.it")) {
                    if (responsedata.getBoolean("mx_found") == true) {
                        flag = true;
                    }
                } else if (responsedata.getBoolean("mx_found") == true && responsedata.getBoolean("smtp_check") == true && responsedata.getBoolean("disposable") == false) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static Boolean checkActionCU(HttpServletRequest request) {
        Boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idCU = Integer.parseInt(request.getParameter("id"));
        try {
            ContattoUrgenze cu = DAOMan.contattoUrgenzeDAO.findById(idCU);
            if (tipoUt.equals(3) && cu.getRegistrato().getId() == idRegistrato) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionAccompagnatore(HttpServletRequest request) {
        Boolean flag = false;
        int idRegistrato = (int) request.getSession().getAttribute("idUtente");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        int idAccompagnatore = Integer.parseInt(request.getParameter("id"));
        try {
            Accompagnatore a = DAOMan.accompagnatoreDAO.findById(idAccompagnatore);
            if (tipoUt.equals(3) && a.getRegistrato().getId() == idRegistrato) {
                flag = true;
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionRagazzo(HttpServletRequest request) {
        Boolean flag = false;
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
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionTerzamedia(HttpServletRequest request) {
        Boolean flag = false;
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
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static Boolean checkActionAnimatore(HttpServletRequest request) {
        Boolean flag = false;
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
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    public static boolean checkIsFromPescantina(Ragazzo r){
        boolean flag;
        String loc = r.getRegistrato().getLocalita();
        String[] dintorni = {"pescantina","settimo","balconi","ospedaletto","arce","arcè","arcé","santa lucia"};
        loc = loc.trim().toLowerCase();
        flag = Arrays.stream(dintorni).parallel().anyMatch(loc::contains);
        return flag;
    }

}
