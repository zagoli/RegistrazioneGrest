package PropertiesTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesTest {

    public PropertiesTest() {
    }

    @Test
    public void output() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("src/conf/config.properties");

            // set the properties value
            prop.setProperty("ISCRRAG", "true");
            prop.setProperty("ISCRAN", "true");
            prop.setProperty("ISCRTER", "false");

            // save properties to project root folder
            prop.store(output, null);

        } catch (NullPointerException | IOException io) {
            Assert.fail(io.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (NullPointerException | IOException e) {
                    Assert.fail("fail chiusura output");
                }
            }

        }

    }

    @Test
    public void input() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src/conf/config.properties");

            // load a properties file
            prop.load(input);

            
            Assert.assertEquals("true", prop.getProperty("ISCRRAG"));
            Assert.assertEquals("true", prop.getProperty("ISCRAN"));
            Assert.assertEquals("false", prop.getProperty("ISCRTER"));

        } catch (NullPointerException | IOException ex) {
            Assert.fail(ex.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (NullPointerException | IOException e) {
                    Assert.fail("fail chiusura input stream");
                }
            }
        }

    }
}
