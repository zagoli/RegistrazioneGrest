package Controller;

import ModelAndView.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerInterface {

    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response);

}