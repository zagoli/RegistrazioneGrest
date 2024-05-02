package DAOManager;

import Domain.Calendario;

import java.sql.SQLException;
import java.util.List;

public interface CalendarioDAO {
    Calendario findById(int id) throws SQLException;

    //SELECT
    List<Calendario> findAll() throws SQLException;

    List<Calendario> findByRagazzoId(int id) throws SQLException;

    List<Calendario> findByTerzamediaId(int id) throws SQLException;

    List<Calendario> findByAnimatoreId(int id) throws SQLException;
}
