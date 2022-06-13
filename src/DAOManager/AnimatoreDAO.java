package DAOManager;

import Domain.Animatore;

import java.sql.SQLException;
import java.util.List;

public interface AnimatoreDAO {
    //CRUD
    void insert(Animatore a) throws SQLException;

    void update(Animatore a) throws SQLException;

    void updateSquadra(int id, Integer idSquadra, boolean responsabile) throws SQLException;

    void updateLaboratorio(int id, int idLaboratorio, boolean responsabile) throws SQLException;
    void delete(Integer idAnimatore) throws SQLException;
    Animatore findById(int id) throws SQLException;
    //SELECT
    List<Animatore> findAll() throws SQLException;
    int count() throws SQLException;
    Animatore findByNominativo(String nome, String cognome) throws SQLException;
    List<Animatore> findByLab(int id) throws SQLException;
    List<Animatore> findByParrocchiaId(int id) throws SQLException;
    List<Animatore> findByCircoloId(int id) throws SQLException;
    List<Animatore> findByCalendarioId(int id) throws SQLException;
    List<Animatore> findByRegistratoId(int id) throws SQLException;

    List<Integer[]> countSettimanale() throws SQLException;
}
