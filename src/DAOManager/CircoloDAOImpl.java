package DAOManager;

import Domain.Circolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CircoloDAOImpl implements CircoloDAO {
    private static final String FIND_CIRCOLO_ID = "select * from Circolo where id = ?;";
    private static final String FIND_ALL_CIRCOLO = "select * from Circolo;";

    @Override
    public Circolo findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CIRCOLO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Circolo res = rs.next() ? this.mapRowToCircolo(rs) : null;
        con.close();
        return res;
    }

    @Override
    public List<Circolo> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_CIRCOLO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Circolo> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCircolo(rs));
        }
        con.close();
        return lc;
    }

    public Circolo mapRowToCircolo(ResultSet rs) throws SQLException{
        return new Circolo(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("luogo")
        );
    }
    
}
