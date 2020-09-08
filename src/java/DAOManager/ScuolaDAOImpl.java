 package DAOManager;

import Domain.Scuola;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ScuolaDAOImpl implements ScuolaDAO{
    final private String INSERT_SCUOLA = "insert into Scuola (grado,descrizione) values (?,?);";
    final private String UPDATE_SCUOLA = "update Scuola set grado = ?, descrizione = ? where id = ?;";
    final private String DELETE_SCUOLA = "delete from Scuola where id = ?;";
    final private String FIND_SCUOLA_ID = "select * from Scuola where id = ?";
    final private String FIND_ALL_SCUOLA = "select * from Scuola order by descrizione,grado";
    final private String COUNT_SCUOLA = "select count(*) from Scuola";

    @Override
    public void insert(Scuola s) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_SCUOLA);
        pst.setString(1, s.getGrado());
        pst.setString(2, s.getDescrizione());
        pst.executeUpdate();
    }

    @Override
    public void update(Scuola s) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_SCUOLA);
        pst.setString(1,s.getGrado());
        pst.setString(2,s.getDescrizione());
        pst.setInt(3,s.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idScuola) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_SCUOLA);
        pst.setInt(1, idScuola);
        pst.executeUpdate();
    }

    @Override
    public Scuola findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_SCUOLA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Scuola s = rs.next() ? this.mapRowToScuola(rs) : null;
        return s;
    }

    @Override
    public List<Scuola> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_SCUOLA);
        ResultSet rs = pst.executeQuery();
        LinkedList ls = new LinkedList();
        while (rs.next()) {
            ls.add(mapRowToScuola(rs));
        }
        return ls;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_SCUOLA);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    
    public Scuola mapRowToScuola(ResultSet rs) throws SQLException {
        return new Scuola(
                rs.getInt("id"),
                rs.getString("grado"),
                rs.getString("descrizione"));
    }
    
}
