package Controller;

import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerVisualizzaQuote implements ControllerInterface {
    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            if (request.getParameterMap().containsKey("ragazzi")) {
                template.addObject("TITOLOPAGINA", "Quote ragazzi");
                template.addObject("supplemento_anticipo", ConfigProperties.getProperty("SUPPLEMENTO_ENTRATA_ANTICIPATA_RAGAZZI"));
                template.addObject("supplemento_fuoricomune", ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_RAGAZZI"));
                template.addObject("prezzo_1", ConfigProperties.getProperty("PREZZO_1_RAGAZZI"));
                template.addObject("prezzo_2", ConfigProperties.getProperty("PREZZO_2_RAGAZZI"));
                template.addObject("prezzo_3", ConfigProperties.getProperty("PREZZO_3_RAGAZZI"));
                template.addObject("prezzo_4", ConfigProperties.getProperty("PREZZO_4_RAGAZZI"));
                template.addObject("prezzo_1_mensa", ConfigProperties.getProperty("PREZZO_1_MENSA_RAGAZZI"));
                template.addObject("prezzo_2_mensa", ConfigProperties.getProperty("PREZZO_2_MENSA_RAGAZZI"));
                template.addObject("prezzo_3_mensa", ConfigProperties.getProperty("PREZZO_3_MENSA_RAGAZZI"));
                template.addObject("prezzo_4_mensa", ConfigProperties.getProperty("PREZZO_4_MENSA_RAGAZZI"));
                template.addObject("prezzo_1_fratelli", ConfigProperties.getProperty("PREZZO_1_FRATELLI_RAGAZZI"));
                template.addObject("prezzo_2_fratelli", ConfigProperties.getProperty("PREZZO_2_FRATELLI_RAGAZZI"));
                template.addObject("prezzo_3_fratelli", ConfigProperties.getProperty("PREZZO_3_FRATELLI_RAGAZZI"));
                template.addObject("prezzo_4_fratelli", ConfigProperties.getProperty("PREZZO_4_FRATELLI_RAGAZZI"));
                template.addObject("prezzo_1_fratelli_mensa", ConfigProperties.getProperty("PREZZO_1_FRATELLI_MENSA_RAGAZZI"));
                template.addObject("prezzo_2_fratelli_mensa", ConfigProperties.getProperty("PREZZO_2_FRATELLI_MENSA_RAGAZZI"));
                template.addObject("prezzo_3_fratelli_mensa", ConfigProperties.getProperty("PREZZO_3_FRATELLI_MENSA_RAGAZZI"));
                template.addObject("prezzo_4_fratelli_mensa", ConfigProperties.getProperty("PREZZO_4_FRATELLI_MENSA_RAGAZZI"));
                template.setView("user/quoteragazzi.html");
            } else if (request.getParameterMap().containsKey("terzamedia")) {
                template.addObject("TITOLOPAGINA", "Quote ragazzi di terza media");
                template.addObject("supplemento_fuoricomune", ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_TERZAMEDIA"));
                template.addObject("prezzo_1", ConfigProperties.getProperty("PREZZO_1_TERZAMEDIA"));
                template.addObject("prezzo_2", ConfigProperties.getProperty("PREZZO_2_TERZAMEDIA"));
                template.addObject("prezzo_3", ConfigProperties.getProperty("PREZZO_3_TERZAMEDIA"));
                template.addObject("prezzo_4", ConfigProperties.getProperty("PREZZO_4_TERZAMEDIA"));
                template.setView("user/quoteterzamedia.html");
            } else {
                throw new IllegalArgumentException("Le quote sono disponibili solo per i ragazzi e i ragazzi di terza media.");
            }
        } catch (final RuntimeException | IOException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerVisualizzaQuote.class.getName());
        }
        return template;
    }
}
