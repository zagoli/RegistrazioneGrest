package DAOManager;

import Domain.Circolo;

import java.sql.SQLException;
import java.util.List;

public interface CircoloDAO {
    Circolo findById(int id) throws SQLException;

    //SELECT
    List<Circolo> findAll() throws SQLException;

}
