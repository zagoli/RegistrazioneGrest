package DAOManager;

import Domain.Calendario;
import Utility.DAOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CalendarioDAOImpl implements CalendarioDAO {
    private static final String FIND_CAL_ID = "select * from Calendario where idSettimana = ?;";
    private static final String FIND_ALL_CAL = "select * from Calendario order by daQuando;";
    private static final String FIND_CAL_RAGAZZO = "select * from Calendario c join presenzaRag p on (c.idSettimana = p.Calendario_idSettimana) where p.Ragazzo_id = ? order by daQuando;";
    private static final String FIND_CAL_ANIMATORE = "select * from Calendario c join presenzaAn p on (c.idSettimana = p.Calendario_idSettimana) where p.Animatore_id = ? order by daQuando;";
    private static final String FIND_CAL_TERZAMEDIA = "select * from Calendario c join presenzaTer p on (c.idSettimana = p.Calendario_idSettimana) where p.Terzamedia_id = ? order by daQuando;";


    @Override
    public Calendario findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_CAL_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToCalendario(rs) : null;
            }
        }
    }

    @Override
    public List<Calendario> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_CAL);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, this::mapRowToCalendario);
        }
    }

    @Override
    public List<Calendario> findByRagazzoId(int id) throws SQLException {
        return findByIscrittoId(FIND_CAL_RAGAZZO, id);
    }

    @Override
    public List<Calendario> findByTerzamediaId(int id) throws SQLException {
        return findByIscrittoId(FIND_CAL_TERZAMEDIA, id);
    }

    @Override
    public List<Calendario> findByAnimatoreId(int id) throws SQLException {
        return findByIscrittoId(FIND_CAL_ANIMATORE, id);
    }

    private List<Calendario> findByIscrittoId(String query, int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAOUtils.resultSetToList(rs, this::mapRowToCalendario);
            }
        }
    }

    public Calendario mapRowToCalendario(ResultSet rs) throws SQLException {
        return new Calendario(
                rs.getInt("idSettimana"),
                rs.getDate("daQuando"),
                rs.getDate("aQuando")
        );
    }
}
