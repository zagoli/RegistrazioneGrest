package DAOManager;

import Domain.Circolo;
import Domain.Laboratorio;
import Domain.Pagamento;
import Domain.Parrocchia;
import Domain.Ragazzo;
import Domain.Registrato;
import Domain.Scuola;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;

public class PagamentoDAOImpl implements PagamentoDAO{
    
    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private final String INSERT_PAGAMENTO = "insert into Pagamento (data,quota,Ragazzo_Id,Registrato_Id,ordineArrivo) values (?,?,?,?,?);";
    private final String UPDATE_PAGAMENTO = "update Pagamento set data = ?, quota = ?, Ragazzo_id = ?, Registrato_id = ?, ordineArrivo = ? where id = ?;";
    private final String DELETE_PAGAMENTO = "delete from Pagamento where id = ?;";
    private final String DELETE_PAGAMENTO_FROM_RAGAZZO_ID = "delete from Pagamento where Ragazzo_id = ?;";
    private final String FIND_PAGAMENTO_ID = "select ra.squadra as rasquadra, p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, ra.id as raid, ra.nome as ranome, ra.cognome as racognome, ra.dataNascita as radataNascita, ra.presenza as rapresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, rera.id as reraid, rera.mail as reramail, rera.password as rerapassword, rera.nome as reranome, rera.cognome as reracognome, rera.telefono as reratelefono, rera.localita as reralocalita, rera.via as reravia, rera.civico as reracivico, rera.tipoUt as reratipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ra.entrataAnticipata as raentrataAnticipata, ra.richieste as rarichieste, ra.noteAlimentari as ranoteAlimentari, ra.mensa as ramensa, ra.saNuotare as rasaNuotare, ra.fratelloIscritto as rafratelloIscritto, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ra.sezione as rasezione, ra.classe as raclasse, ra.nTessera as ranTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Ragazzo ra on (p.Ragazzo_id = ra.id)"
            + " join Laboratorio la on (ra.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ra.Parrocchia_id = pa.id)"
            + " join Registrato rera on (ra.Registrato_id = rera.id)"
            + " join Circolo ci on (ra.Circolo_id = ci.id)"
            + " join Scuola sc on (ra.Scuola_id = sc.id)"
            + " where p.id = ?;";
    private final String FIND_ALL_PAGAMENTO = "select ra.squadra as rasquadra, p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, ra.id as raid, ra.nome as ranome, ra.cognome as racognome, ra.dataNascita as radataNascita, ra.presenza as rapresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, rera.id as reraid, rera.mail as reramail, rera.password as rerapassword, rera.nome as reranome, rera.cognome as reracognome, rera.telefono as reratelefono, rera.localita as reralocalita, rera.via as reravia, rera.civico as reracivico, rera.tipoUt as reratipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ra.entrataAnticipata as raentrataAnticipata, ra.richieste as rarichieste, ra.noteAlimentari as ranoteAlimentari, ra.mensa as ramensa, ra.saNuotare as rasaNuotare, ra.fratelloIscritto as rafratelloIscritto, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ra.sezione as rasezione, ra.classe as raclasse, ra.nTessera as ranTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Ragazzo ra on (p.Ragazzo_id = ra.id)"
            + " join Laboratorio la on (ra.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ra.Parrocchia_id = pa.id)"
            + " join Registrato rera on (ra.Registrato_id = rera.id)"
            + " join Circolo ci on (ra.Circolo_id = ci.id)"
            + " join Scuola sc on (ra.Scuola_id = sc.id);";
    private final String FIND_PAGAMENTO_REGISTRATO_ID = "select ra.squadra as rasquadra, p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, ra.id as raid, ra.nome as ranome, ra.cognome as racognome, ra.dataNascita as radataNascita, ra.presenza as rapresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, rera.id as reraid, rera.mail as reramail, rera.password as rerapassword, rera.nome as reranome, rera.cognome as reracognome, rera.telefono as reratelefono, rera.localita as reralocalita, rera.via as reravia, rera.civico as reracivico, rera.tipoUt as reratipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ra.entrataAnticipata as raentrataAnticipata, ra.richieste as rarichieste, ra.noteAlimentari as ranoteAlimentari, ra.mensa as ramensa, ra.saNuotare as rasaNuotare, ra.fratelloIscritto as rafratelloIscritto, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ra.sezione as rasezione, ra.classe as raclasse, ra.nTessera as ranTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Ragazzo ra on (p.Ragazzo_id = ra.id)"
            + " join Laboratorio la on (ra.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ra.Parrocchia_id = pa.id)"
            + " join Registrato rera on (ra.Registrato_id = rera.id)"
            + " join Circolo ci on (ra.Circolo_id = ci.id)"
            + " join Scuola sc on (ra.Scuola_id = sc.id)"
            + " where p.Registrato_id = ?;";
    private final String FIND_PAGAMENTO_RAGAZZO_ID = "select ra.squadra as rasquadra, p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, ra.id as raid, ra.nome as ranome, ra.cognome as racognome, ra.dataNascita as radataNascita, ra.presenza as rapresenza, la.id as laid, la.descrizione as ladescrizione, la.riservato as lariservato, pa.id as paid, pa.nome as panome, pa.luogo as paluogo, rera.id as reraid, rera.mail as reramail, rera.password as rerapassword, rera.nome as reranome, rera.cognome as reracognome, rera.telefono as reratelefono, rera.localita as reralocalita, rera.via as reravia, rera.civico as reracivico, rera.tipoUt as reratipoUt, ci.id as ciid, ci.nome as cinome, ci.luogo as ciluogo, ra.entrataAnticipata as raentrataAnticipata, ra.richieste as rarichieste, ra.noteAlimentari as ranoteAlimentari, ra.mensa as ramensa, ra.saNuotare as rasaNuotare, ra.fratelloIscritto as rafratelloIscritto, sc.id as scid, sc.grado as scgrado, sc.descrizione as scdescrizione, ra.sezione as rasezione, ra.classe as raclasse, ra.nTessera as ranTessera, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "
            + " join Ragazzo ra on (p.Ragazzo_id = ra.id)"
            + " join Laboratorio la on (ra.Laboratorio_id = la.id)"
            + " join Parrocchia pa on (ra.Parrocchia_id = pa.id)"
            + " join Registrato rera on (ra.Registrato_id = rera.id)"
            + " join Circolo ci on (ra.Circolo_id = ci.id)"
            + " join Scuola sc on (ra.Scuola_id = sc.id)"
            + " where p.Ragazzo_id = ?;";
    private final String COUNT_PAGAMENTO = "select count(*) from Pagamento;";
    // </editor-fold>

    @Override
    public void insert(Pagamento p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setDate(1, new Date(p.getData().getTime()));
        pst.setFloat(2, p.getQuota());
        pst.setInt(3, p.getRagazzo().getId());
        pst.setInt(4, p.getRegistrato().getId());
        pst.setInt(5, p.getOrdineArrivo());
        pst.executeUpdate();
    }

    @Override
    public void update(Pagamento p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PAGAMENTO);
        pst.setDate(1, new Date(p.getData().getTime()));
        pst.setFloat(2, p.getQuota());
        pst.setInt(3, p.getRagazzo().getId());
        pst.setInt(4, p.getRegistrato().getId());
        pst.setInt(5, p.getOrdineArrivo());
        pst.setInt(6, p.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idPagamento) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_PAGAMENTO);
        pst.setInt(1, idPagamento);
        pst.executeUpdate();
    }
    
    @Override
    public void deleteFromRagazzoId(Integer idRagazzo) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_PAGAMENTO_FROM_RAGAZZO_ID);
        pst.setInt(1, idRagazzo);
        pst.executeUpdate();
    }

    @Override
    public Pagamento findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Pagamento p = rs.next() ? this.mapRowToPagamento(rs) : null;
        return p;
    }

    @Override
    public List<Pagamento> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Pagamento> lp = new LinkedList<>();
        while (rs.next()){
            Pagamento p = this.mapRowToPagamento(rs);
            lp.add(p);
        }
        return lp;
    }

    @Override
    public List<Pagamento> findBySegretarioId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Pagamento> lp = new LinkedList<>();
        while (rs.next()){
            Pagamento p = this.mapRowToPagamento(rs);
            lp.add(p);
        }
        return lp;
    }

    @Override
    public Pagamento findByRagazzoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_RAGAZZO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Pagamento p = rs.next() ? this.mapRowToPagamento(rs) : null;
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
    
    public Pagamento mapRowToPagamento (ResultSet rs) throws SQLException{
        Pagamento p = new Pagamento(
                rs.getInt("pid"), 
                rs.getDate("pdata"), 
                rs.getFloat("pquota"), 
                    new Ragazzo(
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
                                    rs.getInt("reraid"),
                                    rs.getString("reramail"),
                                    rs.getString("rerapassword"),
                                    rs.getString("reranome"),
                                    rs.getString("reracognome"),
                                    rs.getString("reratelefono"),
                                    rs.getString("reralocalita"),
                                    rs.getString("reravia"),
                                    rs.getString("reracivico"),
                                    rs.getInt("reratipoUt")
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
                            rs.getString("rasquadra")
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
