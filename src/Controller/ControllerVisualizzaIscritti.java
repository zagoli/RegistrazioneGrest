package Controller;

import DAOManager.DAOMan;
import Domain.Pagamento;
import Domain.PagamentoTerzamedia;
import Domain.Ragazzo;
import Domain.Terzamedia;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerVisualizzaIscritti implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
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
        } catch (SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezioni", ex);
            Logger.getLogger(ControllerVisualizzaIscritti.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
