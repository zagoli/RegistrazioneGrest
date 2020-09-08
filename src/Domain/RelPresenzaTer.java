package Domain;

public class RelPresenzaTer {
    private int calendarioId;
    private int terzamediaId;

    public RelPresenzaTer() {
    }

    public RelPresenzaTer(int calendarioId, int terzamediaId) {
        this.calendarioId = calendarioId;
        this.terzamediaId = terzamediaId;
    }

    public int getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(int calendarioId) {
        this.calendarioId = calendarioId;
    }

    public int getTerzamediaId() {
        return terzamediaId;
    }

    public void setTerzamediaId(int terzamediaId) {
        this.terzamediaId = terzamediaId;
    }
    
}
