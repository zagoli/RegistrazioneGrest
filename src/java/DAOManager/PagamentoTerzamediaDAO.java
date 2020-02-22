package DAOManager;

import Domain.PagamentoTerzamedia;
import java.sql.SQLException;
import java.util.List;

public interface PagamentoTerzamediaDAO {
    //CRUD
    public void insert(PagamentoTerzamedia p) throws SQLException;
    public void update(PagamentoTerzamedia p) throws SQLException;
    public void delete(Integer idPagamentoTer) throws SQLException;
    public void deleteFromTerzamediaId(Integer idTerzamedia) throws SQLException;
    public PagamentoTerzamedia findById(int id) throws SQLException;
    //SELECT
    public List<PagamentoTerzamedia> findAll() throws SQLException;
    public List<PagamentoTerzamedia> findBySegretarioId(int id) throws SQLException;
    public PagamentoTerzamedia findByTerzamediaId(int id) throws SQLException;
    public int count() throws SQLException;
}
