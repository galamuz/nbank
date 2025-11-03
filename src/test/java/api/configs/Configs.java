package api.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configs {
    private static final Configs INSTANCE = new Configs();
    private final Properties properties = new Properties();

    private Configs() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("fail to load config.properties");
        }
    }

    public static String getProperty(String key) {
        //system properties
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            return systemValue;
        }
        //environment properties
        // admin.username -> ADMIN_USERNAME
        String keyToUpperCase = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(keyToUpperCase);
        if (envValue != null) {
            return envValue;
        }
        //config.properties

        return INSTANCE.properties.getProperty(key);
    }

}
