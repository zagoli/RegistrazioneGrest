package Controller;

import Domain.DatiPagamento;
import Domain.Iscritto;
import Domain.PagamentoRiepilogo;
import Response.FreemarkerTemplate;
import Utility.ConfigPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

final class PagamentiSupport {
    private PagamentiSupport() {
    }

    @FunctionalInterface
    interface Quota<T> {
        float calculate(T iscritto, int settimane) throws ConfigPropertyException, IOException;
    }

    @FunctionalInterface
    interface Render {
        void apply(FreemarkerTemplate template) throws SQLException, ConfigPropertyException, IOException;
    }

    @FunctionalInterface
    interface Insert {
        void apply() throws SQLException;
    }

    record PaymentRequest(float quota, int id, int userId, int ordineArrivo) {
        static PaymentRequest read(HttpServletRequest request) {
            return new PaymentRequest(Float.parseFloat(request.getParameter("quota").replace(',', '.')),
                    Integer.parseInt(request.getParameter("addPagamento")),
                    (int) request.getSession().getAttribute("idUtente"),
                    Integer.parseInt(request.getParameter("ordineArrivo")));
        }
    }

    static <T> void render(FreemarkerTemplate template, List<DatiPagamento<T, PagamentoRiepilogo>> iscritti,
                          String key, String view, Quota<T> quota) throws ConfigPropertyException, IOException {
        List<Object[]> dati = new ArrayList<>(iscritti.size());
        if (!iscritti.isEmpty()) {
            for (DatiPagamento<T, PagamentoRiepilogo> dato : iscritti) {
                PagamentoRiepilogo pagamento = dato.getPagamento();
                dati.add(new Object[]{dato.getIscritto(), pagamento != null,
                        pagamento != null ? pagamento : quota.calculate(dato.getIscritto(), dato.getSettimane())});
            }
            template.addObject(key, dati);
        }
        template.setView(view);
    }

    static <T> void addPaymentRows(FreemarkerTemplate template, String key,
                                   List<DatiPagamento<T, PagamentoRiepilogo>> iscritti) {
        List<Object[]> rows = new ArrayList<>();
        for (DatiPagamento<T, PagamentoRiepilogo> dato : iscritti) {
            rows.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
        }
        template.addObject(key, rows);
    }

    static <T extends Iscritto> void statuses(FreemarkerTemplate template, String key, List<T> iscritti,
                                             PresenzeSupport.SqlFunction<Integer, ?> payment) throws SQLException {
        if (!iscritti.isEmpty()) {
            Map<T, Boolean> statuses = new HashMap<>();
            for (T iscritto : iscritti) {
                statuses.put(iscritto, payment.apply(iscritto.getId()) != null);
            }
            template.addObject(key, statuses);
        }
    }

    static boolean insert(FreemarkerTemplate template, Insert insert, String duplicateMessage, Logger logger,
                          Render render) throws SQLException, ConfigPropertyException, IOException {
        try {
            insert.apply();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                logger.warn(duplicateMessage);
                template.addObject("errorePagamento", duplicateMessage);
                render.apply(template);
                return false;
            }
            throw e;
        }
    }
}
