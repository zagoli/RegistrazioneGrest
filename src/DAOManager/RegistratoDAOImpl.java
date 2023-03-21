package DAOManager;

import Domain.Registrato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RegistratoDAOImpl implements RegistratoDAO {
    private static final String INSERT_REGISTRATO = "insert into Registrato (mail,password,nome,cognome,telefono,localita,via,civico,tipoUt) values (?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_REGISTRATO = "update Registrato set mail = ?,nome = ?,cognome = ?,telefono = ?,localita = ?,via = ?,civico = ?,tipoUt = ? where id = ?;";
    private static final String UPDATE_PASSWORD_REGISTRATO = "update Registrato set password = ? where id = ?;";
    private static final String DELETE_REGISTRATO = "delete from Registrato where id = ?;";
    private static final String FIND_REGISTRATO_ID = "select * from Registrato where id = ?;";
    private static final String FIND_ALL_REGISTRATO = "select * from Registrato;";
    private static final String FIND_SEGRETARI = "select * from Registrato where tipoUt > 0 and tipoUt < 3";
    private static final String FIND_AMMINISTRATORI = "select * from Registrato where tipoUt = 0";
    private static final String COUNT_USERS = "select count(*) from Registrato where tipoUt = 3;";
    private static final String FIND_REGISTRATO_MAIL = "select * from Registrato where mail = ?;";
    private static final String FIND_REGISTRATO_NOMINATIVO = "select * from Registrato where nome = ? and cognome = ?;";

    @Override
    public void insert(Registrato r) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_REGISTRATO);
        pst.setString(1, r.getMail());
        pst.setString(2, r.getPassword());
        pst.setString(3, r.getNome());
        pst.setString(4, r.getCognome());
        pst.setString(5, r.getTelefono());
        pst.setString(6, r.getLocalita());
        pst.setString(7, r.getVia());
        pst.setString(8, r.getCivico());
        pst.setInt(9, r.getTipoUt());
        pst.executeUpdate();
        con.close();
    }

    @Override
    public void update(Registrato r) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_REGISTRATO);
        pst.setString(1, r.getMail());
        pst.setString(2, r.getNome());
        pst.setString(3, r.getCognome());
        pst.setString(4, r.getTelefono());
        pst.setString(5, r.getLocalita());
        pst.setString(6, r.getVia());
        pst.setString(7, r.getCivico());
        pst.setInt(8, r.getTipoUt());
        pst.setInt(9, r.getId());
        pst.executeUpdate();
        con.close();
    }

    @Override
    public void delete(Integer idRegistrato) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_REGISTRATO);
        pst.setInt(1, idRegistrato);
        pst.executeUpdate();
        con.close();
    }

    @Override
    public Registrato findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Registrato res = rs.next() ? this.mapRowToRegistrato(rs) : null;
        con.close();
        return res;
    }

    @Override
    public List<Registrato> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_REGISTRATO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()) {
            lr.add(this.mapRowToRegistrato(rs));
        }
        con.close();
        return lr;
    }

    @Override
    public List<Registrato> findSegretari() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_SEGRETARI);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()) {
            lr.add(this.mapRowToRegistrato(rs));
        }
        con.close();
        return lr;
    }

    @Override
    public List<Registrato> findAmministratori() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_AMMINISTRATORI);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()) {
            lr.add(this.mapRowToRegistrato(rs));
        }
        con.close();
        return lr;
    }

    @Override
    public int countUsers() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_USERS);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int res = rs.getInt(1);
        con.close();
        return res;
    }

    @Override
    public Registrato findByMail(String mail) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_MAIL);
        pst.setString(1, mail);
        ResultSet rs = pst.executeQuery();
        Registrato res = rs.next() ? this.mapRowToRegistrato(rs) : null;
        con.close();
        return res;
    }

    @Override
    public Registrato findByNominativo(String nome, String cognome) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_NOMINATIVO);
        pst.setString(1, nome);
        pst.setString(2, cognome);
        ResultSet rs = pst.executeQuery();
        Registrato res = rs.next() ? this.mapRowToRegistrato(rs) : null;
        con.close();
        return res;
    }

    @Override
    public void updatePassword(Registrato r) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PASSWORD_REGISTRATO);
        pst.setString(1, r.getPassword());
        pst.setInt(2, r.getId());
        pst.executeUpdate();
        con.close();
    }
    
    public Registrato mapRowToRegistrato (ResultSet rs) throws SQLException{
        return new Registrato(
                rs.getInt("id"),
                rs.getString("mail"),
                rs.getString("password"),
                rs.getString("nome"),
                rs.getString("cognome"),
                rs.getString("telefono"),
                rs.getString("localita"),
                rs.getString("via"),
                rs.getString("civico"),
                rs.getInt("tipoUt")
        );
    }

}
