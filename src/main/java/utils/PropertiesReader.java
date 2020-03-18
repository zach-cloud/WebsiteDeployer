package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

    private Properties prop;
    private Map<String,String> cache;

    public PropertiesReader() {
        cache = new HashMap<>();
        prop = new Properties();
        try {
            prop.load(PropertiesReader.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean propertyExists(String key) {
        return prop.containsKey(key);
    }

    public String getProperty(String key) {
        if(cache.containsKey(key)) {
            return cache.get(key);
        }
        String value = prop.getProperty(key);
        cache.put(key, value);
        return value;
    }

    public void save(String key, String value) {
        cache.put(key, value);
        prop.put(key, value);
    }

}
