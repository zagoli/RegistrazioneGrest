package DAOManager;

import Domain.AttivitaGen;
import java.sql.SQLException;
import java.util.List;

public interface AttivitaGenDAO {
    //CRUD
    void insert(AttivitaGen l) throws SQLException;
    void update(AttivitaGen l) throws SQLException;
    void delete(Integer idAttGen) throws SQLException;
    AttivitaGen findById(int id) throws SQLException;
    //SELECT
    List<AttivitaGen> findAll() throws SQLException;
    List<AttivitaGen> findByRegistratoId(int id) throws SQLException;
    int count() throws SQLException;
}
