package DAOManager;

import Domain.Calendario;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CalendarioDAO {
    //CRUD
    public void insert (Calendario c) throws SQLException;
    public void update (Calendario c) throws SQLException;
    public void delete (Integer idCalendario) throws SQLException;
    public Calendario findById(int id) throws SQLException;
    
    //SELECT
    public List<Calendario> findAll() throws SQLException;
    public Calendario findByDataInizio(Date data) throws SQLException;
    public List<Calendario> findByRagazzoId(int id) throws SQLException;
    public List<Calendario> findByTerzamediaId(int id) throws SQLException;
    public List<Calendario> findByAnimatoreId(int id) throws SQLException;
    public int count() throws SQLException;
}
