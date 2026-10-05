//span[normalize-space()='Investigation and Research']
package Pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Base.BrowserSetup;

public class TransactionSummaryPage extends BrowserSetup {
	WebDriver driver;
	WebDriverWait wait;

	public TransactionSummaryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// --- Left-side Navigation Menu ---
	@FindBy(xpath = "//span[normalize-space()='Investigation and Research']")
	private WebElement investigationAndResearchMenu;

	@FindBy(xpath = "//a[normalize-space()='All Incidents']")
	private WebElement allIncidentsSubMenu;   

	@FindBy(xpath = "//p[text()='All Incidents']")
	private WebElement workListDropDown;

	@FindBy(xpath = "//input[@value='All Incidents']")
	private WebElement incidentsWorkList;
	
	@FindBy(xpath = "//th[.//*[normalize-space()='Status']]//button[@aria-label='Column Actions']")
	private WebElement statusFilterDot;

	@FindBy(xpath = "(//span[@data-ref='eFilterButton'])[6]")
	private WebElement filterIcon;
	
	@FindBy(xpath = "(//*[local-name()='svg' and contains(@class, 'lucide-chevron-down')])[42]")
	private WebElement filterModeIcon;
	
	@FindBy(xpath = "//div[contains(@class, 'ag-select-list-item')][.//span[text()='Contains']]")
	private WebElement addfilterMode;
	
	@FindBy(xpath = "//div[@data-ref='eDisplayField' and text()='Contains']")
	private WebElement checkModeApplied;
	
	@FindBy(xpath = "//input[@aria-label='Filter Value']")
	private WebElement enterFilterMode;
	
	@FindBy(xpath = "(//div[@role='row' and @row-index=0]//div[@role='gridcell' and @col-id='incident_status'])")
	private WebElement firstRowStatus;		
	
	@FindBy(xpath = "//div[contains(@class,'mantine-Badge-label') and contains(text(),'active')]")
	private WebElement activeFilters;
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Clear all']]")
	private WebElement clearAllFilter;
	
	@FindBy(xpath = "//div[@role='rowgroup' and contains(@class, 'ag-center-cols-viewport')]")
	private WebElement noMatchingRows;
	
	// --- Table & Rows ---
	// Targets the select checkbox or selection node of the absolute first row inside the grid
	@FindBy(xpath = "//div[@role='row' and @row-index='0']")
	private WebElement firstRowSelection;

	@FindBy(xpath = "//div[@role='row' and @row-index='0']//div[@col-id='incident_id']//a") //((//tr[.//td])[1]//td)[2]
	private WebElement incidentNo;    

	@FindBy(xpath = "//button[@data-testid='date-range-filter-trigger']")
	private WebElement dateRangeFilter;

	@FindBy(xpath = "//button[@data-testid='tab-custom']")
	private WebElement customDateRange;

	@FindBy(xpath = "(//button[contains(@class,'mantine-DatePicker-calendarHeaderLevel')])[1]")
	private WebElement selectPreviousMonthHeading;
	
	@FindBy(xpath = "(//button[contains(@class,'mantine-DatePicker-calendarHeaderLevel')])[2]")
	private WebElement selectNextMonthHeading;

	@FindBy(xpath = "//button[@data-direction='previous']")
	private WebElement previousButton;
	
	@FindBy(xpath = "//button[@data-picker-control='true' and text()='Feb']")
	private WebElement selectMonth;

	@FindBy(xpath = "//button[contains(@class, 'mantine-DatePicker-day') and text()='12']")
	private WebElement beforeDateRange;

	@FindBy(xpath = "//button[@data-direction='next']")
	private WebElement nextButton;

	@FindBy(xpath = "//button[contains(@class, 'mantine-DatePicker-day') and text()='12']")
	private WebElement valueofDateRange;

	@FindBy(xpath = "//button[@data-testid='custom-apply-button']")
	private WebElement applyRangeButton;	
	
	@FindBy(xpath = "//span[contains(normalize-space(), 'between')]")
	private WebElement dateRangeSummary;
	
	@FindBy(xpath = "//*[contains(normalize-space(.),'Total outgoing alerted amount:')]")
	private WebElement outgoingAmountSummary;

	@FindBy(xpath = "//*[normalize-space()='Total Outgoing Amount']/following-sibling::*[1]")
	private WebElement totalOutgoingAmount;

	// --- Buttons & Elements Status Identifiers ---
	@FindBy(xpath = "//button[normalize-space()='Summarize']")
	private WebElement summarizeButton;

	@FindBy(xpath = "//button[normalize-space()='Add to Narrative']")
	private WebElement addToNarrativeButton;

	@FindBy(xpath = "//input[@id='tone-select']")
	private WebElement toneDropdownButton;
	private By toneExecutiveOptionLocator = By.xpath("//*[normalize-space()='Executive']");

	@FindBy(xpath = "//input[@id='style-select']")
	private WebElement styleDropdownButton;

	// Style Option items paths formulas
	private By bulletSummaryOption = By.xpath("//*[normalize-space()='Bullet Summary']");
	private By narrativeParagraphOption = By.xpath("//*[normalize-space()='Narrative Paragraph']");
	private By riskFocusedOption = By.xpath("//div[@value='risk_focused']");

	@FindBy(xpath = "//textarea[@placeholder='Additional Instructions']")
	private WebElement additionalInstructionsTextArea;

	@FindBy(xpath = "//input[@id='narrative-heading-input']")
	private WebElement narrativeHeadingElement;

	@FindBy(xpath = "//button[normalize-space()='Regenerate']")
	private WebElement regenerateButton;
	
	@FindBy(xpath = "//button[.//span[text()='Save']]")
	private WebElement saveButton;

	// Output Box for Generated Summary Text content
	@FindBy(xpath = "//div[@class='m_6d731127 mantine-Stack-root']//div//div[@class='m_6d731127 mantine-Stack-root']//div[@class='m_e615b15f mantine-Card-root m_1b7284a3 mantine-Paper-root']") 
	private WebElement generatedSummaryTextBox;  //(//div[@data-scrollbars='xy'])[last()]

	// --- Dynamic Target Lists ---
	private By transactionSummary = By.xpath("//a[.//*[name()='svg' and contains(@class, 'lucide-chart-column-big')]]");
	private By floatingTooltip = By.xpath("//div[@role='tooltip'] | //div[contains(@class, 'mantine-Tooltip-tooltip')]");


	// --- Action Execution Blocks ---
	public void navigateToAllIncidents() {
		wait.until(ExpectedConditions.elementToBeClickable(investigationAndResearchMenu)).click();
		wait.until(ExpectedConditions.elementToBeClickable(allIncidentsSubMenu)).click();
	}

	public void selectAllIncidentsfromDropDown() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(incidentsWorkList)).click();
		wait.until(ExpectedConditions.elementToBeClickable(workListDropDown)).click();
		Thread.sleep(1000);
	}
	
	public boolean isActiveFilterPresent() {

	    List<WebElement> filters = driver.findElements(
	        By.xpath("//span[contains(@class,'mantine-Badge-label') and contains(text(),'active')]")
	    );

	    return !filters.isEmpty();
	}

	public void clearAllFilters() {
	    wait.until(ExpectedConditions.elementToBeClickable(clearAllFilter)).click();

	    wait.until(ExpectedConditions.visibilityOf(filterIcon));
	}
	
	public void filterStatusAsContains() throws InterruptedException {

		// wait.until(ExpectedConditions.elementToBeClickable(statusFilterDot)).click();

		wait.until(ExpectedConditions.elementToBeClickable(filterIcon)).click();

		wait.until(ExpectedConditions.elementToBeClickable(filterModeIcon)).click();

		wait.until(ExpectedConditions.elementToBeClickable(addfilterMode)).click();

		wait.until(ExpectedConditions.visibilityOf(checkModeApplied));

		String filterModeText = checkModeApplied.getText();

		if (!filterModeText.contains("Contains")) {
			throw new AssertionError(
					"Filter Mode is not Contains. Actual text: " + filterModeText);
		}

		wait.until(ExpectedConditions.visibilityOf(enterFilterMode));

		enterFilterMode.click();
		enterFilterMode.sendKeys("New");

		Thread.sleep(500);

		new Actions(driver)
				.click(enterFilterMode)
				.sendKeys(Keys.ENTER)
				.perform();

		Thread.sleep(1500);

		// Check whether matching rows are available
		if (isNoMatchingRowsDisplayed()) {

			System.out.println("No matching rows found for status: New");
			return;
		}

		// Check whether the first row contains "New"
		if (firstRowStatus.getText().contains("New")) {

			System.out.println("Filter applied successfully. Status: New");

			wait.until(ExpectedConditions.visibilityOf(firstRowSelection));

			// Continue your automation here
			firstRowSelection.click();

		} else {

			System.out.println("Filter result is neither 'New' nor 'No matching rows'.");
		}
	}

	public boolean isNoMatchingRowsDisplayed() {
		try {
			return noMatchingRows.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public void selectFirstRowIncident() throws InterruptedException {
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(firstRowSelection)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(incidentNo)).click();
	}

	public void selectCustomDateRange() throws InterruptedException {

		// PART 1: Open Popover & Initialize
		wait.until(ExpectedConditions.elementToBeClickable(dateRangeFilter)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(customDateRange)).click();
		Thread.sleep(1000);
		 // Scroll to the required area
	    //scrollToElement(dateArea);
	}
	
	public void selectPreviousDateRange() throws InterruptedException {
		// PART 2: Handle Start Date (Feb 12)
		wait.until(ExpectedConditions.elementToBeClickable(selectPreviousMonthHeading)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(previousButton)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(selectMonth)).click();      // Feb
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(beforeDateRange)).click();  // 12
		Thread.sleep(1000);
	}
	
	public void selectNextDateRange() throws InterruptedException {
		// PART 3: Handle End Date (Aug 12)
		wait.until(ExpectedConditions.elementToBeClickable(selectNextMonthHeading)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(selectMonth)).click();
		Thread.sleep(1000);// Aug
		wait.until(ExpectedConditions.elementToBeClickable(valueofDateRange)).click(); // 12
		Thread.sleep(1000);

	}
	
	public boolean isApplyButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(applyRangeButton)).isEnabled();
	}
	
	public void clickApply() throws InterruptedException {
		// PART 4: Save & Apply Selection
				wait.until(ExpectedConditions.elementToBeClickable(applyRangeButton)).click();
	}
	
	public String getDisplayedDateRangeValue() {
	    String text = dateRangeFilter.getText().trim(); // Example: "Custom Range (Feb 12, 2024 – Feb 12, 2026)"

	    // Easiest 1-line regex extraction
	    return text.replaceAll(".*\\(|\\).*", "").trim();
	}
	
	public String getDateRangeFromSummary() {
	    String text = dateRangeSummary.getText().trim(); 
	    // Example: "The data shows 639 alerted transactions conducted between 02/12/2024 and 02/12/2026."

	    // 1. Remove everything up to the first date string
	    String cleanDates = text.replaceAll(".*between\\s+", ""); // Result: "02/12/2024 and 02/12/2026."
	    
	    // 2. Swap " and " with a clean dash and wipe the trailing full stop
	    return cleanDates.replace(" and ", " - ").replace(".", "").trim(); // Result: "02/12/2024 - 02/12/2026"
	}
//	public String getCalendarHeaderText() { 
//		return wait.until(ExpectedConditions.visibilityOf(selectPreviousMonthHeading)).getText().trim(); 
//	}
//	public String getAppliedFilterText() { 
//		return wait.until(ExpectedConditions.visibilityOf(dateRangeFilter)).getAttribute("value"); 
//	}
//	public boolean isCustomTabVisible() { 
//		return wait.until(ExpectedConditions.visibilityOf(customDateRange)).isDisplayed(); 
//	}
//	public boolean isCalendarHeaderVisible() { 
//		return wait.until(ExpectedConditions.visibilityOf(selectNextMonthHeading)).isDisplayed(); 
//	}

	public String getTransactionTooltipAndClick() {
		WebElement iconElement = wait.until(ExpectedConditions.visibilityOfElementLocated(transactionSummary));

		// 1. Move the real mouse cursor over the icon to force the dynamic tooltip to generate
		Actions actions = new Actions(driver);
		actions.moveToElement(iconElement).perform();

		// 2. Pause briefly for the CSS animation fade-in to populate the text
		try { 
			Thread.sleep(800); 
		} catch (InterruptedException e) { 
			Thread.currentThread().interrupt(); 
		}

		// 3. Extract the text string from the newly appeared tooltip container
		String tooltipText = wait.until(ExpectedConditions.visibilityOfElementLocated(floatingTooltip)).getText().trim();

		// 4. Perform the clean click operation
		iconElement.click();

		return tooltipText;
	}

	public boolean isSummarizeButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(summarizeButton)).isEnabled();
	}

	public void clickSummarize() {
		summarizeButton.click();
	}

	public boolean isAddToNarrativeButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(addToNarrativeButton)).isEnabled();
	}

	// Single unified method handling Tone selection operations
	public void selectToneExecutive() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(toneDropdownButton)).click();
		Thread.sleep(1000);
		driver.findElement(toneExecutiveOptionLocator).click();
	}

	// Line-by-line selection loop sequentially interacting with style configurations
	public void configureAllStyles() throws InterruptedException {
		// Option 1: Bullet Summary
		wait.until(ExpectedConditions.elementToBeClickable(styleDropdownButton)).click();
		Thread.sleep(800);
		driver.findElement(bulletSummaryOption).click();
		Thread.sleep(500);

		// Option 2: Narrative Paragraph
		styleDropdownButton.click();
		Thread.sleep(800);
		driver.findElement(narrativeParagraphOption).click();
		Thread.sleep(500);

		// Option 3: Risk-focused
		styleDropdownButton.click();
		Thread.sleep(800);
		driver.findElement(riskFocusedOption).click();
	}

	public void enterAdditionalInstructions(String dynamicInstructions) {
		WebElement textArea = wait.until(ExpectedConditions.visibilityOf(additionalInstructionsTextArea));
		textArea.sendKeys(dynamicInstructions);
	}

	public String getNarrativeHeadingText() {
		return wait.until(ExpectedConditions.visibilityOf(narrativeHeadingElement)).getAttribute("value");
	}

	public boolean isRegenerateButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(regenerateButton)).isEnabled();
	}

	public void clickRegenerate() {
		regenerateButton.click();
	}

	public String getGeneratedSummaryTextContent() throws InterruptedException {
		// Pauses to ensure background execution complete and summary layout populated
		wait.until(ExpectedConditions.visibilityOf(generatedSummaryTextBox));
		Thread.sleep(1000);
		return generatedSummaryTextBox.getText();
		
	}
	
	public boolean isSaveButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(saveButton)).isEnabled();
	}
	public void clickSave() {
		saveButton.click();
	}
}
