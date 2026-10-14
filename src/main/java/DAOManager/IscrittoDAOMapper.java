package DAOManager;

import Domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

final class IscrittoDAOMapper {
    private IscrittoDAOMapper() {
    }

    static <T extends Iscritto> T commonFields(ResultSet rs, T iscritto, String prefix,
                                               String tesseraColumn) throws SQLException {
        iscritto.setId(rs.getInt(prefix + "id"));
        iscritto.setNome(rs.getString(prefix + "nome"));
        iscritto.setCognome(rs.getString(prefix + "cognome"));
        iscritto.setDataNascita(rs.getDate(prefix + "dataNascita"));
        iscritto.setPresenza(rs.getString(prefix + "presenza"));
        iscritto.setLaboratorio(laboratorio(rs));
        iscritto.setParrocchia(parrocchia(rs));
        iscritto.setRegistrato(registrato(rs, "re"));
        iscritto.setCircolo(circolo(rs));
        iscritto.setNTessera(rs.getString(tesseraColumn));
        iscritto.setSquadra(squadra(rs));
        return iscritto;
    }

    static Laboratorio laboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(rs.getInt("laid"), rs.getString("ladescrizione"), rs.getBoolean("lariservato"));
    }

    static Parrocchia parrocchia(ResultSet rs) throws SQLException {
        return new Parrocchia(rs.getInt("paid"), rs.getString("panome"), rs.getString("paluogo"));
    }

    static Registrato registrato(ResultSet rs, String prefix) throws SQLException {
        return new Registrato(
                rs.getInt(prefix + "id"),
                rs.getString(prefix + "mail"),
                rs.getString(prefix + "password"),
                rs.getString(prefix + "nome"),
                rs.getString(prefix + "cognome"),
                rs.getString(prefix + "telefono"),
                rs.getString(prefix + "localita"),
                rs.getString(prefix + "via"),
                rs.getString(prefix + "civico"),
                rs.getInt(prefix + "tipoUt"));
    }

    static Circolo circolo(ResultSet rs) throws SQLException {
        return new Circolo(rs.getInt("ciid"), rs.getString("cinome"), rs.getString("ciluogo"));
    }

    static Scuola scuola(ResultSet rs) throws SQLException {
        return new Scuola(rs.getInt("scid"), rs.getString("scgrado"), rs.getString("scdescrizione"));
    }

    static Squadra squadra(ResultSet rs) throws SQLException {
        // Normal DAO mappings retain the empty object (id 0) produced by a missing left join.
        return new Squadra(rs.getInt("sqid"), rs.getString("sqnome"), rs.getString("sqcolore"));
    }

    static void bindCommonFields(PreparedStatement pst, Iscritto iscritto) throws SQLException {
        pst.setString(1, iscritto.getNome());
        pst.setString(2, iscritto.getCognome());
        pst.setDate(3, new java.sql.Date(iscritto.getDataNascita().getTime()));
        pst.setString(4, iscritto.getPresenza());
        pst.setInt(5, iscritto.getLaboratorio().getId());
        pst.setInt(6, iscritto.getParrocchia().getId());
        pst.setInt(7, iscritto.getRegistrato().getId());
        pst.setInt(8, iscritto.getCircolo().getId());
    }

    static void bindSquadra(PreparedStatement pst, Integer idSquadra) throws SQLException {
        if (idSquadra == null) {
            pst.setNull(1, Types.INTEGER);
        } else {
            pst.setInt(1, idSquadra);
        }
    }
}
