package Controller;

import DAOManager.DAOMan;
import Domain.Calendario;
import Domain.Iscritto;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

final class PresenzeSupport {
    private PresenzeSupport() {
    }

    @FunctionalInterface
    interface SqlConsumer<T> {
        void accept(T value) throws SQLException;
    }

    @FunctionalInterface
    interface SqlFunction<T, R> {
        R apply(T value) throws SQLException;
    }

    static <T> void insert(HttpServletRequest request, IntFunction<T> relation, SqlConsumer<T> insert) throws SQLException {
        for (String calId : request.getParameterValues("cal")) {
            insert.accept(relation.apply(Integer.parseInt(calId)));
        }
    }

    static <T> void replace(HttpServletRequest request, List<T> previous, SqlConsumer<T> delete,
                            IntFunction<T> relation, SqlConsumer<T> insert) throws SQLException {
        for (T value : previous) {
            delete.accept(value);
        }
        insert(request, relation, insert);
    }

    static <T> List<Calendario> calendars(List<T> relations, ToIntFunction<T> calendarId) throws SQLException {
        List<Calendario> calendars = new LinkedList<>();
        for (T relation : relations) {
            calendars.add(DAOMan.calendarioDAO.findById(calendarId.applyAsInt(relation)));
        }
        return calendars;
    }

    static <T> String period(List<T> relations, ToIntFunction<T> calendarId) {
        return relations.stream().map(relation -> calendarId.applyAsInt(relation) + " ").reduce("", String::concat);
    }

    static <T extends Iscritto, R> List<Object[]> printRows(List<T> iscritti,
            SqlFunction<Integer, List<R>> presenze, ToIntFunction<R> calendarId, boolean contacts) throws SQLException {
        List<Object[]> rows = new LinkedList<>();
        for (T iscritto : iscritti) {
            String period = period(presenze.apply(iscritto.getId()), calendarId);
            rows.add(contacts
                    ? new Object[]{iscritto, period, DAOMan.contattoUrgenzeDAO.findByRegistratoId(iscritto.getRegistrato().getId())}
                    : new Object[]{iscritto, period});
        }
        return rows;
    }
}
