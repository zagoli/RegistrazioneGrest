package DAOManager;

import Domain.Laboratorio;
import Domain.LaboratorioConNumeroIscritti;

import java.sql.SQLException;
import java.util.List;

public interface LaboratorioDAO {
    Laboratorio findById(int id) throws SQLException;

    //SELECT
    List<Laboratorio> findAll() throws SQLException;
    List<Laboratorio> findNonRiservato() throws SQLException;
    List<LaboratorioConNumeroIscritti> findAllConNumeroIscritti() throws SQLException;

}