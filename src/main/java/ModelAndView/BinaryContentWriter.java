package ModelAndView;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface BinaryContentWriter {
    void write(OutputStream outputStream);
}
