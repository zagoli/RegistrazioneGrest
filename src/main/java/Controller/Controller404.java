package Controller;

import Response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller404 implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate("err/" + 404 + ".html");
        template.setTitoloPagina("404 - pagina non trovata");
        response.setStatus(404);
        template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
        return template;
    }

}
