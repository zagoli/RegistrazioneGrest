package ModelAndView;

import java.util.HashMap;

public interface ModelAndView {

    void addObject(String property, Object obj);
    HashMap<String, Object> getMap();
    String getView();
    void setView(String view);
}
