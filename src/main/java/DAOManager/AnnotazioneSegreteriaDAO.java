package DAOManager;

import Domain.AnnotazioneSegreteria;

import java.sql.SQLException;
import java.util.List;

public interface AnnotazioneSegreteriaDAO {

    void insertForRagazzo(String annotazione, int ragazzoId, int segretarioId) throws SQLException;

    void insertForAnimatore(String annotazione, int animatoreId, int segretarioId) throws SQLException;

    void insertForTerzamedia(String annotazione, int terzamediaId, int segretarioId) throws SQLException;

    void delete(int id) throws SQLException;

    //SELECT
    List<AnnotazioneSegreteria> findByRagazzoId(int ragazzoId) throws SQLException;

    List<AnnotazioneSegreteria> findByAnimatoreId(int animatoreId) throws SQLException;

    List<AnnotazioneSegreteria> findByTerzamediaId(int terzamediaId) throws SQLException;

    int getIdSegretario(int idAnnotazione) throws SQLException;

    @FunctionalInterface
    interface FunzioneInserisciAnnotazione {
        void insert(String annotazione, int idIscritto, int segretarioId) throws SQLException;
    }
}
