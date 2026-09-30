package Servlet;

import Controller.*;
import ModelAndView.ModelAndView;
import Utility.Checker;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dispatcher extends HttpServlet {

    private Configuration configurationTemplate;

    @Override
    public void init() throws ServletException {
        super.init();
        configurationTemplate = getConfiguration();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private Configuration getConfiguration() {
        // Chiamare questo metodo solo dentro init()
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setEncoding(Locale.ITALY, "utf-8");
        return cfg;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ControllerInterface c = this.getHandler(request);
        ModelAndView mv = c.handleRequest(request, response);
        this.rendering(mv, response);
    }

    protected ControllerInterface getHandler(HttpServletRequest request) {
        var servizio = request.getPathInfo();
        if (servizio == null) {
            return new ControllerLoginEPasswordReset();
        }

        // Rotte pubbliche accessibili senza autenticazione
        switch (servizio) {
            case "/":
            case "/Login":
                return new ControllerLoginEPasswordReset();
            case "/RegistraUtente":
                return new ControllerRegistraUtente();
        }

        // Controllo centralizzato autenticazione
        var idUtente = request.getSession().getAttribute("idUtente");
        if (idUtente == null) {
            return new ControllerLoginEPasswordReset();
        }

        // Rotte protette (l'utente è autenticato)
        var tipoUtente = (Integer) request.getSession().getAttribute("tipoUtente");
        return switch (servizio) {
            case "/RegistraRagazzo" -> new ControllerRegistraRagazzo();
            case "/RegistraAnimatore" -> new ControllerRegistraAnimatore();
            case "/RegistraTerzamedia" -> new ControllerRegistraTerzamedia();
            case "/Dashboard" -> new ControllerDashboard();
            case "/AccompagnatoriContatti" -> Integer.valueOf(3).equals(tipoUtente)
                    ? new ControllerDashboardAccCu()
                    : new Controller403();
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
            case "/InserisciAccompagnatore" -> Integer.valueOf(3).equals(tipoUtente)
                    ? new ControllerInserisciAccompagnatore()
                    : new Controller403();
            case "/ModificaAccompagnatore" -> Checker.checkActionAccompagnatore(request)
                    ? new ControllerModificaAccompagnatore()
                    : new Controller403();
            case "/EliminaAccompagnatore" -> Checker.checkActionAccompagnatore(request)
                    ? new ControllerEliminaAccompagnatore()
                    : new Controller403();
            case "/InserisciCU" -> Integer.valueOf(3).equals(tipoUtente)
                    ? new ControllerInserisciCU()
                    : new Controller403();
            case "/EliminaCU" -> Checker.checkActionCU(request)
                    ? new ControllerEliminaCU()
                    : new Controller403();
            case "/ModificaCU" -> Checker.checkActionCU(request)
                    ? new ControllerModificaCU()
                    : new Controller403();
            case "/DashboardAttGen" -> Integer.valueOf(3).equals(tipoUtente)
                    ? new ControllerDashboardAttGen()
                    : new Controller403();
            case "/RegistraAttGen" -> Integer.valueOf(3).equals(tipoUtente)
                    ? new ControllerRegistraAttGen()
                    : new Controller403();
            case "/EliminaPrenotazioneAttGen" -> Integer.valueOf(3).equals(tipoUtente)
                    ? new ControllerEliminaPrenotazioneAttGen()
                    : new Controller403();
            case "/GestisciPagamenti" -> (tipoUtente != null && tipoUtente <= 1)
                    ? new ControllerPagamentiRagazzi()
                    : new Controller403();
            case "/GestisciPagamentiTerzamedia" -> (tipoUtente != null && tipoUtente <= 1)
                    ? new ControllerPagamentiTerzamedia()
                    : new Controller403();
            case "/Squadre" -> (tipoUtente != null && tipoUtente <= 1)
                    ? new ControllerSquadre()
                    : new Controller403();
            case "/Laboratori" -> (tipoUtente != null && tipoUtente <= 1)
                    ? new ControllerLaboratori()
                    : new Controller403();
            case "/InfoDettaglio" -> new ControllerInfoDettaglio();
            case "/VisualizzaIscritti" -> (tipoUtente != null && tipoUtente <= 2)
                    ? new ControllerVisualizzaIscritti()
                    : new Controller403();
            case "/VisualizzaAttGen" -> (tipoUtente != null && tipoUtente <= 2)
                    ? new ControllerVisualizzaAttGen()
                    : new Controller403();
            case "/GestisciSegretari" -> Integer.valueOf(0).equals(tipoUtente)
                    ? new ControllerSegretari()
                    : new Controller403();
            case "/Statistiche" -> Integer.valueOf(0).equals(tipoUtente)
                    ? new ControllerStatistiche()
                    : new Controller403();
            case "/Stampa" -> (tipoUtente != null && tipoUtente <= 2)
                    ? new ControllerStampe()
                    : new Controller403();
            case "/StatoIscrizioni" -> Integer.valueOf(0).equals(tipoUtente)
                    ? new ControllerStatoIscrizioni()
                    : new Controller403();
            case "/SbloccaIscrizioni" -> new ControllerCodice();
            case "/VisualizzaQuote" -> new ControllerVisualizzaQuote();
            default -> new Controller404();
        };
    }

    private void rendering(ModelAndView mv, HttpServletResponse response) {
        String view = mv.getView();
        String contentType = "text/html; charset=UTF-8";
        if (view.endsWith("json")) {
            contentType = "text/json; charset=UTF-8";
        }
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String pathTemplate = view + ".ftl";
            Template template = configurationTemplate.getTemplate(pathTemplate);
            template.process(mv.getMap(), out);
        } catch (NullPointerException | TemplateException | IOException e) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
