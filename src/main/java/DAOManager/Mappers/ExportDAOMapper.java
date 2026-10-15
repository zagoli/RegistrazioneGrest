package DAOManager.Mappers;

import Domain.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ExportDAOMapper {
    private ExportDAOMapper() {
    }

    public static String text(ResultSet rs, String column) throws SQLException {
        String value = rs.getString(column);
        return value == null ? "" : value;
    }

    public static String trimmedText(ResultSet rs, String column) throws SQLException {
        return text(rs, column).trim();
    }

    public static String presenza(ResultSet rs) throws SQLException {
        String value = trimmedText(rs, "presenza");
        return switch (value) {
            case "M" -> "mattina";
            case "P" -> "pomeriggio";
            case "C" -> "completo";
            default -> throw new SQLException("Valore presenza non riconosciuto: " + value);
        };
    }

    public static String fasciaEtaRagazzi(ResultSet rs) throws SQLException {
        String value = trimmedText(rs, "fasciaEtaRagazzi");
        return switch (value) {
            case "PIC" -> "piccoli";
            case "GRA" -> "grandi";
            case "TER" -> "terza media";
            default -> throw new SQLException("Valore fasciaEtaRagazzi non riconosciuto: " + value);
        };
    }

    public static String siNo(boolean value) {
        return value ? "si" : "no";
    }

    public static List<Integer> settimane(ResultSet rs) throws SQLException {
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

    public static BigDecimal importoPagamento(ResultSet rs)
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

    public static Laboratorio laboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(
                rs.getInt("la_id"),
                text(rs, "la_descrizione"),
                rs.getBoolean("la_riservato")
        );
    }

    public static Parrocchia parrocchia(ResultSet rs) throws SQLException {
        return new Parrocchia(
                rs.getInt("pa_id"),
                text(rs, "pa_nome"),
                text(rs, "pa_luogo")
        );
    }

    public static Registrato registrato(ResultSet rs) throws SQLException {
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

    public static Circolo circolo(ResultSet rs) throws SQLException {
        return new Circolo(
                rs.getInt("ci_id"),
                text(rs, "ci_nome"),
                text(rs, "ci_luogo")
        );
    }

    public static Scuola scuola(ResultSet rs) throws SQLException {
        return new Scuola(
                rs.getInt("sc_id"),
                text(rs, "sc_grado"),
                text(rs, "sc_descrizione")
        );
    }

    public static Squadra squadra(ResultSet rs) throws SQLException {
        return new Squadra(
                rs.getInt("sq_id"),
                text(rs, "sq_nome"),
                text(rs, "sq_colore")
        );
    }

    public static RagazzoExport mapRowToRagazzoExport(ResultSet rs) throws SQLException {
        boolean pagato = rs.getObject("pagamento_id") != null;
        return new RagazzoExport(
                ExportDAOMapper.text(rs, "nome"),
                ExportDAOMapper.text(rs, "cognome"),
                rs.getDate("dataNascita"),
                ExportDAOMapper.presenza(rs),
                ExportDAOMapper.laboratorio(rs),
                ExportDAOMapper.parrocchia(rs),
                ExportDAOMapper.registrato(rs),
                ExportDAOMapper.circolo(rs),
                ExportDAOMapper.siNo(rs.getBoolean("entrataAnticipata")),
                ExportDAOMapper.text(rs, "richieste"),
                ExportDAOMapper.text(rs, "noteAlimentari"),
                ExportDAOMapper.siNo(rs.getBoolean("mensa")),
                ExportDAOMapper.siNo(rs.getBoolean("saNuotare")),
                ExportDAOMapper.siNo(rs.getBoolean("fratelloIscritto")),
                ExportDAOMapper.scuola(rs),
                ExportDAOMapper.trimmedText(rs, "nTessera"),
                ExportDAOMapper.trimmedText(rs, "sezione"),
                ExportDAOMapper.trimmedText(rs, "classe"),
                ExportDAOMapper.squadra(rs),
                ExportDAOMapper.settimane(rs),
                ExportDAOMapper.siNo(pagato),
                ExportDAOMapper.importoPagamento(rs)
        );
    }

    public static TerzaMediaExport mapRowToTerzamediaExport(ResultSet rs) throws SQLException {
        boolean pagato = rs.getObject("pagamento_id") != null;
        return new TerzaMediaExport(
                ExportDAOMapper.text(rs, "nome"),
                ExportDAOMapper.text(rs, "cognome"),
                rs.getDate("dataNascita"),
                ExportDAOMapper.presenza(rs),
                ExportDAOMapper.laboratorio(rs),
                ExportDAOMapper.scuola(rs),
                ExportDAOMapper.parrocchia(rs),
                ExportDAOMapper.circolo(rs),
                ExportDAOMapper.trimmedText(rs, "cellulare"),
                ExportDAOMapper.text(rs, "noteAlimentari"),
                ExportDAOMapper.text(rs, "richieste"),
                ExportDAOMapper.siNo(rs.getBoolean("saNuotare")),
                ExportDAOMapper.siNo(rs.getBoolean("festaPassaggio")),
                ExportDAOMapper.trimmedText(rs, "sezione"),
                ExportDAOMapper.trimmedText(rs, "nTessera"),
                ExportDAOMapper.text(rs, "mail"),
                ExportDAOMapper.squadra(rs),
                ExportDAOMapper.settimane(rs),
                ExportDAOMapper.registrato(rs),
                ExportDAOMapper.siNo(pagato),
                ExportDAOMapper.importoPagamento(rs)
        );
    }

    public static AnimatoreExport mapRowToAnimatoreExport(ResultSet rs) throws SQLException {
        return new AnimatoreExport(
                ExportDAOMapper.text(rs, "nome"),
                ExportDAOMapper.text(rs, "cognome"),
                rs.getDate("dataNascita"),
                ExportDAOMapper.text(rs, "codiceFiscale"),
                ExportDAOMapper.trimmedText(rs, "cellularePersonale"),
                ExportDAOMapper.text(rs, "mail"),
                ExportDAOMapper.parrocchia(rs),
                ExportDAOMapper.circolo(rs),
                ExportDAOMapper.trimmedText(rs, "nTessera"),
                ExportDAOMapper.settimane(rs),
                ExportDAOMapper.presenza(rs),
                ExportDAOMapper.fasciaEtaRagazzi(rs),
                ExportDAOMapper.laboratorio(rs),
                ExportDAOMapper.siNo(rs.getBoolean("isResponsabileLaboratorio")),
                ExportDAOMapper.squadra(rs),
                ExportDAOMapper.siNo(rs.getBoolean("isResponsabileSquadra")),
                ExportDAOMapper.registrato(rs)
        );
    }
}
