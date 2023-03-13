package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.ConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerVisualizzaQuote implements ControllerInterface {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        if (request.getParameterMap().containsKey("ragazzi")) {
            mv.addObject("TITOLOPAGINA", "Quote ragazzi");
            mv.addObject("supplemento_anticipo", ConfigProperties.getProperty("SUPPLEMENTO_ENTRATA_ANTICIPATA_RAGAZZI"));
            mv.addObject("supplemento_fuoricomune", ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_RAGAZZI"));
            mv.addObject("prezzo_1", ConfigProperties.getProperty("PREZZO_1_RAGAZZI"));
            mv.addObject("prezzo_2", ConfigProperties.getProperty("PREZZO_2_RAGAZZI"));
            mv.addObject("prezzo_3", ConfigProperties.getProperty("PREZZO_3_RAGAZZI"));
            mv.addObject("prezzo_4", ConfigProperties.getProperty("PREZZO_4_RAGAZZI"));
            mv.addObject("prezzo_1_mensa", ConfigProperties.getProperty("PREZZO_1_MENSA_RAGAZZI"));
            mv.addObject("prezzo_2_mensa", ConfigProperties.getProperty("PREZZO_2_MENSA_RAGAZZI"));
            mv.addObject("prezzo_3_mensa", ConfigProperties.getProperty("PREZZO_3_MENSA_RAGAZZI"));
            mv.addObject("prezzo_4_mensa", ConfigProperties.getProperty("PREZZO_4_MENSA_RAGAZZI"));
            mv.addObject("prezzo_1_fratelli", ConfigProperties.getProperty("PREZZO_1_FRATELLI_RAGAZZI"));
            mv.addObject("prezzo_2_fratelli", ConfigProperties.getProperty("PREZZO_2_FRATELLI_RAGAZZI"));
            mv.addObject("prezzo_3_fratelli", ConfigProperties.getProperty("PREZZO_3_FRATELLI_RAGAZZI"));
            mv.addObject("prezzo_4_fratelli", ConfigProperties.getProperty("PREZZO_4_FRATELLI_RAGAZZI"));
            mv.addObject("prezzo_1_fratelli_mensa", ConfigProperties.getProperty("PREZZO_1_FRATELLI_MENSA_RAGAZZI"));
            mv.addObject("prezzo_2_fratelli_mensa", ConfigProperties.getProperty("PREZZO_2_FRATELLI_MENSA_RAGAZZI"));
            mv.addObject("prezzo_3_fratelli_mensa", ConfigProperties.getProperty("PREZZO_3_FRATELLI_MENSA_RAGAZZI"));
            mv.addObject("prezzo_4_fratelli_mensa", ConfigProperties.getProperty("PREZZO_4_FRATELLI_MENSA_RAGAZZI"));
            mv.setView("user/quoteragazzi.html");
        } else if (request.getParameterMap().containsKey("terzamedia")) {
            mv.addObject("TITOLOPAGINA", "Quote ragazzi di terza media");
            mv.addObject("supplemento_fuoricomune", ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_TERZAMEDIA"));
            mv.addObject("prezzo_1", ConfigProperties.getProperty("PREZZO_1_TERZAMEDIA"));
            mv.addObject("prezzo_2", ConfigProperties.getProperty("PREZZO_2_TERZAMEDIA"));
            mv.addObject("prezzo_3", ConfigProperties.getProperty("PREZZO_3_TERZAMEDIA"));
            mv.addObject("prezzo_4", ConfigProperties.getProperty("PREZZO_4_TERZAMEDIA"));
            mv.setView("user/quoteterzamedia.html");
        } else {
            Exception ex = new IllegalArgumentException("Le quote sono disponibili solo per i ragazzi e i ragazzi di terza media.");
            mv.setView("err/errore.hmtl");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerModificaCU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }
}
