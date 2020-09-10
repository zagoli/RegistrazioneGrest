package DAOManager;

import Domain.ContattoUrgenze;
import Domain.Registrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ContattoUrgenzeDAOImpl implements ContattoUrgenzeDAO{
    private final String INSERT_CU = "insert into Contatto_Urgenze (fisso,cellulare,nome,cognome,relazione,idRegistrato) values (?,?,?,?,?,?);";
    private final String UPDATE_CU = "update Contatto_Urgenze set fisso = ?, cellulare = ?, nome = ?, cognome = ?, relazione = ?, idRegistrato = ? where id = ?;";
    private final String DELETE_CU = "delete from Contatto_Urgenze where id = ?;";
    private final String FIND_CU_ID = "select cu.id as cuid, cu.fisso as cufisso, cu.cellulare as cucellulare, cu.nome as cunome, cu.cognome as cucognome, cu.relazione as curelazione, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Contatto_Urgenze cu join Registrato re on (cu.idRegistrato=re.id) where cu.id = ?;";
    private final String FIND_ALL_CU = "select cu.id as cuid, cu.fisso as cufisso, cu.cellulare as cucellulare, cu.nome as cunome, cu.cognome as cucognome, cu.relazione as curelazione, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Contatto_Urgenze cu join Registrato re on (cu.idRegistrato=re.id);";
    private final String FIND_CU_REGISTRATO_ID = "select cu.id as cuid, cu.fisso as cufisso, cu.cellulare as cucellulare, cu.nome as cunome, cu.cognome as cucognome, cu.relazione as curelazione, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Contatto_Urgenze cu join Registrato re on (cu.idRegistrato=re.id) where re.id = ?;";
    private final String COUNT_CU = "select count(*) from Contatto_Urgenze;";

    @Override
    public void insert(ContattoUrgenze cu) throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(INSERT_CU);
        pst.setString(1, cu.getFisso());
        pst.setString(2, cu.getCellulare());
        pst.setString(3, cu.getNome());
        pst.setString(4, cu.getCognome());
        pst.setString(5, cu.getRelazione());
        pst.setInt(6, cu.getRegistrato().getId());
        pst.executeUpdate();
    }

    @Override
    public void update(ContattoUrgenze cu) throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(UPDATE_CU);
        pst.setString(1, cu.getFisso());
        pst.setString(2, cu.getCellulare());
        pst.setString(3, cu.getNome());
        pst.setString(4, cu.getCognome());
        pst.setString(5, cu.getRelazione());
        pst.setInt(6, cu.getRegistrato().getId());
        pst.setInt(7, cu.getId());
        pst.executeUpdate();
    }

    @Override
    public void delete(Integer idCu) throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(DELETE_CU);
        pst.setInt(1, idCu);
        pst.executeUpdate();
    }

    @Override
    public ContattoUrgenze findById(int id) throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(FIND_CU_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToContattoUrgenze(rs) : null;
    }

    @Override
    public List<ContattoUrgenze> findAll() throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(FIND_ALL_CU);
        ResultSet rs = pst.executeQuery();
        LinkedList<ContattoUrgenze> lcu = new LinkedList<>();
        while (rs.next()){
            lcu.add(this.mapRowToContattoUrgenze(rs));
        }
        return lcu;
    }

    @Override
    public List<ContattoUrgenze> findByRegistratoId(int id) throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(FIND_CU_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<ContattoUrgenze> lcu = new LinkedList<>();
        while (rs.next()){
            lcu.add(this.mapRowToContattoUrgenze(rs));
        }
        return lcu;
    }

    @Override
    public int count() throws SQLException {
        Connection conn;
        conn = DAOMan.getConnection();
        PreparedStatement pst = conn.prepareStatement(COUNT_CU);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    
    public ContattoUrgenze mapRowToContattoUrgenze(ResultSet rs) throws SQLException{
        return new ContattoUrgenze(
                rs.getInt("cuid"),
                rs.getString("cufisso"),
                rs.getString("cucellulare"),
                rs.getString("cunome"),
                rs.getString("cucognome"),
                rs.getString("curelazione"),
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