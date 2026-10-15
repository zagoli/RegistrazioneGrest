package DAOManager.Mappers;

import Domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PagamentoDAOMapper {

    private PagamentoDAOMapper() {
    }

    public static PagamentoRiepilogo riepilogo(ResultSet rs) throws SQLException {
        if (rs.getObject("pid") == null) {
            return null;
        }
        return new PagamentoRiepilogo(
                rs.getInt("pid"), rs.getInt("pordineArrivo"), rs.getDate("pdata"), rs.getFloat("pquota"),
                rs.getString("prenome"), rs.getString("precognome"));
    }

    public static void bindInsert(PreparedStatement pst, int ordineArrivo, float quota,
                           int iscrittoId, int registratoId) throws SQLException {
        pst.setFloat(1, quota);
        pst.setInt(2, iscrittoId);
        pst.setInt(3, registratoId);
        pst.setInt(4, ordineArrivo);
    }

    public static Pagamento mapRowToPagamento(ResultSet rs) throws SQLException {
        return mapRowToPagamento(rs, "re");
    }

    public static Pagamento mapRowToPagamento(ResultSet rs, String registratoPrefix) throws SQLException {
        return new Pagamento(
                rs.getInt("pid"),
                rs.getDate("pdata"),
                rs.getFloat("pquota"),
                rs.getInt("pragid"),
                RegistratoDAOMapper.registrato(rs, registratoPrefix),
                rs.getInt("pordineArrivo")
        );
    }

    public static PagamentoTerzamedia mapRowToPagamentoTerzamedia(ResultSet rs) throws SQLException {
        return mapRowToPagamentoTerzamedia(rs, "re");
    }

    public static PagamentoTerzamedia mapRowToPagamentoTerzamedia(ResultSet rs, String registratoPrefix) throws SQLException {
        return new PagamentoTerzamedia(
                rs.getInt("pid"),
                rs.getDate("pdata"),
                rs.getFloat("pquota"),
                rs.getInt("pterid"),
                RegistratoDAOMapper.registrato(rs, registratoPrefix),
                rs.getInt("pordineArrivo")
        );
    }
}
