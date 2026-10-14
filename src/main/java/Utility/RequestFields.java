package Utility;

import Domain.ContattoUrgenze;
import Domain.Animatore;
import Domain.Iscritto;
import Domain.Ragazzo;
import Domain.Registrato;
import Domain.TerzaMedia;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

public final class RequestFields {
    private RequestFields() {
    }

    public static void optionalText(HttpServletRequest request, String name, Consumer<String> setter) {
        String value = request.getParameter(name);
        if (value != null) {
            setter.accept(value.isEmpty() ? null : value);
        }
    }

    public static void iscritto(HttpServletRequest request, Iscritto iscritto) throws ParseException {
        iscritto.setNome(request.getParameter("nome"));
        iscritto.setCognome(request.getParameter("cognome"));
        iscritto.setDataNascita(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataNascita")));
        iscritto.setPresenza(request.getParameter("presenza"));
        optionalText(request, "nTessera", iscritto::setNTessera);
    }

    public static void ragazzo(HttpServletRequest request, Ragazzo ragazzo) {
        ragazzo.setSezione(request.getParameter("sezione"));
        ragazzo.setClasse(request.getParameter("classe"));
        ragazzo.setEntrataAnticipata(request.getParameterMap().containsKey("entrataAnticipata"));
        ragazzo.setMensa(request.getParameterMap().containsKey("mensa"));
        ragazzo.setSaNuotare(request.getParameterMap().containsKey("saNuotare"));
        optionalText(request, "richieste", ragazzo::setRichieste);
        optionalText(request, "noteAlimentari", ragazzo::setNoteAlimentari);
    }

    public static void terzaMedia(HttpServletRequest request, TerzaMedia terzaMedia) {
        terzaMedia.setSezione(request.getParameter("sezione"));
        terzaMedia.setMail(request.getParameter("mail"));
        terzaMedia.setSaNuotare(request.getParameterMap().containsKey("saNuotare"));
        terzaMedia.setFestaPassaggio(request.getParameterMap().containsKey("festaPassaggio"));
        optionalText(request, "cellulare", terzaMedia::setCellulare);
        optionalText(request, "richieste", terzaMedia::setRichieste);
        optionalText(request, "noteAlimentari", terzaMedia::setNoteAlimentari);
    }

    public static void account(HttpServletRequest request, Registrato registrato) {
        registrato.setMail(request.getParameter("mail"));
        registrato.setNome(request.getParameter("nome"));
        registrato.setCognome(request.getParameter("cognome"));
        optionalText(request, "telefono", registrato::setTelefono);
        optionalText(request, "localita", registrato::setLocalita);
        optionalText(request, "via", registrato::setVia);
        optionalText(request, "civico", registrato::setCivico);
    }

    public static void animatore(HttpServletRequest request, Animatore animatore) {
        animatore.setCellulare(request.getParameter("cellulare"));
        animatore.setFasciaEtaRagazzi(request.getParameter("fasciaEtaRagazzi"));
        animatore.setMail(request.getParameter("mail"));
        animatore.setCodiceFiscale(request.getParameter("codiceFiscale").toUpperCase());
    }

    public static void contatto(HttpServletRequest request, ContattoUrgenze contatto) {
        contatto.setNome(request.getParameter("nome"));
        contatto.setCognome(request.getParameter("cognome"));
        contatto.setRelazione(request.getParameter("relazione"));
        optionalText(request, "cellulare", contatto::setCellulare);
        optionalText(request, "fisso", contatto::setFisso);
    }
}
