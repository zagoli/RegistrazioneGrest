package Domain;

import java.util.Date;
import java.util.Objects;

public class Calendario implements Comparable<Calendario> {
    private int idSettimana;
    private Date daQuando;
    private Date aQuando;

    public Calendario() {
    }

    public Calendario(int idSettimana, Date daQuando, Date aQuando) {
        this.idSettimana = idSettimana;
        this.daQuando = daQuando;
        this.aQuando = aQuando;
    }

    public int getIdSettimana() {
        return idSettimana;
    }

    public void setIdSettimana(int idSettimana) {
        this.idSettimana = idSettimana;
    }

    public Date getDaQuando() {
        return daQuando;
    }

    public void setDaQuando(Date daQuando) {
        this.daQuando = daQuando;
    }

    public Date getaQuando() {
        return aQuando;
    }

    public void setaQuando(Date aQuando) {
        this.aQuando = aQuando;
    }

    @Override
    public int compareTo(Calendario t) {
        int result = 1;
        if (this.idSettimana == t.idSettimana) {
            result = 0;
        } else if (this.idSettimana < t.idSettimana) {
            result = -1;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;
        if (obj instanceof Calendario) {
            Calendario cal = (Calendario) obj;
            if (cal.getIdSettimana() == this.getIdSettimana()) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idSettimana;
        hash = 97 * hash + Objects.hashCode(this.daQuando);
        hash = 97 * hash + Objects.hashCode(this.aQuando);
        return hash;
    }

}
