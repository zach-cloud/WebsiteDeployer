package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class to read from the properties resource
 */
public class PropertiesReader {

    private Properties prop;
    private Map<String,String> cache;

    /**
     * Requires constructor. Creates a PropertiesReader linked to the config.properties class.
     */
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

    /**
     * Determines if a property exists in the properties file.
     *
     * @param key   Name of property
     * @return      True if exists, false if not.
     */
    public boolean propertyExists(String key) {
        return prop.containsKey(key);
    }

    /**
     * Gets a property from the props file.
     *
     * @param key   Name of property
     * @return      Property, if it exists.
     */
    public String getProperty(String key) {
        if(cache.containsKey(key)) {
            return cache.get(key);
        }
        String value = prop.getProperty(key);
        cache.put(key, value);
        return value;
    }
}
