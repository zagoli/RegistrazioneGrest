package DAOTest;

import DAOManager.DAOMan;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GeneralDAOTest {
    
    public GeneralDAOTest() {
    }
    
    @Test
    public void testAllDao() throws SQLException{
        this.testConn();
        ScuolaTest st = new ScuolaTest();
        st.testScuola();
        AccompagnatoreTest at = new AccompagnatoreTest();
        at.testAccompagnatore();
    }
    
    @Test
    public void testConn() {
        Connection con = DAOMan.getConnection();
        try {
            Boolean b = con.isValid(1);
            assertEquals(true, b);
            con.close();
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(GeneralDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
