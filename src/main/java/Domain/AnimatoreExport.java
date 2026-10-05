package Domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnimatoreExport {
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String codiceFiscale;
    private final String cellulare;
    private final String mail;
    private final Parrocchia parrocchia;
    private final Circolo circolo;
    private final String nTessera;
    private final List<Integer> settimanePresenza;
    private final String presenza;
    private final String fasciaEtaRagazzi;
    private final Laboratorio laboratorio;
    private final String responsabileLaboratorio;
    private final Squadra squadra;
    private final String responsabileSquadra;
    private final Registrato registrato;
}
