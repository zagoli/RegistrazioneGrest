package Domain;

public class Scuola {
    private int id;
    private String grado;
    private String descrizione;

    public Scuola() {
    }

    public Scuola(int id, String grado, String descrizione) {
        this.id = id;
        this.grado = grado;
        this.descrizione = descrizione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
