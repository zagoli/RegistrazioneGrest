package DAOManager.Mappers;

import Domain.Registrato;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RegistratoDAOMapper {

    private RegistratoDAOMapper() {
    }

    public static Registrato registrato(ResultSet rs, String prefix) throws SQLException {
        return new Registrato(
                rs.getInt(prefix + "id"),
                rs.getString(prefix + "mail"),
                rs.getString(prefix + "password"),
                rs.getString(prefix + "nome"),
                rs.getString(prefix + "cognome"),
                rs.getString(prefix + "telefono"),
                rs.getString(prefix + "localita"),
                rs.getString(prefix + "via"),
                rs.getString(prefix + "civico"),
                rs.getInt(prefix + "tipoUt"));
    }
}
