package DAOManager;

import Domain.Parrocchia;
import java.sql.SQLException;
import java.util.List;

public interface ParrocchiaDAO {
    //CRUD
    public void insert (Parrocchia p) throws SQLException;
    public void update (Parrocchia p) throws SQLException;
    public void delete (Integer idParrocchia) throws SQLException;
    public Parrocchia findById(int id) throws SQLException;
    
    //SELECT
    public List<Parrocchia> findAll() throws SQLException;
    public List<Parrocchia> findByName(String name) throws SQLException;
    public int count() throws SQLException;
}
