package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CodiceSbloccoIscrizione {
    private String codice;
    private Short utilizzato;
    private Timestamp dataUtilizzo;
}
