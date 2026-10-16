package Domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TerzaMedia extends Iscritto {
    private String richieste;
    private String noteAlimentari;
    private Boolean saNuotare;
    private Boolean festaPassaggio;
    private Scuola scuola;
    private String sezione;
    private String cellulare;
    private String mail;
}
