package DAOManager;

import Domain.ContattoUrgenze;
import java.sql.SQLException;
import java.util.List;

public interface ContattoUrgenzeDAO {
    //CRUD
    public void insert(ContattoUrgenze cu) throws SQLException;
    public void update(ContattoUrgenze cu) throws SQLException;
    public void delete(Integer idCu) throws SQLException;
    public ContattoUrgenze findById(int id) throws SQLException;
    //SELECT
    public List<ContattoUrgenze> findAll() throws SQLException;
    public List<ContattoUrgenze> findByRegistratoId(int id) throws SQLException;
    public int count() throws SQLException;
}
