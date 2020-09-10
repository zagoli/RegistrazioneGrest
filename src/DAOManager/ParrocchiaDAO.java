package DAOManager;

import Domain.Parrocchia;
import java.sql.SQLException;
import java.util.List;

public interface ParrocchiaDAO {
    //CRUD
    void insert(Parrocchia p) throws SQLException;
    void update(Parrocchia p) throws SQLException;
    void delete(Integer idParrocchia) throws SQLException;
    Parrocchia findById(int id) throws SQLException;
    
    //SELECT
    List<Parrocchia> findAll() throws SQLException;
    List<Parrocchia> findByName(String name) throws SQLException;
    int count() throws SQLException;
}
