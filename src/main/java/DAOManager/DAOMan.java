package DAOManager;

import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOMan {

    private static final HikariDataSource DS;

    static {
        HikariConfig config = new HikariConfig();

        try {
            config.setJdbcUrl(ConfigProperties.getProperty("JDBC_URL_PRODUCTION_DATABASE"));
            config.setUsername(ConfigProperties.getProperty("DATABASE_USER"));
            config.setPassword(ConfigProperties.getProperty("DATABASE_PASSWORD"));
            config.setDriverClassName("org.postgresql.Driver");
        } catch (ConfigPropertyException | IOException e) {
            throw new RuntimeException(e);
        }

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        DS = new HikariDataSource(config);
    }

    public static final AccompagnatoreDAO accompagnatoreDAO = new AccompagnatoreDAOImpl();
    public static final AnimatoreDAO animatoreDAO = new AnimatoreDAOImpl();
    public static final AttivitaGenDAO attivitaGenDAO = new AttivitaGenDAOImpl();
    public static final CalendarioDAO calendarioDAO = new CalendarioDAOImpl();
    public static final CircoloDAO circoloDAO = new CircoloDAOImpl();
    public static final ContattoUrgenzeDAO contattoUrgenzeDAO = new ContattoUrgenzeDAOImpl();
    public static final LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl();
    public static final PagamentoDAO pagamentoDAO = new PagamentoDAOImpl();
    public static final ParrocchiaDAO parrocchiaDAO = new ParrocchiaDAOImpl();
    public static final RagazzoDAO ragazzoDAO = new RagazzoDAOImpl();
    public static final RegistratoDAO registratoDAO = new RegistratoDAOImpl();
    public static final RelCollaboraDAO relCollaboraDAO = new RelCollaboraDAOImpl();
    public static final RelPresenzaAnDAO relPresenzaAnDAO = new RelPresenzaAnDAOImpl();
    public static final RelPresenzaRagDAO relPresenzaRagDAO = new RelPresenzaRagDAOImpl();
    public static final ScuolaDAO scuolaDAO = new ScuolaDAOImpl();
    public static final TerzamediaDAO terzamediaDAO = new TerzamediaDAOImpl();
    public static final RelPresenzaTerDAO relPresenzaTerDAO = new RelPresenzaTerDAOImpl();
    public static final PagamentoTerzamediaDAO pagamentoTerzamediaDAO = new PagamentoTerzamediaDAOImpl();
    public static final CodiceSbloccoIscrizioneDAO codiceSbloccoIscrizioneDAO = new CodiceSbloccoIscrizioneDAOImpl();
    public static final SquadraDAO squadraDAO = new SquadraDAOImpl();

    public static Connection getConnection() throws SQLException {
        return DS.getConnection();
    }

}
