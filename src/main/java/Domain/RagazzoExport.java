package Domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RagazzoExport {
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String presenza;
    private final Laboratorio laboratorio;
    private final Parrocchia parrocchia;
    private final Registrato registrato;
    private final Circolo circolo;
    private final String entrataAnticipata;
    private final String richieste;
    private final String noteAlimentari;
    private final String mensa;
    private final String saNuotare;
    private final String fratelloIscritto;
    private final Scuola scuola;
    private final String nTessera;
    private final String sezione;
    private final String classe;
    private final Squadra squadra;
    private final List<Integer> settimanePresenza;
    private final String pagato;
    private final BigDecimal importoPagamento;

    public RagazzoExport(String nome, String cognome, Date dataNascita, String presenza,
                         Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato,
                         Circolo circolo, String entrataAnticipata, String richieste,
                         String noteAlimentari, String mensa, String saNuotare,
                         String fratelloIscritto, Scuola scuola, String nTessera, String sezione,
                         String classe, Squadra squadra, List<Integer> settimanePresenza,
                         String pagato, BigDecimal importoPagamento) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.presenza = presenza;
        this.laboratorio = laboratorio;
        this.parrocchia = parrocchia;
        this.registrato = registrato;
        this.circolo = circolo;
        this.entrataAnticipata = entrataAnticipata;
        this.richieste = richieste;
        this.noteAlimentari = noteAlimentari;
        this.mensa = mensa;
        this.saNuotare = saNuotare;
        this.fratelloIscritto = fratelloIscritto;
        this.scuola = scuola;
        this.nTessera = nTessera;
        this.sezione = sezione;
        this.classe = classe;
        this.squadra = squadra;
        this.settimanePresenza = settimanePresenza;
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

    public Parrocchia getParrocchia() {
        return parrocchia;
    }

    public Registrato getRegistrato() {
        return registrato;
    }

    public Circolo getCircolo() {
        return circolo;
    }

    public String getEntrataAnticipata() {
        return entrataAnticipata;
    }

    public String getRichieste() {
        return richieste;
    }

    public String getNoteAlimentari() {
        return noteAlimentari;
    }

    public String getMensa() {
        return mensa;
    }

    public String getSaNuotare() {
        return saNuotare;
    }

    public String getFratelloIscritto() {
        return fratelloIscritto;
    }

    public Scuola getScuola() {
        return scuola;
    }

    public String getnTessera() {
        return nTessera;
    }

    public String getSezione() {
        return sezione;
    }

    public String getClasse() {
        return classe;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public List<Integer> getSettimanePresenza() {
        return settimanePresenza;
    }

    public String getPagato() {
        return pagato;
    }

    public BigDecimal getImportoPagamento() {
        return importoPagamento;
    }
}
