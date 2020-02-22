package Domain;

public class RelCollabora {
    private int id;
    private int registratoId;
    private int attivitaGenId;
    private String data;

    public RelCollabora() {
    }

    public RelCollabora(int id, int registratoId, int attivitaGenId, String data) {
        this.id = id;
        this.registratoId = registratoId;
        this.attivitaGenId = attivitaGenId;
        this.data = data;
    }
    
    public RelCollabora(int registratoId, int attivitaGenId, String data) {
        this.registratoId = registratoId;
        this.attivitaGenId = attivitaGenId;
        this.data = data;
    }

    public int getRegistratoId() {
        return registratoId;
    }

    public void setRegistratoId(int registratoId) {
        this.registratoId = registratoId;
    }

    public int getAttivitaGenId() {
        return attivitaGenId;
    }

    public void setAttivitaGenId(int attivitaGenId) {
        this.attivitaGenId = attivitaGenId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
