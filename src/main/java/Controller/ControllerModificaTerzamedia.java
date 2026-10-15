package Controller;

import Controller.Utils.IscrizioniUtils;
import Controller.Utils.PresenzeUtils;
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
import java.text.SimpleDateFormat;
import java.util.List;

public class ControllerModificaTerzamedia implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Modifica terza media");
            var tipoUt = (int) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            template.addObject("idUtente", request.getSession().getAttribute("idUtente"));
            int idTerzamedia = Integer.parseInt(request.getParameter("id"));
            TerzaMedia t = DAOMan.terzamediaDAO.findById(idTerzamedia);
            if (request.getParameterMap().containsKey("nome") && Checker.checkMail(request.getParameter("mail"))) {
                //INSERISCO IL TERZAMEDIA E FACCIO UNA REDIRECT
                IscrizioniUtils.populateIscrittoFromRequest(request, t);
                t.setScuola(DAOMan.scuolaDAO.findById(Integer.parseInt(request.getParameter("scuola"))));
                RequestFields.terzaMedia(request, t);
                DAOMan.terzamediaDAO.update(t);
                PresenzeUtils.replace(request, DAOMan.relPresenzaTerDAO.findByTerzamediaId(idTerzamedia),
                        DAOMan.relPresenzaTerDAO::delete, calId -> new RelPresenzaTer(calId, idTerzamedia),
                        DAOMan.relPresenzaTerDAO::insert);
                return IscrizioniUtils.getPaginaRedirectDopoModifica(request, "ter");
            } else {
                //PREPARO I DATI PER LA PAGINA
                List<Laboratorio> laboratori = DAOMan.laboratorioDAO.findAll();
                template.addObject("laboratori", laboratori);
                template.addObject("parrocchie", DAOMan.parrocchiaDAO.findAll());
                template.addObject("circoli", DAOMan.circoloDAO.findAll());
                template.addObject("scuole", DAOMan.scuolaDAO.findAll());
                template.addObject("dataNascita", new SimpleDateFormat("yyyy-MM-dd").format(t.getDataNascita()));
                template.addObject("terzamedia", t);
                template.addObject("calendari", IscrizioniUtils.getSettimaneSelezionate(
                        DAOMan.calendarioDAO.findAll(), DAOMan.calendarioDAO.findByTerzamediaId(idTerzamedia)));
                if (request.getParameterMap().containsKey("mail") && !Checker.checkMail(request.getParameter("mail"))) {
                    template.addObject("INVALIDMAIL", true);
                }
                if (tipoUt < 3) {
                    template.addObject("annotazioni", DAOMan.annotazioneSegreteriaDAO.findByTerzamediaId(idTerzamedia));
                }
                template.setView("user/modificaterzamedia.html");
            }
        } catch (final RuntimeException | SQLException | IOException | ParseException | ConfigPropertyException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaTerzamedia.class.getName());
        }
        return template;
    }

}
