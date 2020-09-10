package ModelAndView;

import java.util.HashMap;

public class ModelAndViewStandard implements ModelAndView   {

    protected String  view = "/err/errore.html";
    protected boolean redirect = false;
    final HashMap<String,Object> map = new HashMap<>();

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
    public void addObject ( String prop, Object ob){
        this.map.put(prop, ob);
    }

    @Override
     public HashMap<String, Object> getMap() {
        return map;
    }

}