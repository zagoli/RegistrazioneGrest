package DAOManager;

import Domain.Circolo;
import Domain.Laboratorio;
import Domain.PagamentoTerzamedia;
import Domain.Parrocchia;
import Domain.Registrato;
import Domain.Scuola;
import Domain.Terzamedia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;

public class PagamentoTerzamediaDAOImpl implements PagamentoTerzamediaDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private final String INSERT_PAGAMENTO = "insert into PagamentoTerzamedia (data,quota,Terzamedia_Id,Registrato_Id,ordineArrivo) values (?,?,?,?,?);";
    private final String UPDATE_PAGAMENTO = "update PagamentoTerzamedia set data = ?, quota = ?, Terzamedia_id = ?, Registrato_id = ?, ordineArrivo = ? where id = ?;";
    private final String DELETE_PAGAMENTO = "delete from PagamentoTerzamedia where id = ?;";
    private final String DELETE_PAGAMENTO_FROM_TERZAMEDIA_ID = "delete from PagamentoTerzamedia where Terzamedia_id = ?;";
    private final String FIND_PAGAMENTO_ID = "select ter.squadra as tersquadra, p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, ter.id as terid, ter.nome as ternome, ter.cognome as tercognome, ter.dataNascita as terdataNascita, ter.presenza as terpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, reter.id as reterid, reter.mail as retermail, reter.password as reterpassword, reter.nome as reternome, reter.cognome as retercognome, reter.telefono as retertelefono, reter.localita as reterlocalita, reter.via as retervia, reter.civico as retercivico, reter.tipoUt as retertipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ter.richieste as terrichieste, ter.noteAlimentari as ternoteAlimentari,ter.saNuotare as tersaNuotare, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ter.sezione as tersezione, ter.nTessera as ternTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,  ter.mail as termail, ter.cellulare as tercellulare, ter.festaPassaggio as terfestaPassaggio "
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Terzamedia ter on (p.Terzamedia_id = ter.id)"
            + " join Laboratorio la on (ter.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ter.Parrocchia_id = pa.id)"
            + " join Registrato reter on (ter.Registrato_id = reter.id)"
            + " join Circolo ci on (ter.Circolo_id = ci.id)"
            + " join Scuola sc on (ter.Scuola_id = sc.id)"
            + " where p.id = ?;";
    private final String FIND_ALL_PAGAMENTO = "select ter.squadra as tersquadra, p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, ter.id as terid, ter.nome as ternome, ter.cognome as tercognome, ter.dataNascita as terdataNascita, ter.presenza as terpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, reter.id as reterid, reter.mail as retermail, reter.password as reterpassword, reter.nome as reternome, reter.cognome as retercognome, reter.telefono as retertelefono, reter.localita as reterlocalita, reter.via as retervia, reter.civico as retercivico, reter.tipoUt as retertipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ter.richieste as terrichieste, ter.noteAlimentari as ternoteAlimentari,ter.saNuotare as tersaNuotare, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ter.sezione as tersezione, ter.nTessera as ternTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,  ter.mail as termail, ter.cellulare as tercellulare, ter.festaPassaggio as terfestaPassaggio "
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Terzamedia ter on (p.Terzamedia_id = ter.id)"
            + " join Laboratorio la on (ter.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ter.Parrocchia_id = pa.id)"
            + " join Registrato reter on (ter.Registrato_id = reter.id)"
            + " join Circolo ci on (ter.Circolo_id = ci.id)"
            + " join Scuola sc on (ter.Scuola_id = sc.id);";
    private final String FIND_PAGAMENTO_REGISTRATO_ID = "select ter.squadra as tersquadra, p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, ter.id as terid, ter.nome as ternome, ter.cognome as tercognome, ter.dataNascita as terdataNascita, ter.presenza as terpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, reter.id as reterid, reter.mail as retermail, reter.password as reterpassword, reter.nome as reternome, reter.cognome as retercognome, reter.telefono as retertelefono, reter.localita as reterlocalita, reter.via as retervia, reter.civico as retercivico, reter.tipoUt as retertipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ter.richieste as terrichieste, ter.noteAlimentari as ternoteAlimentari,ter.saNuotare as tersaNuotare, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ter.sezione as tersezione, ter.nTessera as ternTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,  ter.mail as termail, ter.cellulare as tercellulare, ter.festaPassaggio as terfestaPassaggio "
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Terzamedia ter on (p.Terzamedia_id = ter.id)"
            + " join Laboratorio la on (ter.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ter.Parrocchia_id = pa.id)"
            + " join Registrato reter on (ter.Registrato_id = reter.id)"
            + " join Circolo ci on (ter.Circolo_id = ci.id)"
            + " join Scuola sc on (ter.Scuola_id = sc.id)"
            + " where p.Registrato_id = ?;";
    private final String FIND_PAGAMENTO_TERZAMEDIA_ID = "select ter.squadra as tersquadra, p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, ter.id as terid, ter.nome as ternome, ter.cognome as tercognome, ter.dataNascita as terdataNascita, ter.presenza as terpresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, reter.id as reterid, reter.mail as retermail, reter.password as reterpassword, reter.nome as reternome, reter.cognome as retercognome, reter.telefono as retertelefono, reter.localita as reterlocalita, reter.via as retervia, reter.civico as retercivico, reter.tipoUt as retertipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ter.richieste as terrichieste, ter.noteAlimentari as ternoteAlimentari,ter.saNuotare as tersaNuotare, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ter.sezione as tersezione, ter.nTessera as ternTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt,  ter.mail as termail, ter.cellulare as tercellulare, ter.festaPassaggio as terfestaPassaggio "
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Terzamedia ter on (p.Terzamedia_id = ter.id)"
            + " join Laboratorio la on (ter.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ter.Parrocchia_id = pa.id)"
            + " join Registrato reter on (ter.Registrato_id = reter.id)"
            + " join Circolo ci on (ter.Circolo_id = ci.id)"
            + " join Scuola sc on (ter.Scuola_id = sc.id)"
            + " where p.Terzamedia_id = ?;";
    private final String COUNT_PAGAMENTO = "select count(*) from PagamentoTerzamedia;";
    // </editor-fold>

    @Override
    public void insert(PagamentoTerzamedia p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setDate(1, new Date(p.getData().getTime()));
        pst.setFloat(2, p.getQuota());
        pst.setInt(3, p.getTerzamedia().getId());
        pst.setInt(4, p.getRegistrato().getId());
        pst.setInt(5, p.getOrdineArrivo());
        pst.executeUpdate();
    }

    @Override
    public void update(PagamentoTerzamedia p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PAGAMENTO);
        pst.setDate(1, new Date(p.getData().getTime()));
        pst.setFloat(2, p.getQuota());
        pst.setInt(3, p.getTerzamedia().getId());
        pst.setInt(4, p.getRegistrato().getId());
        pst.setInt(5, p.getOrdineArrivo());
        pst.setInt(6, p.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idPagamentoTer) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_PAGAMENTO);
        pst.setInt(1, idPagamentoTer);
        pst.executeUpdate();
    }
    
    @Override
    public void deleteFromTerzamediaId(Integer idTerzamedia) throws SQLException{
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_PAGAMENTO_FROM_TERZAMEDIA_ID);
        pst.setInt(1, idTerzamedia);
        pst.executeUpdate();
    }

    @Override
    public PagamentoTerzamedia findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        PagamentoTerzamedia p = rs.next() ? this.mapRowToPagamentoTerzamedia(rs) : null;
        return p;
    }

    @Override
    public List<PagamentoTerzamedia> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        LinkedList<PagamentoTerzamedia> lp = new LinkedList<>();
        while (rs.next()) {
            PagamentoTerzamedia p = this.mapRowToPagamentoTerzamedia(rs);
            lp.add(p);
        }
        return lp;
    }

    @Override
    public List<PagamentoTerzamedia> findBySegretarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<PagamentoTerzamedia> lp = new LinkedList<>();
        while (rs.next()) {
            PagamentoTerzamedia p = this.mapRowToPagamentoTerzamedia(rs);
            lp.add(p);
        }
        return lp;
    }

    @Override
    public PagamentoTerzamedia findByTerzamediaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_TERZAMEDIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        PagamentoTerzamedia p = rs.next() ? this.mapRowToPagamentoTerzamedia(rs) : null;
        return p;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }

    public PagamentoTerzamedia mapRowToPagamentoTerzamedia(ResultSet rs) throws SQLException {
        PagamentoTerzamedia p = new PagamentoTerzamedia(
                rs.getInt("pid"),
                rs.getDate("pdata"),
                rs.getFloat("pquota"),
                new Terzamedia(
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
                        rs.getString("ternTessera"),
                        rs.getString("tersquadra"),
                        rs.getString("tercellulare"),
                        rs.getString("termail")
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
                 rs.getInt("pordineArrivo")
        );
        return p;
    }
}
