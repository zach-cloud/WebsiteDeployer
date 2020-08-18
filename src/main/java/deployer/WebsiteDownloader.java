package deployer;

import org.apache.commons.io.FileUtils;
import utils.ConsoleUtils;

import java.io.File;
import java.io.IOException;

/**
 * Class to download a website from S3 to disk.
 */
public class WebsiteDownloader extends GenericWebsiteUtil {

    /**
     * Launches the website downloader.
     * Asks the user to confirm download, and then clears all existing
     * files and downloads the remove S3 files from this website to disk.
     */
    public void launch() {
        try {
            if (ConsoleUtils.getConfirmation("This will replace " + folderName + " with the files from " + websiteName + ". Is that OK?")) {
                File desiredDirectory = new File(folderName);
                if (desiredDirectory.exists()) {
                    if(desiredDirectory.isDirectory()) {
                        FileUtils.deleteDirectory(desiredDirectory);
                    } else {
                        desiredDirectory.delete();
                    }
                }

                s3Accessor.download(websiteName, folderName);

                System.out.println("Done downloading!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
