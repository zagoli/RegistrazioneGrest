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
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_CIRCOLO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToCircolo(rs) : null;
            }
        }
    }

    @Override
    public List<Circolo> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_CIRCOLO);
             ResultSet rs = pst.executeQuery()) {
            LinkedList<Circolo> lc = new LinkedList<>();
            while (rs.next()) {
                lc.add(this.mapRowToCircolo(rs));
            }
            return lc;
        }
    }

    public Circolo mapRowToCircolo(ResultSet rs) throws SQLException {
        return new Circolo(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("luogo")
        );
    }

}
