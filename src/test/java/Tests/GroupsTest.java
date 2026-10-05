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
import Pages.GroupsPage;
import Utilities.ExtentReportListener;
import Utilities.ConfigReader;

@Listeners(ExtentReportListener.class)
public class GroupsTest extends BrowserSetup {

    private LoginPage loginPage;
    private GroupsPage groupsPage;

    @BeforeClass
    public void setupGroupsSuite() {
        ExtentReportListener.info("Initializing Browser and Groups Page");
        super.setup(); 

        loginPage = new LoginPage(driver);
        groupsPage = new GroupsPage(driver); 

        ExtentReportListener.pass("Groups Page initialized successfully");
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
    public void createDynamicGroupFlow() throws InterruptedException {
        ExtentReportListener.info("Generating dynamic test data strings");

        String dynamicName = java.util.UUID.randomUUID()
                .toString()
                .replaceAll("[^a-zA-Z]", "")
                .substring(0, 4);

        String inputGroupName = "Group_" + dynamicName;
        String inputDescription = "Group Description for " + dynamicName + " is added";

        // 1. Navigation Flow
        ExtentReportListener.info("Navigating to Administration -> Groups module");
        groupsPage.navigateToGroups();
        Thread.sleep(1500); 

        // Button Assertion 1: Add Group Button
        Assert.assertTrue(groupsPage.isAddGroupButtonEnabled(), "Add Group button is not enabled");
        ExtentReportListener.pass("Add Group button is enabled");
        
        groupsPage.clickAddGroup();
        ExtentReportListener.pass("Add Group popup opened successfully");

        // 2. Inject Form Data
        ExtentReportListener.info("Entering data into input fields");
        groupsPage.fillGroupDetails(inputGroupName, inputDescription);
        
        String actualTypedName = groupsPage.getUsername();
        String actualTypedDesc = groupsPage.getDescription();
        
//        String actualTypedName ="Group_aecd";
//        String actualTypedDesc = "Group Description for aecd is added";
       
        ExtentReportListener.pass("Data fields captured from UI fields: " + actualTypedName + " and " + actualTypedDesc);
        
        String roleDropDown = "User";
        String memberDropDown = "user2";
        // 3. Dropdown Handling
        ExtentReportListener.info("Selecting Role from Dropdown: " + roleDropDown);
        groupsPage.selectRole(roleDropDown);
        
        ExtentReportListener.info("Selecting Member from Dropdown: " + memberDropDown);
        groupsPage.selectMember(memberDropDown);

        // Button Assertion 2: Add to List Button
        Assert.assertTrue(groupsPage.isAddToListButtonEnabled(), "Add to List button is not enabled");
        ExtentReportListener.pass("Add to List button is enabled");
        
        groupsPage.clickAddToList();
        ExtentReportListener.pass("Successfully clicked on Add to List Button");

        // Button Assertion 3: Save Button
        Assert.assertTrue(groupsPage.isSaveButtonEnabled(), "Save button is not enabled");
        ExtentReportListener.pass("Save button is enabled");

        // 4. Save and Confirm
        groupsPage.saveAndConfirm();
        ExtentReportListener.pass("Save and Yes buttons clicked successfully");

        // 5. Verify Workspace Landing Page
        Thread.sleep(2000);
        Assert.assertTrue(groupsPage.verifyGroupsListVisible(), "Groups List heading is not visible");
        ExtentReportListener.pass("Groups List heading is visible");

        // ================= NEW ADDED SEGMENT =================
        // 6. Pagination Navigation Execution Block
//        ExtentReportListener.info("Selecting page value 5 and navigating to the last page");
//        groupsPage.selectPageValue();
//        
//        ExtentReportListener.pass("Pagination applied successfully");
//        
//        Thread.sleep(1000);

        // 7. Extract Grid Row Records for Data Validation Checks
        ExtentReportListener.info("Extracting values from the last row inside the table grid");
        String uiTableGroupName = groupsPage.getDisplayedGroupName();
        String uiTableDescription = groupsPage.getDisplayedDescription();
        String uiTableCreatedOn = groupsPage.getDisplayedCreatedOn();
        
        ExtentReportListener.info("UI Values -> Name: " + uiTableGroupName + " | Description: " + uiTableDescription + " | Created On: " + uiTableCreatedOn);

        // 8. Assertions: Cross-verify Form Inputs vs Table Outputs
        Assert.assertEquals(uiTableGroupName, actualTypedName, "Group name in table does not match input value");
        ExtentReportListener.pass("Grid Name verified with added Name i.e. : " + actualTypedName);

        Assert.assertEquals(uiTableDescription, actualTypedDesc, "Group description in table does not match input value");        
        ExtentReportListener.pass("Grid Description verified with added Description: " + actualTypedDesc);
        
        String currentSystemDate = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH).format(new Date());
		Assert.assertTrue(uiTableCreatedOn.contains(currentSystemDate),
				"Created On date mismatch! Expected today's date: " + currentSystemDate);
		ExtentReportListener.pass("Created On verified with today's date: " + uiTableCreatedOn);
        
        ExtentReportListener.pass("Data validation complete. Table row records match inputs perfectly.");
    }

    @AfterClass
    public void closeGroupsSuiteSession() {
        ExtentReportListener.info("Closing browser session");
        super.teardown();
        ExtentReportListener.pass("Browser closed successfully");
    }
}




