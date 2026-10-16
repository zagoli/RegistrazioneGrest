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
            switch (request.getParameter("target")) {
                case "rag":
                    template.setView("ammseg/visualizzaragazzi.html");
                    template.setTitoloPagina("Visualizza ragazzi");
                    List<DatiPagamento<Ragazzo, PagamentoRiepilogo>> iscritti1 = DAOMan.ragazzoDAO.findAllConPagamento();
                    List<Object[]> rows1 = new ArrayList<>();
                    for (DatiPagamento<Ragazzo, PagamentoRiepilogo> dato1 : iscritti1) {
                        rows1.add(new Object[]{dato1.getIscritto(), dato1.getPagamento()});
                    }
                    template.addObject("ragazzi", rows1);
                    break;
                case "an":
                    template.setView("ammseg/visualizzaanimatori.html");
                    template.setTitoloPagina("Visualizza animatori");
                    template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                    break;
                case "ter":
                    template.setView("ammseg/visualizzaterzamedia.html");
                    template.setTitoloPagina("Visualizza terza media");
                    List<DatiPagamento<TerzaMedia, PagamentoRiepilogo>> iscritti = DAOMan.terzamediaDAO.findAllConPagamento();
                    List<Object[]> rows = new ArrayList<>();
                    for (DatiPagamento<TerzaMedia, PagamentoRiepilogo> dato : iscritti) {
                        rows.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
                    }
                    template.addObject("terzamedia", rows);
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerVisualizzaIscritti.class.getName());
        }
        return template;
    }

}
