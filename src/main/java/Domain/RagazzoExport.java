package Domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RagazzoExport {
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String presenza;
    private final Laboratorio laboratorio;
    private final Parrocchia parrocchia;
    private final Registrato registrato;
    private final Circolo circolo;
    private final String entrataAnticipata;
    private final String richieste;
    private final String noteAlimentari;
    private final String mensa;
    private final String saNuotare;
    private final String fratelloIscritto;
    private final Scuola scuola;
    private final String nTessera;
    private final String sezione;
    private final String classe;
    private final Squadra squadra;
    private final List<Integer> settimanePresenza;
    private final String pagato;
    private final BigDecimal importoPagamento;

}
