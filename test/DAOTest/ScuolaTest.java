package DAOTest;

import DAOManager.DAOMan;
import Domain.Scuola;
import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ScuolaTest {
    
    Scuola s;
    
    public ScuolaTest() {
        s = new Scuola();
        s.setGrado("secondaria");
        s.setDescrizione("Istituto Primario Francesco Baracca");
    }
    
    @Test
    public void insertScuolaTest(){
        try {
            DAOMan.scuolaDAO.insert(s);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ScuolaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void updateScuolaTest(){
        try {
            s.setId(1);
            s.setGrado("primaria");
            DAOMan.scuolaDAO.update(s);
            s.setGrado("secondaria");
            DAOMan.scuolaDAO.update(s);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ScuolaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void selectScuolaIdTest(){
        try {
            Scuola s1 = DAOMan.scuolaDAO.findById(1);
            assertEquals("Istituto Primario Francesco Baracca",s1.getDescrizione());
            assertEquals("secondaria",s1.getGrado());
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ScuolaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void selectAllScuolaTest(){
        try {
            LinkedList<Scuola> ls = (LinkedList<Scuola>) DAOMan.scuolaDAO.findAll();
            assertEquals(1, ls.size());
            assertEquals("Istituto Primario Francesco Baracca",ls.getFirst().getDescrizione());
            assertEquals("secondaria",ls.getFirst().getGrado());
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ScuolaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void countScuolaTest(){
        try {
            int nscuole = DAOMan.scuolaDAO.count();
            assertEquals(1,nscuole);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ScuolaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void deleteScuolaTest(){
        try {
            DAOMan.scuolaDAO.delete(1);
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(ScuolaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testScuola() throws SQLException{
        this.insertScuolaTest();
        this.updateScuolaTest();
        this.selectScuolaIdTest();
        this.selectAllScuolaTest();
        this.countScuolaTest();
        this.deleteScuolaTest();
    }
}
