package Tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.TransactionSummaryPage;
import Utilities.ExtentReportListener;
import Utilities.ConfigReader;

@Listeners(ExtentReportListener.class)
public class TransactionSummaryTest extends BrowserSetup {

    private LoginPage loginPage;
    private TransactionSummaryPage summaryPage;

    @BeforeClass
    public void setupSummarySuite() {
        ExtentReportListener.info("Initializing Browser and Transaction Summary Components");
        super.setup(); 

        loginPage = new LoginPage(driver);
        summaryPage = new TransactionSummaryPage(driver); 

        ExtentReportListener.pass("Transaction Summary Workspace components loaded successfully");
    }

    @Test(priority = 1)
    public void executeLoginSession() throws InterruptedException {
        ExtentReportListener.info("Loading system credentials");
        String user = ConfigReader.getProperty("username");
        String pass = ConfigReader.getProperty("userpw");

        loginPage.loginToApplication(user, pass);
        ExtentReportListener.pass("Authentication session completed successfully");
    }
    
  //  @Test(priority = 2, dependsOnMethods = "executeLoginSession")
    public void verifyCustomDateRangeSelection() throws InterruptedException {
        
    	summaryPage.selectCustomDateRange();
    	summaryPage.selectPreviousDateRange();
    	summaryPage.selectNextDateRange();
    }
    @Test(priority = 2, dependsOnMethods = "executeLoginSession")
    public void runInvestigationSummaryWorkflow() throws InterruptedException {
        ExtentReportListener.info("Initiating Investigation & Research verification sequence");

        // 1. Navigation Flow
        summaryPage.navigateToAllIncidents();
        ExtentReportListener.pass("Navigated to Investigation&Research -> All Incidents dashboard layout");
        
        Thread.sleep(1000);
        
        summaryPage.selectAllIncidentsfromDropDown();
        ExtentReportListener.pass("All Incidents selected from Incident WorkList DropDown");
        Thread.sleep(1000);

        // 2. Row Interception Check
        summaryPage.selectFirstRowIncident();
        ExtentReportListener.pass("First row selection executed dynamically inside data grid view");

        Thread.sleep(1000);
        // 3. Sync Wait on Transaction Elements Icon Identification
        ExtentReportListener.info("Verifying menu icon tooltip text and navigating to Investigation&Research");
        String expectedTooltipText = "Transaction Summary"; // Change this to your exact expected title string
        
        String actualTooltipText = summaryPage.getTransactionTooltipAndClick();
        
        // Assert that the tooltip matches your requirements
        Assert.assertEquals(actualTooltipText, expectedTooltipText, "Tooltip text verification mismatch!");
        ExtentReportListener.pass("Tooltip text verified successfully: " + actualTooltipText);
        
        Thread.sleep(1500);
        
        ExtentReportListener.info("Starting automation flow to select custom date range.");

        summaryPage.selectCustomDateRange();
        ExtentReportListener.pass("Successfully clicked on Date Range and Custom");
        
        ExtentReportListener.info("Selecting Initial Date for the range");
    	summaryPage.selectPreviousDateRange();
    	ExtentReportListener.pass("Initial Date range is selected");
    	
    	ExtentReportListener.info("Selecting Final Date for the range");
    	summaryPage.selectNextDateRange();
    	ExtentReportListener.pass("Final Date range is selected");
    	
    	Assert.assertTrue(summaryPage.isApplyButtonEnabled(), "Apply button is not enabled");
		ExtentReportListener.pass("Apply button is enabled");

		summaryPage.clickApply();
		ExtentReportListener.pass("Apply button clicked successfully");
		
		ExtentReportListener.info("Starting date validation cross-checks between Top Filter and Bottom Summary.");

	    try {
	        // 1. Fetch values directly from your provided page methods
	        String filterDates = summaryPage.getDisplayedDateRangeValue(); // "Feb 12, 2024 – Feb 12, 2026"
	        String summaryDates = summaryPage.getDateRangeFromSummary();   // "02/12/2024 - 02/12/2026"

	        // 2. Standardise date text layouts into identical formats for instant comparison
	        DateTimeFormatter filterForm = DateTimeFormatter.ofPattern("MMM d, yyyy", java.util.Locale.ENGLISH);
	        DateTimeFormatter summaryForm = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	        // Convert ranges directly by modifying the separator characters
	        String formattedFilterStart = LocalDate.parse(filterDates.split("–")[0].trim(), filterForm).format(summaryForm);
	        String formattedFilterEnd = LocalDate.parse(filterDates.split("–")[1].trim(), filterForm).format(summaryForm);
	        String fullyFormattedFilterRange = formattedFilterStart + " - " + formattedFilterEnd; // "02/12/2024 - 02/12/2026"

	        // 3. Simple, unified validation check
	        Assert.assertEquals(summaryDates, fullyFormattedFilterRange, "Mismatch found on Date Range layout checks!");
	        
	        ExtentReportListener.pass("PASS: Calendar filter date matches with summary block date i.e. : " + summaryDates);
	    
	    } catch (Exception e) {
	    	ExtentReportListener.fail("TEST FAILURE: Exception encountered during processing metrics matrix: " + e.getMessage());
	        throw e;
	    }
    	
	    
        // 4. Summarize Action Trigger Checking
        Assert.assertTrue(summaryPage.isSummarizeButtonEnabled(), "Summarize button is not enabled");
        ExtentReportListener.pass("Summarize button is enabled");
        
        summaryPage.clickSummarize();
        ExtentReportListener.pass("Summarize button clicked successfully; processing panel open");

        // 5. Add to Narrative Passive Enabled Check Status Assertion (No click)
        Assert.assertTrue(summaryPage.isAddToNarrativeButtonEnabled(), "Add to Narrative button is not enabled");
        ExtentReportListener.pass("Add to Narrative button is enabled");

        // 6. Select Executive Tone mapping
        ExtentReportListener.info("Opening Tone dropdown configuration panel");
        summaryPage.selectToneExecutive();
        ExtentReportListener.pass("Tone profile configurations updated to: Executive mode");

        // 7. Sequential Line-by-Line Multi-Style Settings Adjustments Block
        ExtentReportListener.info("Configuring multiple style parameters sequentially line-by-line");
        summaryPage.configureAllStyles();
        ExtentReportListener.pass("Successfully configured Bullet Summary, Narrative Paragraph, and Risk-focused style properties");

        // 8. Inject Dynamic Instructions Text data inside Input Form Textarea
        String dynamicName = UUID.randomUUID()
				.toString()
				.replaceAll("[^a-zA-Z]", "")
				.substring(0, 4);
        
        String generatedInstructionsText = "Detail Instructions on alerted transaction summary for " + dynamicName;
        summaryPage.enterAdditionalInstructions(generatedInstructionsText);
        ExtentReportListener.pass("Dynamic text added to Additional Instructions textarea");

        // 9. Read and Keep Stored parameter values from Narrative Header Element
        String storedNarrativeHeaderTitle = summaryPage.getNarrativeHeadingText();
        ExtentReportListener.pass("Narrative heading text captured : " + storedNarrativeHeaderTitle);
        
        Thread.sleep(1000);
        
        // 10. Regenerate Validation Sequence 
        Assert.assertTrue(summaryPage.isRegenerateButtonEnabled(), "Regenerate button is not enabled");
        ExtentReportListener.pass("Regenerate button is enabled");
        
        summaryPage.clickRegenerate();
        ExtentReportListener.pass("successfully clicked on Regenerate button");
        
        Thread.sleep(1000);

        // 11. Extract output values from data result boxes to run integrity checks
        ExtentReportListener.info("Extracting text from generated summary");
        String finalSummaryOutputText = summaryPage.getGeneratedSummaryTextContent();
        
        // 12. Verification Checks: Length checking and Non-Empty/Invalid matches logic rule asserts
        Assert.assertFalse(finalSummaryOutputText.trim().isEmpty(), "Verification Failure: Generated summary is empty.");
        ExtentReportListener.pass("Generated summary text verified successfully: " +finalSummaryOutputText);
        
        Assert.assertTrue(summaryPage.isSaveButtonEnabled(), "Save button is not enabled");
		ExtentReportListener.pass("Save button is enabled");

		summaryPage.clickSave();
		ExtentReportListener.pass("Save button clicked successfully");
    }

   @AfterClass
    public void closeSummarySuiteSession() {
        ExtentReportListener.info("Closing Browser");
        super.teardown();
        ExtentReportListener.pass("Browser closed safely; automated trace reporting successfully finalized");
    }
}

