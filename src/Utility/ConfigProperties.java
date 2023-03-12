package Utility;

import java.io.*;
import java.util.Properties;

public class ConfigProperties {
    private final String propertiesFilePath = "C:/conf/RegistrazioneGrest/config.properties";

    public String getProperty(String propertyName) {
        try (InputStream inFile = new FileInputStream(propertiesFilePath)) {
            Properties properties = new Properties();
            properties.load(inFile);
            return properties.getProperty(propertyName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProperty(String propertyName, String value) {
        Properties properties = new Properties();
        try (InputStream inFile = new FileInputStream(propertiesFilePath)) {
            properties.load(inFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        properties.setProperty(propertyName, value);
        try (OutputStream outFile = new FileOutputStream(propertiesFilePath)) {
            properties.store(outFile, null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
