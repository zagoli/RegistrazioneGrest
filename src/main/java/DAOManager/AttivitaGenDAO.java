package DAOManager;

import Domain.AttivitaGen;
import Domain.CollaborazioneGen;

import java.sql.SQLException;
import java.util.List;

public interface AttivitaGenDAO {
    AttivitaGen findById(int id) throws SQLException;

    //SELECT
    List<AttivitaGen> findAll() throws SQLException;

    List<AttivitaGen> findByRegistratoId(int id) throws SQLException;

    List<CollaborazioneGen> findCollaborazioniByRegistratoId(int id) throws SQLException;
}
