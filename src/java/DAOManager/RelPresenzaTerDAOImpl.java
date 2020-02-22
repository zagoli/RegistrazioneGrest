package DAOManager;

import Domain.RelPresenzaTer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RelPresenzaTerDAOImpl implements RelPresenzaTerDAO{
    private final String INSERT_RELPRESENZATER = "insert into presenzaTer values (?,?);";
    private final String DELETE_RELPRESENZATER = "delete from presenzaTer where Calendario_idSettimana = ? and Terzamedia_id = ?;";
    private final String FIND_RELPRESENZATER_TER_ID = "select * from presenzaTer where Terzamedia_id = ?;";
    private final String FIND_RELPRESENZATER_CAL_ID = "select * from presenzaTer where Calendario_idSettimana = ?;";

    @Override
    public void insert(RelPresenzaTer rpt) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_RELPRESENZATER);
        pst.setInt(1, rpt.getTerzamediaId());
        pst.setInt(2, rpt.getCalendarioId());
        pst.executeUpdate();
    }

    @Override
    public void delete(RelPresenzaTer rpt) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_RELPRESENZATER);
        pst.setInt(1, rpt.getCalendarioId());
        pst.setInt(2, rpt.getTerzamediaId());
        pst.executeUpdate();
    }

    @Override
    public List<RelPresenzaTer> findByTerzamediaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZATER_TER_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaTer> lrpt = new LinkedList<>();
        while (rs.next()) {
            RelPresenzaTer rpt = this.mapRowToRelPresenzaTer(rs);
            lrpt.add(rpt);
        }
        return lrpt;
    }

    @Override
    public List<RelPresenzaTer> findByCalendarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZATER_CAL_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelPresenzaTer> lrpt = new LinkedList<>();
        while (rs.next()) {
            RelPresenzaTer rpt = this.mapRowToRelPresenzaTer(rs);
            lrpt.add(rpt);
        }
        return lrpt;
    }
    
    public RelPresenzaTer mapRowToRelPresenzaTer (ResultSet rs) throws SQLException {
        RelPresenzaTer rpt = new RelPresenzaTer(
                rs.getInt("Calendario_idSettimana"),
                rs.getInt("Terzamedia_id")
        );
        return rpt;
    }
    
}
