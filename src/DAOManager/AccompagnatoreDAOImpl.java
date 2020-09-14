package DAOManager;

import Domain.Accompagnatore;
import Domain.Registrato;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AccompagnatoreDAOImpl implements AccompagnatoreDAO {

    private static final String INSERT_ACCOMPAGNATORE = "insert into Accompagnatore (nome,cognome,idRegistrato) values (?,?,?);";
    private static final String UPDATE_ACCOMPAGNATORE = "update Accompagnatore set nome = ?, cognome = ?, idRegistrato = ? where id = ?;";
    private static final String DELETE_ACCOMPAGNATORE = "delete from Accompagnatore where id = ?;";
    private static final String FIND_ACCOMPAGNATORE_ID = "select a.id as aid, a.nome as anome, a.cognome as acognome, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Accompagnatore a join Registrato re on (a.idRegistrato=re.id) where a.id = ?;";
    private static final String FIND_ALL_ACCOMPAGNATORE = "select a.id as aid, a.nome as anome, a.cognome as acognome, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Accompagnatore a join Registrato re on (a.idRegistrato=re.id);";
    private static final String FIND_ACCOMPAGNATORE_REGISTRATO_ID = "select a.id as aid, a.nome as anome, a.cognome as acognome, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Accompagnatore a join Registrato re on (a.idRegistrato=re.id) where re.id = ?";
    private static final String COUNT_ACCOMPAGNATORE = "select count(*) from Accompagnatore;";

    @Override
    public void insert(Accompagnatore a) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_ACCOMPAGNATORE, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, a.getNome());
        pst.setString(2, a.getCognome());
        pst.setInt(3, a.getRegistrato().getId());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            a.setId(rs.getInt(1));
        }
    }

    @Override
    public void update(Accompagnatore a) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_ACCOMPAGNATORE);
        pst.setString(1, a.getNome());
        pst.setString(2, a.getCognome());
        pst.setInt(3, a.getRegistrato().getId());
        pst.setInt(4, a.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idAccompagnatore) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_ACCOMPAGNATORE);
        pst.setInt(1, idAccompagnatore);
        pst.executeUpdate();
    }

    @Override
    public Accompagnatore findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ACCOMPAGNATORE_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToAccompagnatore(rs) : null;
    }

    @Override
    public List<Accompagnatore> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_ACCOMPAGNATORE);
        ResultSet rs = pst.executeQuery();
        LinkedList<Accompagnatore> la = new LinkedList<>();
        while (rs.next()) {
            la.add(this.mapRowToAccompagnatore(rs));
        }
        return la;
    }

    @Override
    public List<Accompagnatore> findByRegistratoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ACCOMPAGNATORE_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<Accompagnatore> la = new LinkedList<>();
        while (rs.next()) {
            la.add(this.mapRowToAccompagnatore(rs));
        }
        return la;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_ACCOMPAGNATORE);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public Accompagnatore mapRowToAccompagnatore(ResultSet rs) throws SQLException {
        return new Accompagnatore(
                rs.getInt("aid"),
                rs.getString("anome"),
                rs.getString("acognome"),
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
                )
        );
    }

}
