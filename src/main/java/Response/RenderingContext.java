package Response;

import freemarker.template.Configuration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor()
public class RenderingContext {

    @NonNull
    private final Configuration templateConfiguration;

}
