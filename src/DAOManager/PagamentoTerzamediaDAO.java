package DAOManager;

import Domain.PagamentoTerzamedia;
import java.sql.SQLException;
import java.util.List;

public interface PagamentoTerzamediaDAO {
    //CRUD
    void insert(PagamentoTerzamedia p) throws SQLException;
    void insert(int ordineArrivo, float quota, int terId, int regId) throws SQLException;
    void update(PagamentoTerzamedia p) throws SQLException;
    void delete(Integer idPagamentoTer) throws SQLException;
    void deleteFromTerzamediaId(Integer idTerzamedia) throws SQLException;
    PagamentoTerzamedia findById(int id) throws SQLException;
    //SELECT
    List<PagamentoTerzamedia> findAll() throws SQLException;
    List<PagamentoTerzamedia> findBySegretarioId(int id) throws SQLException;
    PagamentoTerzamedia findByTerzamediaId(int id) throws SQLException;
    int count() throws SQLException;
}
