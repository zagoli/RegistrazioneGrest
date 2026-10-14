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
public class TerzaMedia extends Iscritto {
    private String richieste;
    private String noteAlimentari;
    private Boolean saNuotare;
    private Boolean festaPassaggio;
    private Scuola scuola;
    private String sezione;
    private String cellulare;
    private String mail;
    public TerzaMedia(int id, String nome, String cognome, Date dataNascita, String presenza,
                     Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato,
                     Circolo circolo, String richieste, String noteAlimentari, Boolean saNuotare,
                     Boolean festaPassaggio, Scuola scuola, String sezione, String nTessera,
                     Squadra squadra, String cellulare, String mail) {
        super(id, nome, cognome, dataNascita, presenza, laboratorio, parrocchia, registrato,
                circolo, nTessera, squadra);
        this.richieste = richieste;
        this.noteAlimentari = noteAlimentari;
        this.saNuotare = saNuotare;
        this.festaPassaggio = festaPassaggio;
        this.scuola = scuola;
        this.sezione = sezione;
        this.cellulare = cellulare;
        this.mail = mail;
    }
}
