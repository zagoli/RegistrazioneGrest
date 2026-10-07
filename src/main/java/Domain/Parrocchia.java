package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Parrocchia {
    private int id;
    private String nome;
    private String luogo;
}
