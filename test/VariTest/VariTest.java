package VariTest;

import Domain.Ragazzo;
import Domain.Registrato;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class VariTest {

    public VariTest() {
    }

    @Test
    public void testLocalita() {
        Ragazzo r = new Ragazzo();
        Registrato re = new Registrato();
        re.setLocalita("bussolengo");
        r.setRegistrato(re);
        boolean check = isFromPescantina(r);
        assertEquals(check, false);
        re.setLocalita("arcèé");
        check = isFromPescantina(r);
        assertEquals(check, true);
        re.setLocalita("Pescantina");
        check = isFromPescantina(r);
        assertEquals(check, true);
        re.setLocalita("   balcoNi Di PescaNTINA");
        check = isFromPescantina(r);
        assertEquals(check, true);
    }
    
    private boolean isFromPescantina(Ragazzo r){
        boolean flag;
        String loc = r.getRegistrato().getLocalita();
        String[] dintorni = {"pescantina","settimo","balconi","ospedaletto","arce","arcè","arcé","santa lucia"};
        loc = loc.trim().toLowerCase();
        flag = Arrays.stream(dintorni).parallel().anyMatch(loc::contains);
        return flag;
    }
    
    @Test
    public void testMail() throws IOException, UnirestException{
        boolean c;
        c = checkMail("savocasa@alice.it");
        assertEquals(c, true);
        c = checkMail("mermelletinadifrutta@tim.it");
        assertEquals(c, true);
        c = checkMail("rutti.it");
        assertEquals(c, false);
    }
    
    private boolean checkMail(String mail) throws IOException, UnirestException {
        String dominio = mail.split("@")[1];
        boolean flag = false;
        HttpResponse<JsonNode> response = Unirest.get("https://apilayer.net/api/check").queryString("access_key", "bc92b98f15fbd6039d7d9437dea21d4c").queryString("email", mail).asJson();
        if (response.getStatus() == 200) {
            JSONArray array = response.getBody().getArray();
            JSONObject responsedata = array.getJSONObject(0);
            if(responsedata.has("success") && responsedata.getBoolean("success") == false){
                JSONObject errore = responsedata.getJSONObject("error");
                if (errore.getInt("code") == 104) {
                    flag = true;
                }
            }
            else if (dominio.equalsIgnoreCase("alice.it") || dominio.equalsIgnoreCase("tim.it") || dominio.equalsIgnoreCase("tin.it")) {
                if (responsedata.getBoolean("mx_found") == true && responsedata.getBoolean("format_valid") == true) {
                    flag = true;
                }
            }
            else if (responsedata.getBoolean("mx_found") == true && responsedata.getBoolean("smtp_check") == true && responsedata.getBoolean("format_valid") == true && responsedata.getBoolean("disposable") == false) {
                flag = true;
            }
        }
        return flag;
    }
    
}

