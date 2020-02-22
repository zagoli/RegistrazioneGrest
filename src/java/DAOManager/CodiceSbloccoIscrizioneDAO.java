package DAOManager;

import Domain.CodiceSbloccoIscrizione;
import java.sql.SQLException;
import java.util.List;

public interface CodiceSbloccoIscrizioneDAO {
    //CRUD
    public void insert(CodiceSbloccoIscrizione c) throws SQLException;
    public void update(CodiceSbloccoIscrizione c) throws SQLException;
    public void delete(CodiceSbloccoIscrizione c) throws SQLException;
    public CodiceSbloccoIscrizione findByCodice(String codice) throws SQLException;
    //SELECT
    public List<CodiceSbloccoIscrizione> findAll() throws SQLException;
    public List<CodiceSbloccoIscrizione> findByRegistrato(short idRegistrato) throws SQLException;
    public List<CodiceSbloccoIscrizione> findAllUtilizzato() throws SQLException;
    public List<CodiceSbloccoIscrizione> findAllNonUtilizzato() throws SQLException;
    public int count() throws SQLException;
    public int countUtilizzato() throws SQLException;
    public int countNonUtilizzato() throws SQLException;
}
