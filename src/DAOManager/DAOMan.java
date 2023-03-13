package DAOManager;

import Utility.ConfigProperties;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOMan {

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
    private static final String URL_ENRICO = ConfigProperties.getProperty("JDBC_URL_PRODUCTION_DATABASE");

    public static Connection getConnection() {
        Connection connection;
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setURL(URL_ENRICO);
            connection = ds.getConnection();
        } catch (SQLException e) {
            Logger.getLogger(DAOMan.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Connessione al database non riuscita");
        }
        return connection;
    }

}
