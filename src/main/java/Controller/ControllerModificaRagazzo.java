package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.text.ParseException;

public class ControllerModificaRagazzo implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica ragazzo");
            var tipoUt = (int) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            template.addObject("idUtente", request.getSession().getAttribute("idUtente"));
            int idRagazzo = Integer.parseInt(request.getParameter("id"));
            Ragazzo r = DAOMan.ragazzoDAO.findById(idRagazzo);
            if (!request.getParameterMap().containsKey("nome")) {
                //PREPARO I DATI PER LA PAGINA
                IscrizioneSupport.editIdentity(template, "ragazzo", r);
                IscrizioneSupport.catalogs(template, DAOMan.laboratorioDAO.findNonRiservato(), true);
                template.addObject("calendari", IscrizioneSupport.selectedCalendars(
                        DAOMan.calendarioDAO.findAll(), DAOMan.calendarioDAO.findByRagazzoId(idRagazzo)));
                if (tipoUt < 3) {
                    template.addObject("annotazioni", DAOMan.annotazioneSegreteriaDAO.findByRagazzoId(idRagazzo));
                }
                template.setView("user/modificaragazzo.html");
            } else {
                //INSERISCO IL RAGAZZO E FACCIO UNA REDIRECT
                r.setId(Integer.parseInt(request.getParameter("id")));
                IscrizioneSupport.readIscritto(request, r);
                r.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                RequestFields.ragazzo(request, r);
                r.setFratelloIscritto(request.getParameterMap().containsKey("fratelloIscritto"));

                DAOMan.ragazzoDAO.update(r);

                PresenzeSupport.replace(request, DAOMan.relPresenzaRagDAO.findByRagazzoId(idRagazzo),
                        DAOMan.relPresenzaRagDAO::delete, calId -> new RelPresenzaRag(calId, r.getId()),
                        DAOMan.relPresenzaRagDAO::insert);

                return IscrizioneSupport.afterChange(request, "rag");

            }
        } catch (final RuntimeException | SQLException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaRagazzo.class.getName());
        }
        return template;
    }

}
