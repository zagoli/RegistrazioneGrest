package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatiPagamento<I, P> {
    private final I iscritto;
    private final P pagamento;
    private final int settimane;

    public DatiPagamento(P pagamento, I iscritto) {
        this.pagamento = pagamento;
        this.iscritto = iscritto;
        this.settimane = 0;
    }
}
