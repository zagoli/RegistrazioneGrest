package Domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TerzaMediaExport {
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String presenza;
    private final Laboratorio laboratorio;
    private final Scuola scuola;
    private final Parrocchia parrocchia;
    private final Circolo circolo;
    private final String cellulare;
    private final String noteAlimentari;
    private final String richieste;
    private final String saNuotare;
    private final String festaPassaggio;
    private final String sezione;
    private final String nTessera;
    private final String mail;
    private final Squadra squadra;
    private final List<Integer> settimanePresenza;
    private final Registrato registrato;
    private final String pagato;
    private final BigDecimal importoPagamento;
}
