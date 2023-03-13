package Utility;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class ConfigProperties {
    private static final String propertiesFilePath = "C:/conf/RegistrazioneGrest/config.properties";
    private static final List<String> modifyiablePropertyNames = List.of("ISCRRAG", "ISCRAN", "ISCRTER");

    public static String getProperty(String propertyName) {
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

    public static void setProperty(String propertyName, String value) {
        isPropertyModifyiable(value);
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

    private static void isPropertyModifyiable(String value) {
        if (!modifyiablePropertyNames.contains(value)) {
            throw new IllegalArgumentException("This property is not modifyable");
        }
    }

}
