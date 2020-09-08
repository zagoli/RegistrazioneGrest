package DAOManager;

import Domain.Circolo;
import java.sql.SQLException;
import java.util.List;

public interface CircoloDAO {
    //CRUD
    public void insert (Circolo c) throws SQLException;
    public void update (Circolo c) throws SQLException;
    public void delete (Integer idCircolo) throws SQLException;
    public Circolo findById(int id) throws SQLException;
    
    //SELECT
    public List<Circolo> findAll() throws SQLException;
    public List<Circolo> findByName(String name) throws SQLException;
    public int count() throws SQLException;
}
