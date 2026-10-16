package Controller;

import Controller.Utils.InfoDettaglioUtils;
import Controller.Utils.PresenzeUtils;
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
import java.util.List;

public class ControllerInfoDettaglio implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Info dettagliate");
            String target = request.getParameter("target");
            int id = Integer.parseInt(request.getParameter("id"));
            switch (target) {
                case "inforag": {
                    Ragazzo r = commonRagazzo(id, template);
                    Pagamento pagamento = DAOMan.pagamentoDAO.findByRagazzoId(id);
                    template.addObject("pagamento", pagamento);
                    template.addObject("altroFratelloNonTrovato",
                            r.getFratelloIscritto() &&
                                    DAOMan.ragazzoDAO.countByRegistratoId(r.getRegistrato().getId()) == 1);
                    List<AnnotazioneSegreteria> annotazioni = DAOMan.annotazioneSegreteriaDAO.findByRagazzoId(id);
                    template.addObject("annotazioni", annotazioni);
                    template.setView("ammseg/dettaglioragazzo.html");
                    break;
                }
                case "infoter": {
                    commonTerzaMedia(id, template);
                    PagamentoTerzamedia pt = DAOMan.pagamentoTerzamediaDAO.findByTerzamediaId(id);
                    template.addObject("pagamento", pt);
                    List<AnnotazioneSegreteria> annotazioni = DAOMan.annotazioneSegreteriaDAO.findByTerzamediaId(id);
                    template.addObject("annotazioni", annotazioni);
                    template.setView("ammseg/dettaglioterzamedia.html");
                    break;
                }
                case "infoani": {
                    Animatore a = DAOMan.animatoreDAO.findById(id);
                    template.addObject("animatore", a);
                    int idRegistrato = a.getRegistrato().getId();
                    InfoDettaglioUtils.popolaCu(template, idRegistrato);
                    InfoDettaglioUtils.popolaAccompagnatori(template, idRegistrato);
                    List<Calendario> listCalendario = PresenzeUtils.calendars(
                            DAOMan.relPresenzaAnDAO.findByAnimatoreId(id), RelPresenzaAn::getCalendarioId);
                    template.addObject("calendari", listCalendario);
                    List<AnnotazioneSegreteria> annotazioni = DAOMan.annotazioneSegreteriaDAO.findByAnimatoreId(id);
                    template.addObject("annotazioni", annotazioni);
                    template.setView("ammseg/dettaglioanimatore.html");
                    break;
                }
                case "schedarag": {
                    Ragazzo r = commonRagazzo(id, template);
                    template.addObject("quotaIscrizione", ControllerPagamentiRagazzi.calcolaQuota(r));
                    template.setView("stampe/schedaragazzo.html");
                    break;
                }
                case "schedater": {
                    TerzaMedia t = commonTerzaMedia(id, template);
                    template.addObject("quotaIscrizione", ControllerPagamentiTerzamedia.calcolaQuota(t));
                    template.setView("stampe/schedaterzamedia.html");
                    break;
                }
                case "schedaani": {
                    Animatore a = DAOMan.animatoreDAO.findById(id);
                    template.addObject("animatore", a);
                    int idRegistrato = a.getRegistrato().getId();
                    InfoDettaglioUtils.popolaCu(template, idRegistrato);
                    List<Calendario> listCalendario = PresenzeUtils.calendars(
                            DAOMan.relPresenzaAnDAO.findByAnimatoreId(id), RelPresenzaAn::getCalendarioId);
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

    private static TerzaMedia commonTerzaMedia(int id, FreemarkerTemplate template) throws SQLException {
        TerzaMedia t = DAOMan.terzamediaDAO.findById(id);
        template.addObject("terzamedia", t);
        int idRegistrato = t.getRegistrato().getId();
        InfoDettaglioUtils.popolaCu(template, idRegistrato);
        List<Calendario> listCalendario = PresenzeUtils.calendars(
                DAOMan.relPresenzaTerDAO.findByTerzamediaId(id), RelPresenzaTer::getCalendarioId);
        template.addObject("attgen", DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato));
        template.addObject("calendari", listCalendario);
        return t;
    }

    private static Ragazzo commonRagazzo(int id, FreemarkerTemplate template) throws SQLException {
        Ragazzo r = DAOMan.ragazzoDAO.findById(id);
        template.addObject("ragazzo", r);
        int idRegistrato = r.getRegistrato().getId();
        InfoDettaglioUtils.popolaCu(template, idRegistrato);
        InfoDettaglioUtils.popolaAccompagnatori(template, idRegistrato);
        List<Calendario> listCalendario = PresenzeUtils.calendars(
                DAOMan.relPresenzaRagDAO.findByRagazzoId(id), RelPresenzaRag::getCalendarioId);
        template.addObject("attgen", DAOMan.attivitaGenDAO.findByRegistratoId(idRegistrato));
        template.addObject("calendari", listCalendario);
        return r;
    }

}
