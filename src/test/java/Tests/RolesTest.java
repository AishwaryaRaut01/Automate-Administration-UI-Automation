package Tests;

import java.text.SimpleDateFormat;

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.RolesPage;
import Utilities.ExtentReportListener;
import Utilities.ConfigReader;

@Listeners(ExtentReportListener.class)
public class RolesTest extends BrowserSetup {

    private LoginPage loginPage;
    private RolesPage rolesPage;

    @BeforeClass
    public void setupRolesSuite() {
        ExtentReportListener.info("Initializing Browser and Roles Page");
        super.setup(); 

        loginPage = new LoginPage(driver);
        rolesPage = new RolesPage(driver); 

        ExtentReportListener.pass("Roles Page initialized successfully");
    }

    @Test(priority = 1)
    public void executeLoginSession() throws InterruptedException {
        ExtentReportListener.info("Loading login credentials");
        String user = ConfigReader.getProperty("username");
        String pass = ConfigReader.getProperty("userpw");

        loginPage.loginToApplication(user, pass);
        ExtentReportListener.pass("Login executed successfully");
    }

    @Test(priority = 2, dependsOnMethods = "executeLoginSession")
    public void createRolesAndVerify() throws InterruptedException {
        ExtentReportListener.info("Generating dynamic test data strings");

        // Generate dynamic names using your requested format
        String dynamicName = java.util.UUID.randomUUID()
                .toString()
                .replaceAll("[^a-zA-Z]", "")
                .substring(0, 4);
        String timestamp = String.valueOf(System.currentTimeMillis());

        String inputRoleName = "Role_" + dynamicName + "_" + timestamp;
        String inputDescription = "Desc_" + timestamp;
        String inputLongDesc = "Long_Desc_" + timestamp;

        // 1. Navigation Flow
        ExtentReportListener.info("Navigating to Administration -> Roles module");
        rolesPage.navigateToRoles();
        Thread.sleep(1500); 

        Assert.assertTrue(rolesPage.isAddRoleButtonEnabled(), "Add Role button is not enabled");
        rolesPage.clickAddRole();
        ExtentReportListener.pass("Add Role button is enabled and popup opened successfully");

        // 2. Select Organization Node
        Assert.assertTrue(rolesPage.isLaunchpadVisible(),"Launchpad is not visible");
		ExtentReportListener.pass("Launchpad is visible in organization hierarchy");

		rolesPage.selectLaunchpad();
        ExtentReportListener.pass("Launchpad selected successfully");

        // 3. Inject Form Data
        ExtentReportListener.info("Entering data into input fields");
        rolesPage.fillRoleDetails(inputRoleName, inputDescription, inputLongDesc);
        
        // GET ACTUAL VALUES FROM UI FIELDS BEFORE SUBMITTING USING YOUR EXACT GETTERS
        String actualTypedName = rolesPage.getName();
        String actualTypedDesc = rolesPage.getDescription();
        
//        String actualTypedName = "Role_aaaf";
//        String actualTypedDesc = "Role Description is for aaaf";
        
        ExtentReportListener.pass("Data fields captured from UI fields ");

        // 4. Save Processing Validation Sequence
        Assert.assertTrue(rolesPage.isSaveButtonEnabled(), "Save button is not enabled");
        rolesPage.saveAndConfirm();
        ExtentReportListener.pass("Save and Yes buttons clicked successfully");

        // 5. Landing Workspace Verification Check
        Thread.sleep(2000);
        Assert.assertTrue(rolesPage.verifyRolesListVisible(), "Roles List heading is not visible");
        ExtentReportListener.pass("Roles List heading is visible");

        // 6. Pagination Navigation Execution Block
        ExtentReportListener.info("Selecting page value 5 and navigating to the last page");
        rolesPage.selectPageValue();
        Thread.sleep(1000);
        ExtentReportListener.pass("Pagination applied successfully");

        // 7. Extract Grid Row Records for Data Validation Checks
        ExtentReportListener.info("Extracting values from the last row inside the table grid");
        String uiTableRoleName = rolesPage.getDisplayedRoleName();
        String uiTableDescription = rolesPage.getDisplayedDescription();
        String uiTableCreatedOn = rolesPage.getDisplayedCreatedOn();
        
        ExtentReportListener.info("UI Values -> Name: " + uiTableRoleName + " | Description: " + uiTableDescription + " | Created On: " + uiTableCreatedOn);

        // 8. Assertions: Cross-verify Form Inputs vs Table Outputs
        Assert.assertEquals(uiTableRoleName, actualTypedName, "Role name in table does not match input value");
        ExtentReportListener.pass("Grid Name verified with added Name i.e. : " + actualTypedName);
        
        Assert.assertEquals(uiTableDescription, actualTypedDesc, "Role description in table does not match input value");
        ExtentReportListener.pass("Grid Description verified with added Description: " + actualTypedDesc);
        
        String currentSystemDate = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH).format(new Date());
		Assert.assertTrue(uiTableCreatedOn.contains(currentSystemDate),
				"Created On date mismatch! Expected today's date: " + currentSystemDate);
		ExtentReportListener.pass("Created On verified with today's date: " + uiTableCreatedOn);
        
        ExtentReportListener.pass("Data validation complete. Table row records match inputs perfectly.");
        System.out.println("[PASS] Dynamic Roles and table validations completed successfully.");
    }

    @AfterClass
    public void closeRolesSessionBrowser() {
        ExtentReportListener.info("Closing browser session");
        super.teardown();
        ExtentReportListener.pass("Browser closed successfully");
    }
}
