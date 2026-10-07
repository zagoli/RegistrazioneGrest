package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animatore {
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String presenza;
    private Laboratorio laboratorio;
    private Parrocchia parrocchia;
    private Registrato registrato;
    private Circolo circolo;
    private String cellulare;
    private String fasciaEtaRagazzi;
    private String mail;
    private String nTessera;
    private Squadra squadra;
    private String codiceFiscale;
    private boolean responsabileSquadra;
    private boolean responsabileLaboratorio;

}
