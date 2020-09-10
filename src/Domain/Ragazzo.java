package Domain;

import java.util.Date;

public class Ragazzo {
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String presenza;
    private Laboratorio laboratorio;
    private Parrocchia parrocchia;
    private Registrato registrato;
    private Circolo circolo;
    private Boolean entrataAnticipata;
    private String richieste;
    private String noteAlimentari;
    private Boolean mensa;
    private Boolean saNuotare;
    private Boolean fratelloIscritto;
    private Scuola scuola;
    private String sezione;
    private String classe;
    private String nTessera;
    private Squadra squadra;

    public Ragazzo() {
    }

    public Ragazzo(int id, String nome, String cognome, Date dataNascita, String presenza, Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato, Circolo circolo, Boolean entrataAnticipata, String richieste, String noteAlimentari, Boolean mensa, Boolean saNuotare, Boolean fratelloIscritto, Scuola scuola, String sezione, String classe, String nTessera, Squadra squadra) {
        this.id = id;
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
        this.sezione = sezione;
        this.classe = classe;
        this.nTessera = nTessera;
        this.squadra = squadra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getPresenza() {
        return presenza;
    }

    public void setPresenza(String presenza) {
        this.presenza = presenza;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Parrocchia getParrocchia() {
        return parrocchia;
    }

    public void setParrocchia(Parrocchia parrocchia) {
        this.parrocchia = parrocchia;
    }

    public Registrato getRegistrato() {
        return registrato;
    }

    public void setRegistrato(Registrato registrato) {
        this.registrato = registrato;
    }

    public Circolo getCircolo() {
        return circolo;
    }

    public void setCircolo(Circolo circolo) {
        this.circolo = circolo;
    }

    public Boolean getEntrataAnticipata() {
        return entrataAnticipata;
    }

    public void setEntrataAnticipata(Boolean entrataAnticipata) {
        this.entrataAnticipata = entrataAnticipata;
    }

    public String getRichieste() {
        return richieste;
    }

    public void setRichieste(String richieste) {
        this.richieste = richieste;
    }

    public String getNoteAlimentari() {
        return noteAlimentari;
    }

    public void setNoteAlimentari(String noteAlimentari) {
        this.noteAlimentari = noteAlimentari;
    }

    public Boolean getMensa() {
        return mensa;
    }

    public void setMensa(Boolean mensa) {
        this.mensa = mensa;
    }

    public Boolean getSaNuotare() {
        return saNuotare;
    }

    public void setSaNuotare(Boolean saNuotare) {
        this.saNuotare = saNuotare;
    }

    public Boolean getFratelloIscritto() {
        return fratelloIscritto;
    }

    public void setFratelloIscritto(Boolean fratelloIscritto) {
        this.fratelloIscritto = fratelloIscritto;
    }

    public Scuola getScuola() {
        return scuola;
    }

    public void setScuola(Scuola scuola) {
        this.scuola = scuola;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getnTessera() {
        return nTessera;
    }

    public void setnTessera(String nTessera) {
        this.nTessera = nTessera;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }
    
}
