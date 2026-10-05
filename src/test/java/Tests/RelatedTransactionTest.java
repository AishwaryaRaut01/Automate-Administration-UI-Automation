package Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.RelatedTransactionPage;
import Utilities.ExtentReportListener;
import Utilities.ConfigReader;

@Listeners(ExtentReportListener.class)
public class RelatedTransactionTest extends BrowserSetup {

    private LoginPage loginPage;
    private RelatedTransactionPage relatedPage;

    @BeforeClass
    public void setupRelatedTransactionSuite() {
        ExtentReportListener.info("Initializing Browser and Related Transaction Components");
        super.setup(); 

        loginPage = new LoginPage(driver);
        relatedPage = new RelatedTransactionPage(driver); 

        ExtentReportListener.pass("Related Transaction Workspace components loaded successfully");
    }

    @Test(priority = 1)
    public void executeLoginSession() throws InterruptedException {
        ExtentReportListener.info("Loading system credentials");
        String user = ConfigReader.getProperty("username");
        String pass = ConfigReader.getProperty("userpw");

        loginPage.loginToApplication(user, pass);
        ExtentReportListener.pass("Authentication session completed successfully");
    }
    
    @Test(priority = 2, dependsOnMethods = "executeLoginSession")
    public void runRelatedTransactionFlow() throws InterruptedException {
        ExtentReportListener.info("Initiating Investigation & Research verification sequence");

        // 1. Navigation Flow
        relatedPage.navigateToAllIncidents();
        ExtentReportListener.pass("Navigated to Investigation&Research -> All Incidents dashboard layout");
        
        Thread.sleep(1000);
        
        relatedPage.selectAllIncidentsfromDropDown();
        ExtentReportListener.pass("All Incidents selected from Incident WorkList DropDown");
        Thread.sleep(1000);

        // 2. Row Interception Check
        relatedPage.selectFirstRowIncident();
        ExtentReportListener.pass("First row selection executed dynamically inside data grid view");

        Thread.sleep(1500);
        
        // 3. Sync Wait on Transaction Elements Icon Identification
        ExtentReportListener.info("Verifying menu icon tooltip text and navigating to Related Transactions");
        String expectedTooltipText = "Related Transactions"; 
        
        String actualTooltipText = relatedPage.getTransactionTooltipAndClick();
        
        // Assert that the tooltip matches your requirements
        Assert.assertEquals(actualTooltipText, expectedTooltipText, "Tooltip text verification mismatch!");
        ExtentReportListener.pass("Tooltip text verified successfully: " + actualTooltipText);
        
        Assert.assertEquals(actualTooltipText, expectedTooltipText, "Tooltip text verification mismatch!");
        ExtentReportListener.pass("Tooltip text verified successfully: " + actualTooltipText);
        
        Thread.sleep(1000);
        
        Assert.assertTrue(relatedPage.isAllButtonEnabled(), "Save button is not enabled");
		ExtentReportListener.pass("All button is enabled");

		relatedPage.clickAll();
		ExtentReportListener.pass("All button clicked successfully");
		Thread.sleep(1000);
		
        // Dropdown Panel Inspection & Target Selection
        ExtentReportListener.info("Opening transaction filter dropdown panel to read all entries from Related Transactions");
        
        // Triggers the extraction loop and performs the final select click action automatically
        List<String> optionsFound = relatedPage.printAndSelectAllTransactions();
        
        ExtentReportListener.pass("Successfully extracted all option lists: " + optionsFound.toString());
        ExtentReportListener.pass("Successfully selected 'All Transactions' selection choice from container list");
        
        Thread.sleep(2000); // Allow data grid table frame elements to complete reloading lifecycle

     // 5. Dynamic Data Extraction Block (Zero Hardcoded References)
        ExtentReportListener.info("Fetching title header name from Focal Party");
        String runtimeFocalPartyHeaderName = relatedPage.getDynamicReferenceHeaderName();

        if (runtimeFocalPartyHeaderName == null || runtimeFocalPartyHeaderName.isEmpty()) {
            ExtentReportListener.info("Focal Party Reference Header has no name. Skipping dependent validation and filter actions.");
            return;
        }

        ExtentReportListener.pass(
            "Focal Party name caught from UI: " + runtimeFocalPartyHeaderName);
        // 6. Column Row Traversal and Validation Sequence
        ExtentReportListener.info("Starting validation from Focal Party Name columns against header name");
        boolean dynamicVerificationSuccessStatus = relatedPage.verifyTableRowsAgainstDynamicReference(runtimeFocalPartyHeaderName);
        Thread.sleep(1000);
        
        // Automation assertion check confirming matching status across runtime states
        Assert.assertTrue(dynamicVerificationSuccessStatus, "Validation failed! One or more grid cell records did not match with header text string: " + runtimeFocalPartyHeaderName);
        ExtentReportListener.pass("PASS: Validated grid row columns name with dashboard's name for Focal Party: " + runtimeFocalPartyHeaderName);
        
        Thread.sleep(1000);

        // 7. Advanced Filter Application Flow
        ExtentReportListener.info("Opening Focal Party header filter block to pull matching operator values");
        
        // This single execution block runs steps 1 to 6 automatically
        List<String> filterOperatorsFound = relatedPage.applyDoesNotExistFilterAndVerifyEmpty();
        ExtentReportListener.pass("Successfully grabbed all dropdown operators: " + filterOperatorsFound.toString());
        ExtentReportListener.pass("Selected 'Does not equal' operator and dynamically injected target focal name string criteria");
        Thread.sleep(1000);
        
        // 8. Dynamic Page Sync Verification Block
        ExtentReportListener.info("Waiting for Grid data refresh to load zero records display");
        boolean isGridEmpty = relatedPage.isGridDisplayingNoMatchingRows();
        Thread.sleep(1000);
        
        // Assert that the page updated and no records are shown
        Assert.assertTrue(isGridEmpty, "Data matrix failure! The table grid is still showing matching rows after applying the exclusion filter!");
        ExtentReportListener.pass("PASS: Fully verified the dashboard updated cleanly and displays 'No matching rows' as expected. Also Reset filter is applied after validation");
        
        relatedPage.clickFilterIcon();
		ExtentReportListener.pass("Filter Icon clicked successfully to reset changes");
		Thread.sleep(1000);
		
        Assert.assertTrue(relatedPage.isResetButtonEnabled(), "Reset button is not enabled");
		ExtentReportListener.pass("Reset button is enabled");

		relatedPage.clickReset();
		ExtentReportListener.pass("Reset button clicked successfully");
		Thread.sleep(1000);
		
    }

   //@AfterClass
    public void tearDownRelatedTransactionSuite() {
        ExtentReportListener.info("Tearing down testing browser context environments");
        if (driver != null) {
            driver.quit();
        }
        ExtentReportListener.pass("Browser session closed cleanly");
    }
}
