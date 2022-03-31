package Controller;

import DAOManager.DAOMan;
import Domain.Pagamento;
import Domain.Ragazzo;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerPagamenti implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
        try {
            if (request.getParameterMap().isEmpty()) {
                // pagamento contiene solo l'id del ragazzo, quindi sono stupido e non si può migliorare
                List<Ragazzo> listRagazzo = DAOMan.ragazzoDAO.findAll();
                List<Pagamento> pagamenti = DAOMan.pagamentoDAO.findAll();
                Set<Object[]> datiRagazzi = new HashSet<>();
                if (!listRagazzo.isEmpty()) {
                    for (Ragazzo ragazzo : listRagazzo) {
                        Optional<Pagamento> p = pagamenti.parallelStream().filter(pag -> pag.getRagazzoId() == ragazzo.getId()).findFirst();
                        if (p.isPresent()) {
                            Object[] o = {ragazzo, true, p.get()};
                            datiRagazzi.add(o);
                        } else {
                            Object[] o = {ragazzo, false, ControllerPagamenti.calcolaQuota(ragazzo)};
                            datiRagazzi.add(o);
                        }
                    }
                    mv.addObject("ragazzi", datiRagazzi);
                }
                mv.setView("ammseg/gestiscipagamenti.html");
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                float quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                int idRagazzo = Integer.parseInt(request.getParameter("addPagamento"));
                int idUt = (int) request.getSession().getAttribute("idUtente");
                DAOMan.pagamentoDAO.insert(Integer.parseInt(request.getParameter("ordineArrivo")),quota, idRagazzo, idUt);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamenti");
            } else if (request.getParameterMap().containsKey("deletePagamento")) {
                int id = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoDAO.delete(id);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamenti");
            }
        } catch (NullPointerException | IOException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerPagamenti.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

    //cambiare con metodo matematico se possibile
    protected static float calcolaQuota(Ragazzo r) throws SQLException {
        int supplementoAnticipo = 10;
        int supplementoFuoriComune = 15;
        int nSettimane = DAOMan.relPresenzaRagDAO.findByRagazzoId(r.getId()).size();
        int[][][] quotaBase = {
                // normale
                {   //senza mensa   |   con mensa
                        {50, 75},    // una settimana
                        {75, 125},   // due settimane
                        {95, 170},   // tre settimane
                        {115, 215}    // quattro settimane
                },
                // fratello iscritto
                {   //senza mensa   |   con mensa
                        {40, 60},    // una settimana
                        {60, 100},   // due settimane
                        {76, 136},   // tre settimane
                        {95, 172}    // quattro settimane
                }
        };
        return 0;
        /*
        return quotaBase[r.getFratelloIscritto() ? 1 : 0][nSettimane - 1][r.getMensa() ? 1 : 0] +
                nSettimane * (r.getEntrataAnticipata() ? supplementoAnticipo : 0) +
                nSettimane * (Checker.checkIsFromPescantina(r) ? 0 : supplementoFuoriComune);*/
    }

}
