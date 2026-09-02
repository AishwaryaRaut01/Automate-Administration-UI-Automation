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
import org.testng.Assert;

import Base.BrowserSetup;

public class OrganizationPage extends BrowserSetup{

	WebDriver driver;
	WebDriverWait wait;

	public OrganizationPage(WebDriver driver) { // Constructor
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[normalize-space()='Administration']")
	WebElement adminmenu;

	@FindBy(xpath = "//a[normalize-space()='Organizations']")
	WebElement organization;

	// Locates the bold structural header text "launchpad" inside the content window
	@FindBy(xpath = "//span[normalize-space()='Organization Management']")
	WebElement orgmanagement; // Organization Management

	@FindBy(xpath = "//h1[normalize-space()='launchpad']")
	WebElement launchpadhead;

	@FindBy(xpath = "//button[.//*[contains(@class,'lucide-list-tree')]]")
	WebElement hierarchyDashButton;

	@FindBy(xpath = "//input[@placeholder='Name']")
	private List<WebElement> nameFields;

	@FindBy(xpath = "//button[.//span[normalize-space()='Add more']]")
	WebElement addMoreButton;

	@FindBy(xpath = "//div[contains(@class,'mantine-Stack-root')][.//p[contains(normalize-space(),'Do you really want to add it?')]]//button[.//span[normalize-space()='Yes']]")
	WebElement yesButton;

	@FindBy(xpath = "//button[.//span[text()='Save']]")
	WebElement saveButton;

//	@FindBy(xpath = "//div[contains(@class,'mantine-Stack-root')][.//p[contains(normalize-space(),'Are you sure, you want to continue?')]]//button[.//span[normalize-space()='Yes']]")
	@FindBy(xpath = "//button[.//span[normalize-space()='Yes']]")
	WebElement yesToSave;

	//@FindBy(xpath = "//h5[normalize-space()='Organization Hierarchy']/following-sibling::div//button[contains(@class,'mantine-ActionIcon-root')]")
	@FindBy(xpath = "//*[name()='svg' and contains(@class,'lucide-x')]")
	WebElement closeHierarchyButton;

	// + button next to launchpad in Organization Hierarchy
	@FindBy(xpath = "(//button[@type='button']/span[1])[5]")
	WebElement launchpadPlusButton;

	@FindBy(xpath = "//input[@name='name' and @placeholder='Name']")
	private WebElement organizationNameField;

	@FindBy(xpath = "//textarea[@name='description' and @placeholder='Description']")
	private WebElement descriptionField;

	// Save button inside New Organization Unit
	@FindBy(xpath = "//button[.//span[normalize-space()='Save']]")
	private WebElement saveOrganizationButton;

	@FindBy(xpath = "//button[normalize-space()='Yes']")
	WebElement yesToSaveOrg;

	@FindBy(xpath = "//h6[normalize-space()='Name']/following-sibling::p")
	private WebElement rightSideOrganizationName;

	@FindBy(xpath = "//h6[normalize-space()='Organization Code']/following-sibling::p")
	private WebElement organizationCode;

	@FindBy(xpath = "//h6[normalize-space()='Parent Organization']/following-sibling::p")
	private WebElement parentOrganization;

	@FindBy(xpath = "//h6[normalize-space()='Enabled']/following-sibling::p")
	private WebElement enabledStatus;

	@FindBy(xpath = "//h6[normalize-space()='Description']/following-sibling::p")
	private WebElement organizationDescription;

	@FindBy(xpath = "//h6[normalize-space()='Long Description']/following-sibling::p")
	private WebElement longDescription;

	@FindBy(xpath = "//h6[normalize-space()='Created By']/following-sibling::p")
	private WebElement createdBy;

	@FindBy(xpath = "//h6[normalize-space()='Created On']/following-sibling::p")
	private WebElement createdOn;

	@FindBy(xpath = "//h5[contains(@class,'cardHeading') and contains(normalize-space(.),'>')]/parent::div//button[1]")
	private WebElement editOrganizationButton;

	@FindBy(xpath = "//h6[normalize-space()='Name']/following-sibling::p")
	private WebElement newName;

	@FindBy(xpath = "//h6[normalize-space()='Description']/following-sibling::p")
	private WebElement newDescription;

	@FindBy(xpath = "//h6[normalize-space()='Updated On']/following-sibling::p")
	private WebElement updatedOn;

	@FindBy(xpath = "//h6[normalize-space()='Updated By']/following-sibling::p")
	private WebElement updatedBy;

	public void movetoOrganization() {
		wait.until(ExpectedConditions.elementToBeClickable(adminmenu));
		adminmenu.click();

		wait.until(ExpectedConditions.visibilityOf(organization));

		wait.until(ExpectedConditions.elementToBeClickable(organization));
		organization.click();
	}

	public String getOrgManagementText() {
		wait.until(ExpectedConditions.visibilityOf(orgmanagement));
		return orgmanagement.getText().trim();
	}

	public String getLaunchpadHeadingText() {
		wait.until(ExpectedConditions.visibilityOf(launchpadhead));
		return launchpadhead.getText().trim();
	}

	public void clickHierarchyDashButton() {
		wait.until(ExpectedConditions.elementToBeClickable(hierarchyDashButton)).click();
	}

	// Gets all currently available Organization Unit names
	public List<String> getAllOrganizationNames() {

		List<String> names = new ArrayList<>();

		List<WebElement> fields = driver.findElements(By.xpath("//input[@placeholder='Name']"));

		System.out.println("Total Fields Found: " + fields.size());
		for (WebElement field : fields) {

			String value = field.getAttribute("value").trim();
			if (!value.isEmpty()) {
				names.add(value);
			}
		}
		return names;
	}

	// Clicks the Add More button and confirms the action
	public void clickAddMore() {
		wait.until(ExpectedConditions.elementToBeClickable(addMoreButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(yesButton)).click();

	}

	// Enters an Organization Unit name into the last available Name field
	public void enterOrganizationName(String organizationName) throws InterruptedException {
		List<WebElement> fields = driver.findElements(By.xpath("//input[@placeholder='Name']"));

		// Target the newly appended blank entry at the bottom row boundary
		WebElement lastField = fields.get(fields.size() - 1);

		wait.until(ExpectedConditions.visibilityOf(lastField));
		Thread.sleep(1500);
		lastField.click();
		lastField.sendKeys(organizationName);
	}

	// Verifies whether "Name is required" validation message is displayed
	public boolean isNameRequiredMessageDisplayed() {

		By errorMessage = By.xpath("//*[normalize-space()='Name is required']");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
	}

	// Saves the Organization Unit
	public void clickSave() {

		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

		wait.until(ExpectedConditions.elementToBeClickable(yesToSave)).click();
	}

	public void closeHierarchy() {

		wait.until(ExpectedConditions.visibilityOf(closeHierarchyButton));
		wait.until(ExpectedConditions.elementToBeClickable(closeHierarchyButton));
		closeHierarchyButton.click();
	}

	public void clickLaunchpadAddButton() throws InterruptedException {
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(launchpadPlusButton)).click();
	}

	public void enterName(String organizationName) {
		wait.until(ExpectedConditions.elementToBeClickable(organizationNameField));
		organizationNameField.click();
		organizationNameField.sendKeys(organizationName);
	}

	public void enterDescription(String description) {
		// wait.until(ExpectedConditions.elementToBeClickable(descriptionField));
		// descriptionField.click();
		descriptionField.sendKeys(description);
	}

	public void saveOrganization() throws InterruptedException {

		wait.until(ExpectedConditions.visibilityOf(saveOrganizationButton));

		Assert.assertTrue(saveOrganizationButton.isEnabled(), "Save Organization button is not enabled");

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(saveOrganizationButton)).click();
		Thread.sleep(2000);
	}

	public void yesForAddOrg() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(yesToSaveOrg)).click();
		Thread.sleep(1000);
	}

	public void clickOrganization(String organizationName) throws InterruptedException {

		By organization = By
				.xpath("//p[contains(@class,'_treeLabel_') and normalize-space()='" + organizationName + "']");

		 // 1. Wait for presence in DOM so we can find its coordinates
	    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(organization));
	    
	    // 2. Scroll the element into the center of the viewport first
	    scrollToElement(element);
	    Thread.sleep(500); // Small native stabilization buffer to let the scroll animation freeze completely
	    
	    // 3. CRITICAL: Now verify it is fully clickable at its final resting screen position
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    
	    try {
	        element.click();
	    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
	        // Fallback: If a sticky overlay is still stubbornly blocking it, bypass via JavaScript
	        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	    }

	    // 4. Final step synchronization
	    wait.until(ExpectedConditions.visibilityOfElementLocated(organization));
		
//		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(organization));
//		
//		scrollToElement(element);
//		Thread.sleep(1500);
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(organization));
//		Thread.sleep(1500);
	}

	public String getRightSideOrganizationName() {
		return rightSideOrganizationName.getText();
	}

	public String getRightSideDescription() {
		return organizationDescription.getText();
	}

	public String getRightSideCreatedBy() {
		return createdBy.getText();
	}

	public String getRightSideCreatedOn() {
		return createdOn.getText();
	}

	public boolean isEditButtonEnabled() throws InterruptedException {
		Thread.sleep(1500);
		return wait.until(ExpectedConditions.visibilityOf(editOrganizationButton)).isEnabled();

	}

	public void editOrganization(String newName, String newDescription) throws InterruptedException {
		Thread.sleep(1500);

		wait.until(ExpectedConditions.elementToBeClickable(editOrganizationButton)).click();

		WebElement nameField = wait.until(ExpectedConditions.visibilityOf(organizationNameField));

		nameField.sendKeys(Keys.CONTROL, "a");
		nameField.sendKeys(Keys.BACK_SPACE);
		nameField.sendKeys(newName);

		WebElement descField = wait.until(ExpectedConditions.visibilityOf(descriptionField));

		// descField.click();`
		descField.sendKeys(Keys.CONTROL, "a");
		descField.sendKeys(Keys.BACK_SPACE);
		descField.sendKeys(newDescription);
	}

	public void saveEditedOrganization() {
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Save']")));

		Assert.assertTrue(saveButton.isEnabled(), "Save button is not enabled");
		saveButton.click();

		WebElement yesButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='Yes']]")));

		Assert.assertTrue(yesButton.isEnabled(), "Yes button is not enabled");
		yesButton.click();

		wait.until(ExpectedConditions.invisibilityOf(yesButton));
	}

	public String getNewName() {
		return wait.until(ExpectedConditions.visibilityOf(newName)).getText().trim();
	}

	public String getNewDescription() {
		return wait.until(ExpectedConditions.visibilityOf(newDescription)).getText().trim();
	}

	public String getUpdatedOn() {
		return wait.until(ExpectedConditions.visibilityOf(updatedOn)).getText().trim();
	}

	public String getUpdatedBy() {
		return wait.until(ExpectedConditions.visibilityOf(updatedBy)).getText().trim();
	}
}
