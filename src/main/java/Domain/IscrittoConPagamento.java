package Domain;

public class IscrittoConPagamento<I> {
    private final I iscritto;
    private final PagamentoRiepilogo pagamento;

    public IscrittoConPagamento(I iscritto, PagamentoRiepilogo pagamento) {
        this.iscritto = iscritto;
        this.pagamento = pagamento;
    }

    public I getIscritto() {
        return iscritto;
    }

    public PagamentoRiepilogo getPagamento() {
        return pagamento;
    }
}
