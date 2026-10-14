package DAOManager;

import Domain.RelPresenzaTer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelPresenzaTerDAOImpl implements RelPresenzaTerDAO {
    private static final String INSERT_RELPRESENZATER = "insert into presenzaTer values (?,?);";
    private static final String DELETE_RELPRESENZATER = "delete from presenzaTer where Calendario_idSettimana = ? and Terzamedia_id = ?;";
    private static final String FIND_RELPRESENZATER_TER_ID = "select * from presenzaTer where Terzamedia_id = ?;";

    @Override
    public void insert(RelPresenzaTer rpt) throws SQLException {
        RelazioneDAOOperations.updateIds(INSERT_RELPRESENZATER, rpt.getTerzamediaId(), rpt.getCalendarioId());
    }

    @Override
    public void delete(RelPresenzaTer rpt) throws SQLException {
        RelazioneDAOOperations.updateIds(DELETE_RELPRESENZATER, rpt.getCalendarioId(), rpt.getTerzamediaId());
    }

    @Override
    public List<RelPresenzaTer> findByTerzamediaId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZATER_TER_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAORows.readAll(rs, this::mapRowToRelPresenzaTer);
            }
        }
    }

    public RelPresenzaTer mapRowToRelPresenzaTer(ResultSet rs) throws SQLException {
        return new RelPresenzaTer(
                rs.getInt("Calendario_idSettimana"),
                rs.getInt("Terzamedia_id")
        );
    }

}
