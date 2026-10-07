package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Calendario implements Comparable<Calendario> {
    private int idSettimana;
    private Date daQuando;
    private Date aQuando;

    @Override
    public int compareTo(Calendario other) {
        return other.idSettimana - this.idSettimana;
    }
}
