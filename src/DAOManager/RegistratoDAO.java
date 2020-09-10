package DAOManager;

import Domain.Registrato;
import java.sql.SQLException;
import java.util.List;

public interface RegistratoDAO {
    //CRUD
    void insert(Registrato r) throws SQLException;
    void update(Registrato r) throws SQLException;
    void updatePassword(Registrato r) throws SQLException;
    void delete(Integer idRegistrato) throws SQLException;
    Registrato findById(int id) throws SQLException;
    //SELECT
    List<Registrato> findAll() throws SQLException;
    int count() throws SQLException;
    int countUsers() throws SQLException;
    Registrato findByMail(String mail) throws SQLException;
    Registrato findByNominativo(String nome, String cognome) throws SQLException;
    List<Registrato> findAllUsers() throws SQLException;
    List<Registrato> findUsersByAccompagnatoreId(int id) throws SQLException;
    List<Registrato> findUsersByContattoUrgenzeId(int id) throws SQLException;
    List<Registrato> findUsersByAttGenId(int id) throws SQLException;
    
}
