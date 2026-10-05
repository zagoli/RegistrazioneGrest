package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ControllerSegretari implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerSegretari.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Gestisci segretari");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            if (request.getParameterMap().isEmpty()) {
                List<Registrato> lseg = DAOMan.registratoDAO.findSegretari();
                List<Registrato> lamm = DAOMan.registratoDAO.findAmministratori();
                if (!lseg.isEmpty()) {
                    template.addObject("segretari", lseg);
                }
                template.addObject("amministratori", lamm);
                template.setView("ammseg/gestiscisegretari.html");

            } else if (request.getParameterMap().containsKey("del")) {
                DAOMan.registratoDAO.delete(Integer.parseInt(request.getParameter("id")));
                return new RedirectResult("/RegistrazioneGrest/App/GestisciSegretari");

            } else if (request.getParameterMap().containsKey("promote")) {
                //problema in caso di più utenti omonimi, ma nel caso sistemo direttamente nel db
                Registrato r = DAOMan.registratoDAO.findByNominativo(request.getParameter("nome"), request.getParameter("cognome"));
                int oldLevel = r.getTipoUt();
                int level = Integer.parseInt(request.getParameter("level"));
                if (level > 0) {
                    //non ci si promuove ad amministratori dal portale!
                    r.setTipoUt(Integer.parseInt(request.getParameter("level")));
                    DAOMan.registratoDAO.update(r);
                    logger.info("Registrato {} {} passato da tipo utente {} a {}.", r.getNome(), r.getCognome(), oldLevel, level);
                } else {
                    logger.warn("Registrato {} {} non promosso ad amministratore.", r.getNome(), r.getCognome());
                }
                return new RedirectResult("/RegistrazioneGrest/App/GestisciSegretari");
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerSegretari.class.getName());
        }
        return template;
    }

}
