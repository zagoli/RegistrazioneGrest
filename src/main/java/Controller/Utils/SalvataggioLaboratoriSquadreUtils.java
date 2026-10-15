package Controller.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Map;

public final class SalvataggioLaboratoriSquadreUtils {

    private SalvataggioLaboratoriSquadreUtils() {
    }

    @FunctionalInterface
    public interface FunzioneSalvataggio {
        void salva(int id, Integer assignment) throws SQLException;
    }

    @FunctionalInterface
    public interface FunzioneSalvataggioAnimatore {
        void salva(int id, Integer assignment, boolean responsible) throws SQLException;
    }

    public static void salvataggio(String[] values, boolean optional, FunzioneSalvataggio funzioneSalvataggio) throws SQLException {
        if (values != null) {
            for (String value : values) {
                String[] pair = value.split(",");
                int id = Integer.parseInt(pair[0]);
                Integer assignment = optional && pair.length != 2 ? null : Integer.valueOf(pair[1]);
                funzioneSalvataggio.salva(id, assignment);
            }
        }
    }

    public static void salvataggioAnimatori(HttpServletRequest request, boolean optionalTeam, FunzioneSalvataggioAnimatore update) throws SQLException {
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
                update.salva(Integer.parseInt(entry.getKey()), assignment, value.length == 2);
            }
        }
    }
}
