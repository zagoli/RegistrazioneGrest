package Domain;

public class Accompagnatore {
    private int id;
    private String nome;
    private String cognome;
    private Registrato registrato;

    public Accompagnatore() {
    }

    public Accompagnatore(int id, String nome, String cognome, Registrato registrato) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.registrato = registrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Registrato getRegistrato() {
        return registrato;
    }

    public void setRegistrato(Registrato registrato) {
        this.registrato = registrato;
    }
    
}
