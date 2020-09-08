package Domain;

import java.sql.Timestamp;

public class CodiceSbloccoIscrizione {
    private String codice;
    private Short utilizzato;
    private Timestamp dataUtilizzo;

    public CodiceSbloccoIscrizione() {
    }

    public CodiceSbloccoIscrizione(String codice, Short utilizzato, Timestamp dataUtilizzo) {
        this.codice = codice;
        this.utilizzato = utilizzato;
        this.dataUtilizzo = dataUtilizzo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Short getUtilizzato() {
        return utilizzato;
    }

    public void setUtilizzato(Short utilizzato) {
        this.utilizzato = utilizzato;
    }

    public Timestamp getDataUtilizzo() {
        return dataUtilizzo;
    }

    public void setDataUtilizzo(Timestamp dataUtilizzo) {
        this.dataUtilizzo = dataUtilizzo;
    }
    
}
