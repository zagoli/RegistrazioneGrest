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
        ControllerInterface c;
        String servizio = request.getPathInfo();
        switch (servizio) {
            case "/":
            case "/Login":
                c = new ControllerLoginEPasswordReset();
                break;
            case "/RegistraUtente":
                c = new ControllerRegistraUtente();
                break;
            case "/RegistraRagazzo":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerRegistraRagazzo();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/RegistraAnimatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerRegistraAnimatore();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/RegistraTerzamedia":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerRegistraTerzamedia();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/Dashboard":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerDashboard();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/AccompagnatoriContatti":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                        c = new ControllerDashboardAccCu();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/EliminaRagazzo":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionRagazzo(request)) {
                        c = new ControllerEliminaRagazzo();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/EliminaTerzamedia":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionTerzamedia(request)) {
                        c = new ControllerEliminaTerzamedia();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaRagazzo":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionRagazzo(request)) {
                        c = new ControllerModificaRagazzo();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaTerzamedia":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionTerzamedia(request)) {
                        c = new ControllerModificaTerzamedia();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/EliminaAnimatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionAnimatore(request)) {
                        c = new ControllerEliminaAnimatore();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaAnimatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionAnimatore(request)) {
                        c = new ControllerModificaAnimatore();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaPassword":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerModificaPassword();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaUtente":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerModificaUtente();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/InserisciAccompagnatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                        c = new ControllerInserisciAccompagnatore();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaAccompagnatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionAccompagnatore(request)) {
                        c = new ControllerModificaAccompagnatore();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/EliminaAccompagnatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionAccompagnatore(request)) {
                        c = new ControllerEliminaAccompagnatore();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/InserisciCU":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                        c = new ControllerInserisciCU();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/EliminaCU":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionCU(request)) {
                        c = new ControllerEliminaCU();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/ModificaCU":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (Checker.checkActionCU(request)) {
                        c = new ControllerModificaCU();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/DashboardAttGen":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                        c = new ControllerDashboardAttGen();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/RegistraAttGen":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                        c = new ControllerRegistraAttGen();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/EliminaPrenotazioneAttGen":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(3)) {
                        c = new ControllerEliminaPrenotazioneAttGen();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/GestisciPagamenti":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 1) {
                        c = new ControllerPagamentiRagazzi();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/GestisciPagamentiTerzamedia":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 1) {
                        c = new ControllerPagamentiTerzamedia();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/Squadre":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 1) {
                        c = new ControllerSquadre();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/Laboratori":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 1) {
                        c = new ControllerLaboratori();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/InfoDettaglio":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerInfoDettaglio();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/VisualizzaIscritti":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 2) {
                        c = new ControllerVisualizzaIscritti();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/VisualizzaAttGen":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 2) {
                        c = new ControllerVisualizzaAttGen();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/GestisciSegretari":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(0)) {
                        c = new ControllerSegretari();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/Statistiche":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(0)) {
                        c = new ControllerStatistiche();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/Stampa":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 2) {
                        c = new ControllerStampe();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/StatoIscrizioni":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(0)) {
                        c = new ControllerStatoIscrizioni();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/SbloccaIscrizioni":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerCodice();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            case "/VisualizzaQuote":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerVisualizzaQuote();
                } else {
                    c = new ControllerLoginEPasswordReset();
                }
                break;
            default:
                c = new Controller404();
                break;
        }
        return c;
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
