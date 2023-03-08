package DAOManager;

import Domain.AttivitaGen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AttivitaGenDAOImpl implements AttivitaGenDAO {
    private static final String FIND_ATTIVITAGEN_ID = "select * from Attivita_Gen where id = ?;";
    private static final String FIND_ALL_ATTIVITAGEN = "select * from Attivita_Gen;";
    private static final String FIND_ATTIVITAGEN_REGISTRATO_ID = "select * from Attivita_Gen ag join collabora c on (ag.id = c.Attivita_Gen_Id) where c.Registrato_Id = ?;";

    @Override
    public AttivitaGen findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ATTIVITAGEN_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        AttivitaGen res = rs.next() ? this.mapRowToAttivitaGen(rs) : null;
        con.close();
        return res;
    }

    @Override
    public List<AttivitaGen> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_ATTIVITAGEN);
        ResultSet rs = pst.executeQuery();
        LinkedList<AttivitaGen> la = new LinkedList<>();
        while(rs.next()){
            la.add(this.mapRowToAttivitaGen(rs));
        }
        con.close();
        return la;
    }

    @Override
    public List<AttivitaGen> findByRegistratoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ATTIVITAGEN_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<AttivitaGen> la = new LinkedList<>();
        while(rs.next()){
            la.add(this.mapRowToAttivitaGen(rs));
        }
        con.close();
        return la;
    }

    public AttivitaGen mapRowToAttivitaGen(ResultSet rs) throws SQLException{
        return new AttivitaGen(
                rs.getInt("id"),
                rs.getString("descrizione")
        );
    }
    
}
