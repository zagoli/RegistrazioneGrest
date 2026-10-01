package Controller;

import ModelAndView.ControllerResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerInterface {

    ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response);

}