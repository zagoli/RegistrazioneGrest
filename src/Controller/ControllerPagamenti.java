package Controller;

import DAOManager.DAOMan;
import Domain.Pagamento;
import Domain.Ragazzo;
import Domain.RelPresenzaRag;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerPagamenti implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
        try {
            if (request.getParameterMap().isEmpty()) {
                //forse più veloce?
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

    protected static float calcolaQuota(Ragazzo r) throws SQLException {
        float quota = 0;
        List<RelPresenzaRag> lrpr = DAOMan.relPresenzaRagDAO.findByRagazzoId(r.getId());
        //cambiare con metodo matematico se possibile
        if (r.getFratelloIscritto()) {
            if (r.getMensa()) {
                switch (lrpr.size()) {
                    case 1:
                        quota = 75;
                        break;
                    case 2:
                        quota = 120;
                        break;
                    case 3:
                        quota = 165;
                        break;
                    case 4:
                        quota = 210;
                        break;
                }
            } else {
                switch (lrpr.size()) {
                    case 1:
                        quota = 50;
                        break;
                    case 2:
                        quota = 70;
                        break;
                    case 3:
                        quota = 90;
                        break;
                    case 4:
                        quota = 110;
                        break;
                }
            }
        } else {
            if (r.getMensa()) {
                switch (lrpr.size()) {
                    case 1:
                        quota = 90;
                        break;
                    case 2:
                        quota = 145;
                        break;
                    case 3:
                        quota = 195;
                        break;
                    case 4:
                        quota = 240;
                        break;
                }
            } else {
                switch (lrpr.size()) {
                    case 1:
                        quota = 65;
                        break;
                    case 2:
                        quota = 95;
                        break;
                    case 3:
                        quota = 120;
                        break;
                    case 4:
                        quota = 140;
                        break;
                }
            }
        }
        if (r.getEntrataAnticipata()) {
            quota += 10 * lrpr.size();
        }
        //se non è di pescantina aggiungi 15 a settimana
        if (!Checker.checkIsFromPescantina(r)) {
            quota += 15 * lrpr.size();
        }
        return quota;
    }

}
