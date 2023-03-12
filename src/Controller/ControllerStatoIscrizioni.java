package Controller;

import ApplicationContext.ApplicationContext;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.ConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerStatoIscrizioni implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "NULL");
        String state = request.getParameter("state");
        ConfigProperties properties = (ConfigProperties) ApplicationContext.getContext().get("Properties");
        switch (request.getParameter("target")) {
            case "ISCRRAG":
                properties.setProperty("ISCRRAG", state);
                break;
            case "ISCRAN":
                properties.setProperty("ISCRAN", state);
                break;
            case "ISCRTER":
                properties.setProperty("ISCRTER", state);
                break;
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
