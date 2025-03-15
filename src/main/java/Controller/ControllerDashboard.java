package Controller;

import DAOManager.DAOMan;
import Domain.*;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerDashboard implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            mv.addObject("tipoUt", tipoUt);
            switch (tipoUt) {
                case 3:
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
                    mv.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
                    mv.setView("user/dashboardutente.html");
                    mv.addObject("TITOLOPAGINA", "Dashboard utente");
                    break;
                case 1:
                case 2:
                    mv.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    mv.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    mv.addObject("squadre", DAOMan.squadraDAO.findAll());
                    mv.setView("ammseg/dashboardsegretario.html");
                    mv.addObject("TITOLOPAGINA", "Dashboard segretario");
                    break;
                case 0:
                    mv.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    mv.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    mv.addObject("squadre", DAOMan.squadraDAO.findAll());
                    mv.addObject("ISCRRAG", ConfigProperties.getProperty("ISCRRAG").equals("true"));
                    mv.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
                    mv.addObject("ISCRTER", ConfigProperties.getProperty("ISCRTER").equals("true"));
                    mv.setView("ammseg/dashboardamministratore.html");
                    mv.addObject("TITOLOPAGINA", "Dashboard amministratore");
                    break;
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerDashboard.class.getName());
        }
        return mv;
    }
}
