package DAOManager;

import Domain.Pagamento;
import Domain.Registrato;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PagamentoDAOImpl implements PagamentoDAO {

    // <editor-fold defaultstate="collapsed" desc="Tutte le query necessarie">
    private static final String INSERT_PAGAMENTO = "insert into Pagamento (quota,Ragazzo_Id,Registrato_Id,ordineArrivo) values (?,?,?,?);";
    private static final String DELETE_PAGAMENTO = "delete from Pagamento where id = ?;";
    private static final String FIND_ALL_PAGAMENTO =
            """
                    select p.id as pid, p.ordineArrivo as pordineArrivo, p.data as pdata, p.quota as pquota, p.Ragazzo_id as pragid,
                    re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt
                    from Pagamento p
                    join Registrato re on (p.Registrato_id = re.id) ;
                    """;
    private static final String FIND_PAGAMENTO_RAGAZZO_ID =
            """
                    select p.ordineArrivo as pordineArrivo, p.id as pid, p.data as pdata, p.quota as pquota, p.Ragazzo_id as pragid,
                    re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt
                    from Pagamento p
                    join Registrato re on (p.Registrato_id = re.id)
                    where p.Ragazzo_id = ?;
                    """;
    private static final String COUNT_PAGAMENTO = "select count(*) from Pagamento;";
    // </editor-fold>

    @Override
    public void insert(int ordineArrivo, float quota, int ragId, int regId) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PAGAMENTO);
        pst.setFloat(1, quota);
        pst.setInt(2, ragId);
        pst.setInt(3, regId);
        pst.setInt(4, ordineArrivo);
        pst.executeUpdate();
        con.close();
    }

    @Override
    public void delete(Integer idPagamento) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_PAGAMENTO);
        pst.setInt(1, idPagamento);
        pst.executeUpdate();
        con.close();
    }

    @Override
    public List<Pagamento> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        List<Pagamento> lp = new LinkedList<>();
        while (rs.next()){
            lp.add(this.mapRowToPagamento(rs));
        }
        con.close();
        return lp;
    }

    @Override
    public Pagamento findByRagazzoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PAGAMENTO_RAGAZZO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Pagamento res = rs.next() ? this.mapRowToPagamento(rs) : null;
        con.close();
        return res;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_PAGAMENTO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int res = rs.getInt(1);
        con.close();
        return res;
    }
    
    public Pagamento mapRowToPagamento (ResultSet rs) throws SQLException{
        return new Pagamento(
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
    }
}
