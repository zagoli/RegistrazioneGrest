package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Laboratorio {
    private int id;
    private String descrizione;
    private Boolean riservato;
}
