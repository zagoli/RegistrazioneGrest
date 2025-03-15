package DAOManager;

import Domain.RelPresenzaTer;

import java.sql.SQLException;
import java.util.List;

public interface RelPresenzaTerDAO {
    //CRD
    void insert(RelPresenzaTer rpr) throws SQLException;

    void delete(RelPresenzaTer rpr) throws SQLException;

    List<RelPresenzaTer> findByTerzamediaId(int id) throws SQLException;
}
