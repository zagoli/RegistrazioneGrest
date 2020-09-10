package DAOManager;

import Domain.Scuola;
import java.sql.SQLException;
import java.util.List;

public interface ScuolaDAO {
    //CRUD
    void insert(Scuola s) throws SQLException;
    void update(Scuola s) throws SQLException;
    void delete(Integer idScuola) throws SQLException;
    Scuola findById(int id) throws SQLException;
    //SELECT
    List<Scuola> findAll() throws SQLException;
    int count() throws SQLException;
}
