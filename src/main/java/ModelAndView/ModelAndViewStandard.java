package ModelAndView;

import java.util.HashMap;

public class ModelAndViewStandard implements ModelAndView {

    final HashMap<String, Object> map = new HashMap<>();
    protected String view = "/err/errore.html";

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

}