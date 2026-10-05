package Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Response {
    void render(HttpServletResponse response, RenderingContext context)  throws ServletException, IOException;
}
