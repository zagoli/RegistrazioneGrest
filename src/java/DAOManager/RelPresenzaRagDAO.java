package DAOManager;

import Domain.RelPresenzaRag;
import java.sql.SQLException;
import java.util.List;

public interface RelPresenzaRagDAO {
    //CRD
    public void insert (RelPresenzaRag rpr) throws SQLException;
    public void delete (RelPresenzaRag rpr) throws SQLException;
    public List<RelPresenzaRag> findByRagazzoId (int id) throws SQLException;
    public List<RelPresenzaRag> findByCalendarioId (int id) throws SQLException;
}
