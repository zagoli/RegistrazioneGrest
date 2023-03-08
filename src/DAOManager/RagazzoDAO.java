package DAOManager;

import Domain.Ragazzo;

import java.sql.SQLException;
import java.util.List;

public interface RagazzoDAO {
    //CRUD
    void insert(Ragazzo r) throws SQLException;

    void update(Ragazzo r) throws SQLException;

    void updateSquadra(int id, Integer idSquadra) throws SQLException;

    void updateLaboratorio(int id, int idLaboratorio) throws SQLException;
    void delete(Integer idRagazzo) throws SQLException;
    Ragazzo findById(int id) throws SQLException;
    //SELECT
    List<Ragazzo> findAll() throws SQLException;
    int count() throws SQLException;
    int countMensaTotale() throws SQLException;
    int countAnticipatoTotale() throws SQLException;
    List<Integer[]> countMensaSettimanale() throws SQLException;
    List<Integer[]> countAnticipatoSettimanale() throws SQLException;
    List<Integer[]> countSettimanale() throws SQLException;

    List<Ragazzo> findByCalendarioId(int id) throws SQLException;
    List<Ragazzo> findByRegistratoId(int id) throws SQLException;
}
