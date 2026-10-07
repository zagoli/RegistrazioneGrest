package DAOManager;

import Domain.TerzaMediaExport;

import java.sql.SQLException;
import java.util.List;

public interface TerzamediaExportDAO {
    List<TerzaMediaExport> findAll() throws SQLException;
}
