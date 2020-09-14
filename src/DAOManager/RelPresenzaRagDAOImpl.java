package DAOManager;

import Domain.RelPresenzaRag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RelPresenzaRagDAOImpl implements RelPresenzaRagDAO {
    private static final String INSERT_RELPRESENZARAG = "insert into presenzaRag values (?,?);";
    private static final String DELETE_RELPRESENZARAG = "delete from presenzaRag where Calendario_idSettimana = ? and Ragazzo_id = ?;";
    private static final String FIND_RELPRESENZARAG_RAGAZZO_ID = "select * from presenzaRag where Ragazzo_id = ?;";
    private static final String FIND_RELPRESENZARAG_CAL_ID = "select * from presenzaRag where Calendario_idSettimana = ?;";

    @Override
    public void insert(RelPresenzaRag rpr) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_RELPRESENZARAG);
        pst.setInt(1, rpr.getRagazzoId());
        pst.setInt(2, rpr.getCalendarioId());
        pst.executeUpdate();
    }

    @Override
    public void delete(RelPresenzaRag rpr) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_RELPRESENZARAG);
        pst.setInt(1, rpr.getCalendarioId());
        pst.setInt(2, rpr.getRagazzoId());
        pst.executeUpdate();
    }

    @Override
    public List<RelPresenzaRag> findByRagazzoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZARAG_RAGAZZO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaRag> lrpr = new LinkedList<>();
        while (rs.next()) {
            lrpr.add(this.mapRowToRelPresenzaRag(rs));
        }
        return lrpr;
    }

    @Override
    public List<RelPresenzaRag> findByCalendarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZARAG_CAL_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaRag> lrpr = new LinkedList<>();
        while (rs.next()) {
            lrpr.add(this.mapRowToRelPresenzaRag(rs));
        }
        return lrpr;
    }
    
    public RelPresenzaRag mapRowToRelPresenzaRag (ResultSet rs) throws SQLException {
        return new RelPresenzaRag(
                rs.getInt("Calendario_idSettimana"),
                rs.getInt("Ragazzo_id")
        );
    }
    
}
