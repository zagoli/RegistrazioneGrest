package DAOManager;

import Domain.Circolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CircoloDAOImpl implements CircoloDAO{
    private final String INSERT_CIRCOLO = "insert into Circolo (nome,luogo) values (?,?);";
    private final String UPDATE_CIRCOLO = "update Circolo set nome = ?, luogo = ? where id = ?;";
    private final String DELETE_CIRCOLO = "delete from Circolo where id = ?;";
    private final String FIND_CIRCOLO_ID = "select * from Circolo where id = ?;";
    private final String FIND_ALL_CIRCOLO = "select * from Circolo;";
    private final String FIND_CIRCOLO_NAME = "select * from Circolo where nome = ?;";
    private final String COUNT_CIRCOLO = "select count(*) from Circolo;";

    @Override
    public void insert(Circolo c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_CIRCOLO);
        pst.setString(1,c.getNome());
        pst.setString(2,c.getLuogo());
        pst.executeUpdate();
    }

    @Override
    public void update(Circolo c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_CIRCOLO);
        pst.setString(1, c.getNome());
        pst.setString(2, c.getLuogo());
        pst.setInt(3, c.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idCircolo) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_CIRCOLO);
        pst.setInt(1, idCircolo);
        pst.executeUpdate();
    }

    @Override
    public Circolo findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CIRCOLO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Circolo c = rs.next() ? this.mapRowToCircolo(rs) : null;
        return c;
    }

    @Override
    public List<Circolo> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_CIRCOLO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Circolo> lc = new LinkedList();
        while (rs.next()){
            lc.add(this.mapRowToCircolo(rs));
        }
        return lc;
    }

    @Override
    public List<Circolo> findByName(String name) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CIRCOLO_NAME);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        LinkedList<Circolo> lc = new LinkedList();
        while (rs.next()){
            lc.add(this.mapRowToCircolo(rs));
        }
        return lc;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_CIRCOLO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    
    public Circolo mapRowToCircolo(ResultSet rs) throws SQLException{
        return new Circolo(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("luogo")
        );
    }
    
}
