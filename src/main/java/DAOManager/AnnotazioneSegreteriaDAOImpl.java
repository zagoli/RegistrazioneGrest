package DAOManager;

import DAOManager.Mappers.RegistratoDAOMapper;

import DAOManager.Mappers.IscrittoDAOMapper;
import Domain.AnnotazioneSegreteria;
import Utility.DAOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnnotazioneSegreteriaDAOImpl implements AnnotazioneSegreteriaDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private static final String INSERT_ANNOTAZIONE_RAGAZZO = "insert into AnnotazioniSegreteria (annotazione,ragazzo_id,segretario_id) values (?,?,?);";
    private static final String INSERT_ANNOTAZIONE_ANIMATORE = "insert into AnnotazioniSegreteria (annotazione,animatore_id,segretario_id) values (?,?,?);";
    private static final String INSERT_ANNOTAZIONE_TERZAMEDIA = "insert into AnnotazioniSegreteria (annotazione,terzamedia_id,segretario_id) values (?,?,?);";
    private static final String DELETE_ANNOTAZIONE = "delete from AnnotazioniSegreteria where id = ?;";
    private static final String FIND_ID_SEGRETARIO_DI_ANNOTAZIONE = "select segretario_id from AnnotazioniSegreteria where id = ?;";
    private static final String FIND_ANNOTAZIONI_RAGAZZO_ID =
            """
                    select an.id as anid, an.annotazione as anannotazione, an.dataCreazione as andataCreazione,
                    se.id as seid, se.mail as semail, se.password as sepassword, se.nome as senome, se.cognome as secognome, se.telefono as setelefono, se.localita as selocalita, se.via as sevia, se.civico as secivico, se.tipoUt as setipoUt,
                    ragazzo.*
                    from AnnotazioniSegreteria an
                    join Registrato se on (an.segretario_id = se.id)
                    join (
                    """ + RagazzoDAOImpl.GENERIC_RAGAZZO_FIND + """
                    ) ragazzo on (an.ragazzo_id = ragazzo.raid)
                    where an.ragazzo_id = ?
                    order by an.dataCreazione desc;
                    """;
    private static final String FIND_ANNOTAZIONI_ANIMATORE_ID =
            """
                    select an.id as anid, an.annotazione as anannotazione, an.dataCreazione as andataCreazione,
                    se.id as seid, se.mail as semail, se.password as sepassword, se.nome as senome, se.cognome as secognome, se.telefono as setelefono, se.localita as selocalita, se.via as sevia, se.civico as secivico, se.tipoUt as setipoUt,
                    animatore.*
                    from AnnotazioniSegreteria an
                    join Registrato se on (an.segretario_id = se.id)
                    join (
                    """ + AnimatoreDAOImpl.GENERIC_ANIMATORE_FIND + """
                    ) animatore on (an.animatore_id = animatore.anid)
                    where an.animatore_id = ?
                    order by an.dataCreazione desc;
                    """;
    private static final String FIND_ANNOTAZIONI_TERZAMEDIA_ID =
            """
                    select an.id as anid, an.annotazione as anannotazione, an.dataCreazione as andataCreazione,
                    se.id as seid, se.mail as semail, se.password as sepassword, se.nome as senome, se.cognome as secognome, se.telefono as setelefono, se.localita as selocalita, se.via as sevia, se.civico as secivico, se.tipoUt as setipoUt,
                    terzamedia.*
                    from AnnotazioniSegreteria an
                    join Registrato se on (an.segretario_id = se.id)
                    join (
                    """ + TerzamediaDAOImpl.GENERIC_TERZAMEDIA_FIND + """
                    ) terzamedia on (an.terzamedia_id = terzamedia.terid)
                    where an.terzamedia_id = ?
                    order by an.dataCreazione desc;
                    """;
    // </editor-fold>

    @Override
    public void insertForRagazzo(String annotazione, int ragazzoId, int segretarioId) throws SQLException {
        insert(INSERT_ANNOTAZIONE_RAGAZZO, annotazione, ragazzoId, segretarioId);
    }

    @Override
    public void insertForAnimatore(String annotazione, int animatoreId, int segretarioId) throws SQLException {
        insert(INSERT_ANNOTAZIONE_ANIMATORE, annotazione, animatoreId, segretarioId);
    }

    @Override
    public void insertForTerzamedia(String annotazione, int terzamediaId, int segretarioId) throws SQLException {
        insert(INSERT_ANNOTAZIONE_TERZAMEDIA, annotazione, terzamediaId, segretarioId);
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_ANNOTAZIONE)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    @Override
    public List<AnnotazioneSegreteria> findByRagazzoId(int ragazzoId) throws SQLException {
        return find(FIND_ANNOTAZIONI_RAGAZZO_ID, ragazzoId, rs -> {
            AnnotazioneSegreteria annotazione = mapRowToAnnotazioneSegreteria(rs);
            annotazione.setRagazzo(IscrittoDAOMapper.mapRowToRagazzo(rs));
            return annotazione;
        });
    }

    @Override
    public List<AnnotazioneSegreteria> findByAnimatoreId(int animatoreId) throws SQLException {
        return find(FIND_ANNOTAZIONI_ANIMATORE_ID, animatoreId, rs -> {
            AnnotazioneSegreteria annotazione = mapRowToAnnotazioneSegreteria(rs);
            annotazione.setAnimatore(IscrittoDAOMapper.mapRowToAnimatore(rs));
            return annotazione;
        });
    }

    @Override
    public List<AnnotazioneSegreteria> findByTerzamediaId(int terzamediaId) throws SQLException {
        return find(FIND_ANNOTAZIONI_TERZAMEDIA_ID, terzamediaId, rs -> {
            AnnotazioneSegreteria annotazione = mapRowToAnnotazioneSegreteria(rs);
            annotazione.setTerzaMedia(IscrittoDAOMapper.mapRowToTerzamedia(rs));
            return annotazione;
        });
    }

    @Override
    public int getIdSegretario(int idAnnotazione) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ID_SEGRETARIO_DI_ANNOTAZIONE)) {
            pst.setInt(1, idAnnotazione);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private void insert(String query, String annotazione, int destinatarioId, int segretarioId) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, annotazione);
            pst.setInt(2, destinatarioId);
            pst.setInt(3, segretarioId);
            pst.executeUpdate();
        }
    }

    private List<AnnotazioneSegreteria> find(String query, int id, DAOUtils.Mapper<AnnotazioneSegreteria> mapper) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAOUtils.resultSetToList(rs, mapper);
            }
        }
    }

    public AnnotazioneSegreteria mapRowToAnnotazioneSegreteria(ResultSet rs) throws SQLException {
        return new AnnotazioneSegreteria(
                rs.getInt("anid"),
                rs.getString("anannotazione"),
                null,
                null,
                null,
                RegistratoDAOMapper.registrato(rs, "se"),
                rs.getTimestamp("andataCreazione")
        );
    }
}
