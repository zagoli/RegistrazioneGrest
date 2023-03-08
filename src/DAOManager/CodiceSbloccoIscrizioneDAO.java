package DAOManager;

import Domain.CodiceSbloccoIscrizione;
import java.sql.SQLException;
import java.util.List;

public interface CodiceSbloccoIscrizioneDAO {
    void update(CodiceSbloccoIscrizione c) throws SQLException;

    CodiceSbloccoIscrizione findByCodice(String codice) throws SQLException;

}
