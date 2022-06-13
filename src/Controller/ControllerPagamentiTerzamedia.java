package Controller;

import DAOManager.DAOMan;
import Domain.PagamentoTerzamedia;
import Domain.Terzamedia;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;

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

public class ControllerPagamentiTerzamedia implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Gestisci pagamenti terzamedia");
        try {
            if (request.getParameterMap().isEmpty()) {
                List<Terzamedia> listTerzamedia = DAOMan.terzamediaDAO.findAll();
                List<PagamentoTerzamedia> pagamenti = DAOMan.pagamentoTerzamediaDAO.findAll();
                Set<Object[]> datiTerzamedia = new HashSet<>();
                if (!listTerzamedia.isEmpty()) {
                    for (Terzamedia terzamedia : listTerzamedia) {
                        Optional<PagamentoTerzamedia> p = pagamenti.parallelStream().filter(pag -> pag.getTerzamediaId() == terzamedia.getId()).findFirst();
                        if (p.isPresent()) {
                            Object[] o = {terzamedia, true, p.get()};
                            datiTerzamedia.add(o);
                        } else {
                            Object[] o = {terzamedia, false, ControllerPagamentiTerzamedia.calcolaQuota(terzamedia)};
                            datiTerzamedia.add(o);
                        }
                    }
                    mv.addObject("terzamedia", datiTerzamedia);
                }
                mv.setView("ammseg/gestiscipagamentiterzamedia.html");
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                float quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                int idTerzamedia = Integer.parseInt(request.getParameter("addPagamento"));
                int idUt = (int) request.getSession().getAttribute("idUtente");
                DAOMan.pagamentoTerzamediaDAO.insert(Integer.parseInt(request.getParameter("ordineArrivo")), quota, idTerzamedia, idUt);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamentiTerzamedia");
            } else if (request.getParameterMap().containsKey("deletePagamento")) {
                int id = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoTerzamediaDAO.delete(id);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamentiTerzamedia");
            }
        } catch (NullPointerException | IOException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerPagamentiTerzamedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }

    protected static float calcolaQuota(Terzamedia t) throws SQLException {
        final int[] quota = {60, 90, 120, 140};
        final int supplementoFuoriComune = 15;
        final int nSettimane = DAOMan.relPresenzaTerDAO.findByTerzamediaId(t.getId()).size();
        return quota[nSettimane - 1] +
                nSettimane * (Checker.checkIsFromPescantina(t.getRegistrato().getLocalita()) ? 0 : supplementoFuoriComune);
    }

}
