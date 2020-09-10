package DAOManager;

import Domain.Circolo;
import java.sql.SQLException;
import java.util.List;

public interface CircoloDAO {
    //CRUD
    void insert(Circolo c) throws SQLException;
    void update(Circolo c) throws SQLException;
    void delete(Integer idCircolo) throws SQLException;
    Circolo findById(int id) throws SQLException;
    
    //SELECT
    List<Circolo> findAll() throws SQLException;
    List<Circolo> findByName(String name) throws SQLException;
    int count() throws SQLException;
}
