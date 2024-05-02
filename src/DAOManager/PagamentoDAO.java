package DAOManager;

import Domain.Pagamento;

import java.sql.SQLException;
import java.util.List;

public interface PagamentoDAO {
    void insert(int ordineArrivo, float quota, int ragId, int regId) throws SQLException;

    void delete(Integer idPagamento) throws SQLException;

    //SELECT
    List<Pagamento> findAll() throws SQLException;

    Pagamento findByRagazzoId(int id) throws SQLException;

    int count() throws SQLException;
}
