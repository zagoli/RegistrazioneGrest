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
    public static SquadraDAO squadraDAO = new SquadraDAOImpl();
    
    /* connessione per MySql
    private static Connection initConnMysql() {
        Connection conn = null;
        // la stringa di connessione deve essere PERSONALIZZATA
        String url = "jdbc:mysql://localhost/sakila";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, "nomeutente", "password");
        } catch (NullPointerException | SQLException | ClassNotFoundException ex) {
        }
        return conn;
    
    }
    */
    
    private static Connection initConnSqlServer() {
        //PRIMA DI DARE AD ENRICO: METTERE QUA URL ENRICO E NEL DISPATCHER COME GESTIONE ERRORI RETHROW_HANDLER
        Connection conn = null;
        final String urlEnrico =  "jdbc:sqlserver://localhost:1433;database=GrestDb;user=AppGrest;password=pf2hjdcYiX5F+5LZ9Q1wlIKTSKCbYxhtrJ/LREPXp7A=;loginTimeout=30;";
        final String urlAzure = "jdbc:sqlserver://serverdbgrest.database.windows.net:1433;database=GrestDb;user=AppGrest@serverdbgrest;password={afuio(573ehop89*°#,cfe9x)(/G()};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(urlAzure);
        } catch (NullPointerException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOMan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static Connection getConnection() {
        return initConnSqlServer();
    }

}
