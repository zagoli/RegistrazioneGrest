package Controller;

import DAOManager.DAOMan;
import Domain.CodiceSbloccoIscrizione;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ControllerCodice implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard("user/rispostacodice.json");
        boolean success = false;
        try {
            if (request.getParameter("scope").equals("verifica")) {
                String codiceString = request.getParameter("codice");
                CodiceSbloccoIscrizione c = DAOMan.codiceSbloccoIscrizioneDAO.findByCodice(codiceString);
                if (c != null && c.getUtilizzato() == 0) {
                    Integer idoInteger = (Integer) request.getSession().getAttribute("idUtente");
                    Short id = idoInteger.shortValue();
                    c.setUtilizzato(id);
                    c.setDataUtilizzo(new Timestamp(System.currentTimeMillis()));
                    DAOMan.codiceSbloccoIscrizioneDAO.update(c);
                    success = true;
                }
            }
        } catch (final RuntimeException | SQLException e) {
            Utils.logException(e, ControllerCodice.class.getName());
        } finally {
            mv.addObject("result", success);
        }
        return mv;
    }

}
