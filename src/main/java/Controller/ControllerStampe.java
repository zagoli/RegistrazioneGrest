package Controller;

import Controller.Utils.PresenzeUtils;
import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
                    List<Object[]> ragGiusti = PresenzeUtils.printRows(
                            allRagazzi.stream().filter(rag -> rag.getSquadra().getId() != 0 && rag.getSquadra().getId() == squadraId).collect(Collectors.toList()),
                            DAOMan.relPresenzaRagDAO::findByRagazzoId, RelPresenzaRag::getCalendarioId, false);
                    List<Object[]> anGiusti = PresenzeUtils.printRows(
                            allAnimatori.stream().filter(an -> an.getSquadra().getId() != 0 && an.getSquadra().getId() == squadraId).collect(Collectors.toList()),
                            DAOMan.relPresenzaAnDAO::findByAnimatoreId, RelPresenzaAn::getCalendarioId, false);
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
                    List<Object[]> dati = PresenzeUtils.printRows(allRagazzi,
                            DAOMan.relPresenzaRagDAO::findByRagazzoId, RelPresenzaRag::getCalendarioId, true);
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
                    List<Object[]> lob = PresenzeUtils.printRows(
                            lrag.stream().filter(Ragazzo::getMensa).collect(Collectors.toList()),
                            DAOMan.relPresenzaRagDAO::findByRagazzoId, RelPresenzaRag::getCalendarioId, true);
                    template.addObject("set", DAOMan.calendarioDAO.findById(Integer.parseInt(request.getParameter("idSet"))));
                    template.addObject("dati", lob);
                    template.addObject("TITOLOPAGINA", "Mensa");
                    template.setView("stampe/mensa.html");
                }
                break;
                case "anticipo": {
                    List<Ragazzo> lrag = DAOMan.ragazzoDAO.findAll();
                    List<Object[]> lob = PresenzeUtils.printRows(
                            lrag.stream().filter(Ragazzo::getEntrataAnticipata).collect(Collectors.toList()),
                            DAOMan.relPresenzaRagDAO::findByRagazzoId, RelPresenzaRag::getCalendarioId, true);
                    template.addObject("dati", lob);
                    template.addObject("TITOLOPAGINA", "Anticipo");
                    template.setView("stampe/anticipo.html");
                }
                break;
                case "presgiornani": {
                    List<Animatore> allAnimatore = DAOMan.animatoreDAO.findByCalendarioId(Integer.parseInt(request.getParameter("idSet")));
                    List<Object[]> lob = PresenzeUtils.printRows(allAnimatore,
                            DAOMan.relPresenzaAnDAO::findByAnimatoreId, RelPresenzaAn::getCalendarioId, false);
                    template.addObject("animatori", lob);
                    template.addObject("TITOLOPAGINA", "Presenza giornaliera animatori");
                    template.setView("stampe/presgiornani.html");
                }
                break;
                case "elesintan": {
                    List<Animatore> allAnimatore = DAOMan.animatoreDAO.findAll();
                    List<Object[]> lob = PresenzeUtils.printRows(allAnimatore,
                            DAOMan.relPresenzaAnDAO::findByAnimatoreId, RelPresenzaAn::getCalendarioId, false);
                    template.addObject("animatori", lob);
                    template.addObject("TITOLOPAGINA", "Elenco sintesi animatori");
                    template.setView("stampe/elesintan.html");
                }
                break;
                case "elesintter": {
                    List<TerzaMedia> allTerzaMedia = DAOMan.terzamediaDAO.findAll();
                    List<Object[]> dati = PresenzeUtils.printRows(allTerzaMedia,
                            DAOMan.relPresenzaTerDAO::findByTerzamediaId, RelPresenzaTer::getCalendarioId, true);
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
