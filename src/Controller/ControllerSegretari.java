package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerSegretari implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Gestisci segretari");
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            if (request.getParameterMap().isEmpty()) {
                List<Registrato> lseg = DAOMan.registratoDAO.findSegretari();
                if (!lseg.isEmpty()) {
                    mv.addObject("segretari", lseg);
                }
                mv.setView("ammseg/gestiscisegretari.html");

            } else if (request.getParameterMap().containsKey("del")) {
                DAOMan.registratoDAO.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("/RegistrazioneGrest/App/GestisciSegretari");

            } else if (request.getParameterMap().containsKey("promote")) {
                //problema in caso di più utenti omonimi, ma nel caso sistemo direttamente nel db
                Registrato r = DAOMan.registratoDAO.findByNominativo(request.getParameter("nome"), request.getParameter("cognome"));
                int level = Integer.parseInt(request.getParameter("level"));
                if (level > 0) //non ci si promuove ad amministratori dal portale!
                    r.setTipoUt(Integer.parseInt(request.getParameter("level")));
                DAOMan.registratoDAO.update(r);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciSegretari");
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerSegretari.class.getName());
        }
        return mv;
    }

}
