package DAOManager;

import Domain.RelPresenzaAn;
import java.sql.SQLException;
import java.util.List;

public interface RelPresenzaAnDAO {
    //CRD
    public void insert (RelPresenzaAn rpa) throws SQLException;
    public void delete (RelPresenzaAn rpa) throws SQLException;
    public List<RelPresenzaAn> findByAnimatoreId (int id) throws SQLException;
    public List<RelPresenzaAn> findByCalendarioId (int id) throws SQLException;
}
