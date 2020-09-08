package Domain;

import java.util.Date;

public class Animatore {
    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String presenza;
    private Laboratorio laboratorio;
    private Parrocchia parrocchia;
    private Registrato registrato;
    private Circolo circolo;
    private String cellulare;
    private String fasciaEtaRagazzi;
    private String mail;
    private String nTessera;
    private String squadra;
    private String codiceFiscale;
    private boolean responsabileSquadra;
    private boolean responsabileLaboratorio;

    public Animatore() {
    }

    public Animatore(int id, String nome, String cognome, Date dataNascita, String presenza, Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato, Circolo circolo, String cellulare, String fasciaEtaRagazzi, String mail, String nTessera, String squadra, String codiceFiscale, boolean isResponsabileSquadra, boolean isResponsabileLaboratorio) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.presenza = presenza;
        this.laboratorio = laboratorio;
        this.parrocchia = parrocchia;
        this.registrato = registrato;
        this.circolo = circolo;
        this.cellulare = cellulare;
        this.fasciaEtaRagazzi = fasciaEtaRagazzi;
        this.mail = mail;
        this.nTessera = nTessera;
        this.squadra = squadra;
        this.codiceFiscale = codiceFiscale;
        this.responsabileSquadra = isResponsabileSquadra;
        this.responsabileLaboratorio = isResponsabileLaboratorio;
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

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getFasciaEtaRagazzi() {
        return fasciaEtaRagazzi;
    }

    public void setFasciaEtaRagazzi(String fasciaEtaRagazzi) {
        this.fasciaEtaRagazzi = fasciaEtaRagazzi;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getnTessera() {
        return nTessera;
    }

    public void setnTessera(String nTessera) {
        this.nTessera = nTessera;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public boolean isResponsabileSquadra() {
        return responsabileSquadra;
    }

    public void setResponsabileSquadra(boolean responsabileSquadra) {
        this.responsabileSquadra = responsabileSquadra;
    }

    public boolean isResponsabileLaboratorio() {
        return responsabileLaboratorio;
    }

    public void setResponsabileLaboratorio(boolean responsabileLaboratorio) {
        this.responsabileLaboratorio = responsabileLaboratorio;
    }

}
