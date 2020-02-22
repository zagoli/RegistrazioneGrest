package DAOManager;

import Domain.Ragazzo;
import java.sql.SQLException;
import java.util.List;

public interface RagazzoDAO {
    //CRUD
    public void insert(Ragazzo r) throws SQLException;
    public void update(Ragazzo r) throws SQLException;
    public void updateSquadra(int id, String squadra) throws SQLException;
    public void updateLaboratorio(int id, int idLaboratorio) throws SQLException;
    public void delete(Integer idRagazzo) throws SQLException;
    public Ragazzo findById(int id) throws SQLException;
    //SELECT
    public List<Ragazzo> findAll() throws SQLException;
    public int count() throws SQLException;
    public int countMensaTotale() throws SQLException;
    public int countAnticipatoTotale() throws SQLException;
    public List<Integer[]> countMensaSettimanale() throws SQLException;
    public List<Integer[]> countAnticipatoSettimanale() throws SQLException;
    public List<Integer[]> countSettimanale() throws SQLException;
    public List<Object[]> countLaboratorioClasse() throws SQLException;
    public List<Ragazzo> findByNominativo(String nome,String cognome) throws SQLException;
    public List<Ragazzo> findByLabId(int id) throws SQLException;
    public List<Ragazzo> findByScuolaId(int id) throws SQLException;
    public List<Ragazzo> findByParrocchiaId(int id) throws SQLException;
    public List<Ragazzo> findByCircoloId(int id) throws SQLException;
    public List<Ragazzo> findByCalendarioId(int id) throws SQLException;
    public List<Ragazzo> findByRegistratoId(int id) throws SQLException;
}
