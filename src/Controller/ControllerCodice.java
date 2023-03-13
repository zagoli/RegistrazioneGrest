package Controller;

import DAOManager.DAOMan;
import Domain.CodiceSbloccoIscrizione;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerCodice implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        if ("verifica".equals(request.getParameter("scope"))) {
            boolean result = false;
            mv.setView("user/rispostacodice.json");
            String codiceString = request.getParameter("codice");
            try {
                CodiceSbloccoIscrizione c = DAOMan.codiceSbloccoIscrizioneDAO.findByCodice(codiceString);
                if (c != null && c.getUtilizzato() == 0) {
                    //non so perchè ma l'oggetto preso dalla sessione è un intero
                    Integer idoInteger = (Integer) request.getSession().getAttribute("idUtente");
                    Short id = idoInteger.shortValue();
                    c.setUtilizzato(id);
                    c.setDataUtilizzo(new Timestamp(System.currentTimeMillis()));
                    DAOMan.codiceSbloccoIscrizioneDAO.update(c);
                    result = true;
                }
            } catch (SQLException ex) {
                mv.addObject("TITOLOPAGINA", "errore");

                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerCodice.class.getName()).log(Level.SEVERE, null, ex);
            }
            mv.addObject("result", result);
        }
        return mv;
    }

}
