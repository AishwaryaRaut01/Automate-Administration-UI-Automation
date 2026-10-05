package Tests;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BrowserSetup;
import Pages.LoginPage;
import Pages.InvestigationResearch;
import Utilities.ExtentReportListener;
import Utilities.ConfigReader;

@Listeners(ExtentReportListener.class)
public class InvestigationResearchTest extends BrowserSetup {

    private LoginPage loginPage;
    private InvestigationResearch investigationPage;

    @BeforeClass
    public void setupInvestigationResearchSuite() {
        ExtentReportListener.info("Initializing Browser and Investigation Research Components");
        super.setup(); 

        loginPage = new LoginPage(driver);
        investigationPage = new InvestigationResearch(driver);

        ExtentReportListener.pass("Investigation & Research Workspace components loaded successfully");
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
    public void verifyDropdownAndElements() throws InterruptedException{
    	
    	ExtentReportListener.info("Initiating dynamic table layout analysis");

        // 1. Navigation Flow
        investigationPage.navigateToAllIncidents();
        ExtentReportListener.pass("Navigated to Investigation&Research -> All Incidents dashboard layout");
        Thread.sleep(1000);
        
        investigationPage.selectAllIncidentsfromDropDown();
        ExtentReportListener.pass("All Incidents selected from Incident WorkList DropDown");
        Thread.sleep(2000); 

     // --- FLOW 1: INTERACT WITH THE SCENARIOS DROPDOWN ---
        ExtentReportListener.info(" Expanding the Scenarios dropdown filter panel...");
     		investigationPage.openDropdown("scenarios");
     		
     		List<String> scenarioOptions = investigationPage.getOpenDropdownOptions();
     		ExtentReportListener.info("Available Scenarios Choices Discovered (" + scenarioOptions.size() + "):");
     		for (String option : scenarioOptions) {
     			System.out.println(" -> " + option);
     		}
     		
     		String targetScenario = "WireTrendB";
     		Assert.assertTrue(scenarioOptions.contains(targetScenario), "[FAIL] Target choice not found inside list view: " + targetScenario);
     		
     		ExtentReportListener.info("Selecting option target item: " + targetScenario);
     		investigationPage.selectDropdownOption(targetScenario);
     		
     		Thread.sleep(1000);
     		ExtentReportListener.info("Clicking Apply and resetting via Clear All actions...");
     		investigationPage.applyFilter();
     		ExtentReportListener.pass("Apply button successfully clicked for Scenarios.\n");
     		Thread.sleep(1000);
     		investigationPage.clearFilter();
     		ExtentReportListener.pass("Clear All button successfully clicked for Scenarios.\n");

     		
     		// --- FLOW 2: INTERACT WITH THE SIGNALS DROPDOWN ---
     		ExtentReportListener.info("Expanding the Signals dropdown filter panel...");
     		investigationPage.openDropdown("signals");
     		
     		List<String> signalOptions = investigationPage.getOpenDropdownOptions();
     		ExtentReportListener.info("Available Signals Choices Discovered (" + signalOptions.size() + "):");
     		for (String option : signalOptions) {
     			System.out.println(" -> " + option);
     		}
     		
     		String targetSignal = "REPUTATIONAL_RISK_PARTY";
     		Assert.assertTrue(signalOptions.contains(targetSignal), "[FAIL] Target choice not found inside list view: " + targetSignal);
     		
     		ExtentReportListener.info("Selecting option target item: " + targetSignal);
     		investigationPage.selectDropdownOption(targetSignal);
     		
     		ExtentReportListener.info("Clicking Apply and resetting via Clear All actions...");
     		investigationPage.applyFilter();
     		ExtentReportListener.pass("Apply button successfully clicked for Scenarios.\n");
     		Thread.sleep(1000);
     		investigationPage.clearFilter();
     		ExtentReportListener.pass("Clear All button successfully clicked for Scenarios.\n");

     		investigationPage.enterSearchQuery(targetSignal);
    }
   
    //@Test(priority = 3, dependsOnMethods = "executeLoginSession")
    public void verifyTableGridMetrics() throws InterruptedException {
        
        // 2. Fetch column names via page class method
        ExtentReportListener.info("Extracting live table header column names");
        List<String> visibleHeaders = investigationPage.getTableColumnNames();
        
        // Assert that the page successfully rendered column elements (Bypasses zero-count failures)
        Assert.assertTrue(visibleHeaders.size() > 0, "Data layout verification failure! Table structural columns failed to render.");
        ExtentReportListener.pass("Dynamic Column count calculated. Found total functional columns: " + visibleHeaders.size());

        // 3. FIXED: Assembles and prints all column names HORIZONTALLY inside ONE single row log string
        String horizontalHeaderRow = "";
        for (String columnName : visibleHeaders) {
            // Clean up any internal hidden carriage breaks (\n) to avoid vertical text stacking
            String cleanName = columnName.replace("\n", " ").trim();
            if (!cleanName.isEmpty()) {
                horizontalHeaderRow = horizontalHeaderRow + cleanName + "   |   ";
            }
        }
        
        // Log the final consolidated horizontal headers entry to Extent Report in one line
        ExtentReportListener.info("Columns Structural Layout -> " + horizontalHeaderRow);
        ExtentReportListener.pass("Table dynamic column identification traversal completed successfully.");

        // 4. Extract entire data grid body rows horizontally into Extent Logs
        ExtentReportListener.info("Extracting live table data grid rows:");
        
        // Grab the elements from the page object cleanly
        List<WebElement> allRows = investigationPage.getTableRows();
        int rowNumber = 1;
        
        for (WebElement row : allRows) {
            List<WebElement> rowCells = row.findElements(By.xpath("./td[@data-index]"));
            String horizontalRowString = "";
            
            for (WebElement cell : rowCells) {
                String cellText = cell.getText().trim();
                if (cellText.isEmpty()) {
                    cellText = "[Icon/Box]";
                }
                horizontalRowString = horizontalRowString + cellText + " 			";
            }
            
            ExtentReportListener.info("Row " + rowNumber + " -> " + horizontalRowString);
            rowNumber++;
        }
        
        ExtentReportListener.pass("Table dynamic data grid cell traversal completed successfully.");
    }

   //@AfterClass
    public void tearDownInvestigationResearchSuite() {
        ExtentReportListener.info("Tearing down testing browser context environments");
        if (driver != null) {
            driver.quit();
        }
        ExtentReportListener.pass("Browser session closed cleanly");
    }
}

