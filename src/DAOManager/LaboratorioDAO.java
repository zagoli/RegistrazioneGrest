package DAOManager;

import Domain.Laboratorio;
import java.sql.SQLException;
import java.util.List;

public interface LaboratorioDAO {
    //CRUD
    public void insert (Laboratorio l) throws SQLException;
    public void update (Laboratorio l) throws SQLException;
    public void delete (Integer idLaboratorio) throws SQLException;
    public Laboratorio findById(int id) throws SQLException;
    
    //SELECT
    public List<Laboratorio> findAll() throws SQLException;
    public List<Laboratorio> findNonRiservato() throws SQLException;
    public Laboratorio findByDescrizione(String descrizione) throws SQLException;
    public int count() throws SQLException;
}