package deployer;

import utils.ConsoleUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.FileUtils.recursiveFolderDiscovery;

/**
 * Class to deploy a website from disk to S3
 */
public class WebsiteDeployer extends GenericWebsiteUtil {

    /**
     * Launches the Website Deployer
     * Prompts the user to confirm deployment, then deletes all
     * remote files and uploads files from disk.
     */
    public void launch() {
        if(ConsoleUtils.getConfirmation("This will replace " + websiteName + " with the files from " + folderName + ". Is that OK?")) {
            List<File> files = new ArrayList<>();
            recursiveFolderDiscovery(new File(folderName), new ArrayList<>(), files);
            System.out.println("Deleting all files in bucket: " + websiteName);
            s3Accessor.removeFiles(websiteName);
            System.out.println("Uploading files from " + folderName + " to " + websiteName);
            for(File file : files) {
                if(!file.getAbsolutePath().contains("\\.")) {
                    s3Accessor.writeFile(websiteName, file.getAbsolutePath().replace(folderName, "").replace("\\", "/"), file);
                }
            }
            System.out.println("Deployment finished. Exiting.");
        } else {
            System.out.println("Exiting.");
        }
    }
}
