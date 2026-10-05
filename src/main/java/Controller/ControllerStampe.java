package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ControllerStampe implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            switch (request.getParameter("target")) {
                case "pressetsqu": {
                    int idset = Integer.parseInt(request.getParameter("idset"));
                    int squadraId = Integer.parseInt(request.getParameter("squadra"));
                    List<Ragazzo> allRagazzi = DAOMan.ragazzoDAO.findByCalendarioId(idset);
                    List<Animatore> allAnimatori = DAOMan.animatoreDAO.findByCalendarioId(idset);
                    List<Object[]> ragGiusti = new LinkedList<>();
                    allRagazzi.stream().filter((rag) -> (rag.getSquadra().getId() != 0 && rag.getSquadra().getId() == squadraId)).forEachOrdered((Ragazzo rag) -> {
                        String periodoString = "";
                        List<RelPresenzaRag> periodo;
                        try {
                            periodo = DAOMan.relPresenzaRagDAO.findByRagazzoId(rag.getId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        periodoString = periodo.stream().map((relPresenzaRag) -> relPresenzaRag.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        Object[] o = {rag, periodoString};
                        ragGiusti.add(o);
                    });
                    List<Object[]> anGiusti = new LinkedList<>();
                    allAnimatori.stream().filter((an) -> (an.getSquadra().getId() != 0 && an.getSquadra().getId() == squadraId)).forEachOrdered((an) -> {
                        String periodoString = "";
                        List<RelPresenzaAn> periodo;
                        try {
                            periodo = DAOMan.relPresenzaAnDAO.findByAnimatoreId(an.getId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        periodoString = periodo.stream().map((RelPresenzaAn) -> RelPresenzaAn.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        Object[] o = {an, periodoString};
                        anGiusti.add(o);
                    });
                    template.addObject("TITOLOPAGINA", "Presenze settimanali squadre");
                    template.setView("stampe/pressetsqu.html");
                    template.addObject("settimana", DAOMan.calendarioDAO.findById(idset));
                    template.addObject("squadra", DAOMan.squadraDAO.findById(squadraId));
                    template.addObject("ragazzi", ragGiusti);
                    template.addObject("animatori", anGiusti);
                }
                break;
                case "elesintrag": {
                    List<Ragazzo> allRagazzi = DAOMan.ragazzoDAO.findAll();
                    List<Object[]> dati = new LinkedList<>();
                    for (Ragazzo rag : allRagazzi) {
                        String periodoString = "";
                        List<RelPresenzaRag> periodo = DAOMan.relPresenzaRagDAO.findByRagazzoId(rag.getId());
                        periodoString = periodo.stream().map((relPresenzaRag) -> relPresenzaRag.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        int idreg = rag.getRegistrato().getId();
                        List<ContattoUrgenze> cu = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idreg);
                        Object[] o = {rag, periodoString, cu};
                        dati.add(o);
                    }
                    template.addObject("ragazzi", dati);
                    template.addObject("TITOLOPAGINA", "Elenco sintesi ragazzi");
                    template.setView("stampe/elesintrag.html");
                }
                break;
                case "pressetlab": {
                    int labid = Integer.parseInt(request.getParameter("lab"));
                    int idset = Integer.parseInt(request.getParameter("idset"));
                    List<Ragazzo> allRagazzi = DAOMan.ragazzoDAO.findByCalendarioId(idset);
                    List<Ragazzo> ragGiusti = new LinkedList<>();
                    List<Animatore> allAnimatori = DAOMan.animatoreDAO.findByCalendarioId(idset);
                    List<Animatore> anGiusti = new LinkedList<>();
                    allRagazzi.stream().filter((rag) -> (rag.getLaboratorio().getId() == labid)).forEachOrdered(ragGiusti::add);
                    allAnimatori.stream().filter((an) -> (an.getLaboratorio().getId() == labid)).forEachOrdered(anGiusti::add);
                    template.addObject("ragazzi", ragGiusti);
                    template.addObject("animatori", anGiusti);
                    template.addObject("settimana", DAOMan.calendarioDAO.findById(idset));
                    template.addObject("laboratorio", DAOMan.laboratorioDAO.findById(labid));
                    template.addObject("TITOLOPAGINA", "Presenze settimanali laboratori");
                    template.setView("stampe/pressetlab.html");
                }
                break;
                case "collaborazione": {
                    List<RelCollabora> lrelc = DAOMan.relCollaboraDAO.findAll();
                    List<Object[]> lob = new LinkedList<>();
                    for (RelCollabora col : lrelc) {
                        AttivitaGen ag = DAOMan.attivitaGenDAO.findById(col.getAttivitaGenId());
                        Registrato reg = DAOMan.registratoDAO.findById(col.getRegistratoId());
                        List<Ragazzo> lragtemp = DAOMan.ragazzoDAO.findByRegistratoId(col.getRegistratoId());
                        Ragazzo rag = lragtemp.isEmpty() ? null : lragtemp.get(0);
                        List<Calendario> lcal = lragtemp.isEmpty() ? null : DAOMan.calendarioDAO.findByRagazzoId(rag.getId());
                        Object[] o = {col, ag, reg, rag, lcal};
                        lob.add(o);
                    }
                    template.addObject("dati", lob);
                    template.addObject("TITOLOPAGINA", "Collaborazione genitori");
                    template.setView("stampe/collaborazione.html");
                }
                break;
                case "mensa": {
                    List<Ragazzo> lrag = DAOMan.ragazzoDAO.findByCalendarioId(Integer.parseInt(request.getParameter("idSet")));
                    List<Object[]> lob = new LinkedList<>();
                    for (Ragazzo rag : lrag) {
                        if (rag.getMensa()) {
                            String periodoString = "";
                            List<RelPresenzaRag> periodo = DAOMan.relPresenzaRagDAO.findByRagazzoId(rag.getId());
                            periodoString = periodo.stream().map((relPresenzaRag) -> relPresenzaRag.getCalendarioId() + " ").reduce(periodoString, String::concat);
                            List<ContattoUrgenze> cu = DAOMan.contattoUrgenzeDAO.findByRegistratoId(rag.getRegistrato().getId());
                            Object[] o = {rag, periodoString, cu};
                            lob.add(o);
                        }
                    }
                    template.addObject("set", DAOMan.calendarioDAO.findById(Integer.parseInt(request.getParameter("idSet"))));
                    template.addObject("dati", lob);
                    template.addObject("TITOLOPAGINA", "Mensa");
                    template.setView("stampe/mensa.html");
                }
                break;
                case "anticipo": {
                    List<Ragazzo> lrag = DAOMan.ragazzoDAO.findAll();
                    List<Object[]> lob = new LinkedList<>();
                    for (Ragazzo rag : lrag) {
                        if (rag.getEntrataAnticipata()) {
                            String periodoString = "";
                            List<RelPresenzaRag> periodo = DAOMan.relPresenzaRagDAO.findByRagazzoId(rag.getId());
                            periodoString = periodo.stream().map((relPresenzaRag) -> relPresenzaRag.getCalendarioId() + " ").reduce(periodoString, String::concat);
                            List<ContattoUrgenze> cu = DAOMan.contattoUrgenzeDAO.findByRegistratoId(rag.getRegistrato().getId());
                            Object[] o = {rag, periodoString, cu};
                            lob.add(o);
                        }
                    }
                    template.addObject("dati", lob);
                    template.addObject("TITOLOPAGINA", "Anticipo");
                    template.setView("stampe/anticipo.html");
                }
                break;
                case "presgiornani": {
                    List<Animatore> allAnimatore = DAOMan.animatoreDAO.findByCalendarioId(Integer.parseInt(request.getParameter("idSet")));
                    List<Object[]> lob = new LinkedList<>();
                    for (Animatore ani : allAnimatore) {
                        String periodoString = "";
                        List<RelPresenzaAn> periodo = DAOMan.relPresenzaAnDAO.findByAnimatoreId(ani.getId());
                        periodoString = periodo.stream().map((RelPresenzaAn) -> RelPresenzaAn.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        Object[] o = {ani, periodoString};
                        lob.add(o);
                    }
                    template.addObject("animatori", lob);
                    template.addObject("TITOLOPAGINA", "Presenza giornaliera animatori");
                    template.setView("stampe/presgiornani.html");
                }
                break;
                case "elesintan": {
                    List<Animatore> allAnimatore = DAOMan.animatoreDAO.findAll();
                    List<Object[]> lob = new LinkedList<>();
                    for (Animatore ani : allAnimatore) {
                        String periodoString = "";
                        List<RelPresenzaAn> periodo = DAOMan.relPresenzaAnDAO.findByAnimatoreId(ani.getId());
                        periodoString = periodo.stream().map((RelPresenzaAn) -> RelPresenzaAn.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        Object[] o = {ani, periodoString};
                        lob.add(o);
                    }
                    template.addObject("animatori", lob);
                    template.addObject("TITOLOPAGINA", "Elenco sintesi animatori");
                    template.setView("stampe/elesintan.html");
                }
                break;
                case "elesintter": {
                    List<Terzamedia> allTerzamedia = DAOMan.terzamediaDAO.findAll();
                    List<Object[]> dati = new LinkedList<>();
                    for (Terzamedia ter : allTerzamedia) {
                        String periodoString = "";
                        List<RelPresenzaTer> periodo = DAOMan.relPresenzaTerDAO.findByTerzamediaId(ter.getId());
                        periodoString = periodo.stream().map((relPresenzaTer) -> relPresenzaTer.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        int idreg = ter.getRegistrato().getId();
                        List<ContattoUrgenze> cu = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idreg);
                        Object[] o = {ter, periodoString, cu};
                        dati.add(o);
                    }
                    template.addObject("terzamedia", dati);
                    template.addObject("TITOLOPAGINA", "Elenco sintesi terzamedia");
                    template.setView("stampe/elesintter.html");
                }
                break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerStampe.class.getName());
        }
        return template;
    }

}
