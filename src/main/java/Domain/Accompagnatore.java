package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accompagnatore {
    private int id;
    private String nome;
    private String cognome;
    private Registrato registrato;
}
