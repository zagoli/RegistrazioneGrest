package DAOManager;

import Domain.Laboratorio;
import java.sql.SQLException;
import java.util.List;

public interface LaboratorioDAO {
    Laboratorio findById(int id) throws SQLException;
    
    //SELECT
    List<Laboratorio> findAll() throws SQLException;
    List<Laboratorio> findNonRiservato() throws SQLException;

}