package Controller;

import DAOManager.DAOMan;
import Domain.RelCollabora;
import ModelAndView.ControllerResult;
import ModelAndView.ModelAndView;
import ModelAndView.RedirectResult;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerEliminaPrenotazioneAttGen implements ControllerInterface {

    @Override
    public ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response) {
        new ModelAndViewStandard();
        ModelAndView mv;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            RelCollabora toDeleteCollabora = DAOMan.relCollaboraDAO.findById(id);
            DAOMan.relCollaboraDAO.delete(toDeleteCollabora);
            return new RedirectResult("/RegistrazioneGrest/App/DashboardAttGen");
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerEliminaPrenotazioneAttGen.class.getName());
        }
        return mv;
    }

}
