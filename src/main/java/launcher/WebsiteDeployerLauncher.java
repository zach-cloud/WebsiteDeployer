package launcher;

import deployer.WebsiteDeployer;

public class WebsiteDeployerLauncher {

    private WebsiteDeployerLauncher() {}

    public static void main(String[] args) {
        new WebsiteDeployer().launch();
    }
}
