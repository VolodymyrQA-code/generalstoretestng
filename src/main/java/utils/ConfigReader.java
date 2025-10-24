package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не вдалося завантажити config.properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
