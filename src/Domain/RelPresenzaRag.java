package Domain;

public class RelPresenzaRag {
    private int calendarioId;
    private int ragazzoId;

    public RelPresenzaRag() {
    }

    public RelPresenzaRag(int calendarioId, int ragazzoId) {
        this.calendarioId = calendarioId;
        this.ragazzoId = ragazzoId;
    }

    public int getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(int calendarioId) {
        this.calendarioId = calendarioId;
    }

    public int getRagazzoId() {
        return ragazzoId;
    }

    public void setRagazzoId(int ragazzoId) {
        this.ragazzoId = ragazzoId;
    }
    
}
