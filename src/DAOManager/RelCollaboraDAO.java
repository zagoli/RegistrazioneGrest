package DAOManager;

import Domain.RelCollabora;
import java.sql.SQLException;
import java.util.List;

public interface RelCollaboraDAO {
    //CRD
    void insert(RelCollabora rc) throws SQLException;
    void delete(RelCollabora rc) throws SQLException;
    RelCollabora findById(int id) throws SQLException;
    List<RelCollabora> findByRegistratoId(int id) throws SQLException;

    List<RelCollabora> findAll() throws SQLException;
}
