package Controller;

import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller404 implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate("err/404.html");
        template.setTitoloPagina("404 - pagina non trovata");
        response.setStatus(404);
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        template.addObject("tipoUt", tipoUt);
        return template;
    }

}
