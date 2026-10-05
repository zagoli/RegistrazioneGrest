package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.Response;
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
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            var tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);

            var dashboardRichiesta = switch (tipoUt) {
                case 3 -> "dashboardRegistrato";
                case 1, 2 -> "dashboardSegretario";
                case 0 -> "dashboardAmministratore";
                default -> throw new IllegalStateException("Unexpected user type value: " + tipoUt);
            };

            if (request.getParameter("user") != null) {
                dashboardRichiesta = "dashboardRegistrato";
            }

            switch (dashboardRichiesta) {
                case "dashboardRegistrato":
                    int idUtente = (int) request.getSession().getAttribute("idUtente");
                    List<Ragazzo> listRagazzo = DAOMan.ragazzoDAO.findByRegistratoId(idUtente);
                    if (!listRagazzo.isEmpty()) {
                        Map<Ragazzo, Boolean> mapRagazzoPagato = new HashMap<>();
                        for (Ragazzo ragazzo : listRagazzo) {
                            Pagamento p = DAOMan.pagamentoDAO.findByRagazzoId(ragazzo.getId());
                            mapRagazzoPagato.put(ragazzo, p != null);
                        }
                        template.addObject("ragazzi", mapRagazzoPagato);
                    }
                    List<Animatore> listAnimatore = DAOMan.animatoreDAO.findByRegistratoId(idUtente);
                    if (!listAnimatore.isEmpty()) {
                        template.addObject("animatori", listAnimatore);
                    }
                    List<Terzamedia> listTerzamedia = DAOMan.terzamediaDAO.findByRegistratoId(idUtente);
                    if (!listTerzamedia.isEmpty()) {
                        Map<Terzamedia, Boolean> mapTerzamediaPagato = new HashMap<>();
                        for (Terzamedia ter : listTerzamedia) {
                            PagamentoTerzamedia p = DAOMan.pagamentoTerzamediaDAO.findByTerzamediaId(ter.getId());
                            mapTerzamediaPagato.put(ter, p != null);
                        }
                        template.addObject("terzamedia", mapTerzamediaPagato);
                    }
                    int nContattiUrgenze = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idUtente).size();
                    if (nContattiUrgenze == 0) {
                        template.addObject("NOCU", "true");
                    }
                    //iscrizioni per animatori aperte o chiuse (per togliere bottoni modifica/elimina)
                    template.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
                    template.setView("user/dashboardutente.html");
                    template.addObject("TITOLOPAGINA", "Dashboard utente");
                    break;
                case "dashboardSegretario":
                    template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    template.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    template.addObject("squadre", DAOMan.squadraDAO.findAll());
                    template.setView("ammseg/dashboardsegretario.html");
                    template.addObject("TITOLOPAGINA", "Dashboard segretario");
                    break;
                case "dashboardAmministratore":
                    template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    template.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    template.addObject("squadre", DAOMan.squadraDAO.findAll());
                    template.addObject("ISCRRAG", ConfigProperties.getProperty("ISCRRAG").equals("true"));
                    template.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
                    template.addObject("ISCRTER", ConfigProperties.getProperty("ISCRTER").equals("true"));
                    template.setView("ammseg/dashboardamministratore.html");
                    template.addObject("TITOLOPAGINA", "Dashboard amministratore");
                    break;
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerDashboard.class.getName());
        }
        return template;
    }
}
