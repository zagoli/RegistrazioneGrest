package DAOManager;

import Domain.Scuola;
import java.sql.SQLException;
import java.util.List;

public interface ScuolaDAO {
    //CRUD
    public void insert(Scuola s) throws SQLException;
    public void update(Scuola s) throws SQLException;
    public void delete(Integer idScuola) throws SQLException;
    public Scuola findById(int id) throws SQLException;
    //SELECT
    public List<Scuola> findAll() throws SQLException;
    public int count() throws SQLException;
}
