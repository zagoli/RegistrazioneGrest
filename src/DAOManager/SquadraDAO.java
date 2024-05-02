package DAOManager;

import Domain.Squadra;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jacopo
 */
public interface SquadraDAO {
    Squadra findById(int id) throws SQLException;

    List<Squadra> findAll() throws SQLException;

}
