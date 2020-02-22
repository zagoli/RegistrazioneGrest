package JBCryptTest;

import Utility.BCrypt;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;


public class JBCryptTest {
    
    public JBCryptTest() {
    }
    
    @Test
    public void testPassword(){
        String plainpswd = "";
        String salt = BCrypt.gensalt();
        String encryptedpswd = BCrypt.hashpw(plainpswd, salt);
        System.out.println(encryptedpswd);
        Boolean check = BCrypt.checkpw(plainpswd, encryptedpswd);
        assertEquals(check, Boolean.TRUE);
    }
    
    
    @Test
    public void testBinaryPassword(){
        String plainpswd = "Barbapapa123è+òàù!!£$%&/()=?/\"\\";
        String salt = BCrypt.gensalt();
        String encryptedpswd = BCrypt.hashpw(plainpswd, salt);
        byte[] encryptedarray = encryptedpswd.getBytes();
        String encryptedarrayString = Arrays.toString(encryptedarray);
        assertEquals(encryptedpswd, encryptedarrayString);
        Boolean check = BCrypt.checkpw(plainpswd, encryptedarrayString);
        assertEquals(check, Boolean.TRUE);
    }

    
}
