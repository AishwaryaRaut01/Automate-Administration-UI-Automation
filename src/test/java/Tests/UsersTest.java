package Tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.TransactionSummaryPage;
import Pages.UsersPage;
import Utilities.ConfigReader;
import Utilities.ExtentReportListener;

public class UsersTest extends BrowserSetup {

	LoginPage loginPage;
	UsersPage usersPage;

	// These will store the actual values entered in the UI
	String createdUsername;
	String createdEmail;
	String createdPassword;
	String createdFirstName;
	String createdLastName;


	@BeforeClass
	public void setupUserPage() {

		ExtentReportListener.info("Initializing Users Page");
		usersPage = new UsersPage(driver);
		ExtentReportListener.pass("Users Page initialized successfully");
	}
	
	 @BeforeClass
	    public void setupSummarySuite() {
	        ExtentReportListener.info("Initializing Browser and Users Page");
	        super.setup(); 

	        loginPage = new LoginPage(driver);
	        usersPage = new UsersPage(driver);

	        ExtentReportListener.pass("Users Page initialized successfully");
	    }
	 
	 @Test(priority = 1)
	    public void executeLoginSession() throws InterruptedException {
	        ExtentReportListener.info("Loading system credentials");
	        String user = ConfigReader.getProperty("username");
	        String pass = ConfigReader.getProperty("userpw");

	        loginPage.loginToApplication(user, pass);
	        ExtentReportListener.pass("Authentication session completed successfully");
	    }
	 
	
//CREATE NEW USER
	@Test(priority = 2)
	public void createNewUser() throws InterruptedException {

//USERS MODULE
		ExtentReportListener.info("Navigating to Users module");
		
		usersPage.movetoOrganization();
		usersPage.clickUsers();

		ExtentReportListener.pass("Users module opened successfully");

// ADD USER
		Assert.assertTrue(usersPage.isAddUserButtonEnabled(), "Add User button is not enabled");
		ExtentReportListener.pass("Add User button is enabled");

		usersPage.clickAddUser();
		ExtentReportListener.pass("Add User popup opened successfully");

// SELECT ORGANIZATION
		Assert.assertTrue(usersPage.isLaunchpadVisible(),"Launchpad is not visible");
		ExtentReportListener.pass("Launchpad is visible in organization hierarchy");

		usersPage.selectLaunchpad();
		ExtentReportListener.pass("Launchpad clicked successfully");
		
// REQUIRED FIELD VALIDATION
		ExtentReportListener.info("Validating required field messages");
		
		Assert.assertTrue(usersPage.validateRequiredField(usersPage.usernameField, "Username"), "Username is required message is not displayed");
		ExtentReportListener.pass("Username required validation passed");
		
		Thread.sleep(500);
		Assert.assertTrue(usersPage.validateRequiredField(usersPage.emailField, "Email"), "Email is required message is not displayed");
		ExtentReportListener.pass("Email required validation passed");

		Thread.sleep(500);
		Assert.assertTrue(usersPage.validateRequiredField(usersPage.passwordField, "Password"), "Password validation message is not displayed");
		ExtentReportListener.pass("Password required/validation message passed");

		Thread.sleep(500);
		Assert.assertTrue(usersPage.validateRequiredField(usersPage.firstNameField, "First Name"), "First Name is required message is not displayed");
		ExtentReportListener.pass("First Name required validation passed");

		Thread.sleep(500);
		Assert.assertTrue(usersPage.validateRequiredField(usersPage.lastNameField, "Last Name"), "Last Name is required message is not displayed");
		ExtentReportListener.pass("Last Name required validation passed");

//Generate dynamic data
		String dynamicName = UUID.randomUUID()
				.toString()
				.replaceAll("[^a-zA-Z]", "")
				.substring(0, 4);
		String timestamp = String.valueOf(System.currentTimeMillis());

		String username = "testuser_" + dynamicName;
		String email = "testuser" + dynamicName + "@gmail.com";
		String password = "Test@" + timestamp;
		String firstName = "Test" + dynamicName;
		String lastName = "User" + dynamicName;
		
		Thread.sleep(1000);

//enter user details
		ExtentReportListener.info("Entering dynamically generated user details");

		usersPage.enterUserDetails(
				username,
				email,
				password,
				firstName,
				lastName
				);
		
		Thread.sleep(1000);
//get actual values from UI
		createdUsername = usersPage.getUsername();
		createdEmail = usersPage.getEmail();
		createdPassword = usersPage.getPassword();
		createdFirstName = usersPage.getFirstName();
		createdLastName = usersPage.getLastName();

		ExtentReportListener.pass("Username entered: " + createdUsername);
		ExtentReportListener.pass("Email entered: " + createdEmail);
		ExtentReportListener.pass("First Name entered: " + createdFirstName);
		ExtentReportListener.pass("Last Name entered: " + createdLastName);
		ExtentReportListener.pass("Password entered: " + createdPassword);
		Thread.sleep(1000);

//Saving user
		Assert.assertTrue(usersPage.isSaveButtonEnabled(),"Save button is not enabled");
		ExtentReportListener.pass("Save button is enabled");
		Thread.sleep(1000);
		usersPage.clickSave();
		ExtentReportListener.pass("Save button clicked successfully");
		
		
//to click yes to save
		Assert.assertTrue(usersPage.isYesButtonEnabled(),"Yes button is not enabled");
		ExtentReportListener.pass("Yes button is enabled");

		Thread.sleep(1000);
		usersPage.clickYesAndNextPage();

		ExtentReportListener.pass("Yes and navigating to next page button clicked successfully");
		Thread.sleep(1500);
		
		usersPage.selectPageValue();

		Thread.sleep(1000);
		ExtentReportListener.pass("Page value selected successfully");

		System.out.println("[PASS] New User creation flow completed successfully");
	}

//verify user added data in table
	@Test(priority = 3)
	public void verifyLastAddedUserData() throws InterruptedException {
		
		//get data stored in the table
		String actualFirstName = usersPage.getDisplayedFirstName();
		String actualEmail = usersPage.getDisplayedEmail();
		String actualCreatedOn = usersPage.getDisplayedCreatedOn();
		
		Thread.sleep(1500);

		Assert.assertEquals(actualFirstName, createdFirstName, "First Name does not match");
		ExtentReportListener.pass("First Name verified : " + createdFirstName);
		
		Assert.assertEquals(actualEmail, createdEmail,"Email does not match");
		ExtentReportListener.pass("Email Id verified : " + createdEmail);
		Thread.sleep(1000);
		
		String currentSystemDate = new java.text.SimpleDateFormat("MMM dd yyyy", java.util.Locale.ENGLISH).format(new java.util.Date());
		Assert.assertTrue(actualCreatedOn.contains(currentSystemDate),
				"Created On date mismatch! Expected today's date: " + currentSystemDate);
		
//		String currentSystemDateTime = new java.text.SimpleDateFormat("MMM dd yyyy, hh:mm a", java.util.Locale.ENGLISH).format(new java.util.Date());
//		Assert.assertTrue(actualCreatedOn.contains(currentSystemDateTime), "Created On date and time mismatch! Expected: " + currentSystemDateTime);

		ExtentReportListener.pass("Created On verified with today's date: " + actualCreatedOn);
		Thread.sleep(1000);
	}

	@AfterClass
	public void closeCompleteSuiteSession() {
		ExtentReportListener.info("Closing browser session");
		super.teardown();
		ExtentReportListener.pass("Browser closed successfully");
	}
}