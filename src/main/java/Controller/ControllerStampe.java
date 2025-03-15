package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ControllerStampe implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
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
                        List<RelPresenzaRag> periodo = null;
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
                        List<RelPresenzaAn> periodo = null;
                        try {
                            periodo = DAOMan.relPresenzaAnDAO.findByAnimatoreId(an.getId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        periodoString = periodo.stream().map((RelPresenzaAn) -> RelPresenzaAn.getCalendarioId() + " ").reduce(periodoString, String::concat);
                        Object[] o = {an, periodoString};
                        anGiusti.add(o);
                    });
                    mv.addObject("TITOLOPAGINA", "Presenze settimanali squadre");
                    mv.setView("stampe/pressetsqu.html");
                    mv.addObject("settimana", DAOMan.calendarioDAO.findById(idset));
                    mv.addObject("squadra", DAOMan.squadraDAO.findById(squadraId));
                    mv.addObject("ragazzi", ragGiusti);
                    mv.addObject("animatori", anGiusti);
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
                    mv.addObject("ragazzi", dati);
                    mv.addObject("TITOLOPAGINA", "Elenco sintesi ragazzi");
                    mv.setView("stampe/elesintrag.html");
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
                    mv.addObject("ragazzi", ragGiusti);
                    mv.addObject("animatori", anGiusti);
                    mv.addObject("settimana", DAOMan.calendarioDAO.findById(idset));
                    mv.addObject("laboratorio", DAOMan.laboratorioDAO.findById(labid));
                    mv.addObject("TITOLOPAGINA", "Presenze settimanali laboratori");
                    mv.setView("stampe/pressetlab.html");
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
                    mv.addObject("dati", lob);
                    mv.addObject("TITOLOPAGINA", "Collaborazione genitori");
                    mv.setView("stampe/collaborazione.html");
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
                    mv.addObject("set", DAOMan.calendarioDAO.findById(Integer.parseInt(request.getParameter("idSet"))));
                    mv.addObject("dati", lob);
                    mv.addObject("TITOLOPAGINA", "Mensa");
                    mv.setView("stampe/mensa.html");
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
                    mv.addObject("dati", lob);
                    mv.addObject("TITOLOPAGINA", "Anticipo");
                    mv.setView("stampe/anticipo.html");
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
                    mv.addObject("animatori", lob);
                    mv.addObject("TITOLOPAGINA", "Presenza giornaliera animatori");
                    mv.setView("stampe/presgiornani.html");
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
                    mv.addObject("animatori", lob);
                    mv.addObject("TITOLOPAGINA", "Elenco sintesi animatori");
                    mv.setView("stampe/elesintan.html");
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
                    mv.addObject("terzamedia", dati);
                    mv.addObject("TITOLOPAGINA", "Elenco sintesi terzamedia");
                    mv.setView("stampe/elesintter.html");
                }
                break;
            }
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerStampe.class.getName());
        }
        return mv;
    }

}
