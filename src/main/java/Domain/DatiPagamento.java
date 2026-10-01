package Domain;

public class DatiPagamento<I, P> {
    private final I iscritto;
    private final P pagamento;
    private final int settimane;

    public DatiPagamento(I iscritto, P pagamento, int settimane) {
        this.iscritto = iscritto;
        this.pagamento = pagamento;
        this.settimane = settimane;
    }

    public I getIscritto() {
        return iscritto;
    }

    public P getPagamento() {
        return pagamento;
    }

    public int getSettimane() {
        return settimane;
    }
}
