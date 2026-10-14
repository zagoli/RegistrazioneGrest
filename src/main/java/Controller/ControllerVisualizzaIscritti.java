package Controller;

import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerVisualizzaIscritti implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            switch (request.getParameter("target")) {
                case "rag":
                    template.setView("ammseg/visualizzaragazzi.html");
                    template.addObject("TITOLOPAGINA", "Visualizza ragazzi");
                    PagamentiSupport.addPaymentRows(template, "ragazzi", DAOMan.ragazzoDAO.findAllConPagamento());
                    break;
                case "an":
                    template.setView("ammseg/visualizzaanimatori.html");
                    template.addObject("TITOLOPAGINA", "Visualizza animatori");
                    template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                    break;
                case "ter":
                    template.setView("ammseg/visualizzaterzamedia.html");
                    template.addObject("TITOLOPAGINA", "Visualizza terza media");
                    PagamentiSupport.addPaymentRows(template, "terzamedia", DAOMan.terzamediaDAO.findAllConPagamento());
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerVisualizzaIscritti.class.getName());
        }
        return template;
    }

}
