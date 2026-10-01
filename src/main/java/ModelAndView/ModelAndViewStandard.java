package ModelAndView;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

public class ModelAndViewStandard extends ModelAndView {

    final HashMap<String, Object> map = new HashMap<>();
    protected String view = "err/errore.html";

    public ModelAndViewStandard() {
    }

    public ModelAndViewStandard(String view) {
        this.view = view;
    }

    @Override
    public String getView() {
        return view;
    }

    @Override
    public void setView(String viewJsp) {
        this.view = viewJsp;
    }

    @Override
    public void addObject(String prop, Object ob) {
        this.map.put(prop, ob);
    }

    @Override
    public HashMap<String, Object> getMap() {
        return map;
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

        String contentType = view.endsWith("json")
                ? "application/json; charset=UTF-8"
                : "text/html; charset=UTF-8";
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(renderedView.toString());
    }
}