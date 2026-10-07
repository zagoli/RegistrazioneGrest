package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ragazzo {
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String presenza;
    private Laboratorio laboratorio;
    private Parrocchia parrocchia;
    private Registrato registrato;
    private Circolo circolo;
    private Boolean entrataAnticipata;
    private String richieste;
    private String noteAlimentari;
    private Boolean mensa;
    private Boolean saNuotare;
    private Boolean fratelloIscritto;
    private Scuola scuola;
    private String sezione;
    private String classe;
    private String nTessera;
    private Squadra squadra;

}
