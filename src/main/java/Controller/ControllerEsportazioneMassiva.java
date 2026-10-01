package Controller;

import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerEsportazioneMassiva implements ControllerInterface {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard("err/errore.html");
        mv.addObject("TITOLOPAGINA", "Esportazione Excel");
        mv.addObject(
                "eccezione",
                new UnsupportedOperationException("L'esportazione Excel non e ancora implementata")
        );
        return mv;
    }
}
