package DAOManager;

import Domain.Calendario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CalendarioDAOImpl implements CalendarioDAO{
    private final String INSERT_CAL = "insert into Calendario (daQuando,aQuando) values (?,?);";
    private final String UPDATE_CAL = "update Calendario set daQuando = ?, aQuando = ? where idSettimana = ?;";
    private final String DELETE_CAL = "delete from Calendario where idSettimana = ?;";
    private final String FIND_CAL_ID = "select * from Calendario where idSettimana = ?;";
    private final String FIND_ALL_CAL = "select * from Calendario order by daQuando;";
    private final String FIND_CAL_INIZIO = "select * from Calendario where daQuando = ?;";
    private final String FIND_CAL_RAGAZZO = "select * from Calendario c join presenzaRag p on (c.idSettimana = p.Calendario_idSettimana) where p.Ragazzo_id = ? order by daQuando;";
    private final String FIND_CAL_ANIMATORE = "select * from Calendario c join presenzaAn p on (c.idSettimana = p.Calendario_idSettimana) where p.Animatore_id = ? order by daQuando;";
    private final String FIND_CAL_TERZAMEDIA = "select * from Calendario c join presenzaTer p on (c.idSettimana = p.Calendario_idSettimana) where p.Terzamedia_id = ? order by daQuando;";
    private final String COUNT_CAL = "select count(*) from Calendario;";
    

    @Override
    public void insert(Calendario c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_CAL);
        pst.setDate(1, (java.sql.Date) c.getDaQuando());
        pst.setDate(2, (java.sql.Date) c.getaQuando());
        pst.executeUpdate();
    }

    @Override
    public void update(Calendario c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_CAL);
        pst.setDate(1, (java.sql.Date) c.getDaQuando());
        pst.setDate(2, (java.sql.Date) c.getaQuando());
        pst.setInt(3, c.getIdSettimana());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idCalendario) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_CAL);
        pst.setInt(1, idCalendario);
        pst.executeUpdate();
    }

    @Override
    public Calendario findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CAL_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToCalendario(rs) : null;
    }

    @Override
    public List<Calendario> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_CAL);
        ResultSet rs = pst.executeQuery();
        LinkedList<Calendario> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCalendario(rs));
        }
        return lc;
    }

    @Override
    public Calendario findByDataInizio(Date data) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CAL_INIZIO);
        pst.setDate(1, (java.sql.Date) data);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToCalendario(rs) : null;
    }

    @Override
    public List<Calendario> findByRagazzoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CAL_RAGAZZO);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Calendario> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCalendario(rs));
        }
        return lc;
    }
    
    @Override
    public List<Calendario> findByTerzamediaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CAL_TERZAMEDIA);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Calendario> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCalendario(rs));
        }
        return lc;
    }

    @Override
    public List<Calendario> findByAnimatoreId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CAL_ANIMATORE);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Calendario> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCalendario(rs));
        }
        return lc;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_CAL);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    
    public Calendario mapRowToCalendario (ResultSet rs) throws SQLException{
        return new Calendario(
                rs.getInt("idSettimana"),
                rs.getDate("daQuando"),
                rs.getDate("aQuando")
        );
    }
}
