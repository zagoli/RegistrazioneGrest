package Domain;

import java.util.Date;
import java.util.List;

public class AnimatoreExport {
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String presenza;
    private final Laboratorio laboratorio;
    private final Parrocchia parrocchia;
    private final Registrato registrato;
    private final Circolo circolo;
    private final String cellulare;
    private final String fasciaEtaRagazzi;
    private final String mail;
    private final String nTessera;
    private final String codiceFiscale;
    private final String responsabileSquadra;
    private final String responsabileLaboratorio;
    private final Squadra squadra;
    private final List<Integer> settimanePresenza;

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
