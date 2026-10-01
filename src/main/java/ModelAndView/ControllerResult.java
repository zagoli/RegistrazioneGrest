package ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class ControllerResult {
    public abstract void render(HttpServletResponse response, RenderingContext context)
            throws ServletException, IOException;
}
