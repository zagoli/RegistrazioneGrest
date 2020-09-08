package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.Animatore;
import Domain.AttivitaGen;
import Domain.Calendario;
import Domain.ContattoUrgenze;
import Domain.Pagamento;
import Domain.PagamentoTerzamedia;
import Domain.Ragazzo;
import Domain.RelPresenzaAn;
import Domain.RelPresenzaRag;
import Domain.RelPresenzaTer;
import Domain.Terzamedia;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerInfoDettaglio implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("TITOLOPAGINA", "Info dettagliate");
        String target = request.getParameter("target");
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            switch (target) {
                case "infoani": {
                    Animatore a = DAOMan.animatoreDAO.findById(id);
                    mv.addObject("animatore", a);
                    int idRegistrato = a.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        mv.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaAn> listRelPresenzaAn = DAOMan.relPresenzaAnDAO.findByAnimatoreId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaAn rpa : listRelPresenzaAn) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpa.getCalendarioId());
                        listCalendario.add(c);
                    }
                    mv.addObject("calendari", listCalendario);
                    mv.setView("ammseg/dettaglioanimatore.html");
                    break;
                }
                case "inforag": {
                    Ragazzo r = DAOMan.ragazzoDAO.findById(id);
                    mv.addObject("ragazzo", r);
                    int idRegistrato = r.getRegistrato().getId();
                    List listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        mv.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaRag> listRelPresenzaRag = DAOMan.relPresenzaRagDAO.findByRagazzoId(id);
                    List listCalendario = new LinkedList<>();
                    for (RelPresenzaRag rpr : listRelPresenzaRag) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpr.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGen = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    mv.addObject("attgen", listAttivitaGen);
                    mv.addObject("calendari", listCalendario);
                    Pagamento pagamento = DAOMan.pagamentoDAO.findByRagazzoId(id);
                    mv.addObject("pagamento", pagamento);
                    mv.setView("ammseg/dettaglioragazzo.html");
                    break;
                }
                case "schedarag": {
                    Ragazzo r = DAOMan.ragazzoDAO.findById(id);
                    mv.addObject("ragazzo", r);
                    int idRegistrato = r.getRegistrato().getId();
                    List listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        mv.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaRag> listRelPresenzaRag = DAOMan.relPresenzaRagDAO.findByRagazzoId(id);
                    List listCalendario = new LinkedList<>();
                    for (RelPresenzaRag rpr : listRelPresenzaRag) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpr.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGenComp = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    List<AttivitaGen> listAttivitaGen = listAttivitaGenComp.stream().distinct().collect(Collectors.toList());
                    mv.addObject("attgen", listAttivitaGen);
                    mv.addObject("calendari", listCalendario);
                    mv.addObject("quotaIscrizione", ControllerPagamenti.calcolaQuota(r));
                    mv.setView("stampe/schedaragazzo.html");
                    break;
                }
                case "infoter": {
                    Terzamedia t = DAOMan.terzamediaDAO.findById(id);
                    mv.addObject("terzamedia", t);
                    int idRegistrato = t.getRegistrato().getId();
                    List listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<RelPresenzaTer> listRelPresenzaTer = DAOMan.relPresenzaTerDAO.findByTerzamediaId(id);
                    List listCalendario = new LinkedList<>();
                    for (RelPresenzaTer rpt : listRelPresenzaTer) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpt.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGen = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    mv.addObject("attgen", listAttivitaGen);
                    mv.addObject("calendari", listCalendario);
                    PagamentoTerzamedia pt = DAOMan.pagamentoTerzamediaDAO.findByTerzamediaId(id);
                    mv.addObject("pagamento", pt);
                    mv.setView("ammseg/dettaglioterzamedia.html");
                    break;
                }
                case "schedater": {
                    Terzamedia t = DAOMan.terzamediaDAO.findById(id);
                    mv.addObject("terzamedia", t);
                    int idRegistrato = t.getRegistrato().getId();
                    List listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<RelPresenzaTer> listRelPresenzaTer = DAOMan.relPresenzaTerDAO.findByTerzamediaId(id);
                    List listCalendario = new LinkedList<>();
                    for (RelPresenzaTer rpt : listRelPresenzaTer) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpt.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGenComp = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    List<AttivitaGen> listAttivitaGen = listAttivitaGenComp.stream().distinct().collect(Collectors.toList());
                    mv.addObject("attgen", listAttivitaGen);
                    mv.addObject("calendari", listCalendario);
                    mv.addObject("quotaIscrizione", ControllerPagamentiTerzamedia.calcolaQuota(t));
                    mv.setView("stampe/schedaterzamedia.html");
                    break;
                }
                case "schedaani": {
                    Animatore a = DAOMan.animatoreDAO.findById(id);
                    mv.addObject("animatore", a);
                    int idRegistrato = a.getRegistrato().getId();
                    List listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<RelPresenzaAn> listRelPresenzaAn = DAOMan.relPresenzaAnDAO.findByAnimatoreId(id);
                    List listCalendario = new LinkedList<>();
                    for (RelPresenzaAn rpa : listRelPresenzaAn) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpa.getCalendarioId());
                        listCalendario.add(c);
                    }
                    mv.addObject("calendari", listCalendario);
                    mv.setView("stampe/schedaanimatore.html");
                    break;
                }
            }
        } catch (NullPointerException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerInfoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.addObject("tipoUt", tipoUt);
        return mv;

    }

}
