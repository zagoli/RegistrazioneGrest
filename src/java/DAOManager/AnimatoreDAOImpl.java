package DAOManager;

import Domain.Animatore;
import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Parrocchia;
import Domain.Registrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class AnimatoreDAOImpl implements AnimatoreDAO{
    
    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private final String INSERT_ANIMATORE = "insert into Animatore(nome,cognome,dataNascita,presenza,Laboratorio_id,Parrocchia_id,Registrato_id,Circolo_id,cellularePersonale,fasciaEtaRagazzi,mail,nTessera,squadra,codiceFiscale,isResponsabileSquadra,isResponsabileLaboratorio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_ANIMATORE = "update Animatore set nome = ?, cognome = ?, dataNascita = ?, presenza = ?, Laboratorio_id = ?, Parrocchia_id = ?, Registrato_id = ?, Circolo_id = ?, cellularePersonale = ?, fasciaEtaRagazzi = ?, mail = ?, nTessera = ?, squadra = ?, codiceFiscale = ?, isResponsabileSquadra = ?, isResponsabileLaboratorio = ? where id = ?;";
    private final String UPDATE_SQUADRA_ANIMATORE = "update Animatore set squadra = ?, isResponsabileSquadra = ? where id = ?;";
    private final String UPDATE_LABORATORIO_ANIMATORE = "update Animatore set Laboratorio_id = ?, isResponsabileLaboratorio = ? where id = ?;";
    private final String DELETE_ANIMATORE = "delete from Animatore where id = ?;";
    private final String FIND_ANIMATORE_ID = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra, an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " where an.id = ?";
    private final String FIND_ALL_ANIMATORE = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " order by an.cognome, an.nome asc;";
    private final String COUNT_ANIMATORE = "select count(*) from Animatore;";
    private final String FIND_ANIMATORE_NOMINATIVO = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " where an.nome = ? and an.cognome = ?"
            + " order by an.cognome, an.nome asc;";
    private final String FIND_ANIMATORE_LAB_ID = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " where la.id = ?"
            + " order by an.dataNascita, an.cognome, an.nome asc;";
    private final String FIND_ANIMATORE_PARROCCHIA_ID = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " where pa.id = ?"
            + " order by an.cognome, an.nome asc;";
    private final String FIND_ANIMATORE_CIRCOLO_ID = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " where ci.id = ?"
            + " order by an.cognome, an.nome asc;";
    private final String FIND_ANIMATORE_CALENDARIO_ID = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " join presenzaAn pra on (an.id = pra.Animatore_id)"
            + " where pra.Calendario_idSettimana = ?"
            + " order by an.cognome, an.nome asc;";
    private final String FIND_ANIMATORE_REGISTRATO_ID = "select an.codiceFiscale as ancodiceFiscale, an.squadra as ansquadra,an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio"
            + " from Animatore an"
            + " join Parrocchia pa on (an.Parrocchia_id = pa.id)"
            + " join Circolo ci on (an.Circolo_id = ci.id)"
            + " join Laboratorio la on (an.Laboratorio_id = la.id)"
            + " join Registrato re on (an.Registrato_id = re.id)"
            + " where re.id = ?"
            + " order by an.cognome, an.nome asc;";
    // </editor-fold>
    
    @Override
    public void insert(Animatore a) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_ANIMATORE,Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, a.getNome());
        pst.setString(2, a.getCognome());
        pst.setDate(3, new java.sql.Date(a.getDataNascita().getTime()));
        pst.setString(4, a.getPresenza());
        pst.setInt(5, a.getLaboratorio().getId());
        pst.setInt(6, a.getParrocchia().getId());
        pst.setInt(7, a.getRegistrato().getId());
        pst.setInt(8, a.getCircolo().getId());
        pst.setString(9, a.getCellulare());
        pst.setString(10, a.getFasciaEtaRagazzi());
        pst.setString(11, a.getMail());
        pst.setString(12, a.getnTessera());
        pst.setString(13, a.getSquadra());
        pst.setString(14, a.getCodiceFiscale());
        pst.setBoolean(15, a.isResponsabileSquadra());
        pst.setBoolean(16, a.isResponsabileLaboratorio());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            a.setId(rs.getInt(1));
        }
    }

    @Override
    public void update(Animatore a) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_ANIMATORE);
        pst.setString(1, a.getNome());
        pst.setString(2, a.getCognome());
        pst.setDate(3, new java.sql.Date(a.getDataNascita().getTime()));
        pst.setString(4, a.getPresenza());
        pst.setInt(5, a.getLaboratorio().getId());
        pst.setInt(6, a.getParrocchia().getId());
        pst.setInt(7, a.getRegistrato().getId());
        pst.setInt(8, a.getCircolo().getId());
        pst.setString(9, a.getCellulare());
        pst.setString(10, a.getFasciaEtaRagazzi());
        pst.setString(11, a.getMail());
        pst.setString(12, a.getnTessera());
        pst.setString(13, a.getSquadra());
        pst.setString(14, a.getCodiceFiscale());
        pst.setBoolean(15, a.isResponsabileSquadra());
        pst.setBoolean(16, a.isResponsabileLaboratorio());
        pst.setInt(17, a.getId());
        pst.executeUpdate();
    }
    
    @Override
    public void updateSquadra(int id, String squadra, boolean responsabile) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_SQUADRA_ANIMATORE);
        pst.setString(1, squadra);
        pst.setBoolean(2, responsabile);
        pst.setInt(3, id);
        pst.executeUpdate();
    }

    @Override
    public void updateLaboratorio(int id, int idLaboratorio, boolean responsabile) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO_ANIMATORE);
        pst.setInt(1, idLaboratorio);
        pst.setBoolean(2, responsabile);
        pst.setInt(3, id);
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idAnimatore) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_ANIMATORE);
        pst.setInt(1, idAnimatore);
        pst.executeUpdate();
    }

    @Override
    public Animatore findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Animatore a = rs.next() ? this.mapRowToAnimatore(rs) : null;
        return a;
    }

    @Override
    public List<Animatore> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_ANIMATORE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Animatore> la = new LinkedList<>();
        while (rs.next()){
            Animatore a = this.mapRowToAnimatore(rs);
            la.add(a);
        }
        return la;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_ANIMATORE);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }

    @Override
    public Animatore findByNominativo(String nome, String cognome) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_NOMINATIVO);
        pst.setString(1, nome);
        pst.setString(2, cognome);
        ResultSet rs = pst.executeQuery();
        Animatore a = rs.next() ? this.mapRowToAnimatore(rs) : null;
        return a;
    }

    @Override
    public List<Animatore> findByLab(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_LAB_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Animatore> la = new LinkedList<>();
        while (rs.next()){
            Animatore a = this.mapRowToAnimatore(rs);
            la.add(a);
        }
        return la;
    }

    @Override
    public List<Animatore> findByParrocchiaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_PARROCCHIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Animatore> la = new LinkedList<>();
        while (rs.next()){
            Animatore a = this.mapRowToAnimatore(rs);
            la.add(a);
        }
        return la;
    }

    @Override
    public List<Animatore> findByCircoloId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_CIRCOLO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Animatore> la = new LinkedList<>();
        while (rs.next()){
            Animatore a = this.mapRowToAnimatore(rs);
            la.add(a);
        }
        return la;
    }

    @Override
    public List<Animatore> findByCalendarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_CALENDARIO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Animatore> la = new LinkedList<>();
        while (rs.next()){
            Animatore a = this.mapRowToAnimatore(rs);
            la.add(a);
        }
        return la;
    }
    
    
    @Override
    public List<Animatore> findByRegistratoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Animatore> la = new LinkedList<>();
        while (rs.next()){
            Animatore a = this.mapRowToAnimatore(rs);
            la.add(a);
        }
        return la;
    }
    
    public Animatore mapRowToAnimatore (ResultSet rs) throws SQLException{
        Animatore a = new Animatore(
                rs.getInt("anid"), 
                rs.getString("annome"),
                rs.getString("ancognome"),
                rs.getDate("andataNascita"),
                rs.getString("anpresenza"),
                new Laboratorio(
                    rs.getInt("laid"), 
                    rs.getString("ladescrizione"),
                    rs.getBoolean("lariservato")
                ),
                new Parrocchia(
                    rs.getInt("paid"), 
                    rs.getString("panome"), 
                    rs.getString("paluogo")
                ),
                new Registrato(
                    rs.getInt("reid"),
                    rs.getString("remail"),
                    rs.getString("repassword"),
                    rs.getString("renome"),
                    rs.getString("recognome"),
                    rs.getString("retelefono"),
                    rs.getString("relocalita"),
                    rs.getString("revia"),
                    rs.getString("recivico"),
                    rs.getInt("retipoUt")
                ),
                new Circolo(
                    rs.getInt(("ciid")), 
                    rs.getString("cinome"), 
                    rs.getString("ciluogo")
                ),
                rs.getString("ancellulare"),
                rs.getString("anfasciaEtaRagazzi"),
                rs.getString("anmail"),
                rs.getString("annTessera"),
                rs.getString("ansquadra"),
                rs.getString("ancodiceFiscale"),
                rs.getBoolean("anisResponsabileSquadra"),
                rs.getBoolean("anisResponsabileLaboratorio")
        );
        return a;
    }

}
