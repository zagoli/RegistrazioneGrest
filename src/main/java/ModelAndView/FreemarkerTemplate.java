package ModelAndView;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

@NoArgsConstructor
public class FreemarkerTemplate implements Response {

    @Getter
    final HashMap<String, Object> map = new HashMap<>();
    @Setter
    protected String view = "err/errore.html";

    public FreemarkerTemplate(String view) {
        this.view = view;
    }

    public void addObject(String prop, Object ob) {
        this.map.put(prop, ob);
    }

    public void setTitoloPagina(String titoloPagina) {
        this.addObject("TITOLOPAGINA", titoloPagina);
    }

    @Override
    public void render(HttpServletResponse response, RenderingContext context)
            throws ServletException, IOException {
        StringWriter renderedView = new StringWriter();
        try {
            Template template = context.getTemplateConfiguration().getTemplate(view + ".ftl");
            template.process(map, renderedView);
        } catch (TemplateException | IOException e) {
            throw new ServletException("Errore durante il rendering della vista " + view, e);
        }

        String contentType = view.endsWith("json")  ? "application/json; charset=UTF-8" : "text/html; charset=UTF-8";
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(renderedView.toString());
    }
}