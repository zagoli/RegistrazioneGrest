package Domain;

import lombok.Data;

import java.util.Date;

@Data
public class PagamentoRiepilogo {
    private final int id;
    private final int ordineArrivo;
    private final Date data;
    private final float quota;
    private final String nomeRegistrato;
    private final String cognomeRegistrato;
}
