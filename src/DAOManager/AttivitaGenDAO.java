package DAOManager;

import Domain.AttivitaGen;
import java.sql.SQLException;
import java.util.List;

public interface AttivitaGenDAO {
    //CRUD
    public void insert (AttivitaGen l) throws SQLException;
    public void update (AttivitaGen l) throws SQLException;
    public void delete (Integer idAttGen) throws SQLException;
    public AttivitaGen findById(int id) throws SQLException;
    //SELECT
    public List<AttivitaGen> findAll() throws SQLException;
    public List<AttivitaGen> findByRegistratoId(int id) throws SQLException;
    public int count() throws SQLException;
}
