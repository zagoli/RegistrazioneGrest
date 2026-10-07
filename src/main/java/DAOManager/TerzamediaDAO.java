package DAOManager;

import Domain.DatiPagamento;
import Domain.IscrittoPagamento;
import Domain.PagamentoRiepilogo;
import Domain.TerzaMedia;

import java.sql.SQLException;
import java.util.List;

public interface TerzamediaDAO {
    //CRUD
    void insert(TerzaMedia r) throws SQLException;

    void update(TerzaMedia r) throws SQLException;

    void updateSquadra(int id, Integer idSquadra) throws SQLException;

    void updateLaboratorio(int id, int idLaboratorio) throws SQLException;

    void delete(Integer idTerzamedia) throws SQLException;

    TerzaMedia findById(int id) throws SQLException;

    //SELECT
    List<TerzaMedia> findAll() throws SQLException;

    List<DatiPagamento<IscrittoPagamento, PagamentoRiepilogo>> findAllConPagamenti() throws SQLException;

    List<DatiPagamento<TerzaMedia, PagamentoRiepilogo>> findAllConPagamento() throws SQLException;

    int count() throws SQLException;

    List<TerzaMedia> findByRegistratoId(int id) throws SQLException;
}
