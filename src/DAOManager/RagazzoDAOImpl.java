package DAOManager;

import Domain.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RagazzoDAOImpl implements RagazzoDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private static final String INSERT_RAGAZZO = "insert into Ragazzo (nome,cognome,dataNascita,presenza,Laboratorio_id,Parrocchia_id,Registrato_id,Circolo_id,entrataAnticipata,richieste,noteAlimentari,mensa,saNuotare,fratelloIscritto,Scuola_id,sezione,classe,nTessera) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_RAGAZZO = "update Ragazzo set nome = ?, cognome = ?, dataNascita = ?, presenza = ?, Laboratorio_id = ?, Parrocchia_id = ?, Registrato_id = ?, Circolo_id = ?, entrataAnticipata = ?, richieste = ?, noteAlimentari = ?, mensa = ?, saNuotare = ?, fratelloIscritto = ?, Scuola_id = ?, sezione = ?, classe = ?, nTessera = ? where id = ?;";
    private static final String UPDATE_SQUADRA_RAGAZZO = "update Ragazzo set squadra_id = ? where id = ?;";
    private static final String UPDATE_LABORATORIO_RAGAZZO = "update Ragazzo set Laboratorio_id = ? where id = ?;";
    private static final String DELETE_RAGAZZO = "delete from Ragazzo where id = ?;";
    private static final String GENERIC_RAGAZZO_FIND =
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
    private static final String FIND_RAGAZZO_NOMINATIVO = GENERIC_RAGAZZO_FIND + " where ra.nome = ? and ra.cognome = ?;";
    private static final String FIND_RAGAZZO_LAB_ID = GENERIC_RAGAZZO_FIND + " where la.id = ? order by ra.cognome, ra.nome;";
    private static final String FIND_RAGAZZO_SCUOLA_ID = GENERIC_RAGAZZO_FIND + " where sc.id = ? order by ra.cognome, ra.nome;";
    private static final String FIND_RAGAZZO_PARROCCHIA_ID = GENERIC_RAGAZZO_FIND + " where pa.id = ? order by ra.cognome, ra.nome;";
    private static final String FIND_RAGAZZO_CIRCOLO_ID = GENERIC_RAGAZZO_FIND + " where ci.id = ? order by ra.cognome, ra.nome;";
    private static final String FIND_RAGAZZO_CAL_ID = GENERIC_RAGAZZO_FIND + " join presenzaRag pr on (ra.id = pr.Ragazzo_id) where pr.Calendario_idSettimana = ? order by ra.cognome, ra.nome;";
    private static final String FIND_RAGAZZO_REGISTRATO_ID = GENERIC_RAGAZZO_FIND + " where re.id = ? order by ra.cognome, ra.nome;";
    private static final String COUNT_RAGAZZO = "select count(*) from Ragazzo;";
    private static final String COUNT_MENSA_TOT = "select count(*) from Ragazzo where mensa = 1;";
    private static final String COUNT_MENSA_SETTIMANALE = "select pr.Calendario_IdSettimana, count(*) from Ragazzo r join presenzaRag pr on (r.id = pr.Ragazzo_id) where mensa = '1' group by pr.Calendario_idSettimana;";
    private static final String COUNT_ANTICIPATO_TOT = "select count(*) from Ragazzo where entrataAnticipata = 1;";
    private static final String COUNT_ANTICIPATO_SETTIMANALE = "select pr.Calendario_idSettimana, count(*) from Ragazzo r join presenzaRag pr on (r.id = pr.Ragazzo_id)  where entrataAnticipata = 1 group by pr.Calendario_idSettimana;";
    private static final String COUNT_SETTIMANALE = "select pr.Calendario_idSettimana, count(*) from presenzaRag pr group by pr.Calendario_idSettimana;";
    private static final String COUNT_LABORATORIO_PER_CLASSE = "select l.descrizione, r.sezione, r.classe, s.grado, count(*) from Ragazzo r join Scuola s on s.id=r.Scuola_id join Laboratorio l on l.id=r.Laboratorio_id group by l.descrizione, s.grado, r.classe;";
    // </editor-fold>

    @Override
    public void insert(Ragazzo r) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_RAGAZZO, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, r.getNome());
        pst.setString(2, r.getCognome());
        pst.setDate(3, new java.sql.Date(r.getDataNascita().getTime()));
        pst.setString(4, r.getPresenza());
        pst.setInt(5, r.getLaboratorio().getId());
        pst.setInt(6, r.getParrocchia().getId());
        pst.setInt(7, r.getRegistrato().getId());
        pst.setInt(8, r.getCircolo().getId());
        pst.setBoolean(9, r.getEntrataAnticipata());
        pst.setString(10, r.getRichieste());
        pst.setString(11, r.getNoteAlimentari());
        pst.setBoolean(12, r.getMensa());
        pst.setBoolean(13, r.getSaNuotare());
        pst.setBoolean(14, r.getFratelloIscritto());
        pst.setInt(15, r.getScuola().getId());
        pst.setString(16, r.getSezione());
        pst.setString(17, r.getClasse());
        pst.setString(18, r.getnTessera());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            r.setId(rs.getInt(1));
        }
    }

    @Override
    public void update(Ragazzo r) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_RAGAZZO);
        pst.setString(1, r.getNome());
        pst.setString(2, r.getCognome());
        pst.setDate(3, new java.sql.Date(r.getDataNascita().getTime()));
        pst.setString(4, r.getPresenza());
        pst.setInt(5, r.getLaboratorio().getId());
        pst.setInt(6, r.getParrocchia().getId());
        pst.setInt(7, r.getRegistrato().getId());
        pst.setInt(8, r.getCircolo().getId());
        pst.setBoolean(9, r.getEntrataAnticipata());
        pst.setString(10, r.getRichieste());
        pst.setString(11, r.getNoteAlimentari());
        pst.setBoolean(12, r.getMensa());
        pst.setBoolean(13, r.getSaNuotare());
        pst.setBoolean(14, r.getFratelloIscritto());
        pst.setInt(15, r.getScuola().getId());
        pst.setString(16, r.getSezione());
        pst.setString(17, r.getClasse());
        pst.setString(18, r.getnTessera());
        pst.setInt(19, r.getId());
        pst.executeUpdate();
    }

    @Override
    public void updateSquadra(int id, Integer idSquadra) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_SQUADRA_RAGAZZO);
        if (idSquadra == null) {
            pst.setNull(1, Types.INTEGER);
        } else {
            pst.setInt(1, idSquadra);
        }
        pst.setInt(2, id);
        pst.executeUpdate();
    }

    @Override
    public void updateLaboratorio(int id, int idLaboratorio) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO_RAGAZZO);
        pst.setInt(1, idLaboratorio);
        pst.setInt(2, id);
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idRagazzo) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_RAGAZZO);
        pst.setInt(1, idRagazzo);
        pst.executeUpdate();
    }

    @Override
    public Ragazzo findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next()? this.mapRowToRagazzo(rs):null;
    }

    @Override
    public List<Ragazzo> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_RAGAZZO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while(rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_RAGAZZO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public List<Ragazzo> findByNominativo(String nome, String cognome) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_NOMINATIVO);
        pst.setString(1, nome);
        pst.setString(2, cognome);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }

    @Override
    public List<Ragazzo> findByLabId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_LAB_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }

    @Override
    public List<Ragazzo> findByScuolaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_SCUOLA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }

    @Override
    public List<Ragazzo> findByParrocchiaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_PARROCCHIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }

    @Override
    public List<Ragazzo> findByCircoloId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_CIRCOLO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }

    @Override
    public List<Ragazzo> findByCalendarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_CAL_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }
    
    @Override
    public List<Ragazzo> findByRegistratoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RAGAZZO_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Ragazzo> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRagazzo(rs));
        }
        return lr;
    }
    
        @Override
    public int countMensaTotale() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_MENSA_TOT);
        ResultSet rs = pst.executeQuery();
        rs.next();
            return rs.getInt(1);
    }

    @Override
    public List<Integer[]> countMensaSettimanale() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_MENSA_SETTIMANALE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Integer[]> count = new LinkedList<>();
        while (rs.next()){
            Integer[] i = {rs.getInt(1),rs.getInt(2)};
            count.add(i);
        }
        return count;
    }
    
    
    @Override
    public int countAnticipatoTotale() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_ANTICIPATO_TOT);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public List<Integer[]> countAnticipatoSettimanale() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_ANTICIPATO_SETTIMANALE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Integer[]> count = new LinkedList<>();
        while (rs.next()){
            Integer[] i = {rs.getInt(1),rs.getInt(2)};
            count.add(i);
        }
        return count;
    }
    
    @Override
    public List<Integer[]> countSettimanale() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_SETTIMANALE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Integer[]> count = new LinkedList<>();
        while (rs.next()){
            Integer[] i = {rs.getInt(1),rs.getInt(2)};
            count.add(i);
        }
        return count;
    }
    
    
    @Override
    public List<Object[]> countLaboratorioClasse() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_LABORATORIO_PER_CLASSE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Object[]> count = new LinkedList<>();
        while (rs.next()){
            Object[] i = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)};
            count.add(i);
        }
        return count;
    }

    public Ragazzo mapRowToRagazzo(ResultSet rs) throws SQLException{
        return new Ragazzo(
                rs.getInt("raid"),
                rs.getString("ranome"),
                rs.getString("racognome"),
                rs.getDate("radataNascita"),
                rs.getString("rapresenza"),
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
                rs.getBoolean("raentrataAnticipata"),
                rs.getString("rarichieste"),
                rs.getString("ranoteAlimentari"),
                rs.getBoolean("ramensa"),
                rs.getBoolean("rasaNuotare"),
                rs.getBoolean("rafratelloIscritto"),
                new Scuola(
                        rs.getInt("scid"),
                        rs.getString("scgrado"),
                        rs.getString("scdescrizione")
                ),
                rs.getString("rasezione"),
                rs.getString("raclasse"),
                rs.getString("ranTessera"),
                new Squadra(
                        rs.getInt("sqid"),
                        rs.getString("sqnome"),
                        rs.getString("sqcolore")
                )
        );
    }

}
