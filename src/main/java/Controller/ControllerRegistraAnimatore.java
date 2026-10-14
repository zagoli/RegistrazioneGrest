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

public class ControllerRegistraAnimatore implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.addObject("TITOLOPAGINA", "Registra Animatore");
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                Animatore animatore = new Animatore();
                IscrizioneSupport.readIscritto(request, animatore);
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                animatore.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                RequestFields.animatore(request, animatore);
                DAOMan.animatoreDAO.insert(animatore);
                PresenzeSupport.insert(request, calId -> new RelPresenzaAn(animatore.getId(), calId),
                        DAOMan.relPresenzaAnDAO::insert);
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                template.setView("user/registraanimatore.html");
                //preparo i dati necessari per l'iscrizione
                IscrizioneSupport.catalogs(template, DAOMan.laboratorioDAO.findAll(), false);
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                Registrato reg = DAOMan.registratoDAO.findById((int) request.getSession().getAttribute("idUtente"));
                template.addObject("registrato", reg);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                //iscrizioni aperte o chiuse
                template.addObject("ISCRAN", ConfigProperties.getProperty("ISCRAN").equals("true"));
            }
        } catch (final RuntimeException | SQLException | IOException | ConfigPropertyException | ParseException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerRegistraAnimatore.class.getName());
        }
        return template;
    }

}
