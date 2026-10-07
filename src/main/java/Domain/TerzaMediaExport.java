package Domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record TerzaMediaExport (
        String nome,
        String cognome,
        Date dataNascita,
        String presenza,
        Laboratorio laboratorio,
        Scuola scuola,
        Parrocchia parrocchia,
        Circolo circolo,
        String cellulare,
        String noteAlimentari,
        String richieste,
        String saNuotare,
        String festaPassaggio,
        String sezione,
        String nTessera,
        String mail,
        Squadra squadra,
        List<Integer> settimanePresenza,
        Registrato registrato,
        String pagato,
        BigDecimal importoPagamento
) {}
