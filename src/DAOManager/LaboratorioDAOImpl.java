package DAOManager;

import Domain.Laboratorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO {

    private static final String INSERT_LABORATORIO = "insert into Laboratorio (descrizione,riservato) values (?,?);";
    private static final String UPDATE_LABORATORIO = "update Laboratorio set descrizione = ? where id = ?;";
    private static final String DELETE_LABORATORIO = "delete from Laboratorio where id = ?;";
    private static final String FIND_LABORATORIO_ID = "select * from Laboratorio where id = ?;";
    private static final String FIND_ALL_LABORATORIO = "select * from Laboratorio;";
    private static final String FIND_NON_RISERVATO = "select * from Laboratorio where riservato = 0;";
    private static final String FIND_LABORATORIO_DESCRIZIONE = "select * from Laboratorio where descrizione = ?;";
    private static final String COUNT_LABORATORIO = "select count(*) from Laboratorio;";

    @Override
    public void insert(Laboratorio l) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_LABORATORIO);
        pst.setString(1, l.getDescrizione());
        pst.setBoolean(1, l.getRiservato());
        pst.executeUpdate();
    }

    @Override
    public void update(Laboratorio l) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_LABORATORIO);
        pst.setString(1, l.getDescrizione());
        pst.setInt(2, l.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idLaboratorio) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_LABORATORIO);
        pst.setInt(1, idLaboratorio);
        pst.executeUpdate();
    }

    @Override
    public Laboratorio findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_LABORATORIO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToLaboratorio(rs) : null;
    }

    @Override
    public List<Laboratorio> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_LABORATORIO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Laboratorio> ll = new LinkedList<>();
        while (rs.next()){
            ll.add(this.mapRowToLaboratorio(rs));
        }
        return ll;
    }

    @Override
    public Laboratorio findByDescrizione(String descrizione) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_LABORATORIO_DESCRIZIONE);
        pst.setString(1, descrizione);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToLaboratorio(rs) : null;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_LABORATORIO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public List<Laboratorio> findNonRiservato() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_NON_RISERVATO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Laboratorio> ll = new LinkedList<>();
        while (rs.next()){
            ll.add(this.mapRowToLaboratorio(rs));
        }
        return ll;
    }
    
    public Laboratorio mapRowToLaboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(
                rs.getInt("id"),
                rs.getString("descrizione"),
                rs.getBoolean("riservato")
        );
    }

}
