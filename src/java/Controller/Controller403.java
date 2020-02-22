package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller403 implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.setView("err/403.html");
        mv.addObject("TITOLOPAGINA", "403 - Non autorizzato");
        
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
    
}
