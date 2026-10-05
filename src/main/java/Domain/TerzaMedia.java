package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerzaMedia {
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String presenza;
    private Laboratorio laboratorio;
    private Parrocchia parrocchia;
    private Registrato registrato;
    private Circolo circolo;
    private String richieste;
    private String noteAlimentari;
    private Boolean saNuotare;
    private Boolean festaPassaggio;
    private Scuola scuola;
    private String sezione;
    private String nTessera;
    private Squadra squadra;
    private String cellulare;
    private String mail;
}
