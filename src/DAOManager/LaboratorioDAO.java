package DAOManager;

import Domain.Laboratorio;
import java.sql.SQLException;
import java.util.List;

public interface LaboratorioDAO {
    //CRUD
    void insert(Laboratorio l) throws SQLException;
    void update(Laboratorio l) throws SQLException;
    void delete(Integer idLaboratorio) throws SQLException;
    Laboratorio findById(int id) throws SQLException;
    
    //SELECT
    List<Laboratorio> findAll() throws SQLException;
    List<Laboratorio> findNonRiservato() throws SQLException;
    Laboratorio findByDescrizione(String descrizione) throws SQLException;
    int count() throws SQLException;
}