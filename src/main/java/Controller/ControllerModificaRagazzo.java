package Controller;

import Controller.Utils.IscrizioniUtils;
import Controller.Utils.PresenzeUtils;
import DAOManager.DAOMan;
import Domain.Laboratorio;
import Domain.Ragazzo;
import Domain.RelPresenzaRag;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.RequestFields;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ControllerModificaRagazzo implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerModificaRagazzo.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Modifica ragazzo");
            var tipoUt = (int) request.getSession().getAttribute("tipoUtente");
            template.addObject("idUtente", request.getSession().getAttribute("idUtente"));
            int idRagazzo = Integer.parseInt(request.getParameter("id"));
            Ragazzo r = DAOMan.ragazzoDAO.findById(idRagazzo);
            if (!request.getParameterMap().containsKey("nome")) {
                //PREPARO I DATI PER LA PAGINA
                template.addObject("dataNascita", new SimpleDateFormat("yyyy-MM-dd").format(r.getDataNascita()));
                template.addObject("ragazzo", r);
                List<Laboratorio> laboratori = DAOMan.laboratorioDAO.findNonRiservato();
                template.addObject("laboratori", laboratori);
                template.addObject("parrocchie", DAOMan.parrocchiaDAO.findAll());
                template.addObject("circoli", DAOMan.circoloDAO.findAll());
                template.addObject("scuole", DAOMan.scuolaDAO.findAll());
                template.addObject("calendari", IscrizioniUtils.getSettimaneSelezionate(
                        DAOMan.calendarioDAO.findAll(), DAOMan.calendarioDAO.findByRagazzoId(idRagazzo)));
                if (tipoUt < 3) {
                    template.addObject("annotazioni", DAOMan.annotazioneSegreteriaDAO.findByRagazzoId(idRagazzo));
                }
                template.setView("user/modificaragazzo.html");
            } else {
                //INSERISCO IL RAGAZZO E FACCIO UNA REDIRECT
                logger.info("Modifica ragazzo con id {}", r.getId());
                r.setId(Integer.parseInt(request.getParameter("id")));
                IscrizioniUtils.populateIscrittoFromRequest(request, r);
                r.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                RequestFields.ragazzo(request, r);
                r.setFratelloIscritto(request.getParameterMap().containsKey("fratelloIscritto"));

                DAOMan.ragazzoDAO.update(r);

                PresenzeUtils.replace(request, DAOMan.relPresenzaRagDAO.findByRagazzoId(idRagazzo),
                        DAOMan.relPresenzaRagDAO::delete, calId -> new RelPresenzaRag(calId, r.getId()),
                        DAOMan.relPresenzaRagDAO::insert);

                logger.info("Ragazzo con id {} modificato", r.getId());
                return IscrizioniUtils.getPaginaRedirectDopoModifica(request, "rag");

            }
        } catch (final RuntimeException | SQLException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaRagazzo.class.getName());
        }
        return template;
    }

}
