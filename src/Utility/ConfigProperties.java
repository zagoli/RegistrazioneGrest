package Utility;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConfigProperties {
    private static final String propertiesFilePath = "C:/conf/RegistrazioneGrest/config.properties";
    private static final List<String> modifyiablePropertyNames = List.of("ISCRRAG", "ISCRAN", "ISCRTER");
    private static Map<String, String> cache = new HashMap<>();

    public static String getProperty(String propertyName) {
        if (cache.containsKey(propertyName)) {
            return cache.get(propertyName);
        }
        try (InputStream inFile = new FileInputStream(propertiesFilePath)) {
            Properties properties = new Properties();
            properties.load(inFile);
            String propertyValue = properties.getProperty(propertyName);
            if (isPropertyImmutable(propertyName)) {
                cache.put(propertyName, propertyValue);
            }
            return propertyValue;
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setProperty(String propertyName, String value) {
        if (isPropertyImmutable(propertyName)) {
            throw new IllegalArgumentException("This property is immutable");
        }
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

    private static boolean isPropertyImmutable(String value) {
        return !modifyiablePropertyNames.contains(value);
    }

}
