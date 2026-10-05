package Domain;

import java.util.Date;
import java.util.List;

public record AnimatoreExport (
        String nome,
        String cognome,
        Date dataNascita,
        String codiceFiscale,
        String cellulare,
        String mail,
        Parrocchia parrocchia,
        Circolo circolo,
        String nTessera,
        List<Integer> settimanePresenza,
        String presenza,
        String fasciaEtaRagazzi,
        Laboratorio laboratorio,
        String responsabileLaboratorio,
        Squadra squadra,
        String responsabileSquadra,
        Registrato registrato
) {}
