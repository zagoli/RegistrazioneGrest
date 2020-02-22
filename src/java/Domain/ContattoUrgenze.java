package Domain;

public class ContattoUrgenze {
    private int id;
    private String fisso;
    private String cellulare;
    private String nome;
    private String cognome;
    private String relazione;
    private Registrato registrato;

    public ContattoUrgenze() {
    }

    public ContattoUrgenze(int id, String fisso, String cellulare, String nome, String cognome, String relazione, Registrato registrato) {
        this.id = id;
        this.fisso = fisso;
        this.cellulare = cellulare;
        this.nome = nome;
        this.cognome = cognome;
        this.relazione = relazione;
        this.registrato = registrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFisso() {
        return fisso;
    }

    public void setFisso(String fisso) {
        this.fisso = fisso;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
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

    public String getRelazione() {
        return relazione;
    }

    public void setRelazione(String relazione) {
        this.relazione = relazione;
    }

    public Registrato getRegistrato() {
        return registrato;
    }

    public void setRegistrato(Registrato registrato) {
        this.registrato = registrato;
    }
    
}
