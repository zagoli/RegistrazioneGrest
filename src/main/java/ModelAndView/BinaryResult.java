package ModelAndView;

import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class BinaryResult implements Response {
    private final String contentType;
    private final String fileName;
    private final BinaryContentWriter contentWriter;

    public BinaryResult(String contentType, String fileName, BinaryContentWriter contentWriter) {
        this.contentType = requireHeaderValue(contentType, "Il content type è obbligatorio");
        this.fileName = requireHeaderValue(fileName, "Il nome file è obbligatorio");
        this.contentWriter = Objects.requireNonNull(contentWriter, "Il writer binario è obbligatorio");
    }

    @Override
    public void render(HttpServletResponse response, RenderingContext context) throws IOException {
        ByteArrayOutputStream renderedContent = new ByteArrayOutputStream();
        contentWriter.write(renderedContent);

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setContentLengthLong(renderedContent.size());
        OutputStream outputStream = response.getOutputStream();
        renderedContent.writeTo(outputStream);
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
}
