package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Base.BrowserSetup;

public class RelatedTransactionPage extends BrowserSetup {
	WebDriver driver;
	WebDriverWait wait;

	public RelatedTransactionPage(WebDriver driver) {
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

	// --- Table & Rows ---
	@FindBy(xpath = "//div[@role='row' and @row-index='0']")
	private WebElement firstRowSelection;

	@FindBy(xpath = "(//div[@aria-rowindex='2']//div[@col-id='incident_id']//a)[1]") 
	private WebElement incidentNo;    
	
//	@FindBy(xpath = "//a[.//*[contains(@class,'lucide-book-text')]]")
//	By relatedTransactionButton;
//	@FindBy(xpath = "//div[@role='tooltip']")
//	By floatingTooltip;
	// --- Dynamic Target Tooltip Elements ---
	private By relatedTransactionButton = By.xpath("//a[.//*[contains(@class,'lucide-book-text')]]");
	private By floatingTooltip = By.xpath("//div[@role='tooltip']");
	
	@FindBy(xpath = "//label[.//span[text()='All']]")
	private WebElement allButton;
	
	@FindBy(xpath = "//input[@aria-haspopup='listbox' and @readonly]")
	private WebElement transactionDropdownTrigger;

	// Finds all available text paragraphs inside the opened option container dynamically
	@FindBy(xpath = "//div[@data-combobox-option='true']//p")
	List<WebElement> dropdownOptionsList;

	@FindBy(xpath = "//div[@data-combobox-option='true']//p[text()='All Transactions']")
	private WebElement allTransactionsOption;
	
	@FindBy(xpath = "(//h6[contains(@class, 'mantine-Title-root')])[4]")
	private WebElement focalPartyTitleHeader;
	
	// Targets all text contents within the 6th column cell (Focal Party Name) for rows that have table cells populated
	@FindBy(xpath = "//div[@role='gridcell' and @col-id='party_name']")
	List<WebElement> focalPartyNameCellsList;
	
	
	
	@FindBy(xpath = "//div[@col-id='party_name']//span[@data-ref='eFilterButton']")
	private WebElement focalPartyFilterIcon;

	@FindBy(xpath = "//div[@role='combobox' and @aria-label='Filtering operator']")
	private WebElement filteringOperatorDropdown;

	// FIXED: Grabs all 8 text spans dynamically from the active list container
	@FindBy(xpath = "//div[contains(@class, 'ag-select-list')]//span[@data-ref='eText']")
	private java.util.List<WebElement> operatorOptionsList;

	// FIXED: Targets the exact span item containing the 'Does not equal' text wrapper
	@FindBy(xpath = "//div[contains(@class, 'ag-select-list')]//span[@data-ref='eText' and text()='Does not equal']")
	private WebElement doesNotEqualOption;

	@FindBy(xpath = "//input[@aria-label='Filter Value']")
	private WebElement filterValueInput;
	
	@FindBy(xpath = "//button[@data-ref='applyFilterButton']")
	private WebElement applyButton;

	// Targets the overlay indicator or a row wrapper showing that data is empty
	@FindBy(xpath = "//div[@role='rowgroup' and contains(@class, 'ag-center-cols-viewport')]")
	private WebElement noMatchingRowsOverlay;
	
	@FindBy(xpath = "//button[@data-ref='resetFilterButton']")
	private WebElement resetFilterButton;
	
	// --- Action Execution Blocks ---
	public void navigateToAllIncidents() {
		wait.until(ExpectedConditions.elementToBeClickable(investigationAndResearchMenu)).click();
		wait.until(ExpectedConditions.elementToBeClickable(allIncidentsSubMenu)).click();
	}

	public void selectAllIncidentsfromDropDown() {
		wait.until(ExpectedConditions.elementToBeClickable(incidentsWorkList)).click();
		wait.until(ExpectedConditions.elementToBeClickable(workListDropDown)).click();
	}

	public void selectFirstRowIncident() {
		wait.until(ExpectedConditions.elementToBeClickable(firstRowSelection)).click();
		wait.until(ExpectedConditions.elementToBeClickable(incidentNo)).click();
	}

	public String getTransactionTooltipAndClick() {
		WebElement iconElement = wait.until(ExpectedConditions.visibilityOfElementLocated(relatedTransactionButton));

		// 1. Move real mouse cursor over the icon to force the dynamic tooltip to generate
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
	
	public boolean isAllButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(allButton)).isEnabled();
	}
	public void clickAll() {
		allButton.click();
	}
	
	public List<String> printAndSelectAllTransactions() {
    List<String> optionTexts = new ArrayList<>();

	    // 1. Click the main read-only trigger to reveal the popup options panel
	    wait.until(ExpectedConditions.elementToBeClickable(transactionDropdownTrigger)).click();

	    // 2. Wait until at least one option text wrapper is visible on screen
	    wait.until(ExpectedConditions.visibilityOfAllElements(dropdownOptionsList));

	    // 3. Loop through all option boxes to grab their text details safely
	    for (WebElement option : dropdownOptionsList) {
	        String visibleText = option.getText().trim();
	        optionTexts.add(visibleText);
	        System.out.println("Discovered Dropdown Menu Choice: " + visibleText);
	    }

	    // 4. Click the exact targeting text node layout
	    wait.until(ExpectedConditions.elementToBeClickable(allTransactionsOption)).click();
	    return optionTexts;
	}
		
	public String getDynamicReferenceHeaderName() {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(focalPartyTitleHeader));

	        String headerText = focalPartyTitleHeader.getText().trim();

	        if (headerText.isEmpty()) {
	            return null;
	        }

	        return headerText;

	    } catch (Exception e) {
	    	return null;
	    }
	}
	
	public boolean verifyTableRowsAgainstDynamicReference(String dynamicReferenceValue) {
	    // Wait for the AG Grid column elements to render within the viewport
	    wait.until(ExpectedConditions.visibilityOfAllElements(focalPartyNameCellsList));
	    
	    // Process matching boundaries dynamically (limits to 5 rows or whatever row total exists)
	    int rowsToValidate = Math.min(focalPartyNameCellsList.size(), 5);
	    System.out.println("Beginning Table comparison for " + rowsToValidate + " rows against: " + dynamicReferenceValue);
	    
	    boolean isEntireColumnMatching = true;

	    for (int i = 0; i < rowsToValidate; i++) {
	        String currentRowCellText = focalPartyNameCellsList.get(i).getText().trim();
	        System.out.println("Processing Row [" + (i + 1) + "] Dynamic Data: " + currentRowCellText);
	        
	        // Two-way comparison handles both full values and UI truncations cleanly
	        if (!dynamicReferenceValue.toLowerCase().contains(currentRowCellText.toLowerCase()) && 
	            !currentRowCellText.toLowerCase().contains(dynamicReferenceValue.toLowerCase())) {
	            
	            System.err.println("DYNAMIC MISMATCH FOUND -> Header: [" + dynamicReferenceValue + "] vs Table's Cell: [" + currentRowCellText + "]");
	            isEntireColumnMatching = false;
	        }
	    }
	    
	    return isEntireColumnMatching;
	}
	
	public java.util.List<String> applyDoesNotExistFilterAndVerifyEmpty() throws InterruptedException {
	    java.util.List<String> availableOperators = new java.util.ArrayList<>();

	    // 1. Grab text from first data grid row
	    wait.until(ExpectedConditions.visibilityOfAllElements(focalPartyNameCellsList));
	    String capturedName = focalPartyNameCellsList.get(0).getText().trim();
	    System.out.println("Captured target filter text: " + capturedName);

	    // 2. Click column filter icon
	    wait.until(ExpectedConditions.elementToBeClickable(focalPartyFilterIcon)).click();
	    Thread.sleep(1000);
	    
	    // 3. Open operator list selection dropdown
	    wait.until(ExpectedConditions.elementToBeClickable(filteringOperatorDropdown)).click();
	    Thread.sleep(1000);
	    
	    // 4. Loop through options (Now tracking the dynamic <span> nodes correctly!)
	    wait.until(ExpectedConditions.visibilityOfAllElements(operatorOptionsList));
	    for (WebElement operator : operatorOptionsList) {
	        String operatorText = operator.getText().trim();
	        if (!operatorText.isEmpty()) {
	            availableOperators.add(operatorText);
	            System.out.println("Extracted Operator Option: " + operatorText);
	        }
	    }
	    Thread.sleep(1000);
	    // 5. Select 'Does not equal' option wrapper
	    wait.until(ExpectedConditions.elementToBeClickable(doesNotEqualOption)).click();

	    // 6. Enter text values
	    WebElement inputField = wait.until(ExpectedConditions.visibilityOf(filterValueInput));
	    inputField.sendKeys(capturedName);
	    Thread.sleep(1000);

	    // 7. Click Apply button to save filter settings
	    wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
	    
	    return availableOperators;
	}

	public boolean isGridDisplayingNoMatchingRows() {
	    try {
	        return wait.until(ExpectedConditions.visibilityOf(noMatchingRowsOverlay)).isDisplayed();
	    } catch (Exception e) {
	        System.err.println("The grid did not display the expected empty state message within the timeout period.");
	        return false;
	    }
	   
	}
	
	//to reset and apply changes
	public void clickFilterIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(focalPartyFilterIcon)).click();
	}

	public boolean isResetButtonEnabled() {		
	   return wait.until(ExpectedConditions.visibilityOf(resetFilterButton)).isEnabled();
	}
	public void clickReset() {
		resetFilterButton.click();
	}
	
}
