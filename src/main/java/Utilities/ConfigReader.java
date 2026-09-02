package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(file);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("Configuration file load failure.", e);
        }
    }

    // A single, reusable method to read ANY key from your properties file
    public static String getProperty(String key) {
    	String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key error: '" + key + "' is missing from config.properties!");
        }
        return value;
    }
}