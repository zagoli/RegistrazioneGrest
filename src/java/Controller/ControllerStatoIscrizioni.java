package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerStatoIscrizioni implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "NULL");
        String state = request.getParameter("state");
        Properties prop = new Properties();
        try {
            InputStream in = new FileInputStream("C:/conf/RegistrazioneGrest/config.properties");
            prop.load(in);
            in.close();
            switch (request.getParameter("target")) {
                case "ISCRRAG":
                    prop.setProperty("ISCRRAG", state);
                    break;
                case "ISCRAN":
                    prop.setProperty("ISCRAN", state);
                    break;
                case "ISCRTER":
                    prop.setProperty("ISCRTER", state);
                    break;
            }
            OutputStream out = new FileOutputStream("C:/conf/RegistrazioneGrest/config.properties");
            prop.store(out, null);
            out.close();
        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(ControllerStatoIscrizioni.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
