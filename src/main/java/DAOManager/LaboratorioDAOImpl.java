package DAOManager;

import Domain.Laboratorio;
import Domain.LaboratorioConNumeroIscritti;
import Utility.DAOUtils;

import java.sql.*;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO {

    private static final String FIND_LABORATORIO_ID = "select * from Laboratorio where id = ?;";
    private static final String FIND_ALL_LABORATORIO = "select * from Laboratorio;";
    private static final String FIND_NON_RISERVATO = "select * from Laboratorio where riservato = false;";
    private static final String FIND_ALL_CON_NUMERO_ISCRITTI = """
            select l.*,
            (
                (select count(*) from Ragazzo r where r.laboratorio_id = l.id) +
                (select count(*) from Terzamedia t where t.laboratorio_id = l.id) +
                (select count(*) from Animatore a where a.laboratorio_id = l.id)
            ) as numeroIscritti
            from Laboratorio l
            """;
    private static final String INSERT_LABORATORIO = "insert into Laboratorio (descrizione, riservato) values (?, ?);";
    private static final String DELETE_LABORATORIO = "delete from Laboratorio where id = ?;";

    @Override
    public Laboratorio findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection(); PreparedStatement pst = con.prepareStatement(FIND_LABORATORIO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToLaboratorio(rs) : null;
            }
        }
    }

    @Override
    public List<Laboratorio> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection(); PreparedStatement pst = con.prepareStatement(FIND_ALL_LABORATORIO); ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, this::mapRowToLaboratorio);
        }
    }

    @Override
    public List<Laboratorio> findNonRiservato() throws SQLException {
        try (Connection con = DAOMan.getConnection(); PreparedStatement pst = con.prepareStatement(FIND_NON_RISERVATO); ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, this::mapRowToLaboratorio);
        }
    }

    @Override
    public List<LaboratorioConNumeroIscritti> findAllConNumeroIscritti() throws SQLException {
        try (Connection con = DAOMan.getConnection(); PreparedStatement pst = con.prepareStatement(FIND_ALL_CON_NUMERO_ISCRITTI); ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, row ->
                    new LaboratorioConNumeroIscritti(mapRowToLaboratorio(row), row.getInt("numeroIscritti")));
        }
    }

    @Override
    public int insert(Laboratorio laboratorio) throws SQLException {
        try (Connection con = DAOMan.getConnection(); PreparedStatement pst = con.prepareStatement(INSERT_LABORATORIO, Statement.RETURN_GENERATED_KEYS)) {
            DAOUtils.setNormalizedString(pst, 1, laboratorio.getDescrizione());
            pst.setBoolean(2, laboratorio.getRiservato());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting laboratorio failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    laboratorio.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting laboratorio failed, no ID obtained.");
                }
            }
            return laboratorio.getId();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection(); PreparedStatement pst = con.prepareStatement(DELETE_LABORATORIO)) {
            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting laboratorio failed, no rows affected.");
            }
        }
    }

    public Laboratorio mapRowToLaboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(rs.getInt("id"), rs.getString("descrizione"), rs.getBoolean("riservato"));
    }

}
