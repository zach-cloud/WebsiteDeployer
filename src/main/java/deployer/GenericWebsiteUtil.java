package deployer;

import utils.PropertiesReader;
import utils.S3Accessor;

/**
 * Base class that has the behavior to modify an S3 website.
 */
public abstract class GenericWebsiteUtil {

    protected S3Accessor s3Accessor;
    protected String websiteName;
    protected String folderName;

    /**
     * Constructor which uses the config.properties file to build this object.
     */
    public GenericWebsiteUtil() {
        PropertiesReader propertiesReader = new PropertiesReader();
        this.s3Accessor = new S3Accessor(propertiesReader.getProperty("s3AccessKey"), propertiesReader.getProperty("s3SecretKey"));
        this.websiteName = propertiesReader.getProperty("websiteName");
        this.folderName = propertiesReader.getProperty("rootFolder");
    }
}
