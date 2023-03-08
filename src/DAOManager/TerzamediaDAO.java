package DAOManager;

import Domain.Terzamedia;

import java.sql.SQLException;
import java.util.List;

public interface TerzamediaDAO {
    //CRUD
    void insert(Terzamedia r) throws SQLException;

    void update(Terzamedia r) throws SQLException;

    void updateSquadra(int id, Integer idSquadra) throws SQLException;

    void updateLaboratorio(int id, int idLaboratorio) throws SQLException;
    void delete(Integer idTerzamedia) throws SQLException;
    Terzamedia findById(int id) throws SQLException;
    //SELECT
    List<Terzamedia> findAll() throws SQLException;
    int count() throws SQLException;

    List<Terzamedia> findByRegistratoId(int id) throws SQLException;
}
