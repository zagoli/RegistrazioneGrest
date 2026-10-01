package ModelAndView;

import freemarker.template.Configuration;

import java.util.Objects;

public class RenderingContext {
    private final Configuration templateConfiguration;

    public RenderingContext(Configuration templateConfiguration) {
        this.templateConfiguration = Objects.requireNonNull(
                templateConfiguration,
                "La configurazione FreeMarker e obbligatoria"
        );
    }

    public Configuration getTemplateConfiguration() {
        return templateConfiguration;
    }
}
