package DAOManager;

import Domain.RelCollabora;
import java.sql.SQLException;
import java.util.List;

public interface RelCollaboraDAO {
    //CRD
    public void insert (RelCollabora rc) throws SQLException;
    public void delete (RelCollabora rc) throws SQLException;
    public RelCollabora findById (int id) throws SQLException;
    public List<RelCollabora> findByRegistratoId (int id) throws SQLException;
    public List<RelCollabora> findByAttGenId (int id) throws SQLException;
    public List<RelCollabora> findAll () throws SQLException;
}
