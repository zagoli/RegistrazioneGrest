package Controller;

import Controller.Utils.IscrizioniUtils;
import Controller.Utils.PresenzeUtils;
import DAOManager.DAOMan;
import Domain.*;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Checker;
import Utility.DAOUtils;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;
import Utility.RequestFields;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ControllerRegistraAnimatore implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerRegistraAnimatore.class);

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.setTitoloPagina("Registra Animatore");
            String mail = DAOUtils.normalizeText(request.getParameter("mail"));
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(mail)) {
                Animatore animatore = new Animatore();
                IscrizioniUtils.populateIscrittoFromRequest(request, animatore);
                int idUtente = (int) request.getSession().getAttribute("idUtente");
                animatore.setRegistrato(DAOMan.registratoDAO.findById(idUtente));
                RequestFields.animatore(request, animatore);
                DAOMan.animatoreDAO.insert(animatore);
                PresenzeUtils.insert(request, calId -> new RelPresenzaAn(animatore.getId(), calId),
                        DAOMan.relPresenzaAnDAO::insert);
                logger.info("Animatore {} {} registrato", animatore.getNome(), animatore.getCognome());
                return new RedirectResult("/RegistrazioneGrest/App/Dashboard");
            } else {
                template.setView("user/registraanimatore.html");
                //preparo i dati necessari per l'iscrizione
                List<Laboratorio> laboratori = DAOMan.laboratorioDAO.findAll();
                template.addObject("laboratori", laboratori);
                template.addObject("parrocchie", DAOMan.parrocchiaDAO.findAll());
                template.addObject("circoli", DAOMan.circoloDAO.findAll());
                List<Calendario> listaCalendario = DAOMan.calendarioDAO.findAll();
                template.addObject("calendari", listaCalendario);
                Registrato reg = DAOMan.registratoDAO.findById((int) request.getSession().getAttribute("idUtente"));
                template.addObject("registrato", reg);
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(mail)) {
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
