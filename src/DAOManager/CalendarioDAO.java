package DAOManager;

import Domain.Calendario;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CalendarioDAO {
    //CRUD
    void insert(Calendario c) throws SQLException;
    void update(Calendario c) throws SQLException;
    void delete(Integer idCalendario) throws SQLException;
    Calendario findById(int id) throws SQLException;
    
    //SELECT
    List<Calendario> findAll() throws SQLException;
    Calendario findByDataInizio(Date data) throws SQLException;
    List<Calendario> findByRagazzoId(int id) throws SQLException;
    List<Calendario> findByTerzamediaId(int id) throws SQLException;
    List<Calendario> findByAnimatoreId(int id) throws SQLException;
    int count() throws SQLException;
}
