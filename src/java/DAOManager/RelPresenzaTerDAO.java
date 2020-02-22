package DAOManager;

import Domain.RelPresenzaTer;
import java.sql.SQLException;
import java.util.List;

public interface RelPresenzaTerDAO {
    //CRD
    public void insert (RelPresenzaTer rpr) throws SQLException;
    public void delete (RelPresenzaTer rpr) throws SQLException;
    public List<RelPresenzaTer> findByTerzamediaId (int id) throws SQLException;
    public List<RelPresenzaTer> findByCalendarioId (int id) throws SQLException;
}
