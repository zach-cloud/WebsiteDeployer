package launcher;

import deployer.WebsiteDeployer;

public class WebsiteDeployerLauncher {

    /**
     * No instantiation.
     */
    private WebsiteDeployerLauncher() {}

    /**
     * Runs the website deployer.
     *
     * @param args  Ignored
     */
    public static void main(String[] args) {
        new WebsiteDeployer().launch();
    }
}
