package DAOManager;

import Domain.Registrato;
import java.sql.SQLException;
import java.util.List;

public interface RegistratoDAO {
    //CRUD
    public void insert(Registrato r) throws SQLException;
    public void update(Registrato r) throws SQLException;
    public void updatePassword(Registrato r) throws SQLException;
    public void delete(Integer idRegistrato) throws SQLException;
    public Registrato findById(int id) throws SQLException;
    //SELECT
    public List<Registrato> findAll() throws SQLException;
    public int count() throws SQLException;
    public int countUsers() throws SQLException;
    public Registrato findByMail(String mail) throws SQLException;
    public Registrato findByNominativo(String nome,String cognome) throws SQLException;
    public List<Registrato> findAllUsers() throws SQLException;
    public List<Registrato> findUsersByAccompagnatoreId(int id) throws SQLException;
    public List<Registrato> findUsersByContattoUrgenzeId(int id) throws SQLException;
    public List<Registrato> findUsersByAttGenId(int id) throws SQLException;
    
}
