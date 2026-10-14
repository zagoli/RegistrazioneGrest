package Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ragazzo extends Iscritto {
    private Boolean entrataAnticipata;
    private String richieste;
    private String noteAlimentari;
    private Boolean mensa;
    private Boolean saNuotare;
    private Boolean fratelloIscritto;
    private Scuola scuola;
    private String sezione;
    private String classe;
    public Ragazzo(int id, String nome, String cognome, Date dataNascita, String presenza,
                   Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato,
                   Circolo circolo, Boolean entrataAnticipata, String richieste, String noteAlimentari,
                   Boolean mensa, Boolean saNuotare, Boolean fratelloIscritto, Scuola scuola,
                   String sezione, String classe, String nTessera, Squadra squadra) {
        super(id, nome, cognome, dataNascita, presenza, laboratorio, parrocchia, registrato,
                circolo, nTessera, squadra);
        this.entrataAnticipata = entrataAnticipata;
        this.richieste = richieste;
        this.noteAlimentari = noteAlimentari;
        this.mensa = mensa;
        this.saNuotare = saNuotare;
        this.fratelloIscritto = fratelloIscritto;
        this.scuola = scuola;
        this.sezione = sezione;
        this.classe = classe;
    }
}
