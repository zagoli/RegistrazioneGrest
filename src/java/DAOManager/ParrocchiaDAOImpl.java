package DAOManager;

import Domain.Parrocchia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ParrocchiaDAOImpl implements ParrocchiaDAO{
    private final String INSERT_PARROCCHIA = "insert into Parrocchia (nome,luogo) values (?,?)";
    private final String UPDATE_PARROCCHIA = "update Parrocchia set nome = ?, luogo = ? where id = ?;";
    private final String DELETE_PARROCCHIA = "delete from Parrocchia where id = ?;";
    private final String FIND_PARROCCHIA_ID = "select * from Parrocchia where id = ?;";
    private final String FIND_ALL_PARROCCHIA = "select * from Parrocchia order by luogo,nome;";
    private final String FIND_PARROCCHIA_NOME = "select * from Parrocchia where nome = ?;";
    private final String COUNT_PARROCCHIA = "select count(*) from Parrocchia;";

    @Override
    public void insert(Parrocchia p) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_PARROCCHIA);
        pst.setString(1,p.getNome());
        pst.setString(2,p.getLuogo());
        pst.executeUpdate();
    }

    @Override
    public void update(Parrocchia p) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PARROCCHIA);
        pst.setString(1, p.getNome());
        pst.setString(2, p.getLuogo());
        pst.setInt(3, p.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idParrocchia) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_PARROCCHIA);
        pst.setInt(1, idParrocchia);
        pst.executeUpdate();
    }

    @Override
    public Parrocchia findById(int id) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PARROCCHIA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Parrocchia p = rs.next() ? this.mapRowToParrocchia(rs) : null;
        return p;
    }

    @Override
    public List<Parrocchia> findAll() throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_PARROCCHIA);
        ResultSet rs = pst.executeQuery();
        LinkedList<Parrocchia> lp = new LinkedList();
        while(rs.next()){
            Parrocchia p = this.mapRowToParrocchia(rs);
            lp.add(p);
        }
        return lp;
    }

    @Override
    public List<Parrocchia> findByName(String name) throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_PARROCCHIA_NOME);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        LinkedList<Parrocchia> lp = new LinkedList();
        while(rs.next()){
            Parrocchia p = this.mapRowToParrocchia(rs);
            lp.add(p);
        }
        return lp;
    }

    @Override
    public int count() throws SQLException {
        Connection con;
        con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_PARROCCHIA);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    
    public Parrocchia mapRowToParrocchia(ResultSet rs) throws SQLException {
        Parrocchia p = new Parrocchia(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("luogo")
                );
        return p;
    }
    
}