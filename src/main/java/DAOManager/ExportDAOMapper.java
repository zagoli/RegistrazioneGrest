package DAOManager;

import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Parrocchia;
import Domain.Registrato;
import Domain.Scuola;
import Domain.Squadra;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class ExportDAOMapper {
    private ExportDAOMapper() {
    }

    static String text(ResultSet rs, String column) throws SQLException {
        String value = rs.getString(column);
        return value == null ? "" : value;
    }

    static String trimmedText(ResultSet rs, String column) throws SQLException {
        return text(rs, column).trim();
    }

    static String presenza(ResultSet rs) throws SQLException {
        String value = trimmedText(rs, "presenza");
        return switch (value) {
            case "M" -> "mattina";
            case "P" -> "pomeriggio";
            case "C" -> "completo";
            default -> throw new SQLException("Valore presenza non riconosciuto: " + value);
        };
    }

    static String fasciaEtaRagazzi(ResultSet rs) throws SQLException {
        String value = trimmedText(rs, "fasciaEtaRagazzi");
        return switch (value) {
            case "PIC" -> "piccoli";
            case "GRA" -> "grandi";
            case "TER" -> "terza media";
            default -> throw new SQLException("Valore fasciaEtaRagazzi non riconosciuto: " + value);
        };
    }

    static String siNo(boolean value) {
        return value ? "si" : "no";
    }

    static List<Integer> settimane(ResultSet rs) throws SQLException {
        String value = trimmedText(rs, "settimane");
        if (value.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> settimane = new ArrayList<>();
        for (String id : value.split(",")) {
            try {
                settimane.add(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                throw new SQLException("ID settimana non valido: " + id, e);
            }
        }
        return settimane;
    }

    static BigDecimal importoPagamento(ResultSet rs)
            throws SQLException {
        if (rs.getObject("pagamento_id") == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal quota = rs.getBigDecimal("pagamento_quota");
        if (quota == null) {
            throw new SQLException("Pagamento presente senza quota");
        }
        return quota;
    }

    static Laboratorio laboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(
                rs.getInt("la_id"),
                text(rs, "la_descrizione"),
                rs.getBoolean("la_riservato")
        );
    }

    static Parrocchia parrocchia(ResultSet rs) throws SQLException {
        return new Parrocchia(
                rs.getInt("pa_id"),
                text(rs, "pa_nome"),
                text(rs, "pa_luogo")
        );
    }

    static Registrato registrato(ResultSet rs) throws SQLException {
        return new Registrato(
                0,
                text(rs, "re_mail"),
                null,
                text(rs, "re_nome"),
                text(rs, "re_cognome"),
                trimmedText(rs, "re_telefono"),
                text(rs, "re_localita"),
                text(rs, "re_via"),
                text(rs, "re_civico"),
                null
        );
    }

    static Circolo circolo(ResultSet rs) throws SQLException {
        return new Circolo(
                rs.getInt("ci_id"),
                text(rs, "ci_nome"),
                text(rs, "ci_luogo")
        );
    }

    static Scuola scuola(ResultSet rs) throws SQLException {
        return new Scuola(
                rs.getInt("sc_id"),
                text(rs, "sc_grado"),
                text(rs, "sc_descrizione")
        );
    }

    static Squadra squadra(ResultSet rs) throws SQLException {
        return new Squadra(
                rs.getInt("sq_id"),
                text(rs, "sq_nome"),
                text(rs, "sq_colore")
        );
    }
}
