package Controller;

import Response.FreemarkerTemplate;
import Response.Response;
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
                addWeeklyPrices(template, "", "_RAGAZZI");
                addWeeklyPrices(template, "_mensa", "_MENSA_RAGAZZI");
                addWeeklyPrices(template, "_fratelli", "_FRATELLI_RAGAZZI");
                addWeeklyPrices(template, "_fratelli_mensa", "_FRATELLI_MENSA_RAGAZZI");
                template.setView("user/quoteragazzi.html");
            } else if (request.getParameterMap().containsKey("terzamedia")) {
                template.addObject("TITOLOPAGINA", "Quote ragazzi di terza media");
                template.addObject("supplemento_fuoricomune", ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_TERZAMEDIA"));
                addWeeklyPrices(template, "", "_TERZAMEDIA");
                template.setView("user/quoteterzamedia.html");
            } else {
                throw new IllegalArgumentException("Le quote sono disponibili solo per i ragazzi e i ragazzi di terza media.");
            }
        } catch (final RuntimeException | IOException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerVisualizzaQuote.class.getName());
        }
        return template;
    }

    private static void addWeeklyPrices(FreemarkerTemplate template, String keySuffix, String propertySuffix)
            throws ConfigPropertyException, IOException {
        for (int week = 1; week <= 4; week++) {
            template.addObject("prezzo_" + week + keySuffix, ConfigProperties.getProperty("PREZZO_" + week + propertySuffix));
        }
    }
}
