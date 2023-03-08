package DAOManager;

import Domain.AttivitaGen;
import java.sql.SQLException;
import java.util.List;

public interface AttivitaGenDAO {
    AttivitaGen findById(int id) throws SQLException;
    //SELECT
    List<AttivitaGen> findAll() throws SQLException;
    List<AttivitaGen> findByRegistratoId(int id) throws SQLException;
}
