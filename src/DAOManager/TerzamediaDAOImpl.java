package DAOManager;

import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Parrocchia;
import Domain.Registrato;
import Domain.Scuola;
import Domain.Squadra;
import Domain.Terzamedia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class TerzamediaDAOImpl implements TerzamediaDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private final String INSERT_TERZAMEDIA = "insert into Terzamedia (nome,cognome,dataNascita,presenza,Laboratorio_id,Parrocchia_id,Registrato_id,Circolo_id,richieste,noteAlimentari,saNuotare,Scuola_id,sezione,nTessera,Squadra_id,cellulare,festaPassaggio,mail) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_TERZAMEDIA = "update Terzamedia set nome = ?, cognome = ?, dataNascita = ?, presenza = ?, Laboratorio_id = ?, Parrocchia_id = ?, Registrato_id = ?, Circolo_id = ?,  richieste = ?, noteAlimentari = ?,  saNuotare = ?,  Scuola_id = ?, sezione = ?,  nTessera = ?, Squadra_id = ?, cellulare = ?, festaPassaggio = ?, mail = ? where id = ?;";
    private final String UPDATE_SQUADRA_TERZAMEDIA = "update Terzamedia set Squadra_id = ? where id = ?;";
    private final String UPDATE_LABORATORIO_TERZAMEDIA = "update Terzamedia set Laboratorio_id = ? where id = ?;";
    private final String DELETE_TERZAMEDIA = "delete from Terzamedia where id = ?;";
    private final String GENERIC_TERZAMEDIA_FIND = "select"
            + " ter.id as terid, ter.nome as ternome, ter.cognome as tercognome, ter.dataNascita as terdataNascita, ter.presenza as terpresenza, ter.richieste as terrichieste, ter.noteAlimentari as ternoteAlimentari, ter.saNuotare as tersaNuotare, ter.sezione as tersezione, ter.nTessera as terTessera, ter.mail as termail, ter.cellulare as tercellulare, ter.festaPassaggio as terfestaPassaggio,"
            + " la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato,"
            + " pa.id as paid, pa.nome as panome, pa.luogo as paluogo,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,"
            + " ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo,"
            + " sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione,"
            + " sq.id as sqid, sq.nome as sqnome, sq.colore as sqcolore"
            + " from Terzamedia ter"
            + " join Laboratorio la on (ter.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ter.Parrocchia_id = pa.id)"
            + " join Registrato re on (ter.Registrato_id = re.id)"
            + " join Circolo ci on (ter.Circolo_id = ci.id)"
            + " join Scuola sc on (ter.Scuola_id = sc.id)"
            + " join Squadra sq on (ter.Squadra_id = sq.id)";
    private final String FIND_TERZAMEDIA_ID = GENERIC_TERZAMEDIA_FIND + " where ter.id = ?;";
    private final String FIND_ALL_TERZAMEDIA = GENERIC_TERZAMEDIA_FIND + " order by ter.cognome, ter.nome;";
    private final String FIND_TERZAMEDIA_LAB_ID = GENERIC_TERZAMEDIA_FIND + " where la.id = ? order by ter.cognome, ter.nome;";
    private final String FIND_TERZAMEDIA_SCUOLA_ID = GENERIC_TERZAMEDIA_FIND + " where sc.id = ? order by ter.cognome, ter.nome;";
    private final String FIND_TERZAMEDIA_PARROCCHIA_ID = GENERIC_TERZAMEDIA_FIND + " where pa.id = ? order by ter.cognome, ter.nome;";
    private final String FIND_TERZAMEDIA_CIRCOLO_ID = GENERIC_TERZAMEDIA_FIND + " where ci.id = ? order by ter.cognome, ter.nome;";
    private final String FIND_TERZAMEDIA_CAL_ID = GENERIC_TERZAMEDIA_FIND + " where pr.Calendario_idSettimana = ? order by ter.cognome, ter.nome;";
    private final String FIND_TERZAMEDIA_REGISTRATO_ID = GENERIC_TERZAMEDIA_FIND + " where re.id = ? order by ter.cognome, ter.nome;";
    private final String COUNT_TERZAMEDIA = "select count(*) from Terzamedia;";
    private final String COUNT_SETTIMANALE = "select pr.Calendario_idSettimana, count(*) from Terzamedia r join presenzaRag pr on (r.id = pr.Terzamedia_id) group by pr.Calendario_idSettimana;";
    // </editor-fold>

    @Override
    public void insert(Terzamedia t) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_TERZAMEDIA, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, t.getNome());
        pst.setString(2, t.getCognome());
        pst.setDate(3, new java.sql.Date(t.getDataNascita().getTime()));
        pst.setString(4, t.getPresenza());
        pst.setInt(5, t.getLaboratorio().getId());
        pst.setInt(6, t.getParrocchia().getId());
        pst.setInt(7, t.getRegistrato().getId());
        pst.setInt(8, t.getCircolo().getId());
        pst.setString(9, t.getRichieste());
        pst.setString(10, t.getNoteAlimentari());
        pst.setBoolean(11, t.getSaNuotare());
        pst.setInt(12, t.getScuola().getId());
        pst.setString(13, t.getSezione());
        pst.setString(14, t.getnTessera());
        pst.setInt(15, t.getSquadra().getId());
        pst.setString(16, t.getCellulare());
        pst.setBoolean(17, t.getFestaPassaggio());
        pst.setString(18, t.getMail());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            t.setId(rs.getInt(1));
        }
    }

    @Override
    public void update(Terzamedia t) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_TERZAMEDIA);
        pst.setString(1, t.getNome());
        pst.setString(2, t.getCognome());
        pst.setDate(3, new java.sql.Date(t.getDataNascita().getTime()));
        pst.setString(4, t.getPresenza());
        pst.setInt(5, t.getLaboratorio().getId());
        pst.setInt(6, t.getParrocchia().getId());
        pst.setInt(7, t.getRegistrato().getId());
        pst.setInt(8, t.getCircolo().getId());
        pst.setString(9, t.getRichieste());
        pst.setString(10, t.getNoteAlimentari());
        pst.setBoolean(11, t.getSaNuotare());
        pst.setInt(12, t.getScuola().getId());
        pst.setString(13, t.getSezione());
        pst.setString(14, t.getnTessera());
        pst.setInt(15, t.getSquadra().getId());
        pst.setString(16, t.getCellulare());
        pst.setBoolean(17, t.getFestaPassaggio());
        pst.setString(18, t.getMail());
        pst.setInt(19, t.getId());
        pst.executeUpdate();
    }
    
    @Override
    public void updateSquadra(int id, int idSquadra) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_SQUADRA_TERZAMEDIA);
        pst.setInt(1, idSquadra);
        pst.setInt(2, id);
        pst.executeUpdate();
    }

    @Override
    public void updateLaboratorio(int id, int idLaboratorio) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO_TERZAMEDIA);
        pst.setInt(1, idLaboratorio);
        pst.setInt(2, id);
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idTerzamedia) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_TERZAMEDIA);
        pst.setInt(1, idTerzamedia);
        pst.executeUpdate();
    }

    @Override
    public Terzamedia findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToTerzamedia(rs) : null;
    }

    @Override
    public List<Terzamedia> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_TERZAMEDIA);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_TERZAMEDIA);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public List<Terzamedia> findByLabId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_LAB_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public List<Terzamedia> findByScuolaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_SCUOLA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public List<Terzamedia> findByParrocchiaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_PARROCCHIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public List<Terzamedia> findByCircoloId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_CIRCOLO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public List<Terzamedia> findByCalendarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_CAL_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public List<Terzamedia> findByRegistratoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Terzamedia> lt = new LinkedList<>();
        while (rs.next()) {
            lt.add(this.mapRowToTerzamedia(rs));
        }
        return lt;
    }

    @Override
    public List<Integer[]> countSettimanale() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_SETTIMANALE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Integer[]> count = new LinkedList<>();
        while (rs.next()) {
            Integer[] i = {rs.getInt(1), rs.getInt(2)};
            count.add(i);
        }
        return count;
    }

    public Terzamedia mapRowToTerzamedia(ResultSet rs) throws SQLException {
        return new Terzamedia(
                rs.getInt("terid"),
                rs.getString("ternome"),
                rs.getString("tercognome"),
                rs.getDate("terdataNascita"),
                rs.getString("terpresenza"),
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
                rs.getString("terrichieste"),
                rs.getString("ternoteAlimentari"),
                rs.getBoolean("tersaNuotare"),
                rs.getBoolean("terfestaPassaggio"),
                new Scuola(
                        rs.getInt("scid"),
                        rs.getString("scgrado"),
                        rs.getString("scdescrizione")
                ),
                rs.getString("tersezione"),
                rs.getString("terTessera"),
                new Squadra(
                        rs.getInt("sqid"),
                        rs.getString("sqnome"),
                        rs.getString("sqcolore")
                ),
                rs.getString("tercellulare"),
                rs.getString("termail")
        );
    }

}
