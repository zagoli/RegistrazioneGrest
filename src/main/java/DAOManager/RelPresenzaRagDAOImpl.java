package DAOManager;

import Domain.RelPresenzaRag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelPresenzaRagDAOImpl implements RelPresenzaRagDAO {
    private static final String INSERT_RELPRESENZARAG = "insert into presenzaRag values (?,?);";
    private static final String DELETE_RELPRESENZARAG = "delete from presenzaRag where Calendario_idSettimana = ? and Ragazzo_id = ?;";
    private static final String FIND_RELPRESENZARAG_RAGAZZO_ID = "select * from presenzaRag where Ragazzo_id = ?;";

    @Override
    public void insert(RelPresenzaRag rpr) throws SQLException {
        RelazioneDAOOperations.updateIds(INSERT_RELPRESENZARAG, rpr.getRagazzoId(), rpr.getCalendarioId());
    }

    @Override
    public void delete(RelPresenzaRag rpr) throws SQLException {
        RelazioneDAOOperations.updateIds(DELETE_RELPRESENZARAG, rpr.getCalendarioId(), rpr.getRagazzoId());
    }

    @Override
    public List<RelPresenzaRag> findByRagazzoId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_RELPRESENZARAG_RAGAZZO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAORows.readAll(rs, this::mapRowToRelPresenzaRag);
            }
        }
    }

    public RelPresenzaRag mapRowToRelPresenzaRag(ResultSet rs) throws SQLException {
        return new RelPresenzaRag(
                rs.getInt("Calendario_idSettimana"),
                rs.getInt("Ragazzo_id")
        );
    }

}
