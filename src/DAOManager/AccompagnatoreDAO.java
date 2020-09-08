package DAOManager;

import Domain.Accompagnatore;
import java.sql.SQLException;
import java.util.List;

public interface AccompagnatoreDAO {
    //CRUD
    public void insert(Accompagnatore a) throws SQLException;
    public void update(Accompagnatore a) throws SQLException;
    public void delete(Integer idAccompagnatore) throws SQLException;
    public Accompagnatore findById(int id) throws SQLException;
    //SELECT
    public List<Accompagnatore> findAll() throws SQLException;
    public List<Accompagnatore> findByRegistratoId(int id) throws SQLException;
    public int count() throws SQLException;
}
