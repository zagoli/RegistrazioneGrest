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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerPagamenti implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
        if (request.getParameterMap().isEmpty()) {
            try {
                List<Ragazzo> listRagazzo = DAOMan.ragazzoDAO.findAll();
                List<Object[]> datiRagazzi = new LinkedList<>();
                if (!listRagazzo.isEmpty()) {
                    for (Ragazzo ragazzo : listRagazzo) {
                        Pagamento p = DAOMan.pagamentoDAO.findByRagazzoId(ragazzo.getId());
                        if (p != null) {
                            Object[] o = {ragazzo, true, p};
                            datiRagazzi.add(o);
                        } else {
                            Object[] o = {ragazzo, false, ControllerPagamenti.calcolaQuota(ragazzo)};
                            datiRagazzi.add(o);
                        }
                    }
                    mv.addObject("ragazzi", datiRagazzi);
                }
                mv.setView("ammseg/gestiscipagamenti.html");
            } catch (NullPointerException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerPagamenti.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (request.getParameterMap().containsKey("addPagamento")) {
            try {
                float quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                int idRagazzo = Integer.parseInt(request.getParameter("addPagamento"));
                int idUt = (int) request.getSession().getAttribute("idUtente");
                Pagamento p = new Pagamento();
                p.setOrdineArrivo(Integer.parseInt(request.getParameter("ordineArrivo")));
                p.setData(new Date());
                p.setQuota(quota);
                p.setRagazzo(DAOMan.ragazzoDAO.findById(idRagazzo));
                p.setRegistrato(DAOMan.registratoDAO.findById(idUt));
                DAOMan.pagamentoDAO.insert(p);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamenti");
            } catch (NullPointerException | IOException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerPagamenti.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (request.getParameterMap().containsKey("deletePagamento")) {
            try {
                int idRagazzo = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoDAO.deleteFromRagazzoId(idRagazzo);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamenti");
            } catch (NullPointerException | IOException | SQLException ex) {
                mv.setView("err/errore.html");
                mv.addObject("eccezione", ex);
                Logger.getLogger(ControllerPagamenti.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            quota += 10*lrpr.size();
        }
        //se non è di pescantina aggiungi 15 a settimana
        if (!Checker.checkIsFromPescantina(r)) {
            quota += 15*lrpr.size();
        }
        return quota;
    }
    
}
