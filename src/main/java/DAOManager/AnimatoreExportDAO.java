package DAOManager;

import Domain.AnimatoreExport;

import java.sql.SQLException;
import java.util.List;

public interface AnimatoreExportDAO {
    List<AnimatoreExport> findAll() throws SQLException;
}
