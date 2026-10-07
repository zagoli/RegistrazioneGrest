package Controller;

import Response.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerInterface {

    Response handleRequest(HttpServletRequest request, HttpServletResponse response);

}