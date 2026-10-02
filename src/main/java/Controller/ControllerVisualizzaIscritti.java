package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerVisualizzaIscritti implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            switch (request.getParameter("target")) {
                case "rag":
                    mv.setView("ammseg/visualizzaragazzi.html");
                    mv.addObject("TITOLOPAGINA", "Visualizza ragazzi");
                    List<Object[]> ragazzi = new ArrayList<>();
                    for (DatiPagamento<Ragazzo, PagamentoRiepilogo> dato : DAOMan.ragazzoDAO.findAllConPagamento()) {
                        ragazzi.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
                    }
                    mv.addObject("ragazzi", ragazzi);
                    break;
                case "an":
                    mv.setView("ammseg/visualizzaanimatori.html");
                    mv.addObject("TITOLOPAGINA", "Visualizza animatori");
                    mv.addObject("animatori", DAOMan.animatoreDAO.findAll());
                    break;
                case "ter":
                    mv.setView("ammseg/visualizzaterzamedia.html");
                    mv.addObject("TITOLOPAGINA", "Visualizza terza media");
                    List<Object[]> terzamedia = new ArrayList<>();
                    for (DatiPagamento<Terzamedia, PagamentoRiepilogo> dato : DAOMan.terzamediaDAO.findAllConPagamento()) {
                        terzamedia.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
                    }
                    mv.addObject("terzamedia", terzamedia);
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerVisualizzaIscritti.class.getName());
        }
        return mv;
    }

}
