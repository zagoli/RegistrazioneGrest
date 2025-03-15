package DAOManager;

import Domain.RelPresenzaTer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RelPresenzaTerDAOImpl implements RelPresenzaTerDAO {
    private static final String INSERT_RELPRESENZATER = "insert into presenzaTer values (?,?);";
    private static final String DELETE_RELPRESENZATER = "delete from presenzaTer where Calendario_idSettimana = ? and Terzamedia_id = ?;";
    private static final String FIND_RELPRESENZATER_TER_ID = "select * from presenzaTer where Terzamedia_id = ?;";

    @Override
    public void insert(RelPresenzaTer rpt) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_RELPRESENZATER);
        pst.setInt(1, rpt.getTerzamediaId());
        pst.setInt(2, rpt.getCalendarioId());
        pst.executeUpdate();
        con.close();
    }

    @Override
    public void delete(RelPresenzaTer rpt) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_RELPRESENZATER);
        pst.setInt(1, rpt.getCalendarioId());
        pst.setInt(2, rpt.getTerzamediaId());
        pst.executeUpdate();
        con.close();
    }

    @Override
    public List<RelPresenzaTer> findByTerzamediaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZATER_TER_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaTer> lrpt = new LinkedList<>();
        while (rs.next()) {
            lrpt.add(this.mapRowToRelPresenzaTer(rs));
        }
        con.close();
        return lrpt;
    }

    public RelPresenzaTer mapRowToRelPresenzaTer(ResultSet rs) throws SQLException {
        return new RelPresenzaTer(
                rs.getInt("Calendario_idSettimana"),
                rs.getInt("Terzamedia_id")
        );
    }

}
