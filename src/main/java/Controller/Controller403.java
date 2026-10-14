package Controller;

import Response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller403 implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return ErrorPageSupport.statusPage(request, response, 403, "403 - Non autorizzato");
    }

}
