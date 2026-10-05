package Domain;

import Utility.BCrypt;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registrato {
    private int id;
    private String mail;
    private String password;
    private String nome;
    private String cognome;
    private String telefono;
    private String localita;
    private String via;
    private String civico;
    private Integer tipoUt;

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
