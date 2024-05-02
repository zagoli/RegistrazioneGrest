package DAOManager;

import Domain.Scuola;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ScuolaDAOImpl implements ScuolaDAO {
    final private String FIND_SCUOLA_ID = "select * from Scuola where id = ?";
    final private String FIND_ALL_SCUOLA = "select * from Scuola order by descrizione,grado";

    @Override
    public Scuola findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_SCUOLA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Scuola res = rs.next() ? this.mapRowToScuola(rs) : null;
        con.close();
        return res;
    }

    @Override
    public List<Scuola> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_SCUOLA);
        ResultSet rs = pst.executeQuery();
        LinkedList<Scuola> ls = new LinkedList<>();
        while (rs.next()) {
            ls.add(mapRowToScuola(rs));
        }
        con.close();
        return ls;
    }

    public Scuola mapRowToScuola(ResultSet rs) throws SQLException {
        return new Scuola(
                rs.getInt("id"),
                rs.getString("grado"),
                rs.getString("descrizione"));
    }

}
