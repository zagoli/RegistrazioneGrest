package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerInfoDettaglio implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            template.addObject("TITOLOPAGINA", "Info dettagliate");
            String target = request.getParameter("target");
            int id = Integer.parseInt(request.getParameter("id"));
            switch (target) {
                case "infoani": {
                    Animatore a = DAOMan.animatoreDAO.findById(id);
                    template.addObject("animatore", a);
                    int idRegistrato = a.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        template.addObject("cu", listCU);
                    }
                    List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        template.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaAn> listRelPresenzaAn = DAOMan.relPresenzaAnDAO.findByAnimatoreId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaAn rpa : listRelPresenzaAn) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpa.getCalendarioId());
                        listCalendario.add(c);
                    }
                    template.addObject("calendari", listCalendario);
                    template.setView("ammseg/dettaglioanimatore.html");
                    break;
                }
                case "inforag": {
                    Ragazzo r = DAOMan.ragazzoDAO.findById(id);
                    template.addObject("ragazzo", r);
                    int idRegistrato = r.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        template.addObject("cu", listCU);
                    }
                    List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        template.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaRag> listRelPresenzaRag = DAOMan.relPresenzaRagDAO.findByRagazzoId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaRag rpr : listRelPresenzaRag) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpr.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGen = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    template.addObject("attgen", listAttivitaGen);
                    template.addObject("calendari", listCalendario);
                    Pagamento pagamento = DAOMan.pagamentoDAO.findByRagazzoId(id);
                    template.addObject("pagamento", pagamento);
                    template.addObject("altroFratelloNonTrovato", r.getFratelloIscritto() && DAOMan.ragazzoDAO.countByRegistratoId(idRegistrato) == 1);
                    template.setView("ammseg/dettaglioragazzo.html");
                    break;
                }
                case "schedarag": {
                    Ragazzo r = DAOMan.ragazzoDAO.findById(id);
                    template.addObject("ragazzo", r);
                    int idRegistrato = r.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        template.addObject("cu", listCU);
                    }
                    List<Accompagnatore> listAccompagnatore = DAOMan.accompagnatoreDAO.findByRegistratoId(idRegistrato);
                    if (!listAccompagnatore.isEmpty()) {
                        template.addObject("accompagnatori", listAccompagnatore);
                    }
                    List<RelPresenzaRag> listRelPresenzaRag = DAOMan.relPresenzaRagDAO.findByRagazzoId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaRag rpr : listRelPresenzaRag) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpr.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGenComp = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    List<AttivitaGen> listAttivitaGen = listAttivitaGenComp.stream().distinct().collect(Collectors.toList());
                    template.addObject("attgen", listAttivitaGen);
                    template.addObject("calendari", listCalendario);
                    template.addObject("quotaIscrizione", ControllerPagamentiRagazzi.calcolaQuota(r));
                    template.setView("stampe/schedaragazzo.html");
                    break;
                }
                case "infoter": {
                    TerzaMedia t = DAOMan.terzamediaDAO.findById(id);
                    template.addObject("terzamedia", t);
                    int idRegistrato = t.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        template.addObject("cu", listCU);
                    }
                    List<RelPresenzaTer> listRelPresenzaTer = DAOMan.relPresenzaTerDAO.findByTerzamediaId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaTer rpt : listRelPresenzaTer) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpt.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGen = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    template.addObject("attgen", listAttivitaGen);
                    template.addObject("calendari", listCalendario);
                    PagamentoTerzamedia pt = DAOMan.pagamentoTerzamediaDAO.findByTerzamediaId(id);
                    template.addObject("pagamento", pt);
                    template.setView("ammseg/dettaglioterzamedia.html");
                    break;
                }
                case "schedater": {
                    TerzaMedia t = DAOMan.terzamediaDAO.findById(id);
                    template.addObject("terzamedia", t);
                    int idRegistrato = t.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        template.addObject("cu", listCU);
                    }
                    List<RelPresenzaTer> listRelPresenzaTer = DAOMan.relPresenzaTerDAO.findByTerzamediaId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaTer rpt : listRelPresenzaTer) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpt.getCalendarioId());
                        listCalendario.add(c);
                    }
                    List<AttivitaGen> listAttivitaGenComp = DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato);
                    List<AttivitaGen> listAttivitaGen = listAttivitaGenComp.stream().distinct().collect(Collectors.toList());
                    template.addObject("attgen", listAttivitaGen);
                    template.addObject("calendari", listCalendario);
                    template.addObject("quotaIscrizione", ControllerPagamentiTerzamedia.calcolaQuota(t));
                    template.setView("stampe/schedaterzamedia.html");
                    break;
                }
                case "schedaani": {
                    Animatore a = DAOMan.animatoreDAO.findById(id);
                    template.addObject("animatore", a);
                    int idRegistrato = a.getRegistrato().getId();
                    List<ContattoUrgenze> listCU = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idRegistrato);
                    if (!listCU.isEmpty()) {
                        template.addObject("cu", listCU);
                    }
                    List<RelPresenzaAn> listRelPresenzaAn = DAOMan.relPresenzaAnDAO.findByAnimatoreId(id);
                    List<Calendario> listCalendario = new LinkedList<>();
                    for (RelPresenzaAn rpa : listRelPresenzaAn) {
                        Calendario c = DAOMan.calendarioDAO.findById(rpa.getCalendarioId());
                        listCalendario.add(c);
                    }
                    template.addObject("calendari", listCalendario);
                    template.setView("stampe/schedaanimatore.html");
                    break;
                }
            }
        } catch (final RuntimeException | SQLException | ConfigPropertyException | IOException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerInfoDettaglio.class.getName());
        }
        return template;

    }

}
