package DAOManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

final class RelazioneDAOOperations {
    private RelazioneDAOOperations() {
    }

    static void updateIds(String query, int firstId, int secondId) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, firstId);
            pst.setInt(2, secondId);
            pst.executeUpdate();
        }
    }

    static void updateSquadra(String query, int id, Integer idSquadra) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            IscrittoDAOMapper.bindSquadra(pst, idSquadra);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }
}
