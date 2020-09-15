package Servlet;

import ApplicationContext.ApplicationContext;
import Controller.*;
import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import Utility.Checker;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dispatcher extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext.getContext().put("DAO", new DAOMan());
        Configuration cfg = new freemarker.template.Configuration(Configuration.VERSION_2_3_27);
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setEncoding(Locale.ITALY, "utf-8");
        ApplicationContext.getContext().put("CfgTemplate", cfg);
        //verifica se esiste file registrazioni aperte o chiuse
        File conf = new File("C:/conf/RegistrazioneGrest/config.properties");
        try {
            if (!conf.exists()) {
                if (!new File("C:/conf/RegistrazioneGrest").mkdirs()) {
                    throw new IOException("non sono riuscito a creare la cartella di configurazione per il file di proprietà");
                }
                try (FileWriter writer = new FileWriter(conf)) {
                    writer.write("#" + new Date().toString() + System.getProperty("line.separator") + "ISCRRAG=true" + System.getProperty("line.separator") + "ISCRAN=true" + System.getProperty("line.separator") + "ISCRTER=true");
                }
            }
        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, "init", ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ControllerInterface c = this.getHandler(request);
        ModelAndView mv = c.handleRequest(request, response);
        this.rendering(mv.getView(), mv, response);
    }

    protected ControllerInterface getHandler(HttpServletRequest request) {
        ControllerInterface c;
        String servizio = request.getPathInfo();
        switch (servizio) {
            case "/Login":
            case "/":
                c = new ControllerLogin();
                break;
            case "/RegistraUtente":
                c = new ControllerRegistraUtente();
                break;
            case "/RegistraRagazzo":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerRegistraRagazzo();
                } else {
                    c = new ControllerLogin();
                }
                break;
            case "/RegistraAnimatore":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerRegistraAnimatore();
                } else {
                    c = new ControllerLogin();
                }
                break;
            case "/RegistraTerzamedia":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerRegistraTerzamedia();
                } else {
                    c = new ControllerLogin();
                }
                break;
            case "/Dashboard":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerDashboard();
                } else {
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
                }
                break;
            case "/ModificaPassword":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerModificaPassword();
                } else {
                    c = new ControllerLogin();
                }
                break;
            case "/ModificaUtente":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerModificaUtente();
                } else {
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
                }
                break;
            case "/GestisciPagamenti":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if ((Integer) request.getSession().getAttribute("tipoUtente") <= 1) {
                        c = new ControllerPagamenti();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
                }
                break;
            case "/InfoDettaglio":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerInfoDettaglio();
                } else {
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
                }
                break;
            case "/Stampa":
                if (request.getSession().getAttribute("idUtente") != null) {
                    if (request.getSession().getAttribute("tipoUtente").equals(0)) {
                        c = new ControllerStampe();
                    } else {
                        c = new Controller403();
                    }
                } else {
                    c = new ControllerLogin();
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
                    c = new ControllerLogin();
                }
                break;
            case "/SbloccaIscrizioni":
                if (request.getSession().getAttribute("idUtente") != null) {
                    c = new ControllerCodice();
                } else {
                    c = new ControllerLogin();
                }
                break;
            default:
                c = new Controller404();
                break;
        }
        return c;
    }

    private void rendering(String view, ModelAndView mv, HttpServletResponse response) {
        String contentType = "text/html; charset=UTF-8";
        if (view.endsWith("json")) {
            contentType = "text/json; charset=UTF-8";
        }
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            freemarker.template.Configuration cfg;
            cfg = (freemarker.template.Configuration) ApplicationContext.getContext().get("CfgTemplate");
            String pathTemplate = view + ".ftl";
            Template template = cfg.getTemplate(pathTemplate);
            template.process(mv.getMap(), out);
        } catch (NullPointerException | TemplateException | IOException ignored) {}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
