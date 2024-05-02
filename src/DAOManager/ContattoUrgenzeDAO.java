package DAOManager;

import Domain.ContattoUrgenze;

import java.sql.SQLException;
import java.util.List;

public interface ContattoUrgenzeDAO {
    //CRUD
    void insert(ContattoUrgenze cu) throws SQLException;

    void update(ContattoUrgenze cu) throws SQLException;

    void delete(Integer idCu) throws SQLException;

    ContattoUrgenze findById(int id) throws SQLException;

    List<ContattoUrgenze> findByRegistratoId(int id) throws SQLException;
}
