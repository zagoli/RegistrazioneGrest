package Domain;

import java.util.Date;
import java.util.List;

public class AnimatoreExport {
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
    private String codiceFiscale;
    private String responsabileSquadra;
    private String responsabileLaboratorio;
    private Squadra squadra;
    private List<Integer> settimanePresenza;

    public AnimatoreExport(String nome, String cognome, Date dataNascita, String presenza,
                           Laboratorio laboratorio, Parrocchia parrocchia, Registrato registrato,
                           Circolo circolo, String cellulare, String fasciaEtaRagazzi, String mail,
                           String nTessera, String codiceFiscale, String responsabileSquadra,
                           String responsabileLaboratorio, Squadra squadra,
                           List<Integer> settimanePresenza) {
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
        this.codiceFiscale = codiceFiscale;
        this.responsabileSquadra = responsabileSquadra;
        this.responsabileLaboratorio = responsabileLaboratorio;
        this.squadra = squadra;
        this.settimanePresenza = settimanePresenza;
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

    public String getCellulare() {
        return cellulare;
    }

    public String getFasciaEtaRagazzi() {
        return fasciaEtaRagazzi;
    }

    public String getMail() {
        return mail;
    }

    public String getnTessera() {
        return nTessera;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getResponsabileSquadra() {
        return responsabileSquadra;
    }

    public String getResponsabileLaboratorio() {
        return responsabileLaboratorio;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public List<Integer> getSettimanePresenza() {
        return settimanePresenza;
    }
}
