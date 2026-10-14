package DAOManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;

final class DAORows {
    private DAORows() {
    }

    @FunctionalInterface
    interface Mapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    static <T> List<T> readAll(ResultSet rs, Mapper<T> mapper) throws SQLException {
        List<T> rows = new LinkedList<>();
        while (rs.next()) {
            rows.add(mapper.map(rs));
        }
        return rows;
    }

    static List<Integer[]> weeklyCounts(ResultSet rs) throws SQLException {
        return readAll(rs, row -> new Integer[]{row.getInt(1), row.getInt(2)});
    }

    static void assignGeneratedId(PreparedStatement pst, IntConsumer setId) throws SQLException {
        try (ResultSet rs = pst.getGeneratedKeys()) {
            if (rs.next()) {
                setId.accept(rs.getInt(1));
            }
        }
    }
}
