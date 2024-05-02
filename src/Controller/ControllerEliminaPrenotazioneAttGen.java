package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerEliminaPrenotazioneAttGen implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            RelCollabora toDeleteCollabora = DAOMan.relCollaboraDAO.findById(id);
            DAOMan.relCollaboraDAO.delete(toDeleteCollabora);
            response.sendRedirect("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerEliminaPrenotazioneAttGen.class.getName());
        }
        return mv;
    }

}
