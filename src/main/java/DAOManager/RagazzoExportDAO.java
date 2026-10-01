package DAOManager;

import Domain.RagazzoExport;

import java.sql.SQLException;
import java.util.List;

public interface RagazzoExportDAO {
    List<RagazzoExport> findAll() throws SQLException;
}
