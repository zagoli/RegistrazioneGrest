package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.Response;
import Utility.Checker;
import Utility.ConfigPropertyException;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ControllerModificaAnimatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            var idAnimatore = Integer.parseInt(request.getParameter("id"));
            var tipoUt = (int) request.getSession().getAttribute("tipoUtente");
            template.addObject("idUtente", request.getSession().getAttribute("idUtente"));
            template.addObject("TITOLOPAGINA", "Modifica animatore");
            template.addObject("tipoUt", tipoUt);
            Animatore a = DAOMan.animatoreDAO.findById(idAnimatore);
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                //INSERISCO ANIMATORE E FACCIO UNA REDIRECT
                a.setId(Integer.parseInt(request.getParameter("id")));
                IscrizioneSupport.readIscritto(request, a);
                RequestFields.animatore(request, a);
                DAOMan.animatoreDAO.update(a);
                PresenzeSupport.replace(request, DAOMan.relPresenzaAnDAO.findByAnimatoreId(idAnimatore),
                        DAOMan.relPresenzaAnDAO::delete, calId -> new RelPresenzaAn(a.getId(), calId),
                        DAOMan.relPresenzaAnDAO::insert);
                return IscrizioneSupport.afterChange(request, "an");
            } else {
                //PREPARO I DATI PER LA PAGINA
                IscrizioneSupport.editIdentity(template, "animatore", a);
                IscrizioneSupport.catalogs(template, DAOMan.laboratorioDAO.findAll(), false);
                template.addObject("calendari", IscrizioneSupport.selectedCalendars(
                        DAOMan.calendarioDAO.findAll(), DAOMan.calendarioDAO.findByAnimatoreId(idAnimatore)));
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                if (tipoUt < 3) {
                    template.addObject("annotazioni", DAOMan.annotazioneSegreteriaDAO.findByAnimatoreId(idAnimatore));
                }
                template.setView("user/modificaanimatore.html");
            }
        } catch (final RuntimeException | SQLException | IOException | ParseException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaAnimatore.class.getName());
        }
        return template;
    }

}
