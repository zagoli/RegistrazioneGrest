package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Laboratorio {
    private int id;
    private String descrizione;
    private Boolean riservato;
}
