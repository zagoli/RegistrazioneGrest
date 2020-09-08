package DAOManager;

import Domain.Registrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RegistratoDAOImpl implements RegistratoDAO{
    private final String INSERT_REGISTRATO = "insert into Registrato (mail,password,nome,cognome,telefono,localita,via,civico,tipoUt) values (?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_REGISTRATO = "update Registrato set mail = ?,nome = ?,cognome = ?,telefono = ?,localita = ?,via = ?,civico = ?,tipoUt = ? where id = ?;";
    private final String UPDATE_PASSWORD_REGISTRATO = "update Registrato set password = ? where id = ?;";
    private final String DELETE_REGISTRATO = "delete from Registrato where id = ?;";
    private final String FIND_REGISTRATO_ID = "select * from Registrato where id = ?;";
    private final String FIND_ALL_REGISTRATO = "select * from Registrato;";
    private final String COUNT_REGISTRATO = "select count(*) from Registrato;";
    private final String COUNT_USERS = "select count(*) from Registrato where tipoUt = 3;";
    private final String FIND_REGISTRATO_MAIL = "select * from Registrato where mail = ?;";
    private final String FIND_REGISTRATO_NOMINATIVO = "select * from Registrato where nome = ? and cognome = ?;";
    private final String FIND_ALL_USERS = "select * from Registrato where tipoUt = 3;";
    private final String FIND_REGISTRATO_ACCOMPAGNATORE_ID = "select * from Registrato r join acc_ut ac on (r.id = ac.Registrato_id) where ac.Accompagnatore_id = ?;";
    private final String FIND_REGISTRATO_CU_ID = "select * from Registrato r join urg_ut ur on (r.id = ur.Registrato_id) where ur.Contatto_Urgenze_id = ?;";
    private final String FIND_REGISTRATO_ATTGEN_ID = "select * from Registrato r join collabora co on (r.id = co.Registrato_id) where co.Attivita_Gen_id = ?;";

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
    }

    @Override
    public void delete(Integer idRegistrato) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_REGISTRATO);
        pst.setInt(1, idRegistrato);
        pst.executeUpdate();
    }

    @Override
    public Registrato findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Registrato r = rs.next() ? this.mapRowToRegistrato(rs) : null;
        return r;
    }

    @Override
    public List<Registrato> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_REGISTRATO);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRegistrato(rs));
        }
        return lr;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_REGISTRATO);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    
    @Override
    public int countUsers() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_USERS);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }

    @Override
    public Registrato findByMail(String mail) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_MAIL);
        pst.setString(1, mail);
        ResultSet rs = pst.executeQuery();
        Registrato r = rs.next() ? this.mapRowToRegistrato(rs) : null;
        return r;
    }

    @Override
    public Registrato findByNominativo(String nome, String cognome) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_NOMINATIVO);
        pst.setString(1, nome);
        pst.setString(2, cognome);
        ResultSet rs = pst.executeQuery();
        Registrato r = rs.next() ? this.mapRowToRegistrato(rs) : null;
        return r;
    }

    @Override
    public List<Registrato> findAllUsers() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_USERS);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()){
            lr.add(this.mapRowToRegistrato(rs));
        }
        return lr;
    }

    @Override
    public List<Registrato> findUsersByAccompagnatoreId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_ACCOMPAGNATORE_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()) {
            lr.add(this.mapRowToRegistrato(rs));
        }
        return lr;
    }

    @Override
    public List<Registrato> findUsersByContattoUrgenzeId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_CU_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()) {
            lr.add(this.mapRowToRegistrato(rs));
        }
        return lr;
    }

    @Override
    public List<Registrato> findUsersByAttGenId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_ATTGEN_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Registrato> lr = new LinkedList<>();
        while (rs.next()) {
            lr.add(this.mapRowToRegistrato(rs));
        }
        return lr;
    }
    
    @Override
    public void updatePassword(Registrato r) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_PASSWORD_REGISTRATO);
        pst.setString(1, r.getPassword());
        pst.setInt(2, r.getId());
        pst.executeUpdate();
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
