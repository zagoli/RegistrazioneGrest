package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerStatoIscrizioni implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            String state = request.getParameter("state");
            switch (request.getParameter("target")) {
                case "ISCRRAG":
                    ConfigProperties.setProperty("ISCRRAG", state);
                    break;
                case "ISCRAN":
                    ConfigProperties.setProperty("ISCRAN", state);
                    break;
                case "ISCRTER":
                    ConfigProperties.setProperty("ISCRTER", state);
                    break;
            }
        } catch (final RuntimeException | IOException | ConfigPropertyException e) {
            Utils.logException(e, ControllerStatoIscrizioni.class.getName());
        }
        return mv;
    }

}
