package Response;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RedirectResult implements Response {
    private final String location;

    public RedirectResult(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("La destinazione del redirect è obbligatoria");
        }
        if (location.indexOf('\r') >= 0 || location.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("La destinazione contiene caratteri non validi");
        }
        this.location = location;
    }

    @Override
    public void render(HttpServletResponse response, RenderingContext context) throws IOException {
        response.sendRedirect(location);
    }
}
