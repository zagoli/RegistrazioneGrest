package Controller;

import DAOManager.AnnotazioneSegreteriaDAO;
import DAOManager.DAOMan;
import Response.FreemarkerTemplate;
import Response.RedirectResult;
import Response.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public class ControllerModificaAnnotazioni implements ControllerInterface {
    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = null;
        try {
            final var idUtente = (int) request.getSession().getAttribute("idUtente");
            final var idIscritto = Integer.parseInt(request.getParameter("idIscritto"));
            if (request.getParameterMap().containsKey("add")) {
                final var tipoIscritto = request.getParameter("tipoIscritto");
                AnnotazioneSegreteriaDAO.FunzioneInserisciAnnotazione funzioneDAO = switch (tipoIscritto) {
                    case "rag" -> DAOMan.annotazioneSegreteriaDAO::insertForRagazzo;
                    case "ter" -> DAOMan.annotazioneSegreteriaDAO::insertForTerzamedia;
                    case "an" -> DAOMan.annotazioneSegreteriaDAO::insertForAnimatore;
                    default -> throw new IllegalArgumentException("Tipo iscritto non valido");
                };
                funzioneDAO.insert(
                        request.getParameter("annotazione"),
                        idIscritto,
                        idUtente
                );

                String redirectPath = "/RegistrazioneGrest/App/Modifica" +
                        switch (tipoIscritto) {
                            case "rag" -> "Ragazzo";
                            case "ter" -> "Terzamedia";
                            case "an" -> "Animatore";
                            default -> throw new IllegalArgumentException("Tipo iscritto non valido");
                        } + "?id=" + idIscritto + "#annotazioni";

                return new RedirectResult(redirectPath);
            }
        } catch (final IllegalArgumentException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerModificaAnimatore.class.getName());
        }
        return template;
    }

}
