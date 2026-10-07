package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelCollabora {
    private int id;
    private int registratoId;
    private int attivitaGenId;
    private String data;
}
