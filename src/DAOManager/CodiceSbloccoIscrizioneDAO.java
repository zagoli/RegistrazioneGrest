package DAOManager;

import Domain.CodiceSbloccoIscrizione;
import java.sql.SQLException;
import java.util.List;

public interface CodiceSbloccoIscrizioneDAO {
    //CRUD
    void insert(CodiceSbloccoIscrizione c) throws SQLException;
    void update(CodiceSbloccoIscrizione c) throws SQLException;
    void delete(CodiceSbloccoIscrizione c) throws SQLException;
    CodiceSbloccoIscrizione findByCodice(String codice) throws SQLException;
    //SELECT
    List<CodiceSbloccoIscrizione> findAll() throws SQLException;
    List<CodiceSbloccoIscrizione> findByRegistrato(short idRegistrato) throws SQLException;
    List<CodiceSbloccoIscrizione> findAllUtilizzato() throws SQLException;
    List<CodiceSbloccoIscrizione> findAllNonUtilizzato() throws SQLException;
    int count() throws SQLException;
    int countUtilizzato() throws SQLException;
    int countNonUtilizzato() throws SQLException;
}
