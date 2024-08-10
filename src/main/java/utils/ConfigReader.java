package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static String readProperty(String property) {
        return switch (property) {
            default -> new ConfigReader().getPropertyFromFile(property);
        };
    }

        private String getPropertyFromFile(String property) {
            Properties prop;
            FileInputStream fileInput;

            try {
                fileInput = new FileInputStream("config.properties");
                prop = new Properties();
                prop.load(fileInput);

                if (prop.getProperty(property) == null){
                    try {
                        fileInput = new FileInputStream("config-local.properties");
                        prop = new Properties();
                        prop.load(fileInput);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return prop.getProperty(property);
        }
}
