package DAOManager;

import Domain.RelPresenzaAn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RelPresenzaAnDAOImpl implements RelPresenzaAnDAO {
    private static final String INSERT_RELPRESENZAAN = "insert into presenzaAn values (?,?);";
    private static final String DELETE_RELPRESENZAAN = "delete from presenzaAn where Calendario_idSettimana = ? and Animatore_id = ?;";
    private static final String FIND_RELPRESENZAAN_ANIMATORE_ID = "select * from presenzaAn where Animatore_id = ?;";
    private static final String FIND_RELPRESENZAAN_CAL_ID = "select * from presenzaAn where Calendario_idSettimana = ?;";

    @Override
    public void insert(RelPresenzaAn rpa) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_RELPRESENZAAN);
        pst.setInt(1, rpa.getAnimatoreId());
        pst.setInt(2, rpa.getCalendarioId());
        pst.executeUpdate();
    }

    @Override
    public void delete(RelPresenzaAn rpa) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_RELPRESENZAAN);
        pst.setInt(1, rpa.getCalendarioId());
        pst.setInt(2, rpa.getAnimatoreId());
        pst.executeUpdate();
    }

    @Override
    public List<RelPresenzaAn> findByAnimatoreId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZAAN_ANIMATORE_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaAn> lrpa = new LinkedList<>();
        while (rs.next()) {
            lrpa.add(this.mapRowToRelPresenzaAn(rs));
        }
        return lrpa;
    }

    @Override
    public List<RelPresenzaAn> findByCalendarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZAAN_CAL_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaAn> lrpa = new LinkedList<>();
        while (rs.next()) {
            lrpa.add(this.mapRowToRelPresenzaAn(rs));
        }
        return lrpa;
    }
    
    public RelPresenzaAn mapRowToRelPresenzaAn (ResultSet rs) throws SQLException {
        return new RelPresenzaAn(
                rs.getInt("Animatore_id"),
                rs.getInt("Calendario_idSettimana")
        );
    }
    
}
