package Domain;

public class Laboratorio {
    private int id;
    private String descrizione;
    private Boolean riservato;

    public Laboratorio() {
    }

    public Laboratorio(int id, String descrizione, Boolean riservato) {
        this.id = id;
        this.descrizione = descrizione;
        this.riservato = riservato;
    }

    public Boolean getRiservato() {
        return riservato;
    }

    public void setRiservato(Boolean riservato) {
        this.riservato = riservato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
}
