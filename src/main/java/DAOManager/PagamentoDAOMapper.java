package DAOManager;

import Domain.PagamentoRiepilogo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

final class PagamentoDAOMapper {
    private PagamentoDAOMapper() {
    }

    static PagamentoRiepilogo riepilogo(ResultSet rs) throws SQLException {
        if (rs.getObject("pid") == null) {
            return null;
        }
        return new PagamentoRiepilogo(
                rs.getInt("pid"), rs.getInt("pordineArrivo"), rs.getDate("pdata"), rs.getFloat("pquota"),
                rs.getString("prenome"), rs.getString("precognome"));
    }

    static void bindInsert(PreparedStatement pst, int ordineArrivo, float quota,
                           int iscrittoId, int registratoId) throws SQLException {
        pst.setFloat(1, quota);
        pst.setInt(2, iscrittoId);
        pst.setInt(3, registratoId);
        pst.setInt(4, ordineArrivo);
    }
}
