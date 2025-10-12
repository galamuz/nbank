package configs;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

public class Configs {
    private static final Configs INSTANCE = new Configs();
    private final Properties properties = new Properties();

    private Configs(){
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if( input==null) {
                throw new RuntimeException("config.properties not found in resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("fail to load config.properties");
        }
    }
    public static String getProperty(String key){
        return INSTANCE.properties.getProperty(key);
    }
}
