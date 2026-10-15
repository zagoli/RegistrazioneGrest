package DAOManager;

import DAOManager.Mappers.RegistratoDAOMapper;

import Domain.Registrato;
import Utility.DAOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegistratoDAOImpl implements RegistratoDAO {
    private static final String INSERT_REGISTRATO = "insert into Registrato (mail,password,nome,cognome,telefono,localita,via,civico,tipoUt) values (?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_REGISTRATO = "update Registrato set mail = ?,nome = ?,cognome = ?,telefono = ?,localita = ?,via = ?,civico = ?,tipoUt = ? where id = ?;";
    private static final String UPDATE_PASSWORD_REGISTRATO = "update Registrato set password = ? where id = ?;";
    private static final String DELETE_REGISTRATO = "delete from Registrato where id = ?;";
    private static final String FIND_REGISTRATO_ID = "select * from Registrato where id = ?;";
    private static final String FIND_ALL_REGISTRATO = "select * from Registrato;";
    private static final String FIND_SEGRETARI = "select * from Registrato where tipoUt > 0 and tipoUt < 3";
    private static final String FIND_AMMINISTRATORI = "select * from Registrato where tipoUt = 0";
    private static final String COUNT_USERS = "select count(*) from Registrato where tipoUt = 3;";
    private static final String FIND_REGISTRATO_MAIL = "select * from Registrato where mail = ?;";
    private static final String FIND_REGISTRATO_NOMINATIVO = "select * from Registrato where nome = ? and cognome = ?;";

    static void bindDatiPersonali(PreparedStatement pst, Registrato r, int start) throws SQLException {
        pst.setString(start, r.getNome());
        pst.setString(start + 1, r.getCognome());
        pst.setString(start + 2, r.getTelefono());
        pst.setString(start + 3, r.getLocalita());
        pst.setString(start + 4, r.getVia());
        pst.setString(start + 5, r.getCivico());
        pst.setInt(start + 6, r.getTipoUt());
    }

    @Override
    public void insert(Registrato r) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_REGISTRATO)) {
            pst.setString(1, r.getMail());
            pst.setString(2, r.getPassword());
            bindDatiPersonali(pst, r, 3);
            pst.executeUpdate();
        }
    }

    @Override
    public void update(Registrato r) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_REGISTRATO)) {
            pst.setString(1, r.getMail());
            bindDatiPersonali(pst, r, 2);
            pst.setInt(9, r.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(Integer idRegistrato) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_REGISTRATO)) {
            pst.setInt(1, idRegistrato);
            pst.executeUpdate();
        }
    }

    @Override
    public Registrato findById(int id) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? RegistratoDAOMapper.registrato(rs, "") : null;
            }
        }
    }

    @Override
    public List<Registrato> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_REGISTRATO);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, row -> RegistratoDAOMapper.registrato(row, ""));
        }
    }

    @Override
    public List<Registrato> findSegretari() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_SEGRETARI);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, row -> RegistratoDAOMapper.registrato(row, ""));
        }
    }

    @Override
    public List<Registrato> findAmministratori() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_AMMINISTRATORI);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, row -> RegistratoDAOMapper.registrato(row, ""));
        }
    }

    @Override
    public int countUsers() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(COUNT_USERS);
             ResultSet rs = pst.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public Registrato findByMail(String mail) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_MAIL)) {
            pst.setString(1, mail);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? RegistratoDAOMapper.registrato(rs, "") : null;
            }
        }
    }

    @Override
    public Registrato findByNominativo(String nome, String cognome) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_REGISTRATO_NOMINATIVO)) {
            pst.setString(1, nome);
            pst.setString(2, cognome);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? RegistratoDAOMapper.registrato(rs, "") : null;
            }
        }
    }

    @Override
    public void updatePassword(Registrato r) throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_PASSWORD_REGISTRATO)) {
            pst.setString(1, r.getPassword());
            pst.setInt(2, r.getId());
            pst.executeUpdate();
        }
    }

}
