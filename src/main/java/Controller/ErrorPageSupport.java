package Controller;

import Response.FreemarkerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

final class ErrorPageSupport {
    private ErrorPageSupport() {
    }

    static FreemarkerTemplate statusPage(HttpServletRequest request, HttpServletResponse response, int status, String title) {
        FreemarkerTemplate template = new FreemarkerTemplate("err/" + status + ".html");
        template.setTitoloPagina(title);
        response.setStatus(status);
        template.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
        return template;
    }
}
