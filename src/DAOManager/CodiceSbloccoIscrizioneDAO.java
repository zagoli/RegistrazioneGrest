package DAOManager;

import Domain.CodiceSbloccoIscrizione;

import java.sql.SQLException;

public interface CodiceSbloccoIscrizioneDAO {
    void update(CodiceSbloccoIscrizione c) throws SQLException;

    CodiceSbloccoIscrizione findByCodice(String codice) throws SQLException;

}
