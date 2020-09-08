package Controller ;
import ModelAndView.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerInterface {

    public ModelAndView handleRequest ( HttpServletRequest request,HttpServletResponse response );

}