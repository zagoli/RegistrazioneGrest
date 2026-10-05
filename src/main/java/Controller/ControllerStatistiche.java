package Controller;

import DAOManager.DAOMan;
import Domain.Animatore;
import Domain.Ragazzo;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerStatistiche implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Statistiche");
            template.setView("ammseg/statistiche.html");
            template.addObject("nrag", DAOMan.ragazzoDAO.count());
            template.addObject("nani", DAOMan.animatoreDAO.count());
            template.addObject("nter", DAOMan.terzamediaDAO.count());
            template.addObject("npag", DAOMan.pagamentoDAO.count());
            template.addObject("nreg", DAOMan.registratoDAO.countUsers());
            template.addObject("nmensatot", DAOMan.ragazzoDAO.countMensaTotale());
            template.addObject("eanttot", DAOMan.ragazzoDAO.countAnticipatoTotale());
            template.addObject("ragsett", DAOMan.ragazzoDAO.countSettimanale());
            template.addObject("anisett", DAOMan.animatoreDAO.countSettimanale());
            template.addObject("mensasett", DAOMan.ragazzoDAO.countMensaSettimanale());
            template.addObject("eansett", DAOMan.ragazzoDAO.countAnticipatoSettimanale());

            Map<String, Integer> mapragclassi = new HashMap<>();
            Map<String, Integer> mapraglab = new HashMap<>();
            List<Ragazzo> lrag = DAOMan.ragazzoDAO.findAll();
            lrag.forEach((Ragazzo rag) -> {
                Integer nragazzi = mapragclassi.putIfAbsent(rag.getClasse() + rag.getScuola().getGrado().substring(0, 1).toUpperCase(), 1);
                if (nragazzi != null) {
                    mapragclassi.put(rag.getClasse() + rag.getScuola().getGrado().substring(0, 1).toUpperCase(), nragazzi + 1);
                }
                Integer nragazzilab = mapraglab.putIfAbsent(rag.getLaboratorio().getDescrizione(), 1);
                if (nragazzilab != null) {
                    mapraglab.put(rag.getLaboratorio().getDescrizione(), nragazzilab + 1);
                }
            });
            template.addObject("mapragclassi", mapragclassi);
            template.addObject("mapraglab", mapraglab);


            Map<Integer, Integer> mapanieta = new HashMap<>();
            Map<String, Integer> mapanilab = new HashMap<>();
            List<Animatore> lani = DAOMan.animatoreDAO.findAll();
            lani.stream().map((ani) -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(ani.getDataNascita());
                return cal;
            }).forEachOrdered((cal) -> {
                Integer nanimatori = mapanieta.putIfAbsent(cal.get(Calendar.YEAR), 1);
                if (nanimatori != null) {
                    mapanieta.put(cal.get(Calendar.YEAR), nanimatori + 1);
                }
            });
            lani.forEach((ani) -> {
                Integer nanilab = mapanilab.putIfAbsent(ani.getLaboratorio().getDescrizione(), 1);
                if (nanilab != null) {
                    mapanilab.put(ani.getLaboratorio().getDescrizione(), nanilab + 1);
                }
            });
            template.addObject("mapanieta", mapanieta);
            template.addObject("mapanilab", mapanilab);
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerStatistiche.class.getName());
        }
        return template;
    }

}
