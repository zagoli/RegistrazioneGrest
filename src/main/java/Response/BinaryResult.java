package Response;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public class BinaryResult implements Response {
    private final String contentType;
    private final String fileName;
    private final byte[] content;

    public BinaryResult(String contentType, String fileName, byte[] content) {
        this.contentType = requireHeaderValue(contentType, "Il content type è obbligatorio");
        this.fileName = requireHeaderValue(fileName, "Il nome file è obbligatorio");
        this.content = Objects.requireNonNull(content, "Il contenuto binario è obbligatorio");
    }

    private static String requireHeaderValue(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        if (value.indexOf('\r') >= 0 || value.indexOf('\n') >= 0 || value.indexOf('"') >= 0) {
            throw new IllegalArgumentException("Il valore contiene caratteri non validi per un header HTTP");
        }
        return value;
    }

    @Override
    public void render(HttpServletResponse response, RenderingContext context) throws IOException {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.getOutputStream().write(content);
    }
}
