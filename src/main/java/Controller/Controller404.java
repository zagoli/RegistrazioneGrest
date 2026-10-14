package Controller;

import Response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller404 implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return ErrorPageSupport.statusPage(request, response, 404, "404 - pagina non trovata");
    }

}
