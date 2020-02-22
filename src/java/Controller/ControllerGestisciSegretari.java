package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerGestisciSegretari implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Gestisci segretari");
        if (request.getParameterMap().isEmpty()) {
            try {
                List<Registrato> lallut = DAOMan.registratoDAO.findAll();
                List<Registrato> lseg = new LinkedList<>();
                lallut.stream().filter((reg) -> (reg.getTipoUt() < 3 && reg.getTipoUt() > 0)).forEachOrdered((reg) -> {
                    lseg.add(reg);
                });
                mv.addObject("segretari", lseg);
                mv.setView("ammseg/gestiscisegretari.html");
            } catch (NullPointerException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerGestisciSegretari.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (request.getParameterMap().containsKey("del")) {
            try {
                DAOMan.registratoDAO.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("/RegistrazioneGrest/App/GestisciSegretari");
            } catch (NullPointerException | IOException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerGestisciSegretari.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
