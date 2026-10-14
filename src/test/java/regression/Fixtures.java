package regression;

import Domain.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public final class Fixtures {
    private Fixtures() {}

    public static final Date BIRTH = new java.sql.Date(1262390400000L);
    public static final Date START = new java.sql.Date(1780272000000L);
    public static final Date END = new java.sql.Date(1780617600000L);

    public static Registrato registrato() {
        return new Registrato(41, "fixture@example.invalid", "not-a-real-password-hash",
                "Ada", "Esempio", "0000000041", "Localita test", "Via sintetica", "12B", 3);
    }

    public static Laboratorio laboratorio() { return new Laboratorio(11, "Laboratorio sintetico", false); }
    public static Parrocchia parrocchia() { return new Parrocchia(12, "Parrocchia test", "Localita test"); }
    public static Circolo circolo() { return new Circolo(13, "Circolo test", "Paese test"); }
    public static Scuola scuola() { return new Scuola(14, "primaria", "Scuola sintetica"); }
    public static Squadra squadra() { return new Squadra(15, "Squadra test", "#12ab34"); }
    public static Calendario calendario() { return new Calendario(1, START, END); }
    public static Calendario secondoCalendario() { return new Calendario(2, END, new java.sql.Date(1781222400000L)); }

    public static Ragazzo ragazzo() {
        return new Ragazzo(21, "Ragazzo", "Sintetico", BIRTH, "C", laboratorio(), parrocchia(),
                registrato(), circolo(), true, "Richiesta di prova", "Nota alimentare di prova",
                true, true, true, scuola(), "B", "4", "R-123", squadra());
    }

    public static TerzaMedia terzamedia() {
        return new TerzaMedia(22, "Terza", "Sintetica", BIRTH, "C", laboratorio(), parrocchia(),
                registrato(), circolo(), "Richiesta terza", "Nota terza", true, true,
                scuola(), "C", "T-456", squadra(), "0000000022", "terza@example.invalid");
    }

    public static Animatore animatore() {
        return new Animatore(23, "Animatore", "Sintetico", BIRTH, "C", laboratorio(), parrocchia(),
                registrato(), circolo(), "0000000023", "PIC", "animatore@example.invalid",
                "A-789", squadra(), "SNTTST00A00A000A", true, true);
    }

    public static ContattoUrgenze contatto() {
        return new ContattoUrgenze(31, "0000000031", "0000000032", "Contatto", "Sintetico",
                "Genitore test", registrato());
    }
    public static Accompagnatore accompagnatore() {
        return new Accompagnatore(32, "Accompagnatore", "Sintetico", registrato());
    }
    public static AttivitaGen attivita() { return new AttivitaGen(33, "Attivita sintetica"); }
    public static Pagamento pagamento() {
        return new Pagamento(34, START, 123.5f, 21, registrato(), 7);
    }
    public static PagamentoRiepilogo riepilogo() {
        return new PagamentoRiepilogo(34, 7, START, 123.5f, "Ada", "Esempio");
    }
    public static AnnotazioneSegreteria annotazione() {
        return new AnnotazioneSegreteria(35, "Annotazione sintetica\nSeconda riga", ragazzo(),
                animatore(), terzamedia(), registrato(), new Timestamp(1780272000000L));
    }
    public static List<Calendario> calendari() { return List.of(calendario(), secondoCalendario()); }
}
