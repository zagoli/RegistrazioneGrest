package DAOManager;

import Domain.Laboratorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO {

    private static final String FIND_LABORATORIO_ID = "select * from Laboratorio where id = ?;";
    private static final String FIND_ALL_LABORATORIO = "select * from Laboratorio;";
    private static final String FIND_NON_RISERVATO = "select * from Laboratorio where riservato = false;";

    @Override
    public Laboratorio findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_LABORATORIO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToLaboratorio(rs) : null;
            }
        }
    }

    @Override
    public List<Laboratorio> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_LABORATORIO);
             ResultSet rs = pst.executeQuery()) {
            LinkedList<Laboratorio> ll = new LinkedList<>();
            while (rs.next()) {
                ll.add(this.mapRowToLaboratorio(rs));
            }
            return ll;
        }
    }

    @Override
    public List<Laboratorio> findNonRiservato() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_NON_RISERVATO);
             ResultSet rs = pst.executeQuery()) {
            LinkedList<Laboratorio> ll = new LinkedList<>();
            while (rs.next()) {
                ll.add(this.mapRowToLaboratorio(rs));
            }
            return ll;
        }
    }

    public Laboratorio mapRowToLaboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(
                rs.getInt("id"),
                rs.getString("descrizione"),
                rs.getBoolean("riservato")
        );
    }

}
