package DAOManager;

import Domain.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TerzamediaDAOImpl implements TerzamediaDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private static final String INSERT_TERZAMEDIA = "insert into Terzamedia (nome,cognome,dataNascita,presenza,Laboratorio_id,Parrocchia_id,Registrato_id,Circolo_id,richieste,noteAlimentari,saNuotare,Scuola_id,sezione,nTessera,cellulare,festaPassaggio,mail) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_TERZAMEDIA = "update Terzamedia set nome = ?, cognome = ?, dataNascita = ?, presenza = ?, Laboratorio_id = ?, Parrocchia_id = ?, Registrato_id = ?, Circolo_id = ?,  richieste = ?, noteAlimentari = ?,  saNuotare = ?,  Scuola_id = ?, sezione = ?,  nTessera = ?, cellulare = ?, festaPassaggio = ?, mail = ? where id = ?;";
    private static final String UPDATE_SQUADRA_TERZAMEDIA = "update Terzamedia set squadra_id = ? where id = ?;";
    private static final String UPDATE_LABORATORIO_TERZAMEDIA = "update Terzamedia set Laboratorio_id = ? where id = ?;";
    private static final String DELETE_TERZAMEDIA = "delete from Terzamedia where id = ?;";
    private static final String GENERIC_TERZAMEDIA_FIND =
            """
                    select
                    ter.id as terid, ter.nome as ternome, ter.cognome as tercognome, ter.dataNascita as terdataNascita, ter.presenza as terpresenza, ter.richieste as terrichieste, ter.noteAlimentari as ternoteAlimentari, ter.saNuotare as tersaNuotare, ter.sezione as tersezione, ter.nTessera as terTessera, ter.mail as termail, ter.cellulare as tercellulare, ter.festaPassaggio as terfestaPassaggio,
                    la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato,
                    pa.id as paid, pa.nome as panome, pa.luogo as paluogo,
                    re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,
                    ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo,
                    sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione,
                    sq.id as sqid, sq.nome as sqnome, sq.colore as sqcolore
                    from Terzamedia ter
                    join Laboratorio la on (ter.Laboratorio_id = la.id)
                    join Parrocchia pa on (ter.Parrocchia_id = pa.id)
                    join Registrato re on (ter.Registrato_id = re.id)
                    join Circolo ci on (ter.Circolo_id = ci.id)
                    join Scuola sc on (ter.Scuola_id = sc.id)
                    left join Squadra sq on (ter.squadra_id = sq.id)
                    """;
    private static final String FIND_TERZAMEDIA_ID = GENERIC_TERZAMEDIA_FIND + " where ter.id = ?;";
    private static final String FIND_ALL_TERZAMEDIA = GENERIC_TERZAMEDIA_FIND + " order by ter.cognome, ter.nome;";
    private static final String FIND_ALL_CON_PAGAMENTI = """
            select ter.id as terid, ter.nome as ternome, ter.cognome as tercognome,
                   re.localita as relocalita,
                   p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata,
                   p.quota as pquota, pre.nome as prenome, pre.cognome as precognome,
                   coalesce(pr.settimane, 0) as settimane
            from Terzamedia ter
            join Registrato re on (ter.Registrato_id = re.id)
            left join lateral (select id, ordineArrivo, data, quota, Registrato_id from PagamentoTerzamedia where Terzamedia_id = ter.id order by id limit 1) p on true
            left join Registrato pre on (p.Registrato_id = pre.id)
            left join (select Terzamedia_id, count(*) as settimane from presenzaTer group by Terzamedia_id) pr on (pr.Terzamedia_id = ter.id)
            order by ter.cognome, ter.nome;
            """;
    private static final String FIND_ALL_CON_PAGAMENTO = """
            select iscritti.*, p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota,
                   pre.nome as prenome, pre.cognome as precognome
            from (
            """ + GENERIC_TERZAMEDIA_FIND + """
            ) iscritti
            left join lateral (select id, ordineArrivo, data, quota, Registrato_id from PagamentoTerzamedia where Terzamedia_id = iscritti.terid order by id limit 1) p on true
            left join Registrato pre on (p.Registrato_id = pre.id)
            order by iscritti.tercognome, iscritti.ternome;
            """;
    private static final String FIND_TERZAMEDIA_REGISTRATO_ID = GENERIC_TERZAMEDIA_FIND + " where re.id = ? order by ter.cognome, ter.nome;";
    private static final String COUNT_TERZAMEDIA = "select count(*) from Terzamedia;";
    // </editor-fold>

    @Override
    public void insert(TerzaMedia t) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_TERZAMEDIA, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, t.getNome());
            pst.setString(2, t.getCognome());
            pst.setDate(3, new java.sql.Date(t.getDataNascita().getTime()));
            pst.setString(4, t.getPresenza());
            pst.setInt(5, t.getLaboratorio().getId());
            pst.setInt(6, t.getParrocchia().getId());
            pst.setInt(7, t.getRegistrato().getId());
            pst.setInt(8, t.getCircolo().getId());
            pst.setString(9, t.getRichieste());
            pst.setString(10, t.getNoteAlimentari());
            pst.setBoolean(11, t.getSaNuotare());
            pst.setInt(12, t.getScuola().getId());
            pst.setString(13, t.getSezione());
            pst.setString(14, t.getNTessera());
            pst.setString(15, t.getCellulare());
            pst.setBoolean(16, t.getFestaPassaggio());
            pst.setString(17, t.getMail());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(TerzaMedia t) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_TERZAMEDIA)) {
            pst.setString(1, t.getNome());
            pst.setString(2, t.getCognome());
            pst.setDate(3, new java.sql.Date(t.getDataNascita().getTime()));
            pst.setString(4, t.getPresenza());
            pst.setInt(5, t.getLaboratorio().getId());
            pst.setInt(6, t.getParrocchia().getId());
            pst.setInt(7, t.getRegistrato().getId());
            pst.setInt(8, t.getCircolo().getId());
            pst.setString(9, t.getRichieste());
            pst.setString(10, t.getNoteAlimentari());
            pst.setBoolean(11, t.getSaNuotare());
            pst.setInt(12, t.getScuola().getId());
            pst.setString(13, t.getSezione());
            pst.setString(14, t.getNTessera());
            pst.setString(15, t.getCellulare());
            pst.setBoolean(16, t.getFestaPassaggio());
            pst.setString(17, t.getMail());
            pst.setInt(18, t.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void updateSquadra(int id, Integer idSquadra) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_SQUADRA_TERZAMEDIA)) {
            if (idSquadra == null) {
                pst.setNull(1, Types.INTEGER);
            } else {
                pst.setInt(1, idSquadra);
            }
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void updateLaboratorio(int id, int idLaboratorio) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO_TERZAMEDIA)) {
            pst.setInt(1, idLaboratorio);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(Integer idTerzamedia) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_TERZAMEDIA)) {
            pst.setInt(1, idTerzamedia);
            pst.executeUpdate();
        }
    }

    @Override
    public TerzaMedia findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToTerzamedia(rs) : null;
            }
        }
    }

    @Override
    public List<TerzaMedia> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_TERZAMEDIA);
             ResultSet rs = pst.executeQuery()) {
            LinkedList<TerzaMedia> lt = new LinkedList<>();
            while (rs.next()) {
                lt.add(this.mapRowToTerzamedia(rs));
            }
            return lt;
        }
    }

    @Override
    public List<DatiPagamento<IscrittoPagamento, PagamentoRiepilogo>> findAllConPagamenti() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_CON_PAGAMENTI);
             ResultSet rs = pst.executeQuery()) {
            List<DatiPagamento<IscrittoPagamento, PagamentoRiepilogo>> dati = new LinkedList<>();
            while (rs.next()) {
                int id = rs.getInt("terid");
                IscrittoPagamento iscritto = new IscrittoPagamento(
                        id, rs.getString("ternome"), rs.getString("tercognome"), rs.getString("relocalita"));
                PagamentoRiepilogo pagamento = rs.getObject("pid") == null ? null : new PagamentoRiepilogo(
                        rs.getInt("pid"), rs.getInt("pordineArrivo"), rs.getDate("pdata"), rs.getFloat("pquota"),
                        rs.getString("prenome"), rs.getString("precognome"));
                dati.add(new DatiPagamento<>(iscritto, pagamento, rs.getInt("settimane")));
            }
            return dati;
        }
    }

    @Override
    public List<DatiPagamento<TerzaMedia, PagamentoRiepilogo>> findAllConPagamento() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_CON_PAGAMENTO);
             ResultSet rs = pst.executeQuery()) {
            List<DatiPagamento<TerzaMedia, PagamentoRiepilogo>> dati = new LinkedList<>();
            while (rs.next()) {
                dati.add(new DatiPagamento<>(RagazzoDAOImpl.mapRowToPagamentoRiepilogo(rs), mapRowToTerzamedia(rs)));
            }
            return dati;
        }
    }

    @Override
    public int count() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(COUNT_TERZAMEDIA);
             ResultSet rs = pst.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public List<TerzaMedia> findByRegistratoId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_TERZAMEDIA_REGISTRATO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                LinkedList<TerzaMedia> lt = new LinkedList<>();
                while (rs.next()) {
                    lt.add(this.mapRowToTerzamedia(rs));
                }
                return lt;
            }
        }
    }

    public TerzaMedia mapRowToTerzamedia(ResultSet rs) throws SQLException {
        return new TerzaMedia(
                rs.getInt("terid"),
                rs.getString("ternome"),
                rs.getString("tercognome"),
                rs.getDate("terdataNascita"),
                rs.getString("terpresenza"),
                new Laboratorio(
                        rs.getInt("laid"),
                        rs.getString("ladescrizione"),
                        rs.getBoolean("lariservato")
                ),
                new Parrocchia(
                        rs.getInt("paid"),
                        rs.getString("panome"),
                        rs.getString("paluogo")
                ),
                new Registrato(
                        rs.getInt("reid"),
                        rs.getString("remail"),
                        rs.getString("repassword"),
                        rs.getString("renome"),
                        rs.getString("recognome"),
                        rs.getString("retelefono"),
                        rs.getString("relocalita"),
                        rs.getString("revia"),
                        rs.getString("recivico"),
                        rs.getInt("retipoUt")
                ),
                new Circolo(
                        rs.getInt(("ciid")),
                        rs.getString("cinome"),
                        rs.getString("ciluogo")
                ),
                rs.getString("terrichieste"),
                rs.getString("ternoteAlimentari"),
                rs.getBoolean("tersaNuotare"),
                rs.getBoolean("terfestaPassaggio"),
                new Scuola(
                        rs.getInt("scid"),
                        rs.getString("scgrado"),
                        rs.getString("scdescrizione")
                ),
                rs.getString("tersezione"),
                rs.getString("terTessera"),
                new Squadra(
                        rs.getInt("sqid"),
                        rs.getString("sqnome"),
                        rs.getString("sqcolore")
                ),
                rs.getString("tercellulare"),
                rs.getString("termail")
        );
    }

}
