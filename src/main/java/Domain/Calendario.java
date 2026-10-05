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
    public int compareTo(Calendario t) {
        int result = 1;
        if (this.idSettimana == t.idSettimana) {
            result = 0;
        } else if (this.idSettimana < t.idSettimana) {
            result = -1;
        }
        return result;
    }
}
