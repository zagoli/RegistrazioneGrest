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

    public static String getProperty(String propertyName) throws IOException, ConfigPropertyException {
        if (cache.containsKey(propertyName)) {
            return cache.get(propertyName);
        }
        InputStream inFile = new FileInputStream(propertiesFilePath);
        Properties properties = new Properties();
        properties.load(inFile);
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null) {
            throw new ConfigPropertyException("Impossibile trovare la proprietà di configurazione " + propertyName + ".");
        }
        if (isPropertyImmutable(propertyName)) {
            cache.put(propertyName, propertyValue);
        }
        return propertyValue;
    }


    public static void setProperty(String propertyName, String value) throws IOException, ConfigPropertyException {
        if (isPropertyImmutable(propertyName)) {
            throw new ConfigPropertyException("This property is immutable");
        }
        Properties properties = new Properties();
        InputStream inFile = new FileInputStream(propertiesFilePath);
        properties.load(inFile);
        properties.setProperty(propertyName, value);
        OutputStream outFile = new FileOutputStream(propertiesFilePath);
        properties.store(outFile, null);
    }

    private static boolean isPropertyImmutable(String value) {
        return !modifyiablePropertyNames.contains(value);
    }

}