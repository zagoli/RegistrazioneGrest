package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnotazioneSegreteria {
    private int id;
    private String annotazione;
    private Ragazzo ragazzo;
    private Animatore animatore;
    private TerzaMedia terzaMedia;
    private Registrato segretario;
    private Timestamp dataCreazione;
}
