package Servlet;

import Controller.*;
import Response.RenderingContext;
import Response.Response;
import Utility.Checker;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

public class Dispatcher extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private RenderingContext renderingContext;

    @Override
    public void init() throws ServletException {
        super.init();
        Configuration configurationTemplate = getConfiguration();
        renderingContext = new RenderingContext(configurationTemplate);
    }

    private Configuration getConfiguration() {
        // Chiamare questo metodo solo dentro init()
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_35);
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setEncoding(Locale.ITALY, "utf-8");
        return cfg;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        ControllerInterface controller = this.getHandler(request);
        logger.debug("Controller scelto: {}", controller.getClass().getSimpleName());
        Response result = controller.handleRequest(request, response);
        if (result == null) {
            logger.error("ControllerResult è null per: {}", controller.getClass().getSimpleName());
            throw new ServletException("Il controller " + controller.getClass().getName() + " ha restituito un risultato null");
        }
        result.render(response, renderingContext);
    }

    protected ControllerInterface getHandler(HttpServletRequest request) {
        var servizio = request.getPathInfo();
        if (servizio == null) {
            return new ControllerLoginEPasswordReset();
        }

        logger.debug("Richiesto servizio {}", servizio);

        // Rotte pubbliche accessibili senza autenticazione
        switch (servizio) {
            case "/":
            case "/Login":
                return new ControllerLoginEPasswordReset();
            case "/RegistraUtente":
                return new ControllerRegistraUtente();
        }

        // Controllo centralizzato autenticazione
        HttpSession session = request.getSession(false);
        if (session == null)
            return new ControllerLoginEPasswordReset();
        var idUtente = session.getAttribute("idUtente");
        var tipoUtente = (Integer) session.getAttribute("tipoUtente");
        if (idUtente == null || tipoUtente == null)
            return new ControllerLoginEPasswordReset();
        else if (tipoUtente == 4)
            return new ControllerProfiloDisabilitato();

        // Rotte protette (l'utente è autenticato)
        return switch (servizio) {
            case "/RegistraRagazzo" -> new ControllerRegistraRagazzo();
            case "/RegistraAnimatore" -> new ControllerRegistraAnimatore();
            case "/RegistraTerzamedia" -> new ControllerRegistraTerzamedia();
            case "/Dashboard" -> new ControllerDashboard();
            case "/AccompagnatoriContatti" -> new ControllerDashboardAccCu();
            case "/EliminaRagazzo" -> Checker.checkActionRagazzo(request)
                    ? new ControllerEliminaRagazzo()
                    : new Controller403();
            case "/EliminaTerzamedia" -> Checker.checkActionTerzamedia(request)
                    ? new ControllerEliminaTerzamedia()
                    : new Controller403();
            case "/ModificaRagazzo" -> Checker.checkActionRagazzo(request)
                    ? new ControllerModificaRagazzo()
                    : new Controller403();
            case "/ModificaTerzamedia" -> Checker.checkActionTerzamedia(request)
                    ? new ControllerModificaTerzamedia()
                    : new Controller403();
            case "/EliminaAnimatore" -> Checker.checkActionAnimatore(request)
                    ? new ControllerEliminaAnimatore()
                    : new Controller403();
            case "/ModificaAnimatore" -> Checker.checkActionAnimatore(request)
                    ? new ControllerModificaAnimatore()
                    : new Controller403();
            case "/ModificaPassword" -> new ControllerModificaPassword();
            case "/ModificaUtente" -> new ControllerModificaUtente();
            case "/InserisciAccompagnatore" -> new ControllerInserisciAccompagnatore();
            case "/ModificaAccompagnatore" -> Checker.checkActionAccompagnatore(request)
                    ? new ControllerModificaAccompagnatore()
                    : new Controller403();
            case "/EliminaAccompagnatore" -> Checker.checkActionAccompagnatore(request)
                    ? new ControllerEliminaAccompagnatore()
                    : new Controller403();
            case "/InserisciCU" -> new ControllerInserisciCU();
            case "/EliminaCU" -> Checker.checkActionCU(request)
                    ? new ControllerEliminaCU()
                    : new Controller403();
            case "/ModificaCU" -> Checker.checkActionCU(request)
                    ? new ControllerModificaCU()
                    : new Controller403();
            case "/DashboardAttGen" -> new ControllerDashboardAttGen();
            case "/RegistraAttGen" -> new ControllerRegistraAttGen();
            case "/EliminaPrenotazioneAttGen" -> new ControllerEliminaPrenotazioneAttGen();
            case "/GestisciPagamenti" -> (tipoUtente <= 1)
                    ? new ControllerPagamentiRagazzi()
                    : new Controller403();
            case "/GestisciPagamentiTerzamedia" -> (tipoUtente <= 1)
                    ? new ControllerPagamentiTerzamedia()
                    : new Controller403();
            case "/Squadre" -> (tipoUtente <= 1)
                    ? new ControllerSquadre()
                    : new Controller403();
            case "/Laboratori" -> (tipoUtente <= 1)
                    ? new ControllerLaboratori()
                    : new Controller403();
            case "/InfoDettaglio" -> new ControllerInfoDettaglio();
            case "/VisualizzaIscritti" -> (tipoUtente <= 2)
                    ? new ControllerVisualizzaIscritti()
                    : new Controller403();
            case "/VisualizzaAttGen" -> (tipoUtente <= 2)
                    ? new ControllerVisualizzaAttGen()
                    : new Controller403();
            case "/GestisciSegretari" -> tipoUtente == 0
                    ? new ControllerSegretari()
                    : new Controller403();
            case "/GestisciLaboratori" -> tipoUtente == 0
                    ? new ControllerGestisciLaboratori()
                    : new Controller403();
            case "/Statistiche" -> tipoUtente == 0
                    ? new ControllerStatistiche()
                    : new Controller403();
            case "/Stampa" -> (tipoUtente <= 2)
                    ? new ControllerStampe()
                    : new Controller403();
            case "/StatoIscrizioni" -> tipoUtente == 0
                    ? new ControllerStatoIscrizioni()
                    : new Controller403();
            case "/EsportazioneMassiva" -> tipoUtente == 0
                    ? new ControllerEsportazioneMassiva()
                    : new Controller403();
            case "/SbloccaIscrizioni" -> new ControllerCodice();
            case "/VisualizzaQuote" -> new ControllerVisualizzaQuote();
            default -> new Controller404();
        };
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Dispatcher";
    }// </editor-fold>
}
