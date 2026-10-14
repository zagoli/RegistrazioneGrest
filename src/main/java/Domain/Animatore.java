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
public class Animatore extends Iscritto {
    private String cellulare;
    private String fasciaEtaRagazzi;
    private String mail;
    private String codiceFiscale;
    private boolean responsabileSquadra;
    private boolean responsabileLaboratorio;
    public Animatore(int id, String nome, String cognome, Date dataNascita, String presenza,
                     Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato,
                     Circolo circolo, String cellulare, String fasciaEtaRagazzi, String mail,
                     String nTessera, Squadra squadra, String codiceFiscale,
                     boolean responsabileSquadra, boolean responsabileLaboratorio) {
        super(id, nome, cognome, dataNascita, presenza, laboratorio, parrocchia, registrato,
                circolo, nTessera, squadra);
        this.cellulare = cellulare;
        this.fasciaEtaRagazzi = fasciaEtaRagazzi;
        this.mail = mail;
        this.codiceFiscale = codiceFiscale;
        this.responsabileSquadra = responsabileSquadra;
        this.responsabileLaboratorio = responsabileLaboratorio;
    }
}
