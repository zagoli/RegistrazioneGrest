package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerStatoIscrizioni implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard("user/rispostastatoiscrizioni.json");
        boolean success = false;
        try {
            String state = request.getParameter("state");
            success = switch (request.getParameter("target")) {
                case "ISCRRAG" -> {
                    ConfigProperties.setProperty("ISCRRAG", state);
                    yield true;
                }
                case "ISCRAN" -> {
                    ConfigProperties.setProperty("ISCRAN", state);
                    yield true;
                }
                case "ISCRTER" -> {
                    ConfigProperties.setProperty("ISCRTER", state);
                    yield true;
                }
                default -> false;
            };
            mv.addObject("error", "none");
        } catch (final RuntimeException | IOException | ConfigPropertyException e) {
            Utils.logException(e, ControllerStatoIscrizioni.class.getName());
            mv.addObject("error", e.getMessage());
        } finally {
            mv.addObject("result", success);
        }
        return mv;
    }

}
