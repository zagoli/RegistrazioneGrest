package DAOManager;

import Domain.Pagamento;
import Domain.Registrato;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PagamentoDAOImpl implements PagamentoDAO{
    
    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private final String INSERT_PAGAMENTO = "insert into Pagamento (quota,Ragazzo_Id,Registrato_Id,ordineArrivo) values (?,?,?,?);";
    private final String UPDATE_PAGAMENTO = "update Pagamento set data = ?, quota = ?, Ragazzo_id = ?, Registrato_id = ?, ordineArrivo = ? where id = ?;";
    private final String DELETE_PAGAMENTO = "delete from Pagamento where id = ?;";
    private final String DELETE_PAGAMENTO_FROM_RAGAZZO_ID = "delete from Pagamento where Ragazzo_id = ?;";
    private final String FIND_PAGAMENTO_ID = "select p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, p.Ragazzo_id as pragid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "                                                        
            + " where p.id = ?;";
    private final String FIND_ALL_PAGAMENTO = "select p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, p.Ragazzo_id as pragid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) ";                                                                    
    private final String FIND_PAGAMENTO_REGISTRATO_ID = "select p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, p.Ragazzo_id as pragid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "                                                                       
            + " where p.Registrato_id = ?;";
    private final String FIND_PAGAMENTO_RAGAZZO_ID = "select p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, p.Ragazzo_id as pragid,"
            + " re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt"
            + " from Pagamento p "
            + " join Registrato re on (p.Registrato_id = re.id) "                                                                       
            + " where p.Ragazzo_id = ?;";
    private final String COUNT_PAGAMENTO = "select count(*) from Pagamento;";
    // </editor-fold>

    @Override
    public void insert(Pagamento p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setFloat(1, p.getQuota());
        pst.setInt(2, p.getRagazzoId());
        pst.setInt(3, p.getRegistrato().getId());
        pst.setInt(4, p.getOrdineArrivo());
        pst.executeUpdate();
    }
    
    @Override
    public void insert(int ordineArrivo, float quota, int ragId, int regId) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setFloat(1, quota);
        pst.setInt(2, ragId);
        pst.setInt(3, regId);
        pst.setInt(4, ordineArrivo);
        pst.executeUpdate();
    }

    @Override
    public void update(Pagamento p) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PAGAMENTO);
        pst.setDate(1, new Date(p.getData().getTime()));
        pst.setFloat(2, p.getQuota());
        pst.setInt(3, p.getRagazzoId());
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
        List<Pagamento> lp = new LinkedList<>();
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
                rs.getInt("pragid"), 
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
