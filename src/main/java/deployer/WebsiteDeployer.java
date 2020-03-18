package deployer;

import utils.PropertiesReader;
import utils.S3Accessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebsiteDeployer {

    private S3Accessor s3Accessor;
    private String websiteName;
    private String folderName;

    /**
     * Creates a Website Deployer using the config.properties file.
     */
    public WebsiteDeployer() {
        PropertiesReader propertiesReader = new PropertiesReader();
        this.s3Accessor = new S3Accessor(propertiesReader.getProperty("s3AccessKey"), propertiesReader.getProperty("s3SecretKey"));
        this.websiteName = propertiesReader.getProperty("websiteName");
        this.folderName = propertiesReader.getProperty("rootFolder");
    }

    /**
     * Runs the Website Deployer
     */
    public void launch() {
        Scanner in = new Scanner(System.in);
        System.out.print("This will replace " + websiteName + " with the files from " + folderName + ". Is that OK? (y/n): ");
        String confirmation = in.nextLine();
        if(confirmation.equalsIgnoreCase("y")){
            List<File> files = new ArrayList<>();
            recursiveFolderDiscovery(new File(folderName), new ArrayList<File>(), files);
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

    /**
     * Finds all files under a folder recursively.
     *
     * @param start         Folder to start at
     * @param foldersList   Empty folder list to store results in
     * @param filesList     Empty files ist to store results in
     */
    private static void recursiveFolderDiscovery(File start, List<File> foldersList, List<File> filesList) {
        if(start.isDirectory()) {
            foldersList.add(start);
            File[] files = start.listFiles();
            if(files != null) {
                for (File file : files) {
                    recursiveFolderDiscovery(file, foldersList, filesList);
                }
            }
        } else {
            filesList.add(start);
        }

    }
}
