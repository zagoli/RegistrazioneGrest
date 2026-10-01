package Domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TerzamediaExport {
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String presenza;
    private final Laboratorio laboratorio;
    private final Scuola scuola;
    private final Parrocchia parrocchia;
    private final Circolo circolo;
    private final String cellulare;
    private final String noteAlimentari;
    private final String richieste;
    private final String saNuotare;
    private final String festaPassaggio;
    private final String sezione;
    private final String nTessera;
    private final String mail;
    private final Squadra squadra;
    private final List<Integer> settimanePresenza;
    private final Registrato registrato;
    private final String pagato;
    private final BigDecimal importoPagamento;

    public TerzamediaExport(String nome, String cognome, Date dataNascita, String presenza,
                            Laboratorio laboratorio, Scuola scuola, Parrocchia parrocchia,
                            Circolo circolo, String cellulare, String noteAlimentari,
                            String richieste, String saNuotare, String festaPassaggio,
                            String sezione, String nTessera, String mail, Squadra squadra,
                            List<Integer> settimanePresenza, Registrato registrato, String pagato,
                            BigDecimal importoPagamento) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.presenza = presenza;
        this.laboratorio = laboratorio;
        this.scuola = scuola;
        this.parrocchia = parrocchia;
        this.circolo = circolo;
        this.cellulare = cellulare;
        this.noteAlimentari = noteAlimentari;
        this.richieste = richieste;
        this.saNuotare = saNuotare;
        this.festaPassaggio = festaPassaggio;
        this.sezione = sezione;
        this.nTessera = nTessera;
        this.mail = mail;
        this.squadra = squadra;
        this.settimanePresenza = settimanePresenza;
        this.registrato = registrato;
        this.pagato = pagato;
        this.importoPagamento = importoPagamento;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public String getPresenza() {
        return presenza;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public Scuola getScuola() {
        return scuola;
    }

    public Parrocchia getParrocchia() {
        return parrocchia;
    }

    public Circolo getCircolo() {
        return circolo;
    }

    public String getCellulare() {
        return cellulare;
    }

    public String getNoteAlimentari() {
        return noteAlimentari;
    }

    public String getRichieste() {
        return richieste;
    }

    public String getSaNuotare() {
        return saNuotare;
    }

    public String getFestaPassaggio() {
        return festaPassaggio;
    }

    public String getSezione() {
        return sezione;
    }

    public String getnTessera() {
        return nTessera;
    }

    public String getMail() {
        return mail;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public List<Integer> getSettimanePresenza() {
        return settimanePresenza;
    }

    public Registrato getRegistrato() {
        return registrato;
    }

    public String getPagato() {
        return pagato;
    }

    public BigDecimal getImportoPagamento() {
        return importoPagamento;
    }
}
