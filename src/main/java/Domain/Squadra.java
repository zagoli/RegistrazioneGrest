package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Squadra {
    private Integer id;
    private String nome;
    private String colore;
}
