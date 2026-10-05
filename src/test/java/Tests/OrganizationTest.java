package Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.OrgApprovalDetailsPage;
import Pages.OrganizationPage;
//import Pages.TransactionSummaryPage;
import Utilities.ConfigReader;
import Utilities.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class OrganizationTest extends BrowserSetup {
	
	LoginPage loginPage;
	OrganizationPage orgPage;
	OrgApprovalDetailsPage approvalPage;
	
//	@BeforeClass
//	public void setupOrganizationPages() {
//		//  We reuse the open driver window from the static memory pool
//		ExtentReportListener.info("Initializing Organization Page");
//
//		orgPage = new OrganizationPage(driver);
//	    approvalPage = new OrgApprovalDetailsPage(driver);
//	    
//	    ExtentReportListener.pass("Organization Pages initialized");
//	}

	   @BeforeClass
	    public void setupSummarySuite() {
	        ExtentReportListener.info("Initializing Browser and Organization Page");
	        super.setup(); 

	        loginPage = new LoginPage(driver);
	        orgPage = new OrganizationPage(driver);
		    approvalPage = new OrgApprovalDetailsPage(driver);

	        ExtentReportListener.pass("Organization Pages initialized successfully");
	    }
	   
	   @Test(priority = 1)
	    public void executeLoginSession() throws InterruptedException {
	        ExtentReportListener.info("Loading system credentials");
	        String user = ConfigReader.getProperty("username");
	        String pass = ConfigReader.getProperty("userpw");

	        loginPage.loginToApplication(user, pass);
	        ExtentReportListener.pass("Authentication session completed successfully");
	    }
	   
	@Test(priority=2)
	public void verifyOrganizationModuleText() throws InterruptedException {
		
		ExtentReportListener.info("Navigating to Organization Module");
		
		// Continues directly without closing the window!
		orgPage.movetoOrganization();

		// Run text validation assertions
		ExtentReportListener.info("Verifying Organization Management title");
		
		String actualTabLabel = orgPage.getOrgManagementText();
		Assert.assertEquals(actualTabLabel, "Organization Management", "Tab text mismatch!");
		
		ExtentReportListener.pass("Organization Management title verified");

	    ExtentReportListener.info("Verifying heading in Organization Management contains text");

	    // Retrieves the text string from the page element
	 	String headingText = orgPage.getLaunchpadHeadingText();
	 		
	 	// Asserts that the retrieved string is not blank/empty
	 	Assert.assertFalse(headingText.trim().isEmpty(), "Launchpad heading element is empty or blank!");

	 	ExtentReportListener.pass("Heading text presence verified successfully");


		 ExtentReportListener.info("Opening Hierarchy section");
		
		 Thread.sleep(1500);
		orgPage.clickHierarchyDashButton();
		
		ExtentReportListener.pass("Hierarchy section opened");
	}
		
	@Test(priority = 3)
	public void hierarchyStructValidAdd() throws InterruptedException {
		// 1. Validate existing hierarchy first
		List<String> actualNames = orgPage.getAllOrganizationNames();
		System.out.println("Organization Names: " + actualNames);

		for (String name : actualNames) {
			ExtentReportListener.info("Organization Name: " + name);
		}

		Assert.assertTrue(actualNames.contains("Root"), "Root is missing");
		Assert.assertTrue(actualNames.contains("new root"), "new root is missing");
		Assert.assertTrue(actualNames.contains("New Sub Root"), "New Sub Root is missing");
		Assert.assertTrue(actualNames.contains("sbi"), "sbi is missing");
		Assert.assertTrue(actualNames.contains("hdfc"), "hdfc is missing");
		Assert.assertTrue(actualNames.contains("Axis"), "Axis is missing");

		// DYNAMIC VALIDATION FOR PREVIOUS TEST RUNS (OPTION 1)
				// ====================================================================
				boolean pastBankEntryExists  = false;
				for (String name : actualNames) {
					if (name.startsWith("bank_")) {
						ExtentReportListener.info("Found a past dynamic entry in the list: " + name);
						pastBankEntryExists = true;
					}
				}
				// Passes silently if database is clean, logs info if past data exists
				if (pastBankEntryExists) {
					ExtentReportListener.pass("Previously created dynamic organization names validated successfully.");
				}
		
		ExtentReportListener.pass("Existing organization names verified successfully.");
		
		String dynamicOrgName = "bank_" + java.util.UUID.randomUUID().toString().replaceAll("[^a-zA-Z]", "").substring(0, 4); // e.g., "BANK_axbcf"
		
		ExtentReportListener.info("Adding new Organization Unit: " + dynamicOrgName);
		orgPage.clickAddMore();
		Thread.sleep(2000);
		
		// Pass the dynamically generated string down to the input handler
		orgPage.enterOrganizationName(dynamicOrgName);
		orgPage.clickSave();

		ExtentReportListener.pass("Organization Unit added successfully");
		Thread.sleep(2000);
		
		// ADD THIS LINE TO FORCE THE UI TO REFRESH THE CORES NAMES MATRIX
		orgPage.clickHierarchyDashButton(); // Re-navigates into the module to refresh the list
		Thread.sleep(1500);
		
		// 3. Re-fetch the updated names list from the view hierarchy to validate the new entry
		List<String> updatedNames = orgPage.getAllOrganizationNames();
		Assert.assertTrue(updatedNames.contains(dynamicOrgName), "Dynamic organization '" + dynamicOrgName + "' was not found in the hierarchy!");
		
		ExtentReportListener.pass("Presence of newly created dynamic organization verified in structural layout hierarchy.");
		Thread.sleep(2000);    
	}
		
		@Test(priority = 4)
	    public void verifyNameRequiredValidation() throws InterruptedException {

	        ExtentReportListener.info(
	                "Validating mandatory Organization Name field"
	        );

	        // Click Add More to create a new empty field
	        orgPage.clickAddMore();
	        
	        Thread.sleep(2000);
	        
	        // Verify "Name is required" validation
	        Assert.assertTrue(orgPage.isNameRequiredMessageDisplayed(),"Name required validation message is not displayed");

	        ExtentReportListener.pass("Mandatory Name validation verified successfully");
	        orgPage.closeHierarchy();
	    }
		
		@Test(priority = 5)
		public void createOrganizationUnit() throws InterruptedException {
		
			String dynamicOrgName = "BANK_" + java.util.UUID.randomUUID().toString().replaceAll("[^a-zA-Z]", "").substring(0, 4); // e.g., "BANK_axbcf"
			
		    ExtentReportListener.info("Opening Organization Unit creation");

		    orgPage.clickLaunchpadAddButton();
		    Thread.sleep(1500);
		    ExtentReportListener.info("Entering Organization Unit name: " + dynamicOrgName);
		    orgPage.enterName(dynamicOrgName);

		    ExtentReportListener.info("Entering Organization Unit description");
		    orgPage.enterDescription(dynamicOrgName +  " Organization Unit");

		    ExtentReportListener.info("Verifying Save button is enabled and saving");
		    orgPage.saveOrganization();
		    orgPage.yesForAddOrg();
		    ExtentReportListener.pass("Organization Unit created successfully");
		    
			ExtentReportListener.info("Clicking " + dynamicOrgName + " organization");
			
			Thread.sleep(1000);
			orgPage.clickOrganization(dynamicOrgName);
			ExtentReportListener.pass(dynamicOrgName + " organization clicked successfully");

			Assert.assertEquals(orgPage.getRightSideOrganizationName(), dynamicOrgName);
			ExtentReportListener.pass("Organization Name verified: " + dynamicOrgName);

			Assert.assertEquals(orgPage.getRightSideDescription(), dynamicOrgName + " Organization Unit");
			ExtentReportListener.pass("Description verified: " + dynamicOrgName + " Organization Unit");

			Assert.assertEquals(orgPage.getRightSideCreatedBy(), "Abhijit");
			ExtentReportListener.pass("Created By verified: Abhijit");

			// 2. Automatically formats today's actual current system date (e.g., "Aug 13 2026")
			String currentSystemDate = new java.text.SimpleDateFormat("MMM dd yyyy", java.util.Locale.ENGLISH).format(new java.util.Date());
			
			Assert.assertTrue(orgPage.getRightSideCreatedOn().contains(currentSystemDate), "Date mismatch! Expected today's date: " + currentSystemDate);
			ExtentReportListener.pass("Created On verified for " + dynamicOrgName + " with today's date");

			System.out.println("[PASS] " + dynamicOrgName + " organization details verified successfully");
		
		}
		
		@Test(priority = 6)
		public void editOrganizationUnit() throws InterruptedException {
		    String oldName = orgPage.getRightSideOrganizationName();
		    String newName = oldName.substring(0, oldName.length() - 1) + "1";

		    String oldDescription = orgPage.getRightSideDescription();
		    String newDescription = oldDescription.replace(oldName, newName);

		    ExtentReportListener.info("Editing organization: " + oldName);

		    Assert.assertTrue(orgPage.isEditButtonEnabled(),"Edit button is not enabled");

		    orgPage.editOrganization(newName, newDescription);

		    orgPage.saveEditedOrganization();

		    ExtentReportListener.pass("Organization Name edited to: " + newName);
		    ExtentReportListener.pass("Description edited to: " + newDescription);
		    ExtentReportListener.pass("Save and Yes buttons clicked successfully");
		    System.out.println("[PASS] Organization edit operation completed successfully");
		    

		    orgPage.clickOrganization(newName);

		    Assert.assertEquals(orgPage.getNewName(), newName, "Name was not updated");
		    ExtentReportListener.pass("Updated Name verified: " + newName);

		    Assert.assertEquals(orgPage.getNewDescription(), newDescription, "Description was not updated");
		    ExtentReportListener.pass("Updated Description verified: " + newDescription);

		    Assert.assertEquals(orgPage.getUpdatedBy(), "Abhijit", "Updated By was not updated");
		    ExtentReportListener.pass("Updated By verified: Abhijit");
		    
		    String today = new java.text.SimpleDateFormat("MMM d yyyy").format(new java.util.Date());

		    Assert.assertTrue(orgPage.getUpdatedOn().contains(today), 
		    		"Updated On does not contain today's date. Expected: " + today + " but found: " + orgPage.getUpdatedOn());
    
		    ExtentReportListener.pass("Updated On verified: " + orgPage.getUpdatedOn());
		    
		}
		
		//@Test(priority = 7)
		public void checkTimelineFormat() {

		    ExtentReportListener.info("Opening Approval Details");

		    // Open Approval Details tab
		    approvalPage.clickApprovalDetailsTab();

		    // Get all timeline events
		    List<WebElement> events = approvalPage.getTimelineItems();

		    // Make sure events are available
		    Assert.assertFalse(events.isEmpty(),"No Approval Details events were found.");

		    ExtentReportListener.info("Total Approval Events Found: " + events.size());


		    // Check every event
		    for (WebElement event : events) {

		        String user = approvalPage.getUser(event);
		        String action = approvalPage.getAction(event);
		        String timestamp = approvalPage.getTimestamp(event);

		        // Print in console
		        System.out.println("User: " + user
		                + " | Action: " + action
		                + " | Timestamp: " + timestamp);
		        // Validate user
		        Assert.assertFalse(user.isEmpty(), "Approval event user is empty.");

		        // Validate action
		        Assert.assertFalse(action.isEmpty(), "Approval event action is empty.");

		        // Validate timestamp
		        Assert.assertFalse(timestamp.isEmpty(), "Approval event timestamp is empty.");

		        ExtentReportListener.info("Approval Event verified: "
		                + user + " | "
		                + action + " | "
		                + timestamp
		        );
		        Assert.assertFalse(user.isEmpty(), "Approval event user is empty.");
			    Assert.assertFalse(action.isEmpty(), "Approval event action is empty.");
			    Assert.assertFalse(timestamp.isEmpty(), "Approval event timestamp is empty.");
		    }

		    ExtentReportListener.pass(
		            "All Approval Details timeline events verified successfully."
		    );
		}
		
	@AfterClass
	public void closeCompleteSuiteSession() {
		//FINALLY CLOSE THE BROWSER: Shut down after the last test class completes
	    ExtentReportListener.info("Closing browser session");

	    super.teardown();

	    ExtentReportListener.pass("Browser closed successfully");
	}
}
