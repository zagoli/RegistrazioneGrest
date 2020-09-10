package DAOManager;

import Domain.PagamentoTerzamedia;
import Domain.Registrato;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PagamentoTerzamediaDAOImpl implements PagamentoTerzamediaDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private final String INSERT_PAGAMENTO = "insert into PagamentoTerzamedia (quota,Terzamedia_Id,Registrato_Id,ordineArrivo) values (?,?,?,?);";
    private final String UPDATE_PAGAMENTO = "update PagamentoTerzamedia set data = ?, quota = ?, Terzamedia_id = ?, Registrato_id = ?, ordineArrivo = ? where id = ?;";
    private final String DELETE_PAGAMENTO = "delete from PagamentoTerzamedia where id = ?;";
    private final String DELETE_PAGAMENTO_FROM_TERZAMEDIA_ID = "delete from PagamentoTerzamedia where Terzamedia_id = ?;";
    private final String FIND_PAGAMENTO_ID = "select p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, p.Terzamedia_id as pterid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "                                                                        
            + " where p.id = ?;";
    private final String FIND_ALL_PAGAMENTO = "select p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, p.Terzamedia_id as pterid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) ";                                                                        
    private final String FIND_PAGAMENTO_REGISTRATO_ID = "select p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, p.Terzamedia_id as pterid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "                                                                        
            + " where p.Registrato_id = ?;";
    private final String FIND_PAGAMENTO_TERZAMEDIA_ID = "select p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, p.Terzamedia_id as pterid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from PagamentoTerzamedia p "
            + " join Registrato re on (p.Registrato_id = re.id) "                                                                        
            + " where p.Terzamedia_id = ?;";
    private final String COUNT_PAGAMENTO = "select count(*) from PagamentoTerzamedia;";
    // </editor-fold>

    @Override
    public void insert(PagamentoTerzamedia p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setFloat(1, p.getQuota());
        pst.setInt(2, p.getTerzamediaId());
        pst.setInt(3, p.getRegistrato().getId());
        pst.setInt(4, p.getOrdineArrivo());
        pst.executeUpdate();
    }
    
    @Override
    public void insert(int ordineArrivo, float quota, int terId, int regId) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setFloat(1, quota);
        pst.setInt(2, terId);
        pst.setInt(3, regId);
        pst.setInt(4, ordineArrivo);
        pst.executeUpdate();
    }

    @Override
    public void update(PagamentoTerzamedia p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PAGAMENTO);
        pst.setDate(1, new Date(p.getData().getTime()));
        pst.setFloat(2, p.getQuota());
        pst.setInt(3, p.getTerzamediaId());
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
        return rs.next() ? this.mapRowToPagamentoTerzamedia(rs) : null;
    }

    @Override
    public List<PagamentoTerzamedia> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        LinkedList<PagamentoTerzamedia> lp = new LinkedList<>();
        while (rs.next()) {
            lp.add(this.mapRowToPagamentoTerzamedia(rs));
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
            lp.add(this.mapRowToPagamentoTerzamedia(rs));
        }
        return lp;
    }

    @Override
    public PagamentoTerzamedia findByTerzamediaId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_TERZAMEDIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToPagamentoTerzamedia(rs) : null;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public PagamentoTerzamedia mapRowToPagamentoTerzamedia(ResultSet rs) throws SQLException {
        return new PagamentoTerzamedia(
                rs.getInt("pid"),
                rs.getDate("pdata"),
                rs.getFloat("pquota"),
                rs.getInt("pterid"),
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
    }
}
