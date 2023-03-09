package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerDashboard implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        switch ((Integer) request.getSession().getAttribute("tipoUtente")) {
            case 3:
                mv.addObject("TITOLOPAGINA", "Dashboard utente");
                mv.setView("user/dashboardutente.html");
                try {
                    int idUtente = (int) request.getSession().getAttribute("idUtente");

                    List<Ragazzo> listRagazzo = DAOMan.ragazzoDAO.findByRegistratoId(idUtente);
                    if (!listRagazzo.isEmpty()) {
                        Map<Ragazzo, Boolean> mapRagazzoPagato = new HashMap<>();
                        for (Ragazzo ragazzo : listRagazzo) {
                            Pagamento p = DAOMan.pagamentoDAO.findByRagazzoId(ragazzo.getId());
                            mapRagazzoPagato.put(ragazzo, p != null);
                        }
                        mv.addObject("ragazzi", mapRagazzoPagato);
                    }

                    List<Animatore> listAnimatore = DAOMan.animatoreDAO.findByRegistratoId(idUtente);
                    if (!listAnimatore.isEmpty()) {
                        mv.addObject("animatori", listAnimatore);
                    }
                    
                    List<Terzamedia> listTerzamedia = DAOMan.terzamediaDAO.findByRegistratoId(idUtente);
                    if (!listTerzamedia.isEmpty()) {
                        Map<Terzamedia, Boolean> mapTerzamediaPagato = new HashMap<>();
                        for (Terzamedia ter : listTerzamedia) {
                            PagamentoTerzamedia p = DAOMan.pagamentoTerzamediaDAO.findByTerzamediaId(ter.getId());
                            mapTerzamediaPagato.put(ter, p != null);
                        }
                        mv.addObject("terzamedia", mapTerzamediaPagato);
                    }
                    
                    int nContattiUrgenze = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idUtente).size();
                    if (nContattiUrgenze == 0) {
                        mv.addObject("NOCU", "true");
                    }
                    
                    //iscrizioni per animatori aperte o chiuse (per togliere bottoni modifica/elimina)
                    Properties properties = new Properties();
                    InputStream in = new FileInputStream("C:/conf/RegistrazioneGrest/config.properties");
                    properties.load(in);
                    mv.addObject("ISCRAN", properties.getProperty("ISCRAN").equals("true"));
                    in.close();
                    
                } catch (IOException | NullPointerException | SQLException ex) {
                    mv.setView("err/errore.html");
                    mv.addObject("eccezione", ex);
                    Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 1:
            case 2:
                mv.setView("ammseg/dashboardsegretario.html");
                mv.addObject("TITOLOPAGINA", "Dashboard segretario");
                break;
            case 0:
                Properties properties = new Properties();
                try {
                    mv.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    mv.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    mv.addObject("squadre", DAOMan.squadraDAO.findAll());
                    InputStream in = new FileInputStream("C:/conf/RegistrazioneGrest/config.properties");
                    properties.load(in);
                    if (properties.getProperty("ISCRRAG").equals("true")) {
                        mv.addObject("ISCRRAG", true);
                    } 
                    if (properties.getProperty("ISCRAN").equals("true")) {
                        mv.addObject("ISCRAN", true);
                    } 
                    if (properties.getProperty("ISCRTER").equals("true")) {
                        mv.addObject("ISCRTER", true);
                    } 
                    in.close();
                } catch (NullPointerException | SQLException | IOException ex) {
                    mv.setView("err/errore.html");
                    mv.addObject("eccezione", ex);
                    Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                mv.setView("ammseg/dashboardamministratore.html");
                mv.addObject("TITOLOPAGINA", "Dashboard amministratore");
                break;
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
}
