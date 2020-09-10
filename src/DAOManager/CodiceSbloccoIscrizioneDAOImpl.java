package DAOManager;

import Domain.CodiceSbloccoIscrizione;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CodiceSbloccoIscrizioneDAOImpl implements CodiceSbloccoIscrizioneDAO{
    
    private final String INSERT_CODICE = "insert into CodiceSbloccoIscrizione (codice) values (?);";
    private final String UPDATE_CODICE = "update CodiceSbloccoIscrizione set utilizzato = ?,dataUtilizzo = ? where codice = ?;";
    private final String DELETE_CODICE = "delete from CodiceSbloccoIscrizione where codice = ?;";
    private final String FIND_CODICE = "select * from CodiceSbloccoIscrizione where codice = ?;";
    private final String FIND_ALL_CODICE = "select * from CodiceSbloccoIscrizione;";
    private final String FIND_CODICE_REGISTRATO = "select * from CodiceSbloccoIscrizione where utilizzato = ?;";
    private final String FIND_ALL_UTILIZZATO = "select * from CodiceSbloccoIscrizione where utilizzato is not null;";
    private final String FIND_ALL_NON_UTILIZZATO = "select * from CodiceSbloccoIscrizione where utilizzato is null;";
    private final String COUNT = "select count(*) from CodiceSbloccoIscrizione;";
    private final String COUNT_UTILIZZATO = "select count(*) from CodiceSbloccoIscrizione where utilizzato is not null;";
    private final String COUNT_NON_UTILIZZATO = "select count(*) from CodiceSbloccoIscrizione where utilizzato is null;";

    @Override
    public void insert(CodiceSbloccoIscrizione c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(INSERT_CODICE);
        pst.setString(1,c.getCodice());
        pst.executeUpdate();
    }

    @Override
    public void update(CodiceSbloccoIscrizione c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_CODICE);
        pst.setShort(1,c.getUtilizzato());
        pst.setTimestamp(2, c.getDataUtilizzo());
        pst.setString(3, c.getCodice());
        pst.executeUpdate();
    }

    @Override
    public void delete(CodiceSbloccoIscrizione c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(DELETE_CODICE);
        pst.setString(1,c.getCodice());
        pst.executeUpdate();    
    }

    @Override
    public CodiceSbloccoIscrizione findByCodice(String codice) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CODICE);
        pst.setString(1, codice);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? this.mapRowToCodiceSbloccoIscrizione(rs) : null;
    }

    @Override
    public List<CodiceSbloccoIscrizione> findAll() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_CODICE);
        ResultSet rs = pst.executeQuery();
        LinkedList<CodiceSbloccoIscrizione> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCodiceSbloccoIscrizione(rs));
        }
        return lc;
    }

    @Override
    public List<CodiceSbloccoIscrizione> findByRegistrato(short idRegistrato) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CODICE_REGISTRATO);
        pst.setShort(1, idRegistrato);
        ResultSet rs = pst.executeQuery();
        LinkedList<CodiceSbloccoIscrizione> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCodiceSbloccoIscrizione(rs));
        }
        return lc;    
    }

    @Override
    public List<CodiceSbloccoIscrizione> findAllUtilizzato() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_UTILIZZATO);
        ResultSet rs = pst.executeQuery();
        LinkedList<CodiceSbloccoIscrizione> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCodiceSbloccoIscrizione(rs));
        }
        return lc;
    }

    @Override
    public List<CodiceSbloccoIscrizione> findAllNonUtilizzato() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_ALL_NON_UTILIZZATO);
        ResultSet rs = pst.executeQuery();
        LinkedList<CodiceSbloccoIscrizione> lc = new LinkedList<>();
        while (rs.next()){
            lc.add(this.mapRowToCodiceSbloccoIscrizione(rs));
        }
        return lc;
    }

    @Override
    public int count() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT);
        ResultSet rs = pst.executeQuery();
        return rs.getInt(1);
    }

    @Override
    public int countUtilizzato() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_UTILIZZATO);
        ResultSet rs = pst.executeQuery();
        return rs.getInt(1);
    }

    @Override
    public int countNonUtilizzato() throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(COUNT_NON_UTILIZZATO);
        ResultSet rs = pst.executeQuery();
        return rs.getInt(1);
    }
    
    public CodiceSbloccoIscrizione mapRowToCodiceSbloccoIscrizione(ResultSet rs) throws SQLException {
        return new CodiceSbloccoIscrizione(
                rs.getString("codice"),
                rs.getShort("utilizzato"),
                rs.getTimestamp("dataUtilizzo")
        );
    }
    
}
