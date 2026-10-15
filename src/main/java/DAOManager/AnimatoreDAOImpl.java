package DAOManager;

import DAOManager.Mappers.IscrittoDAOMapper;
import Domain.*;
import Utility.DAOUtils;

import java.sql.*;
import java.util.List;

public class AnimatoreDAOImpl implements AnimatoreDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private static final String INSERT_ANIMATORE = "insert into Animatore(nome,cognome,dataNascita,presenza,Laboratorio_id,Parrocchia_id,Registrato_id,Circolo_id,cellularePersonale,fasciaEtaRagazzi,mail,nTessera,codiceFiscale,isResponsabileSquadra,isResponsabileLaboratorio) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_ANIMATORE = "update Animatore set nome = ?, cognome = ?, dataNascita = ?, presenza = ?, Laboratorio_id = ?, Parrocchia_id = ?, Registrato_id = ?, Circolo_id = ?, cellularePersonale = ?, fasciaEtaRagazzi = ?, mail = ?, nTessera = ?, codiceFiscale = ?, isResponsabileSquadra = ?, isResponsabileLaboratorio = ? where id = ?;";
    private static final String UPDATE_SQUADRA_ANIMATORE = "update Animatore set squadra_id = ?, isResponsabileSquadra = ? where id = ?;";
    private static final String UPDATE_LABORATORIO_ANIMATORE = "update Animatore set Laboratorio_id = ?, isResponsabileLaboratorio = ? where id = ?;";
    private static final String DELETE_ANIMATORE = "delete from Animatore where id = ?;";
    static final String GENERIC_ANIMATORE_FIND =
            """
                    select
                    an.codiceFiscale as ancodiceFiscale, an.id as anid, an.nome as annome, an.cognome as ancognome, an.dataNascita as andataNascita, an.presenza as anpresenza, an.cellularePersonale as ancellulare, an.fasciaEtaRagazzi as anfasciaEtaRagazzi, an.mail as anmail, an.nTessera as annTessera, an.isResponsabileSquadra as anisResponsabileSquadra, an.isResponsabileLaboratorio as anisResponsabileLaboratorio,
                    la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato,
                    pa.id as paid, pa.nome as panome, pa.luogo as paluogo,
                    re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,
                    ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo,
                    sq.id as sqid, sq.nome as sqnome, sq.colore as sqcolore
                    from Animatore an
                    join Parrocchia pa on (an.Parrocchia_id = pa.id)
                    join Circolo ci on (an.Circolo_id = ci.id)
                    join Laboratorio la on (an.Laboratorio_id = la.id)
                    join Registrato re on (an.Registrato_id = re.id)
                    left join Squadra sq on (an.squadra_id = sq.id)
                    """;
    private static final String FIND_ANIMATORE_ID = GENERIC_ANIMATORE_FIND + " where an.id = ?";
    private static final String FIND_ALL_ANIMATORE = GENERIC_ANIMATORE_FIND + " order by an.cognome, an.nome;";
    private static final String COUNT_ANIMATORE = "select count(*) from Animatore;";
    private static final String FIND_ANIMATORE_CALENDARIO_ID = GENERIC_ANIMATORE_FIND + "  join presenzaAn pra on (an.id = pra.Animatore_id) where pra.Calendario_idSettimana = ? order by an.cognome, an.nome;";
    private static final String FIND_ANIMATORE_REGISTRATO_ID = GENERIC_ANIMATORE_FIND + " where re.id = ? order by an.cognome, an.nome;";
    private static final String COUNT_SETTIMANALE = "select pa.Calendario_idSettimana, count(*) from presenzaAn pa group by pa.Calendario_idSettimana;";
    // </editor-fold>

    static void bindAnimatore(PreparedStatement pst, Animatore a) throws SQLException {
        IscrittoDAOMapper.bindCommonFields(pst, a);
        pst.setString(9, a.getCellulare());
        pst.setString(10, a.getFasciaEtaRagazzi());
        pst.setString(11, a.getMail());
        pst.setString(12, a.getNTessera());
        pst.setString(13, a.getCodiceFiscale());
        pst.setBoolean(14, a.isResponsabileSquadra());
        pst.setBoolean(15, a.isResponsabileLaboratorio());
    }

    @Override
    public void insert(Animatore a) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_ANIMATORE, Statement.RETURN_GENERATED_KEYS)) {
            bindAnimatore(pst, a);
            pst.executeUpdate();
            DAOUtils.idGeneratoFromResultSet(pst, a::setId);
        }
    }

    @Override
    public void update(Animatore a) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_ANIMATORE)) {
            bindAnimatore(pst, a);
            pst.setInt(16, a.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void updateSquadra(int id, Integer idSquadra, boolean responsabile) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_SQUADRA_ANIMATORE)) {
            IscrittoDAOMapper.bindSquadra(pst, idSquadra);
            pst.setBoolean(2, responsabile);
            pst.setInt(3, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void updateLaboratorio(int id, int idLaboratorio, boolean responsabile) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO_ANIMATORE)) {
            pst.setInt(1, idLaboratorio);
            pst.setBoolean(2, responsabile);
            pst.setInt(3, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(Integer idAnimatore) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_ANIMATORE)) {
            pst.setInt(1, idAnimatore);
            pst.executeUpdate();
        }
    }

    @Override
    public Animatore findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ANIMATORE_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? IscrittoDAOMapper.mapRowToAnimatore(rs) : null;
            }
        }
    }

    @Override
    public List<Animatore> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_ANIMATORE);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, IscrittoDAOMapper::mapRowToAnimatore);
        }
    }

    @Override
    public int count() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(COUNT_ANIMATORE);
             ResultSet rs = pst.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public List<Animatore> findByCalendarioId(int id) throws SQLException {
        return findByRelationId(FIND_ANIMATORE_CALENDARIO_ID, id);
    }

    @Override
    public List<Animatore> findByRegistratoId(int id) throws SQLException {
        return findByRelationId(FIND_ANIMATORE_REGISTRATO_ID, id);
    }

    private List<Animatore> findByRelationId(String query, int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAOUtils.resultSetToList(rs, IscrittoDAOMapper::mapRowToAnimatore);
            }
        }
    }

    @Override
    public List<Integer[]> countSettimanale() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(COUNT_SETTIMANALE);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.contaSettimanaleFromResultSet(rs);
        }
    }

}
