package DAOManager;

import Domain.Accompagnatore;

import java.sql.SQLException;
import java.util.List;

public interface AccompagnatoreDAO {
    //CRUD
    void insert(Accompagnatore a) throws SQLException;

    void update(Accompagnatore a) throws SQLException;

    void delete(Integer idAccompagnatore) throws SQLException;

    Accompagnatore findById(int id) throws SQLException;

    List<Accompagnatore> findByRegistratoId(int id) throws SQLException;
}
