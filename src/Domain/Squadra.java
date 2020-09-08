package Domain;

/**
 *
 * @author jacopo
 */
public class Squadra {
    private int id;
    private String nome;
    private String colore;

    public Squadra() {
    }

    public Squadra(int id, String nome, String colore) {
        this.id = id;
        this.nome = nome;
        this.colore = colore;
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

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }
}
