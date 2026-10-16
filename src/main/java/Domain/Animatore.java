package Domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Animatore extends Iscritto {
    private String cellulare;
    private String fasciaEtaRagazzi;
    private String mail;
    private String codiceFiscale;
    private boolean responsabileSquadra;
    private boolean responsabileLaboratorio;
}
