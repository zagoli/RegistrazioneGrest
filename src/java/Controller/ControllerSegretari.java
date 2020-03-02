package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerSegretari implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Gestisci segretari");
        try {
            if (request.getParameterMap().isEmpty()) {
                List<Registrato> lallut = DAOMan.registratoDAO.findAll();
                List<Registrato> lseg;
                lseg = lallut.stream().filter(reg -> (reg.getTipoUt() < 3 && reg.getTipoUt() > 0)).collect(Collectors.toList());
                mv.addObject("segretari", lseg);
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
        } catch (IOException | NumberFormatException | SQLException | NullPointerException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerSegretari.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

}
