package Utility;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    public static ModelAndView getErrorPageAndLogException(Exception e, String loggerName) {
        logException(e, loggerName);
        ModelAndView mv = new ModelAndViewStandard("err/errore.html");
        mv.addObject("TITOLOPAGINA", "Errore!");
        mv.addObject("eccezione", e);
        return mv;
    }

    public static void logException(Exception e, String loggerName) {
        Logger.getLogger(loggerName).log(Level.SEVERE, "", e);
    }
}
