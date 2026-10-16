package Domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ragazzo extends Iscritto {
    private Boolean entrataAnticipata;
    private String richieste;
    private String noteAlimentari;
    private Boolean mensa;
    private Boolean saNuotare;
    private Boolean fratelloIscritto;
    private Scuola scuola;
    private String sezione;
    private String classe;
}
