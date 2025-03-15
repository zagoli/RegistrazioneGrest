package DAOManager;

import Domain.Scuola;

import java.sql.SQLException;
import java.util.List;

public interface ScuolaDAO {
    Scuola findById(int id) throws SQLException;

    //SELECT
    List<Scuola> findAll() throws SQLException;
}
