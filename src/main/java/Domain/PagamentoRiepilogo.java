package Domain;

import java.util.Date;

public class PagamentoRiepilogo {
    private final int id;
    private final int ordineArrivo;
    private final Date data;
    private final float quota;
    private final String nomeRegistrato;
    private final String cognomeRegistrato;

    public PagamentoRiepilogo(int id, int ordineArrivo, Date data, float quota,
                              String nomeRegistrato, String cognomeRegistrato) {
        this.id = id;
        this.ordineArrivo = ordineArrivo;
        this.data = data;
        this.quota = quota;
        this.nomeRegistrato = nomeRegistrato;
        this.cognomeRegistrato = cognomeRegistrato;
    }

    public int getId() {
        return id;
    }

    public int getOrdineArrivo() {
        return ordineArrivo;
    }

    public Date getData() {
        return data;
    }

    public float getQuota() {
        return quota;
    }

    public String getNomeRegistrato() {
        return nomeRegistrato;
    }

    public String getCognomeRegistrato() {
        return cognomeRegistrato;
    }
}
