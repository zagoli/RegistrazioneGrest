package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PagamentoTerzamedia {
    private int id;
    private Date data;
    private float quota;
    private int terzamediaId;
    private Registrato registrato;
    private int ordineArrivo;
}
