package DAOManager;

import Domain.Squadra;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface SquadraDAO {
    // per adesso niente insert, update e delete perchè sono da fare manualmente nel database
    public Squadra findById(int id) throws SQLException;
    public Squadra findByNome(String nome) throws SQLException;
    public List<Squadra> findAll() throws SQLException;

}
