package Domain;

public class RelPresenzaAn {
    private int animatoreId;
    private int calendarioId;

    public RelPresenzaAn() {
    }

    public RelPresenzaAn(int animatoreId, int calendarioId) {
        this.animatoreId = animatoreId;
        this.calendarioId = calendarioId;
    }

    public int getAnimatoreId() {
        return animatoreId;
    }

    public void setAnimatoreId(int animatoreId) {
        this.animatoreId = animatoreId;
    }

    public int getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(int calendarioId) {
        this.calendarioId = calendarioId;
    }

}
