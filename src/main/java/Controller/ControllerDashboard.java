package Controller;

import Controller.Utils.PresenzeUtils;
import DAOManager.DAOMan;
import Domain.Animatore;
import Domain.Ragazzo;
import Domain.TerzaMedia;
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
                        Map<Ragazzo, Boolean> ragazziConStatoPagamento = new HashMap<>();
                        for (Ragazzo ragazzo : listRagazzo) {
                            ragazziConStatoPagamento.put(ragazzo, ((PresenzeUtils.SqlFunction<Integer, ?>) DAOMan.pagamentoDAO::findByRagazzoId).apply(ragazzo.getId()) != null);
                        }
                        template.addObject("ragazzi", ragazziConStatoPagamento);
                    }
                    List<Animatore> listAnimatore = DAOMan.animatoreDAO.findByRegistratoId(idUtente);
                    if (!listAnimatore.isEmpty()) {
                        template.addObject("animatori", listAnimatore);
                    }
                    List<TerzaMedia> listTerzaMedia = DAOMan.terzamediaDAO.findByRegistratoId(idUtente);
                    if (!listTerzaMedia.isEmpty()) {
                        Map<TerzaMedia, Boolean> terzaMediaConStatoPagamento = new HashMap<>();
                        for (TerzaMedia terzaMedia : listTerzaMedia) {
                            terzaMediaConStatoPagamento.put(terzaMedia, ((PresenzeUtils.SqlFunction<Integer, ?>) DAOMan.pagamentoTerzamediaDAO::findByTerzamediaId).apply(terzaMedia.getId()) != null);
                        }
                        template.addObject("terzamedia", terzaMediaConStatoPagamento);
                    }
                    int nContattiUrgenze = DAOMan.contattoUrgenzeDAO.findByRegistratoId(idUtente).size();
                    if (nContattiUrgenze == 0) {
                        template.addObject("NOCU", "true");
                    }
                    //iscrizioni per animatori aperte o chiuse (per togliere bottoni modifica/elimina)
                    template.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
                    template.setView("user/dashboardutente.html");
                    template.setTitoloPagina("Dashboard utente");
                    break;
                case "dashboardSegretario":
                    template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    template.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    template.addObject("squadre", DAOMan.squadraDAO.findAll());
                    template.setView("ammseg/dashboardsegretario.html");
                    template.setTitoloPagina("Dashboard segretario");
                    break;
                case "dashboardAmministratore":
                    template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                    template.addObject("settimane", DAOMan.calendarioDAO.findAll());
                    template.addObject("squadre", DAOMan.squadraDAO.findAll());
                    template.addObject("ISCRRAG", ConfigProperties.getProperty("ISCRRAG").equals("true"));
                    template.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
                    template.addObject("ISCRTER", ConfigProperties.getProperty("ISCRTER").equals("true"));
                    template.setView("ammseg/dashboardamministratore.html");
                    template.setTitoloPagina("Dashboard amministratore");
                    break;
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerDashboard.class.getName());
        }
        return template;
    }
}
