package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Base.BrowserSetup;

public class InvestigationResearch extends BrowserSetup {
	WebDriver driver;
	WebDriverWait wait;

	public InvestigationResearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}
	
	// --- Left-side Navigation Menu ---
		@FindBy(xpath = "//span[normalize-space()='Investigation and Research']")
		private WebElement investigationAndResearchMenu;

		@FindBy(xpath = "//a[normalize-space()='All Incidents']")
		private WebElement allIncidentsSubMenu;   

		@FindBy(xpath = "//p[text()='All Incidents']")  //p[text()='All Incidents']
		private WebElement workListDropDown;

		@FindBy(xpath = "//input[@value='All Incidents']")
		private WebElement incidentsWorkList;
	
	 // Locator for the Scenarios dropdown field container
    @FindBy(xpath = "//button[@data-testid='scenarios-multiselect']")
    private WebElement scenariosDropdown;

    // Locator targeting all the text labels inside the open dropdown panel
    @FindBy(xpath = "//div[contains(@class,'mantine-Checkbox-root')]//label[contains(@class,'mantine-Checkbox-label')]")
    private List<WebElement> activeDropdownOptions;

    // Locator for the Apply button at the bottom of the dropdown
    @FindBy(xpath = "(//span[text()='Apply'])[2]")
    private WebElement applyButton;
    
    @FindBy(xpath = "//span[text()='Clear all']")  //button[@data-size='compact-xs']
    private WebElement clearAllButton;
    
  //button[@data-testid='signals-multiselect']
    @FindBy(xpath = "//button[@data-testid='signals-multiselect']")
    private WebElement signalsDropdown;
    
    private final By successNotificationLocator = By.xpath("//div[contains(@class, 'mantine-Notification-root')]//*[contains(text(), 'Successfully retrieved the list of incidents.')]");

    // Locator targeting all the text labels inside the open dropdown panel
//    @FindBy(xpath = "//div[@data-offset-scrollbars='xy']")
//    private List<WebElement> signalDropdownOptions;
	
    @FindBy(xpath = "//input[@aria-label='Search Incidents']")
    WebElement searchInput;
    
    @FindBy(xpath = "(//div[@id='grid-root-all_incidents_list']//input[@placeholder='Search']/following-sibling::div//button)[2]")
    WebElement enterSearch;
    
    @FindBy(xpath = "//*[local-name()='svg' and @class='lucide lucide-x lucide-icon']")
    WebElement clearSearch;

	// --- Stable & Dynamic Table Locators ---
	private By tableHeaders = By.xpath("//div[@id='grid-root-all_incidents_list']//div[@role='columnheader'] | //div[@id='grid-root-all_incidents_list']//th[contains(@class,'mantine-Table-th')]");
	private By fallbackTableHeaders = By.xpath("(//div[@id='grid-root-all_incidents_list']//tbody//tr[contains(@class, 'mantine-Table-tr')])/td[@data-index]");
	private By tableRows = By.xpath("//div[@id='grid-root-all_incidents_list']//tbody[contains(@class,'Table-tbody')]//tr[contains(@class, 'Table-tr')]");
	
	// --- Navigation Actions ---
	public void navigateToAllIncidents() {
		wait.until(ExpectedConditions.elementToBeClickable(investigationAndResearchMenu)).click();
		wait.until(ExpectedConditions.elementToBeClickable(allIncidentsSubMenu)).click();
	}

	public void selectAllIncidentsfromDropDown() {
		wait.until(ExpectedConditions.elementToBeClickable(incidentsWorkList)).click();
		wait.until(ExpectedConditions.elementToBeClickable(workListDropDown)).click();
	}
	
	public void openDropdown(String dropdownName) {
	    // Dynamically builds the locator using the name you pass ("scenarios" or "signals")
	    By dynamicDropdown = By.xpath("//button[@data-testid='" + dropdownName + "-multiselect']");
	    
	    WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(dynamicDropdown));
	    scrollToElement(trigger);
	    trigger.click();
	}

	/**
	 * Reusable method to parse available checkboxes inside whatever menu viewport is currently open.
	 * @return List of clean text option strings.
	 */
	public List<String> getOpenDropdownOptions() {
		wait.until(ExpectedConditions.visibilityOfAllElements(activeDropdownOptions));
		List<String> optionsList = new ArrayList<>();
		for (WebElement option : activeDropdownOptions) {
			String text = option.getText().trim();
			if (!text.isEmpty()) {
				optionsList.add(text);
			}
		}
		return optionsList;
	}
	
	/**
	 * Dynamic text-driven option locator to check target criteria boxes.
	 * @param optionToSelect The string value target choice label.
	 * @throws InterruptedException 
	 */
	
	public void selectDropdownOption(String optionToSelect) throws InterruptedException {
		String dynamicXpath = "//div[contains(@class,'mantine-ScrollArea-viewport')]//label[normalize-space()='" + optionToSelect + "']";
		Thread.sleep(1000);
		WebElement targetCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
		targetCheckbox.click();
	}
	
	/**
	 * Applies the selected choices and safely removes them using the clear all feature.
	 */
	public void applyFilter() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(applyButton));
		if (applyButton.isEnabled()) {
			applyButton.click();
		}
		Thread.sleep(2000); // Small standard execution layout settle pad
		    
	}
	
	public void clearFilter() throws InterruptedException {
	wait.until(ExpectedConditions.visibilityOf(clearAllButton));
	if (clearAllButton.isEnabled()) {
		clearAllButton.click();
	}
	//wait.until(ExpectedConditions.visibilityOfElementLocated(successNotificationLocator));
	}
	
	public void enterSearchQuery(String searchKeyphrase) throws InterruptedException {
	    
	    // Explicitly check for clickability and capture the element instance
	    WebElement searchInputField = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
	    
	    // Clear any dirty state and input the parameter text value
	    searchInputField.clear();
	    searchInput.sendKeys(searchKeyphrase, Keys.ENTER);
	    //wait.until(ExpectedConditions.elementToBeClickable(enterSearch)).click();
	    Thread.sleep(1000);
	    wait.until(ExpectedConditions.elementToBeClickable(clearSearch)).click();
	}

	 // Dynamically captures the current number of valid columns rendered in the table layout.
	
	public int getColumnCount() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("grid-root-all_incidents_list")));
		List<WebElement> columns = driver.findElements(tableHeaders);
		if (columns.isEmpty()) {
			columns = driver.findElements(fallbackTableHeaders);
		}
		return columns.size();
	}
	
	 //Scans the table headers and extracts clean text labels for the test class.
	public List<String> getTableColumnNames() {
		List<String> columnNamesList = new ArrayList<>();
		List<WebElement> headerElements = driver.findElements(tableHeaders);
		if (headerElements.isEmpty()) {
			headerElements = driver.findElements(fallbackTableHeaders);
		}
		for (WebElement element : headerElements) {
			String text = element.getText().trim();
			if (!text.isEmpty()) {
				columnNamesList.add(text);
			}
		}
		return columnNamesList;
	}

	/**
	 * Returns the live list of table row elements directly to the test class for formatting.
	 */
	public List<WebElement> getTableRows() {
		wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
		return driver.findElements(tableRows);
	}
}

