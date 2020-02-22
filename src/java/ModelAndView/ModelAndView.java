package ModelAndView;

import java.util.HashMap;

public interface ModelAndView {

    public void addObject(String property , Object obj);
    public HashMap<String, Object> getMap();
    public String getView();
    public void setView(String view);
}
