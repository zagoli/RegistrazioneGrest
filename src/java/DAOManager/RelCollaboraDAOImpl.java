package DAOManager;

import Domain.RelCollabora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RelCollaboraDAOImpl implements RelCollaboraDAO{
    private final String INSERT_RELCOLLABORA = "insert into collabora (Registrato_id,Attivita_gen_id,data) values (?,?,?);";
    private final String DELETE_RELCOLLABORA = "delete from collabora where id = ?;";
    private final String FIND_RELCOLLABORA_REGISTRATO_ID = "select * from collabora where Registrato_id = ?;";
    private final String FIND_RELCOLLABORA_ATTGEN_ID = "select * from collabora where Attivita_Gen_id = ?;";
    private final String FIND_RELCOLLABORA_ID = "select * from collabora where id = ?;";
    private final String FIND_ALL_RELCOLLABORA = "select * from collabora order by Attivita_Gen_id asc;";

    @Override
    public void insert(RelCollabora rc) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_RELCOLLABORA);
        pst.setInt(1, rc.getRegistratoId());
        pst.setInt(2, rc.getAttivitaGenId());
        pst.setString(3, rc.getData());
        pst.executeUpdate();
    }

    @Override
    public void delete(RelCollabora rc) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_RELCOLLABORA);
        pst.setInt(1, rc.getId());
        pst.executeUpdate();
    }

    @Override
    public List<RelCollabora> findByRegistratoId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELCOLLABORA_REGISTRATO_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelCollabora> lrc = new LinkedList<>();
        while (rs.next()) {
            RelCollabora rc = this.mapRowToRelCollabora(rs);
            lrc.add(rc);
        }
        return lrc;
    }

    @Override
    public List<RelCollabora> findByAttGenId(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELCOLLABORA_ATTGEN_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelCollabora> lrc = new LinkedList<>();
        while (rs.next()) {
            RelCollabora rc = this.mapRowToRelCollabora(rs);
            lrc.add(rc);
        }
        return lrc;
    }
    
    @Override
    public RelCollabora findById(int id) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_RELCOLLABORA_ID);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        RelCollabora rc = rs.next()? this.mapRowToRelCollabora(rs) : null;
        return rc;
    }
    
    @Override
    public List<RelCollabora> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_RELCOLLABORA);
        ResultSet rs = pst.executeQuery();
        LinkedList<RelCollabora> lrc = new LinkedList<>();
        while (rs.next()) {
            RelCollabora rc = this.mapRowToRelCollabora(rs);
            lrc.add(rc);
        }
        return lrc;
    }
    
    public RelCollabora mapRowToRelCollabora (ResultSet rs) throws SQLException {
        RelCollabora rc = new RelCollabora(
                rs.getInt("id"),
                rs.getInt("Registrato_id"),
                rs.getInt("Attivita_Gen_id"),
                rs.getString("data")
        );
        return rc;
    }

}
