package Controller;

import Response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller403 implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate("err/" + 403 + ".html");
        template.setTitoloPagina("403 - Non autorizzato");
        response.setStatus(403);
        template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
        return template;
    }

}
