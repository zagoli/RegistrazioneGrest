package Domain;

public class IscrittoPagamento {
    private final int id;
    private final String nome;
    private final String cognome;
    private final String localita;

    public IscrittoPagamento(int id, String nome, String cognome, String localita) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.localita = localita;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getLocalita() {
        return localita;
    }
}
