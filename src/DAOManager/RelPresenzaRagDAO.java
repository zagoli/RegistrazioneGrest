package DAOManager;

import Domain.RelPresenzaRag;
import java.sql.SQLException;
import java.util.List;

public interface RelPresenzaRagDAO {
    //CRD
    void insert(RelPresenzaRag rpr) throws SQLException;
    void delete(RelPresenzaRag rpr) throws SQLException;
    List<RelPresenzaRag> findByRagazzoId(int id) throws SQLException;
    List<RelPresenzaRag> findByCalendarioId(int id) throws SQLException;
}
