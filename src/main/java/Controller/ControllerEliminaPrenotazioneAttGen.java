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

public class ControllerEliminaPrenotazioneAttGen implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            RelCollabora toDeleteCollabora = DAOMan.relCollaboraDAO.findById(id);
            DAOMan.relCollaboraDAO.delete(toDeleteCollabora);
            return new RedirectResult("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerEliminaPrenotazioneAttGen.class.getName());
        }
        return template;
    }

}
