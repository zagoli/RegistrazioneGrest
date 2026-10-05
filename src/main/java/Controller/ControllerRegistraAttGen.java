package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerRegistraAttGen implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "errore");
            RelCollabora rc = new RelCollabora();
            int idUt = (int) request.getSession().getAttribute("idUtente");
            int idAttivita = Integer.parseInt(request.getParameter("attivita"));
            String data = request.getParameter("data");
            if (data != null) {
                rc.setData(data);
            }
            rc.setRegistratoId(idUt);
            rc.setAttivitaGenId(idAttivita);
            DAOMan.relCollaboraDAO.insert(rc);
            return new RedirectResult("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraAttGen.class.getName());
        }
        return template;
    }
}
