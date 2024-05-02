package Domain;

import java.util.Date;

public class PagamentoTerzamedia {
    private int id;
    private Date data;
    private float quota;
    private int terzamediaId;
    private Registrato registrato;
    private int ordineArrivo;

    public PagamentoTerzamedia() {
    }

    public PagamentoTerzamedia(int id, Date data, float quota, int terzamediaId, Registrato registrato, int ordineArrivo) {
        this.id = id;
        this.data = data;
        this.quota = quota;
        this.terzamediaId = terzamediaId;
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

    public int getTerzamediaId() {
        return terzamediaId;
    }

    public void setTerzamedia(int terzamediaId) {
        this.terzamediaId = terzamediaId;
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
