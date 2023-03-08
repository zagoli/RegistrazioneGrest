package DAOManager;

import Domain.PagamentoTerzamedia;
import java.sql.SQLException;
import java.util.List;

public interface PagamentoTerzamediaDAO {
    void insert(int ordineArrivo, float quota, int terId, int regId) throws SQLException;

    void delete(Integer idPagamentoTer) throws SQLException;

    //SELECT
    List<PagamentoTerzamedia> findAll() throws SQLException;

    PagamentoTerzamedia findByTerzamediaId(int id) throws SQLException;
}
