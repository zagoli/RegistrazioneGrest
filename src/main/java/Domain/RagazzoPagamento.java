package Domain;

import lombok.Getter;

@Getter
public class RagazzoPagamento extends IscrittoPagamento {
    private final boolean mensa;
    private final boolean fratelloIscritto;
    private final boolean entrataAnticipata;

    public RagazzoPagamento(int id, String nome, String cognome, String localita, boolean mensa, boolean fratelloIscritto, boolean entrataAnticipata) {
        super(id, nome, cognome, localita);
        this.mensa = mensa;
        this.fratelloIscritto = fratelloIscritto;
        this.entrataAnticipata = entrataAnticipata;
    }
}
