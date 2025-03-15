package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller404 implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.setView("err/404.html");
        mv.addObject("TITOLOPAGINA", "404 - pagina non trovata");
        response.setStatus(404);
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
