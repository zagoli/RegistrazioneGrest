package Domain;

import Utility.BCrypt;

public class Registrato {
    private int id;
    private String mail;
    private String password;
    private String nome;
    private String cognome;
    private String telefono;
    private String localita;
    private String via;
    private String civico;
    private Integer tipoUt;
    
    public Registrato() {
    }

    public Registrato(int id, String mail, String password, String nome, String cognome, String telefono, String localita, String via, String civico, Integer tipoUt) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.localita = localita;
        this.via = via;
        this.civico = civico;
        this.tipoUt = tipoUt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings("empty-statement")
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public Integer getTipoUt() {
        return tipoUt;
    }

    public void setTipoUt(Integer tipoUt) {
        this.tipoUt = tipoUt;
    }
    
}
