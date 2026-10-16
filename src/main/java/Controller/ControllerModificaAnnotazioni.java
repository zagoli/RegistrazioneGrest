package Controller;

import DAOManager.AnnotazioneSegreteriaDAO;
import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ControllerModificaAnnotazioni implements ControllerInterface {

    private static final Logger logger = LoggerFactory.getLogger(ControllerModificaAnnotazioni.class);

    private static String getRedirectPath(String tipoIscritto, int idIscritto) {
        return "/RegistrazioneGrest/App/Modifica" + switch (tipoIscritto) {
            case "rag" -> "Ragazzo";
            case "ter" -> "Terzamedia";
            case "an" -> "Animatore";
            default -> throw new IllegalArgumentException("Tipo iscritto non valido");
        } + "?id=" + idIscritto + "#annotazioni";
    }

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = null;
        try {
            final var idUtente = (int) request.getSession().getAttribute("idUtente");
            final var tipoIscritto = request.getParameter("tipoIscritto");
            final var idIscritto = Integer.parseInt(request.getParameter("idIscritto"));
            if (request.getParameterMap().containsKey("add")) {
                AnnotazioneSegreteriaDAO.FunzioneInserisciAnnotazione funzioneDAO = switch (tipoIscritto) {
                    case "rag" -> DAOMan.annotazioneSegreteriaDAO::insertForRagazzo;
                    case "ter" -> DAOMan.annotazioneSegreteriaDAO::insertForTerzamedia;
                    case "an" -> DAOMan.annotazioneSegreteriaDAO::insertForAnimatore;
                    default -> throw new IllegalArgumentException("Tipo iscritto non valido");
                };
                funzioneDAO.insert(request.getParameter("annotazione"), idIscritto, idUtente);
                logger.info("Annotazione inserita da utente con id {} per iscritto con id {}", idUtente, idIscritto);
                return new RedirectResult(getRedirectPath(tipoIscritto, idIscritto));
            } else if (request.getParameterMap().containsKey("del")) {
                var idAnnotazione = Integer.parseInt(request.getParameter("id"));
                DAOMan.annotazioneSegreteriaDAO.delete(idAnnotazione);
                logger.info("Annotazione eliminata da utente con id {} per iscritto con id {}", idUtente, idIscritto);
                return new RedirectResult(getRedirectPath(tipoIscritto, idIscritto));
            }
        } catch (final IllegalArgumentException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaAnimatore.class.getName());
        }
        return template;
    }

}
