package Controller;

import Response.Response;
import Response.FreemarkerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerProfiloDisabilitato implements ControllerInterface{
    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate("err/profilodisabilitato.html");
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        template.addObject("tipoUt", tipoUt);
        return template;
    }
}
