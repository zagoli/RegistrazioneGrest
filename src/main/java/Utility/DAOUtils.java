package Utility;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;

public final class DAOUtils {

    private DAOUtils() {
    }

    public static void setNormalizedString(PreparedStatement pst, int index, String value) throws SQLException {
        pst.setString(index, StringUtils.trimToNull(value));
    }

    public static <T> List<T> resultSetToList(ResultSet rs, Mapper<T> mapper) throws SQLException {
        List<T> rows = new LinkedList<>();
        while (rs.next()) {
            rows.add(mapper.map(rs));
        }
        return rows;
    }

    public static List<Integer[]> contaSettimanaleFromResultSet(ResultSet rs) throws SQLException {
        return resultSetToList(rs, row -> new Integer[]{row.getInt(1), row.getInt(2)});
    }

    public static void idGeneratoFromResultSet(PreparedStatement pst, IntConsumer setId) throws SQLException {
        try (ResultSet rs = pst.getGeneratedKeys()) {
            if (rs.next()) {
                setId.accept(rs.getInt(1));
            }
        }
    }

    @FunctionalInterface
    public interface Mapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}
