package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller403 implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.setView("err/403.html");
        mv.addObject("TITOLOPAGINA", "403 - Non autorizzato");
        response.setStatus(403);
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
