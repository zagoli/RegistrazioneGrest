package DAOManager;

import java.sql.Connection;
import java.sql.DriverManager;
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
    public static final String URL_ENRICO = "jdbc:sqlserver://172.16.0.10:1433;database=GrestDb;user=AppGrest;password=pf2hjdcYiX5F+5LZ9Q1wlIKTSKCbYxhtrJ/LREPXp7A=;loginTimeout=30;encrypt=false";
    public static final String URL_AZURE = "jdbc:sqlserver://serverdbgrest.database.windows.net:1433;database=GrestDb;user=AppGrest@serverdbgrest;password={afuio(573ehop89*°#,cfe9x)(/G()};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL_ENRICO);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            Logger.getLogger(DAOMan.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Connessione al database non riuscita");
        }
        return connection;
    }

}
