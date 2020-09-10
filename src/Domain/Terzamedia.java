package Domain;

import java.util.Date;

public class Terzamedia {

    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String presenza;
    private Laboratorio laboratorio;
    private Parrocchia parrocchia;
    private Registrato registrato;
    private Circolo circolo;
    private String richieste;
    private String noteAlimentari;
    private Boolean saNuotare;
    private Boolean festaPassaggio;
    private Scuola scuola;
    private String sezione;
    private String nTessera;
    private Squadra squadra;
    private String cellulare;
    private String mail;

    public Terzamedia() {
    }

    public Terzamedia(int id, String nome, String cognome, Date dataNascita, String presenza, Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato, Circolo circolo, String richieste, String noteAlimentari, Boolean saNuotare, Boolean festaPassaggio, Scuola scuola, String sezione, String nTessera, Squadra squadra, String cellulare, String mail) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.presenza = presenza;
        this.laboratorio = laboratorio;
        this.parrocchia = parrocchia;
        this.registrato = registrato;
        this.circolo = circolo;
        this.richieste = richieste;
        this.noteAlimentari = noteAlimentari;
        this.saNuotare = saNuotare;
        this.festaPassaggio = festaPassaggio;
        this.scuola = scuola;
        this.sezione = sezione;
        this.nTessera = nTessera;
        this.squadra = squadra;
        this.cellulare = cellulare;
        this.mail = mail;
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

    public Boolean getSaNuotare() {
        return saNuotare;
    }

    public void setSaNuotare(Boolean saNuotare) {
        this.saNuotare = saNuotare;
    }

    public Boolean getFestaPassaggio() {
        return festaPassaggio;
    }

    public void setFestaPassaggio(Boolean festaPassaggio) {
        this.festaPassaggio = festaPassaggio;
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

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
