package Domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record RagazzoExport (
        String nome,
        String cognome,
        Date dataNascita,
        String presenza,
        Laboratorio laboratorio,
        Parrocchia parrocchia,
        Registrato registrato,
        Circolo circolo,
        String entrataAnticipata,
        String richieste,
        String noteAlimentari,
        String mensa,
        String saNuotare,
        String fratelloIscritto,
        Scuola scuola,
        String nTessera,
        String sezione,
        String classe,
        Squadra squadra,
        List<Integer> settimanePresenza,
        String pagato,
        BigDecimal importoPagamento
) {}
