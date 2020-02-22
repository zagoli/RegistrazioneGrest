package DAOTest;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

public class AccompagnatoreTest {
    
    Accompagnatore a;
    
    public AccompagnatoreTest() {
        a = new Accompagnatore();
        a.setNome("Gianni");
        a.setCognome("Riveira");
    }
    
    @Test
    public void insertAccompagnatoreTest(){
        try {
            DAOMan.accompagnatoreDAO.insert(a);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(AccompagnatoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void updateAccompagnatoreTest(){
        try {
            a.setId(1);
            a.setNome("carlo");
            DAOMan.accompagnatoreDAO.update(a);
            a.setNome("Gianni");
            DAOMan.accompagnatoreDAO.update(a);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(AccompagnatoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void selectAccompagnatoreIdTest(){
        try {
            Accompagnatore a1 = DAOMan.accompagnatoreDAO.findById(1);
            assertEquals("Gianni",a1.getNome());
            assertEquals("Riveira",a1.getCognome());
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(AccompagnatoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void selectAllAccompagnatoreTest(){
        try {
            LinkedList<Accompagnatore> ls = (LinkedList<Accompagnatore>) DAOMan.accompagnatoreDAO.findAll();
            assertEquals(1, ls.size());
            assertEquals("Gianni",ls.getFirst().getNome());
            assertEquals("Riveira",ls.getFirst().getCognome());
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(AccompagnatoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void countAccompagnatoreTest(){
        try {
            int nscuole = DAOMan.accompagnatoreDAO.count();
            assertEquals(1,nscuole);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(AccompagnatoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void deleteAccompagnatoreTest(){
        try {
            DAOMan.accompagnatoreDAO.delete(1);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(AccompagnatoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testAccompagnatore() throws SQLException{
        this.insertAccompagnatoreTest();
        this.updateAccompagnatoreTest();
        this.selectAccompagnatoreIdTest();
        this.selectAllAccompagnatoreTest();
        this.countAccompagnatoreTest();
        this.deleteAccompagnatoreTest(); 
    }
    
}
