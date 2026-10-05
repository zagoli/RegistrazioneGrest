package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContattoUrgenze {
    private int id;
    private String fisso;
    private String cellulare;
    private String nome;
    private String cognome;
    private String relazione;
    private Registrato registrato;
}
