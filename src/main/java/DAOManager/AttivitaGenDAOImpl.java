package DAOManager;

import Domain.AttivitaGen;
import Domain.CollaborazioneGen;
import Utility.DAOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AttivitaGenDAOImpl implements AttivitaGenDAO {

    private static final String FIND_ATTIVITAGEN_ID = "select * from Attivita_Gen where id = ?;";
    private static final String FIND_ALL_ATTIVITAGEN = "select * from Attivita_Gen;";
    private static final String FIND_ATTIVITAGEN_REGISTRATO_ID = "select * from Attivita_Gen ag join collabora c on (ag.id = c.Attivita_Gen_Id) where c.Registrato_Id = ?;";
    private static final String FIND_COLLABORAZIONE_REGISTRATO_ID = "select c.id as cid, ag.id as agid, ag.descrizione as descrizione, c.data as periodo from Attivita_Gen ag join collabora c on (ag.id = c.Attivita_Gen_Id) where c.Registrato_Id = ?;";

    @Override
    public AttivitaGen findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ATTIVITAGEN_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToAttivitaGen(rs) : null;
            }
        }
    }

    @Override
    public List<AttivitaGen> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_ATTIVITAGEN);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, this::mapRowToAttivitaGen);
        }
    }

    @Override
    public List<AttivitaGen> findByRegistratoId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ATTIVITAGEN_REGISTRATO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAOUtils.resultSetToList(rs, this::mapRowToAttivitaGen);
            }
        }
    }

    public List<CollaborazioneGen> findCollaborazioniByRegistratoId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_COLLABORAZIONE_REGISTRATO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAOUtils.resultSetToList(rs, this::mapRowToCollaborazioneGen);
            }
        }
    }

    public AttivitaGen mapRowToAttivitaGen(ResultSet rs) throws SQLException {
        return new AttivitaGen(
                rs.getInt("id"),
                rs.getString("descrizione")
        );
    }

    public CollaborazioneGen mapRowToCollaborazioneGen(ResultSet rs) throws SQLException {
        return new CollaborazioneGen(
                rs.getInt("cid"),
                new AttivitaGen(
                        rs.getInt("agid"),
                        rs.getString("descrizione")
                ),
                rs.getString("periodo")
        );
    }

}
