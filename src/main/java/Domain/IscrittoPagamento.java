package Domain;

import lombok.Getter;

@Getter
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
}
