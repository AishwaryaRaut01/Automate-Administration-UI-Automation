package Tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Utilities.ExtentReportListener;
import Utilities.AccessibilityUtil;
import Utilities.ConfigReader;

@Listeners(ExtentReportListener.class)
public class LoginTest extends BrowserSetup{
	
	LoginPage loginPage;  //Reference variable to call inside code
	
	
	@BeforeClass
	public void setup() {
		ExtentReportListener.info("Launching Browser");
		super.setup();                           // 2. You explicitly called "base.setup()" 
		loginPage = new LoginPage(driver); //Directly binds the inherited driver context to your Page Object
		ExtentReportListener.pass("Browser launched successfully");
	}
	
	@Test(priority = 1)
	public void verifyLoginPageAccessibility() {

	    ExtentReportListener.info("Starting accessibility testing for Login Page");
	  //  System.out.println("[INFO] Starting accessibility testing for Login Page");

	    boolean accessibilityPassed = AccessibilityUtil.checkAccessibility(driver);

	    Assert.assertTrue(accessibilityPassed,"Accessibility violations found on Login Page");

	    ExtentReportListener.pass("Login Page accessibility test passed");
	  //  System.out.println("[PASS] Login Page accessibility test passed");
	}
	
	
	@Test(priority=2)
	public void verifyLogin() throws InterruptedException {
		
		ExtentReportListener.info("Loading Login Page");
		// System.out.println("[INFO] Loading Login Page");
		// 1. Fetch credentials here in the test layer [INDEX]
        String user = ConfigReader.getProperty("username");
        String pass = ConfigReader.getProperty("userpw");

        // 2. Pass those strings dynamically down into your Page Object [INDEX]
        loginPage.loginToApplication(user, pass);
        
        ExtentReportListener.pass("Login Successful");
        //System.out.println("[PASS] Login Successful");

    }	
	
	@AfterClass
	public void quitBrowser() {
		ExtentReportListener.info("Closing browser session");
		super.teardown();
		ExtentReportListener.pass("Browser Closing Successfully");
       // System.out.println("[PASS] Browser Closing Successful");
	}

}
	

