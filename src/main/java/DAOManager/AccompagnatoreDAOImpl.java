package DAOManager;

import Domain.Accompagnatore;

import java.sql.*;
import java.util.List;

public class AccompagnatoreDAOImpl implements AccompagnatoreDAO {

    private static final String INSERT_ACCOMPAGNATORE = "insert into Accompagnatore (nome,cognome,idRegistrato) values (?,?,?);";
    private static final String UPDATE_ACCOMPAGNATORE = "update Accompagnatore set nome = ?, cognome = ?, idRegistrato = ? where id = ?;";
    private static final String DELETE_ACCOMPAGNATORE = "delete from Accompagnatore where id = ?;";
    private static final String FIND_ACCOMPAGNATORE_ID = "select a.id as aid, a.nome as anome, a.cognome as acognome, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Accompagnatore a join Registrato re on (a.idRegistrato=re.id) where a.id = ?;";
    private static final String FIND_ACCOMPAGNATORE_REGISTRATO_ID = "select a.id as aid, a.nome as anome, a.cognome as acognome, re.id as reid, re.mail as remail, re.password as repassword, re.nome as renome, re.cognome as recognome, re.telefono as retelefono, re.localita as relocalita, re.via as revia, re.civico as recivico, re.tipoUt as retipoUt from Accompagnatore a join Registrato re on (a.idRegistrato=re.id) where re.id = ?";

    @Override
    public void insert(Accompagnatore a) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_ACCOMPAGNATORE, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, a.getNome());
            pst.setString(2, a.getCognome());
            pst.setInt(3, a.getRegistrato().getId());
            pst.executeUpdate();
            DAORows.assignGeneratedId(pst, a::setId);
        }
    }

    @Override
    public void update(Accompagnatore a) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_ACCOMPAGNATORE)) {
            pst.setString(1, a.getNome());
            pst.setString(2, a.getCognome());
            pst.setInt(3, a.getRegistrato().getId());
            pst.setInt(4, a.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(Integer idAccompagnatore) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_ACCOMPAGNATORE)) {
            pst.setInt(1, idAccompagnatore);
            pst.executeUpdate();
        }
    }

    @Override
    public Accompagnatore findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ACCOMPAGNATORE_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? this.mapRowToAccompagnatore(rs) : null;
            }
        }
    }

    @Override
    public List<Accompagnatore> findByRegistratoId(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ACCOMPAGNATORE_REGISTRATO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return DAORows.readAll(rs, this::mapRowToAccompagnatore);
            }
        }
    }

    public Accompagnatore mapRowToAccompagnatore(ResultSet rs) throws SQLException {
        return new Accompagnatore(
                rs.getInt("aid"),
                rs.getString("anome"),
                rs.getString("acognome"),
                IscrittoDAOMapper.registrato(rs, "re")
        );
    }

}
