package Domain;

import java.util.Date;

public class Pagamento {
    private int id;
    private Date data;
    private float quota;
    private Ragazzo ragazzo;
    private Registrato registrato;
    private int ordineArrivo;

    public Pagamento() {
    }

    public Pagamento(int id, Date data, float quota, Ragazzo ragazzo, Registrato registrato, int ordineArrivo) {
        this.id = id;
        this.data = data;
        this.quota = quota;
        this.ragazzo = ragazzo;
        this.registrato = registrato;
        this.ordineArrivo = ordineArrivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    public Ragazzo getRagazzo() {
        return ragazzo;
    }

    public void setRagazzo(Ragazzo ragazzo) {
        this.ragazzo = ragazzo;
    }

    public Registrato getRegistrato() {
        return registrato;
    }

    public void setRegistrato(Registrato registrato) {
        this.registrato = registrato;
    }

    public int getOrdineArrivo() {
        return ordineArrivo;
    }

    public void setOrdineArrivo(int ordineArrivo) {
        this.ordineArrivo = ordineArrivo;
    }
    
}
