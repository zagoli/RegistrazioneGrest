package DAOManager;

import Domain.AttivitaGen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AttivitaGenDAOImpl implements AttivitaGenDAO{
    private final String INSERT_ATTIVITAGEN = "insert into Attivita_Gen (descrizione) values (?);";
    private final String UPDATE_ATTIVITAGEN = "update Attivita_Gen set descrizione = ? where id = ?;";
    private final String DELETE_ATTIVITAGEN = "delete from Attivita_Gen where id = ?;";
    private final String FIND_ATTIVITAGEN_ID = "select * from Attivita_Gen where id = ?;";
    private final String FIND_ALL_ATTIVITAGEN = "select * from Attivita_Gen;";
    private final String FIND_ATTIVITAGEN_REGISTRATO_ID = "select * from Attivita_Gen ag join collabora c on (ag.id = c.Attivita_Gen_Id) where c.Registrato_Id = ?;";
    private final String COUNT_ATTIVITAGEN = "select count(*) from Attivita_Gen;";

    @Override
    public void insert(AttivitaGen l) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_ATTIVITAGEN);
        pst.setString(1,l.getDescrizione());
        pst.executeUpdate();
    }

    @Override
    public void update(AttivitaGen l) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_ATTIVITAGEN);
        pst.setString(1, l.getDescrizione());
        pst.setInt(2,l.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idAttGen) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_ATTIVITAGEN);
        pst.setInt(1, idAttGen);
        pst.executeUpdate();
    }

    @Override
    public AttivitaGen findById(int id) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ATTIVITAGEN_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        AttivitaGen a = rs.next() ? this.mapRowToAttivitaGen(rs) : null;
        return a;
    }

    @Override
    public List<AttivitaGen> findAll() throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_ATTIVITAGEN);
        ResultSet rs = pst.executeQuery();
        LinkedList<AttivitaGen> la = new LinkedList();
        while(rs.next()){
            AttivitaGen a = this.mapRowToAttivitaGen(rs);
            la.add(a);
        }
        return la;
    }

    @Override
    public List<AttivitaGen> findByRegistratoId(int id) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ATTIVITAGEN_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<AttivitaGen> la = new LinkedList();
        while(rs.next()){
            AttivitaGen a = this.mapRowToAttivitaGen(rs);
            la.add(a);
        }
        return la;
    }

    @Override
    public int count() throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_ATTIVITAGEN);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    
    public AttivitaGen mapRowToAttivitaGen(ResultSet rs) throws SQLException{
        AttivitaGen a = new AttivitaGen(
                rs.getInt("id"),
                rs.getString("descrizione")
        );
        return a;
    }
    
}
