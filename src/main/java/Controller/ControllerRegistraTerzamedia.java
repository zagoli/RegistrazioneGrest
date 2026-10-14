package Controller;

import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ControllerRegistraTerzamedia implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Registrazione ragazzo di Terzamedia");
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                TerzaMedia terzamedia = new TerzaMedia();
                IscrizioneSupport.readIscritto(request, terzamedia);
                terzamedia.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                terzamedia.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                RequestFields.terzaMedia(request, terzamedia);

                DAOMan.terzamediaDAO.insert(terzamedia);

                PresenzeSupport.insert(request, calId -> new RelPresenzaTer(calId, terzamedia.getId()),
                        DAOMan.relPresenzaTerDAO::insert);

                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                template.setView("user/registraterzamedia.html");
                //Recupera dati per la registrazione
                IscrizioneSupport.catalogs(template, DAOMan.laboratorioDAO.findAll(), true);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                //iscrizioni aperte o chiuse
                template.addObject("ISCRTER", ConfigProperties.getProperty("ISCRTER").equals("true"));
            }
        } catch (final RuntimeException | IOException | ConfigPropertyException | SQLException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraTerzamedia.class.getName());
        }
        return template;
    }
}
