package Tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.PermissionsPage;
import Utilities.ConfigReader;
import Utilities.ExtentReportListener;

public class PermissionsTest extends BrowserSetup {

    PermissionsPage permissionsPage;
    LoginPage loginPage;


    @BeforeClass
    public void setupPermissionsPage() {
    	 ExtentReportListener.info("Launching Browser and Initializing Page Objects");

         // FIX 1: Explicitly call base setup to spin up the driver session
         super.setup(); 

         // Bind the active driver context safely to your Page Factory elements
         permissionsPage = new PermissionsPage(driver);
         loginPage = new LoginPage(driver); 

         ExtentReportListener.pass("Browser launched and page objects initialized successfully");
    }

 // FIX 2: Priority 1 flow calling your exact LoginPage login mechanism
    @Test(priority = 1)
    public void executeLoginFlow() throws InterruptedException {
        ExtentReportListener.info("Loading configurations and initiating login flow");

        // Pull credentials directly from your ConfigReader layer
        String user = ConfigReader.getProperty("username");
        String pass = ConfigReader.getProperty("userpw");

        // Invoke your exact application login action sequence
        loginPage.loginToApplication(user, pass);

        ExtentReportListener.pass("Login sequence completed and landing page verified");
    }
    
    @Test(priority = 2)
    public void createPermission() throws InterruptedException {

    	String moduleValue = "My Dashboard";
    	String permissionValue = "Dashboard View";
        ExtentReportListener.info(
                "Navigating to Permissions module"
        );

        permissionsPage.moveToPermissions();

        ExtentReportListener.pass(
                "Permissions module opened successfully"
        );


        Assert.assertTrue(
                permissionsPage.isAddPermissionButtonEnabled(),
                "Add Permission button is not enabled"
        );

        ExtentReportListener.pass(
                "Add Permission button is enabled"
        );

        permissionsPage.clickAddPermission();

        ExtentReportListener.pass(
                "New Permissions popup opened successfully"
        );


        Assert.assertTrue(
                permissionsPage.isLaunchpadVisible(),
                "Launchpad is not visible"
        );

        ExtentReportListener.pass(
                "Launchpad is visible"
        );

        permissionsPage.selectLaunchpad();

        ExtentReportListener.pass(
                "Launchpad selected successfully"
        );


        ExtentReportListener.info("Selecting module from Dropdown");

        permissionsPage.selectModule(moduleValue);

        ExtentReportListener.pass("Module selected successfully: " + moduleValue);


        ExtentReportListener.info("Selecting permission from Dropdown");

        permissionsPage.selectPermission(permissionValue);

        ExtentReportListener.pass("Permission selected successfully: " + permissionValue);

        
        Assert.assertTrue(
                permissionsPage.isSaveButtonEnabled(),
                "Save button is not enabled"
        );

        ExtentReportListener.pass("Save button is enabled");

        
        permissionsPage.clickSaveAndConfirm();

        ExtentReportListener.pass(
                "Save and Yes buttons clicked successfully"
        );


        System.out.println(
                "[PASS] Adding Permission flow completed successfully"
        );
    }

    @AfterClass
    public void closeCompleteSuiteSession() {

        ExtentReportListener.info("Closing browser session");

        super.teardown();

        ExtentReportListener.pass("Browser closed successfully");
    }
}