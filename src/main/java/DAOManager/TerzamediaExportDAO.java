package DAOManager;

import Domain.TerzamediaExport;

import java.sql.SQLException;
import java.util.List;

public interface TerzamediaExportDAO {
    List<TerzamediaExport> findAll() throws SQLException;
}
