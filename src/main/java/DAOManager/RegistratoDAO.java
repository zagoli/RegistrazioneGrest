package DAOManager;

import Domain.Registrato;

import java.sql.SQLException;
import java.util.List;

public interface RegistratoDAO {
    void insert(Registrato r) throws SQLException;

    void update(Registrato r) throws SQLException;

    void updatePassword(Registrato r) throws SQLException;

    void delete(Integer idRegistrato) throws SQLException;

    Registrato findById(int id) throws SQLException;

    List<Registrato> findAll() throws SQLException;

    List<Registrato> findSegretari() throws SQLException;

    List<Registrato> findAmministratori() throws SQLException;

    int countUsers() throws SQLException;

    Registrato findByMail(String mail) throws SQLException;

    Registrato findByNominativo(String nome, String cognome) throws SQLException;

}
