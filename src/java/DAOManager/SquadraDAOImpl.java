package DAOManager;

import Domain.Squadra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jacopo
 */
public class SquadraDAOImpl implements SquadraDAO {

    private final String FIND_BY_ID = "select s.id as sid, s.nome as snome, s.colore as scolore from Squadra s where s.id = ?;";
    private final String FIND_BY_NOME = "select s.id as sid, s.nome as snome, s.colore as scolore from Squadra s where s.nome = ?";
    private final String FIND_ALL = "select s.id as sid, s.nome as snome, s.colore as scolore from Squadra s";

    @Override
    public Squadra findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_BY_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToSquadra(rs) : null;
    }

    @Override
    public Squadra findByNome(String nome) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_BY_NOME);
        pst.setString(1, nome);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToSquadra(rs) : null;
    }

    @Override
    public List<Squadra> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL);
        ResultSet rs = pst.executeQuery();
        LinkedList<Squadra> ls = new LinkedList<>();
        while (rs.next()) {
            ls.add(this.mapRowToSquadra(rs));
        }
        return ls;
    }

    public Squadra mapRowToSquadra(ResultSet rs) throws SQLException {
        return new Squadra(
                rs.getInt("sid"),
                rs.getString("snome"),
                rs.getString("scolore"));
    }

}
