package DAOManager;

import DAOManager.Mappers.IscrittoDAOMapper;
import DAOManager.Mappers.PagamentoDAOMapper;
import Domain.*;
import Utility.DAOUtils;

import java.sql.*;
import java.util.List;

public class RagazzoDAOImpl implements RagazzoDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private static final String INSERT_RAGAZZO = "insert into Ragazzo (nome,cognome,dataNascita,presenza,Laboratorio_id,Parrocchia_id,Registrato_id,Circolo_id,entrataAnticipata,richieste,noteAlimentari,mensa,saNuotare,fratelloIscritto,Scuola_id,sezione,classe,nTessera) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_RAGAZZO = "update Ragazzo set nome = ?, cognome = ?, dataNascita = ?, presenza = ?, Laboratorio_id = ?, Parrocchia_id = ?, Registrato_id = ?, Circolo_id = ?, entrataAnticipata = ?, richieste = ?, noteAlimentari = ?, mensa = ?, saNuotare = ?, fratelloIscritto = ?, Scuola_id = ?, sezione = ?, classe = ?, nTessera = ? where id = ?;";
    private static final String UPDATE_SQUADRA_RAGAZZO = "update Ragazzo set squadra_id = ? where id = ?;";
    private static final String UPDATE_LABORATORIO_RAGAZZO = "update Ragazzo set Laboratorio_id = ? where id = ?;";
    private static final String DELETE_RAGAZZO = "delete from Ragazzo where id = ?;";
    static final String GENERIC_RAGAZZO_FIND =
            """
                    select
                    ra.id as raid, ra.nome as ranome, ra.cognome as racognome, ra.dataNascita as radataNascita, ra.presenza as rapresenza,ra.entrataAnticipata as raentrataAnticipata, ra.richieste as rarichieste, ra.noteAlimentari as ranoteAlimentari, ra.mensa as ramensa, ra.saNuotare as rasaNuotare, ra.fratelloIscritto as rafratelloIscritto, ra.sezione as rasezione, ra.classe as raclasse, ra.nTessera as ranTessera,
                    la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato,
                    pa.id as paid, pa.nome as panome, pa.luogo as paluogo,
                    re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,
                    ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo,
                    sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione,
                    sq.id as sqid, sq.nome as sqnome, sq.colore as sqcolore
                    from Ragazzo ra
                    join Laboratorio la on (ra.Laboratorio_id = la.id)
                    join Parrocchia pa on (ra.Parrocchia_id = pa.id)
                    join Registrato re on (ra.Registrato_id = re.id)
                    join Circolo ci on (ra.Circolo_id = ci.id)
                    join Scuola sc on (ra.Scuola_id = sc.id)
                    left join Squadra sq on (ra.squadra_id = sq.id)
                    """;
    private static final String FIND_RAGAZZO_ID = GENERIC_RAGAZZO_FIND + " where ra.id = ?;";
    private static final String FIND_ALL_RAGAZZO = GENERIC_RAGAZZO_FIND + " order by ra.cognome, ra.nome;";
    private static final String FIND_ALL_ISCRITTO_CON_PAGAMENTO = """
        select ra.id as raid, ra.nome as ranome, ra.cognome as racognome,
               ra.mensa as ramensa, ra.fratelloIscritto as rafratelloIscritto,
               ra.entrataAnticipata as raentrataAnticipata, re.localita as relocalita,
               p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata,
               p.quota as pquota, pre.nome as prenome, pre.cognome as precognome,
               coalesce(pr.settimane, 0) as settimane
        from Ragazzo ra
        join Registrato re on (ra.Registrato_id = re.id)
        left join Pagamento p on (p.Ragazzo_id = ra.id)
        left join Registrato pre on (p.Registrato_id = pre.id)
        left join (select Ragazzo_id, count(*) as settimane from presenzaRag group by Ragazzo_id) pr on (pr.Ragazzo_id = ra.id)
        order by ra.cognome, ra.nome;
        """;
    private static final String FIND_ALL_CON_PAGAMENTO = """
            select iscritti.*, p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota,
                   pre.nome as prenome, pre.cognome as precognome
            from (
            """ + GENERIC_RAGAZZO_FIND + """
            ) iscritti
            left join Pagamento p on (p.Ragazzo_id = iscritti.raid)
            left join Registrato pre on (p.Registrato_id = pre.id)
            order by iscritti.racognome, iscritti.ranome;
            """;
    private static final String FIND_RAGAZZO_CAL_ID = GENERIC_RAGAZZO_FIND + " join presenzaRag pr on (ra.id = pr.Ragazzo_id) where pr.Calendario_idSettimana = ? order by ra.cognome, ra.nome;";
    private static final String FIND_RAGAZZO_REGISTRATO_ID = GENERIC_RAGAZZO_FIND + " where re.id = ? order by ra.cognome, ra.nome;";
    private static final String COUNT_RAGAZZO = "select count(*) from Ragazzo;";
    private static final String COUNT_RAGAZZO_REGISTRATO_ID = "select count(*) from Ragazzo where Registrato_id = ?;";
    private static final String COUNT_MENSA_TOT = "select count(*) from Ragazzo where mensa = true;";
    private static final String COUNT_MENSA_SETTIMANALE = "select pr.Calendario_IdSettimana, count(*) from Ragazzo r join presenzaRag pr on (r.id = pr.Ragazzo_id) where mensa = true group by pr.Calendario_idSettimana;";
    private static final String COUNT_ANTICIPATO_TOT = "select count(*) from Ragazzo where entrataAnticipata = true;";
    private static final String COUNT_ANTICIPATO_SETTIMANALE = "select pr.Calendario_idSettimana, count(*) from Ragazzo r join presenzaRag pr on (r.id = pr.Ragazzo_id)  where entrataAnticipata = true group by pr.Calendario_idSettimana;";
    private static final String COUNT_SETTIMANALE = "select pr.Calendario_idSettimana, count(*) from presenzaRag pr group by pr.Calendario_idSettimana;";
    // </editor-fold>

    static void bindRagazzo(PreparedStatement pst, Ragazzo r) throws SQLException {
        IscrittoDAOMapper.bindCommonFields(pst, r);
        pst.setBoolean(9, r.getEntrataAnticipata());
        DAOUtils.setNormalizedString(pst, 10, r.getRichieste());
        DAOUtils.setNormalizedString(pst, 11, r.getNoteAlimentari());
        pst.setBoolean(12, r.getMensa());
        pst.setBoolean(13, r.getSaNuotare());
        pst.setBoolean(14, r.getFratelloIscritto());
        pst.setInt(15, r.getScuola().getId());
        DAOUtils.setNormalizedString(pst, 16, r.getSezione());
        DAOUtils.setNormalizedString(pst, 17, r.getClasse());
        DAOUtils.setNormalizedString(pst, 18, r.getNTessera());
    }

    @Override
    public void insert(Ragazzo r) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_RAGAZZO, Statement.RETURN_GENERATED_KEYS)) {
            bindRagazzo(pst, r);
            pst.executeUpdate();
            DAOUtils.idGeneratoFromResultSet(pst, r::setId);
        }
    }

    @Override
    public void update(Ragazzo r) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_RAGAZZO)) {
            bindRagazzo(pst, r);
            pst.setInt(19, r.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void updateSquadra(int id, Integer idSquadra) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_SQUADRA_RAGAZZO)) {
            IscrittoDAOMapper.bindSquadra(pst, idSquadra);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void updateLaboratorio(int id, int idLaboratorio) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO_RAGAZZO)) {
            pst.setInt(1, idLaboratorio);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(Integer idRagazzo) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_RAGAZZO)) {
            pst.setInt(1, idRagazzo);
            pst.executeUpdate();
        }
    }

    @Override
    public Ragazzo findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? IscrittoDAOMapper.mapRowToRagazzo(rs) : null;
            }
        }
    }

    @Override
    public List<Ragazzo> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_RAGAZZO);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, IscrittoDAOMapper::mapRowToRagazzo);
        }
    }

    @Override
    public List<DatiPagamento<RagazzoPagamento, PagamentoRiepilogo>> findAllIscrittoConPagamento() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_ISCRITTO_CON_PAGAMENTO);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, row -> {
                int id = row.getInt("raid");
                RagazzoPagamento iscritto = new RagazzoPagamento(
                        id, row.getString("ranome"), row.getString("racognome"), row.getString("relocalita"),
                        row.getBoolean("ramensa"), row.getBoolean("rafratelloIscritto"), row.getBoolean("raentrataAnticipata"));
                PagamentoRiepilogo pagamento = PagamentoDAOMapper.riepilogo(row);
                return new DatiPagamento<>(iscritto, pagamento, row.getInt("settimane"));
            });
        }
    }

    @Override
    public List<DatiPagamento<Ragazzo, PagamentoRiepilogo>> findAllConPagamento() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_CON_PAGAMENTO);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, row ->
                    new DatiPagamento<>(PagamentoDAOMapper.riepilogo(row), IscrittoDAOMapper.mapRowToRagazzo(row)));
        }
    }

    @Override
    public int count() throws SQLException {
        return count(COUNT_RAGAZZO);
    }

    private int count(String query) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public int countByRegistratoId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(COUNT_RAGAZZO_REGISTRATO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    @Override
    public List<Ragazzo> findByCalendarioId(int id) throws SQLException {
        return findByRelationId(FIND_RAGAZZO_CAL_ID, id);
    }

    @Override
    public List<Ragazzo> findByRegistratoId(int id) throws SQLException {
        return findByRelationId(FIND_RAGAZZO_REGISTRATO_ID, id);
    }

    private List<Ragazzo> findByRelationId(String query, int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAOUtils.resultSetToList(rs, IscrittoDAOMapper::mapRowToRagazzo);
            }
        }
    }

    @Override
    public int countMensaTotale() throws SQLException {
        return count(COUNT_MENSA_TOT);
    }

    @Override
    public List<Integer[]> countMensaSettimanale() throws SQLException {
        return countSettimanale(COUNT_MENSA_SETTIMANALE);
    }

    @Override
    public int countAnticipatoTotale() throws SQLException {
        return count(COUNT_ANTICIPATO_TOT);
    }

    @Override
    public List<Integer[]> countAnticipatoSettimanale() throws SQLException {
        return countSettimanale(COUNT_ANTICIPATO_SETTIMANALE);
    }

    @Override
    public List<Integer[]> countSettimanale() throws SQLException {
        return countSettimanale(COUNT_SETTIMANALE);
    }

    private List<Integer[]> countSettimanale(String query) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.contaSettimanaleFromResultSet(rs);
        }
    }

}
