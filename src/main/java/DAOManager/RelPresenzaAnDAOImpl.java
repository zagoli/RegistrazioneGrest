package DAOManager;

import Domain.RelPresenzaAn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelPresenzaAnDAOImpl implements RelPresenzaAnDAO {
    private static final String INSERT_RELPRESENZAAN = "insert into presenzaAn values (?,?);";
    private static final String DELETE_RELPRESENZAAN = "delete from presenzaAn where Calendario_idSettimana = ? and Animatore_id = ?;";
    private static final String FIND_RELPRESENZAAN_ANIMATORE_ID = "select * from presenzaAn where Animatore_id = ?;";

    @Override
    public void insert(RelPresenzaAn rpa) throws SQLException {
        RelazioneDAOOperations.updateIds(INSERT_RELPRESENZAAN, rpa.getAnimatoreId(), rpa.getCalendarioId());
    }

    @Override
    public void delete(RelPresenzaAn rpa) throws SQLException {
        RelazioneDAOOperations.updateIds(DELETE_RELPRESENZAAN, rpa.getCalendarioId(), rpa.getAnimatoreId());
    }

    @Override
    public List<RelPresenzaAn> findByAnimatoreId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZAAN_ANIMATORE_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAORows.readAll(rs, this::mapRowToRelPresenzaAn);
            }
        }
    }

    public RelPresenzaAn mapRowToRelPresenzaAn(ResultSet rs) throws SQLException {
        return new RelPresenzaAn(
                rs.getInt("Animatore_id"),
                rs.getInt("Calendario_idSettimana")
        );
    }

}
