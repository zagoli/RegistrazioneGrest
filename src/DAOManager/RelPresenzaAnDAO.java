package DAOManager;

import Domain.RelPresenzaAn;
import java.sql.SQLException;
import java.util.List;

public interface RelPresenzaAnDAO {
    //CRD
    void insert(RelPresenzaAn rpa) throws SQLException;
    void delete(RelPresenzaAn rpa) throws SQLException;
    List<RelPresenzaAn> findByAnimatoreId(int id) throws SQLException;
}
