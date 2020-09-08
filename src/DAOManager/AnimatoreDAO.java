package DAOManager;

import Domain.Animatore;
import java.sql.SQLException;
import java.util.List;

public interface AnimatoreDAO {
    //CRUD
    public void insert(Animatore a) throws SQLException;
    public void update(Animatore a) throws SQLException;
    public void updateSquadra(int id, String squadra, boolean responsabile) throws SQLException;
    public void updateLaboratorio(int id, int idLaboratorio, boolean responsabile) throws SQLException;
    public void delete(Integer idAnimatore) throws SQLException;
    public Animatore findById(int id) throws SQLException;
    //SELECT
    public List<Animatore> findAll() throws SQLException;
    public int count() throws SQLException;
    public Animatore findByNominativo(String nome,String cognome) throws SQLException;
    public List<Animatore> findByLab(int id) throws SQLException;
    public List<Animatore> findByParrocchiaId(int id) throws SQLException;
    public List<Animatore> findByCircoloId(int id) throws SQLException;
    public List<Animatore> findByCalendarioId(int id) throws SQLException;
    public List<Animatore> findByRegistratoId(int id) throws SQLException;
}
