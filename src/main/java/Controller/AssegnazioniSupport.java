package Controller;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Map;

final class AssegnazioniSupport {
    private AssegnazioniSupport() {
    }

    @FunctionalInterface
    interface Update {
        void apply(int id, Integer assignment) throws SQLException;
    }

    @FunctionalInterface
    interface AnimatorUpdate {
        void apply(int id, Integer assignment, boolean responsible) throws SQLException;
    }

    static void pairs(String[] values, boolean optional, Update update) throws SQLException {
        if (values != null) {
            for (String value : values) {
                String[] pair = value.split(",");
                int id = Integer.parseInt(pair[0]);
                Integer assignment = optional && pair.length != 2 ? null : Integer.valueOf(pair[1]);
                update.apply(id, assignment);
            }
        }
    }

    static void animators(HttpServletRequest request, boolean optionalTeam, AnimatorUpdate update) throws SQLException {
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getKey().matches("\\d+")) {
                String[] value = entry.getValue();
                Integer assignment;
                if (optionalTeam && value[0].isEmpty()) {
                    if (value.length == 2) {
                        throw new IllegalArgumentException("Impossibile impostare come responsabile un animatore che non ha nessuna squadra!");
                    }
                    assignment = null;
                } else {
                    assignment = Integer.parseInt(value[0]);
                }
                update.apply(Integer.parseInt(entry.getKey()), assignment, value.length == 2);
            }
        }
    }
}
