package Controller;

import DAOManager.DAOMan;
import Domain.Animatore;
import Domain.Ragazzo;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerStatistiche implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Statistiche");
        mv.setView("ammseg/statistiche.html");
        try {
            mv.addObject("nsett", DAOMan.calendarioDAO.count());
            mv.addObject("nparr", DAOMan.parrocchiaDAO.count());
            mv.addObject("ncirc", DAOMan.circoloDAO.count());
            mv.addObject("nlab", DAOMan.laboratorioDAO.count());
            mv.addObject("nscuo", DAOMan.scuolaDAO.count());
            mv.addObject("nrag", DAOMan.ragazzoDAO.count());
            mv.addObject("nani", DAOMan.animatoreDAO.count());
            mv.addObject("nter", DAOMan.terzamediaDAO.count());
            mv.addObject("npag", DAOMan.pagamentoDAO.count());
            mv.addObject("nreg", DAOMan.registratoDAO.countUsers());
            mv.addObject("nmensatot", DAOMan.ragazzoDAO.countMensaTotale());
            mv.addObject("eanttot", DAOMan.ragazzoDAO.countAnticipatoTotale());
            mv.addObject("ragsett", DAOMan.ragazzoDAO.countSettimanale());
            mv.addObject("mensasett", DAOMan.ragazzoDAO.countMensaSettimanale());
            mv.addObject("eansett", DAOMan.ragazzoDAO.countAnticipatoSettimanale());
            
            Map<String,Integer> mapragclassi = new HashMap<>();
            Map<String,Integer> mapraglab = new HashMap<>();
            List<Ragazzo> lrag = DAOMan.ragazzoDAO.findAll();
            lrag.forEach((Ragazzo rag) -> {
                Integer nragazzi = mapragclassi.putIfAbsent(rag.getClasse()+rag.getScuola().getGrado().substring(0, 1).toUpperCase(), 1);
                if (nragazzi != null) {
                    mapragclassi.put(rag.getClasse()+rag.getScuola().getGrado().substring(0, 1).toUpperCase(), nragazzi + 1);
                }
                Integer nragazzilab = mapraglab.putIfAbsent(rag.getLaboratorio().getDescrizione(), 1);
                if (nragazzilab != null) {
                    mapraglab.put(rag.getLaboratorio().getDescrizione(), nragazzilab + 1);
                }
            });
            mv.addObject("mapragclassi", mapragclassi);
            mv.addObject("mapraglab", mapraglab);
            
            
            Map<Integer,Integer> mapanieta = new HashMap<>();
            Map<String,Integer> mapanilab = new HashMap<>();
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
            mv.addObject("mapanieta", mapanieta);
            mv.addObject("mapanilab", mapanilab);
            
        } catch (NullPointerException | SQLException ex) {
            mv.setView("err/errore.html");
            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerStatistiche.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
    
}
