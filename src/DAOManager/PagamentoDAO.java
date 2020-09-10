package DAOManager;

import Domain.Pagamento;
import java.sql.SQLException;
import java.util.List;

public interface PagamentoDAO {
    //CRUD
    void insert(Pagamento p) throws SQLException;
    void insert(int ordineArrivo, float quota, int ragId, int regId) throws SQLException;
    void update(Pagamento p) throws SQLException;
    void delete(Integer idPagamento) throws SQLException;
    void deleteFromRagazzoId(Integer idRagazzo) throws SQLException;
    Pagamento findById(int id) throws SQLException;
    //SELECT
    List<Pagamento> findAll() throws SQLException;
    List<Pagamento> findBySegretarioId(int id) throws SQLException;
    Pagamento findByRagazzoId(int id) throws SQLException;
    int count() throws SQLException;
}
