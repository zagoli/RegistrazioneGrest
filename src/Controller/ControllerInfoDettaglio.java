package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.ConfigPropertyException;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerInfoDettaglio implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
            mv.addObject("TITOLOPAGINA", "Info dettagliate");
            String target = request.getParameter("target");
            int id = Integer.parseInt(request.getParameter("id"));
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
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        mv.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaRag> listRelPresenzaRag = DAOMan.relPresenzaRagDAO.findByRagazzoId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
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
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        mv.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaRag> listRelPresenzaRag = DAOMan.relPresenzaRagDAO.findByRagazzoId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaRag rpr : listRelPresenzaRag) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpr.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGenComp = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    List<AttivitaGen> listAttivitaGen = listAttivitaGenComp.stream().distinct().collect(Collectors.toList());
                    mv.addObject("attgen", listAttivitaGen);
                    mv.addObject("calendari", listCalendario);
                    mv.addObject("quotaIscrizione", ControllerPagamentiRagazzi.calcolaQuota(r));
                    mv.setView("stampe/schedaragazzo.html");
                    break;
                }
                case "infoter": {
                    Terzamedia t = DAOMan.terzamediaDAO.findById(id);
                    mv.addObject("terzamedia", t);
                    int idRegistrato = t.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<RelPresenzaTer> listRelPresenzaTer = DAOMan.relPresenzaTerDAO.findByTerzamediaId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
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
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<RelPresenzaTer> listRelPresenzaTer = DAOMan.relPresenzaTerDAO.findByTerzamediaId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
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
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        mv.addObject("cu", listCU);
                    }
                    List<RelPresenzaAn> listRelPresenzaAn = DAOMan.relPresenzaAnDAO.findByAnimatoreId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaAn rpa : listRelPresenzaAn) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpa.getCalendarioId());
                        listCalendario.add(c);
                    }
                    mv.addObject("calendari", listCalendario);
                    mv.setView("stampe/schedaanimatore.html");
                    break;
                }
            }
        } catch (final RuntimeException | SQLException | ConfigPropertyException | IOException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerInfoDettaglio.class.getName());
        }
        return mv;

    }

}
