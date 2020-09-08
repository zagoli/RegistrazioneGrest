package DAOManager;

import Domain.Terzamedia;
import java.sql.SQLException;
import java.util.List;

public interface TerzamediaDAO {
    //CRUD
    public void insert(Terzamedia r) throws SQLException;
    public void update(Terzamedia r) throws SQLException;
    public void updateSquadra(int id, String squadra) throws SQLException;
    public void updateLaboratorio(int id, int idLaboratorio) throws SQLException;
    public void delete(Integer idTerzamedia) throws SQLException;
    public Terzamedia findById(int id) throws SQLException;
    //SELECT
    public List<Terzamedia> findAll() throws SQLException;
    public int count() throws SQLException;
    public List<Integer[]> countSettimanale() throws SQLException;
    public List<Terzamedia> findByLabId(int id) throws SQLException;
    public List<Terzamedia> findByScuolaId(int id) throws SQLException;
    public List<Terzamedia> findByParrocchiaId(int id) throws SQLException;
    public List<Terzamedia> findByCircoloId(int id) throws SQLException;
    public List<Terzamedia> findByCalendarioId(int id) throws SQLException;
    public List<Terzamedia> findByRegistratoId(int id) throws SQLException;
}
