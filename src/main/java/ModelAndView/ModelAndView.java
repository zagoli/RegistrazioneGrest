package ModelAndView;

import java.util.HashMap;

public abstract class ModelAndView extends ControllerResult {

    public abstract void addObject(String property, Object obj);

    public abstract HashMap<String, Object> getMap();

    public abstract String getView();

    public abstract void setView(String view);
}
