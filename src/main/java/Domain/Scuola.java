package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scuola {
    private int id;
    private String grado;
    private String descrizione;
}
