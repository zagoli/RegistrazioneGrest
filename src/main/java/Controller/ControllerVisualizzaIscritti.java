package Controller;

import DAOManager.DAOMan;
import Domain.Pagamento;
import Domain.PagamentoTerzamedia;
import Domain.Ragazzo;
import Domain.Terzamedia;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ControllerVisualizzaIscritti implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            switch (request.getParameter("target")) {
                case "rag":
                    mv.setView("ammseg/visualizzaragazzi.html");
                    mv.addObject("TITOLOPAGINA", "Visualizza ragazzi");
                    List<Ragazzo> lrag = DAOMan.ragazzoDAO.findAll();
                    List<Object[]> ragazzi = new LinkedList<>();
                    for (Ragazzo rag : lrag) {
                        Pagamento p = DAOMan.pagamentoDAO.findByRagazzoId(rag.getId());
                        Object[] o = {rag, p};
                        ragazzi.add(o);
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
                    List<Terzamedia> lter = DAOMan.terzamediaDAO.findAll();
                    List<Object[]> terzamedia = new LinkedList<>();
                    for (Terzamedia ter : lter) {
                        PagamentoTerzamedia p = DAOMan.pagamentoTerzamediaDAO.findByTerzamediaId(ter.getId());
                        Object[] o = {ter, p};
                        terzamedia.add(o);
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
