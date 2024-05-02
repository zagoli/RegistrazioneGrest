package DAOManager;

import Domain.CodiceSbloccoIscrizione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodiceSbloccoIscrizioneDAOImpl implements CodiceSbloccoIscrizioneDAO {

    private static final String UPDATE_CODICE = "update CodiceSbloccoIscrizione set utilizzato = ?,dataUtilizzo = ? where codice = ?;";
    private static final String FIND_CODICE = "select * from CodiceSbloccoIscrizione where codice = ?;";

    @Override
    public void update(CodiceSbloccoIscrizione c) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(UPDATE_CODICE);
        pst.setShort(1, c.getUtilizzato());
        pst.setTimestamp(2, c.getDataUtilizzo());
        pst.setString(3, c.getCodice());
        pst.executeUpdate();
        con.close();
    }

    @Override
    public CodiceSbloccoIscrizione findByCodice(String codice) throws SQLException {
        Connection con = DAOMan.getConnection();
        PreparedStatement pst = con.prepareStatement(FIND_CODICE);
        pst.setString(1, codice);
        ResultSet rs = pst.executeQuery();
        CodiceSbloccoIscrizione res = rs.next() ? this.mapRowToCodiceSbloccoIscrizione(rs) : null;
        con.close();
        return res;
    }

    public CodiceSbloccoIscrizione mapRowToCodiceSbloccoIscrizione(ResultSet rs) throws SQLException {
        return new CodiceSbloccoIscrizione(
                rs.getString("codice"),
                rs.getShort("utilizzato"),
                rs.getTimestamp("dataUtilizzo")
        );
    }

}
