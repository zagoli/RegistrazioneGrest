package Controller;

import Response.FreemarkerTemplate;
import Response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerProfiloDisabilitato implements ControllerInterface {
    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return new FreemarkerTemplate("err/profilodisabilitato.html");
    }
}
