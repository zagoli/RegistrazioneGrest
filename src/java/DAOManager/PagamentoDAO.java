package DAOManager;

import Domain.Pagamento;
import java.sql.SQLException;
import java.util.List;

public interface PagamentoDAO {
    //CRUD
    public void insert(Pagamento p) throws SQLException;
    public void insert(int ordineArrivo, float quota, int ragId, int regId) throws SQLException;
    public void update(Pagamento p) throws SQLException;
    public void delete(Integer idPagamento) throws SQLException;
    public void deleteFromRagazzoId(Integer idRagazzo) throws SQLException;
    public Pagamento findById(int id) throws SQLException;
    //SELECT
    public List<Pagamento> findAll() throws SQLException;
    public List<Pagamento> findBySegretarioId(int id) throws SQLException;
    public Pagamento findByRagazzoId(int id) throws SQLException;
    public int count() throws SQLException;
}
