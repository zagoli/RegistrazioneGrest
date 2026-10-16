package DAOManager.Mappers;

import Domain.*;
import Utility.DAOUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public final class IscrittoDAOMapper {

    private IscrittoDAOMapper() {
    }

    public static <T extends Iscritto> T commonFields(ResultSet rs, T iscritto, String prefix,
                                               String tesseraColumn) throws SQLException {
        iscritto.setId(rs.getInt(prefix + "id"));
        iscritto.setNome(rs.getString(prefix + "nome"));
        iscritto.setCognome(rs.getString(prefix + "cognome"));
        iscritto.setDataNascita(rs.getDate(prefix + "dataNascita"));
        iscritto.setPresenza(rs.getString(prefix + "presenza"));
        iscritto.setLaboratorio(laboratorio(rs));
        iscritto.setParrocchia(parrocchia(rs));
        iscritto.setRegistrato(RegistratoDAOMapper.registrato(rs, "re"));
        iscritto.setCircolo(circolo(rs));
        iscritto.setNTessera(rs.getString(tesseraColumn));
        iscritto.setSquadra(squadra(rs));
        return iscritto;
    }

    public static Laboratorio laboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(rs.getInt("laid"), rs.getString("ladescrizione"), rs.getBoolean("lariservato"));
    }

    public static Parrocchia parrocchia(ResultSet rs) throws SQLException {
        return new Parrocchia(rs.getInt("paid"), rs.getString("panome"), rs.getString("paluogo"));
    }

    public static Circolo circolo(ResultSet rs) throws SQLException {
        return new Circolo(rs.getInt("ciid"), rs.getString("cinome"), rs.getString("ciluogo"));
    }

    public static Scuola scuola(ResultSet rs) throws SQLException {
        return new Scuola(rs.getInt("scid"), rs.getString("scgrado"), rs.getString("scdescrizione"));
    }

    public static Squadra squadra(ResultSet rs) throws SQLException {
        // Normal DAO mappings retain the empty object (id 0) produced by a missing left join.
        return new Squadra(rs.getInt("sqid"), rs.getString("sqnome"), rs.getString("sqcolore"));
    }

    public static void bindCommonFields(PreparedStatement pst, Iscritto iscritto) throws SQLException {
        DAOUtils.setNormalizedString(pst, 1, iscritto.getNome());
        DAOUtils.setNormalizedString(pst, 2, iscritto.getCognome());
        pst.setDate(3, new java.sql.Date(iscritto.getDataNascita().getTime()));
        DAOUtils.setNormalizedString(pst, 4, iscritto.getPresenza());
        pst.setInt(5, iscritto.getLaboratorio().getId());
        pst.setInt(6, iscritto.getParrocchia().getId());
        pst.setInt(7, iscritto.getRegistrato().getId());
        pst.setInt(8, iscritto.getCircolo().getId());
    }

    public static void bindSquadra(PreparedStatement pst, Integer idSquadra) throws SQLException {
        if (idSquadra == null) {
            pst.setNull(1, Types.INTEGER);
        } else {
            pst.setInt(1, idSquadra);
        }
    }

    public static Ragazzo mapRowToRagazzo(ResultSet rs) throws SQLException {
        Ragazzo ragazzo = IscrittoDAOMapper.commonFields(rs, new Ragazzo(), "ra", "ranTessera");
        ragazzo.setEntrataAnticipata(rs.getBoolean("raentrataAnticipata"));
        ragazzo.setRichieste(rs.getString("rarichieste"));
        ragazzo.setNoteAlimentari(rs.getString("ranoteAlimentari"));
        ragazzo.setMensa(rs.getBoolean("ramensa"));
        ragazzo.setSaNuotare(rs.getBoolean("rasaNuotare"));
        ragazzo.setFratelloIscritto(rs.getBoolean("rafratelloIscritto"));
        ragazzo.setScuola(IscrittoDAOMapper.scuola(rs));
        ragazzo.setSezione(rs.getString("rasezione"));
        ragazzo.setClasse(rs.getString("raclasse"));
        return ragazzo;
    }

    public static TerzaMedia mapRowToTerzamedia(ResultSet rs) throws SQLException {
        TerzaMedia terzamedia = IscrittoDAOMapper.commonFields(rs, new TerzaMedia(), "ter", "terTessera");
        terzamedia.setRichieste(rs.getString("terrichieste"));
        terzamedia.setNoteAlimentari(rs.getString("ternoteAlimentari"));
        terzamedia.setSaNuotare(rs.getBoolean("tersaNuotare"));
        terzamedia.setFestaPassaggio(rs.getBoolean("terfestaPassaggio"));
        terzamedia.setScuola(IscrittoDAOMapper.scuola(rs));
        terzamedia.setSezione(rs.getString("tersezione"));
        terzamedia.setCellulare(rs.getString("tercellulare"));
        terzamedia.setMail(rs.getString("termail"));
        return terzamedia;
    }

    public static Animatore mapRowToAnimatore(ResultSet rs) throws SQLException {
        Animatore animatore = IscrittoDAOMapper.commonFields(rs, new Animatore(), "an", "annTessera");
        animatore.setCellulare(rs.getString("ancellulare"));
        animatore.setFasciaEtaRagazzi(rs.getString("anfasciaEtaRagazzi"));
        animatore.setMail(rs.getString("anmail"));
        animatore.setCodiceFiscale(rs.getString("ancodiceFiscale"));
        animatore.setResponsabileSquadra(rs.getBoolean("anisResponsabileSquadra"));
        animatore.setResponsabileLaboratorio(rs.getBoolean("anisResponsabileLaboratorio"));
        return animatore;
    }
}
