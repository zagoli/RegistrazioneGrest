package Controller;

import DAOManager.DAOMan;
import Domain.DatiPagamento;
import Domain.PagamentoRiepilogo;
import Domain.Ragazzo;
import Domain.TerzaMedia;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    List<Object[]> ragazzi = new ArrayList<>();
                    for (DatiPagamento<Ragazzo, PagamentoRiepilogo> dato : DAOMan.ragazzoDAO.findAllConPagamento()) {
                        ragazzi.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
                    }
                    template.addObject("ragazzi", ragazzi);
                    break;
                case "an":
                    template.setView("ammseg/visualizzaanimatori.html");
                    template.addObject("TITOLOPAGINA", "Visualizza animatori");
                    template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                    break;
                case "ter":
                    template.setView("ammseg/visualizzaterzamedia.html");
                    template.addObject("TITOLOPAGINA", "Visualizza terza media");
                    List<Object[]> terzamedia = new ArrayList<>();
                    for (DatiPagamento<TerzaMedia, PagamentoRiepilogo> dato : DAOMan.terzamediaDAO.findAllConPagamento()) {
                        terzamedia.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
                    }
                    template.addObject("terzamedia", terzamedia);
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerVisualizzaIscritti.class.getName());
        }
        return template;
    }

}
