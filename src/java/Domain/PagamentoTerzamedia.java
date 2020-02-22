package Domain;

import java.util.Date;

public class PagamentoTerzamedia {
    private int id;
    private Date data;
    private float quota;
    private Terzamedia terzamedia;
    private Registrato registrato;
    private int ordineArrivo;

    public PagamentoTerzamedia() {
    }

    public PagamentoTerzamedia(int id, Date data, float quota, Terzamedia terzamedia, Registrato registrato, int ordineArrivo) {
        this.id = id;
        this.data = data;
        this.quota = quota;
        this.terzamedia = terzamedia;
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

    public Terzamedia getTerzamedia() {
        return terzamedia;
    }

    public void setTerzamedia(Terzamedia terzamedia) {
        this.terzamedia = terzamedia;
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
