package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.BrowserSetup;

public class UsersPage extends BrowserSetup{

	WebDriver driver;
	WebDriverWait wait;

	public UsersPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}
	
	// ================= USERS MODULE =================

	@FindBy(xpath = "//span[normalize-space()='Administration']")
	WebElement adminmenu;

	@FindBy(xpath = "//a[@href='/administration/users']")
	WebElement usersMenu;

	@FindBy(xpath = "//button[.//span[normalize-space()='Add User']]")
	WebElement addUserButton;

	// ================= ORGANIZATION =================

	@FindBy(xpath = "//p[normalize-space()='launchpad']")
	WebElement launchpad;

	// ================= USER FIELDS =================

	@FindBy(xpath = "//input[@data-testid='Username']")
	public WebElement usernameField;

	@FindBy(xpath = "//input[@data-testid='Email Id']")
	public WebElement emailField;

	@FindBy(xpath = "//input[@data-testid='Password']")
	public WebElement passwordField;

	@FindBy(xpath = "//input[@data-testid='First Name']")
	public WebElement firstNameField;

	@FindBy(xpath = "//input[@data-testid='Last Name']")
	public WebElement lastNameField;

	// ================= SAVE =================

	@FindBy(xpath = "//button[.//span[normalize-space()='Save']]")
	WebElement saveButton;

	@FindBy(xpath = "//button[.//span[normalize-space()='Yes']]")
	WebElement yesButton;
	
	@FindBy(xpath = "//h5[normalize-space()='Users List']")
	WebElement usersHeading;

	// ============== validate added details ================
	
	@FindBy(xpath = "//button[@aria-label='Go to last page']")
	WebElement lastPageButton; 

	@FindBy(xpath = "//input[@aria-labelledby='rpp-label' and @aria-haspopup='listbox']")
	WebElement pageSelectButton;

	@FindBy(xpath = "//div[@role='option' and @value='5']")
	WebElement pageValue;

	@FindBy(xpath = "//tr[.//td[@data-last-row='true']]")
	WebElement lastRow;

	@FindBy(xpath = "//td[@data-last-row='true' and @data-index='3']")
	WebElement firstNameValue;

	@FindBy(xpath = "//td[@data-last-row='true' and @data-index='6']")
	WebElement emailValue;

	@FindBy(xpath = "//td[@data-last-row='true' and @data-index='10']")
	WebElement createdOnValue;

	// USERS MODULE
	//have to remove when all test run together
	public void movetoOrganization() {
		wait.until(ExpectedConditions.elementToBeClickable(adminmenu));
		adminmenu.click();
	}

	public void clickUsers() {
		wait.until(ExpectedConditions.elementToBeClickable(usersMenu)).click();
	}

	// ADD USER
	public boolean isAddUserButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(addUserButton)).isEnabled();
	}

	public void clickAddUser() {
		wait.until(ExpectedConditions.elementToBeClickable(addUserButton)).click();
	}

	// ORGANIZATION
	public boolean isLaunchpadVisible() {
		return wait.until(ExpectedConditions.visibilityOf(launchpad)).isDisplayed();
	}

	public void selectLaunchpad() {
		wait.until(ExpectedConditions.elementToBeClickable(launchpad)).click();
	}

	// REQUIRED FIELD VALIDATION
	public boolean validateRequiredField(WebElement field, String fieldName) {

		//field.click();
		field.sendKeys(Keys.TAB);

		By validationMessage = By.xpath(
				"//*[normalize-space()='" + fieldName + " is required' or normalize-space()='Invalid Credentials']"
				);

		for (int i = 0; i < 20; i++) {

			if (!driver.findElements(validationMessage).isEmpty()) {
				return true;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return false;
			}
		}

		return false;
	}

	// ENTER USER DETAILS
	public void enterUserDetails(
			String username,
			String email,
			String password,
			String firstName,
			String lastName) {

		usernameField.sendKeys(username);
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		firstNameField.sendKeys(firstName);
		lastNameField.sendKeys(lastName);
	}

	// GET ACTUAL VALUES FROM UI
	public String getUsername() {
		return usernameField.getAttribute("value");
	}

	public String getEmail() {
		return emailField.getAttribute("value"); //gives actual text typed in this field
	}

	public String getPassword() {
		return passwordField.getAttribute("value");
	}

	public String getFirstName() {
		return firstNameField.getAttribute("value");
	}

	public String getLastName() {
		return lastNameField.getAttribute("value");
	}

	// SAVE
	public boolean isSaveButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(saveButton)).isEnabled();
	}

	public void clickSave() {
		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	}

	// YES CONFIRMATION
	public boolean isYesButtonEnabled() {
		return wait.until(ExpectedConditions.visibilityOf(yesButton)).isEnabled();
	}

	// VALIDATE DATA
	public void clickYesAndNextPage() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(yesButton)).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOf(usersHeading));
		
	}

	public void selectPageValue() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(pageSelectButton)).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(pageValue)).click();
		wait.until(ExpectedConditions.elementToBeClickable(lastPageButton)).click();
		
	}

	public String getDisplayedFirstName() throws InterruptedException {
		Thread.sleep(1000);
		scrollToElement(lastRow);
		return wait.until(ExpectedConditions.visibilityOf(firstNameValue )).getText().trim();

	}
	//    public String getDisplayedFirstName() {
	//
	//    	scrollToElement(firstNameValue);
	//        return wait.until(ExpectedConditions.visibilityOf(firstNameValue)).getText().trim();
	//    }

	public String getDisplayedEmail() {
		return wait.until(ExpectedConditions.visibilityOf(emailValue)).getText().trim();
	}

	public String getDisplayedCreatedOn() {
		return wait.until(ExpectedConditions.visibilityOf(createdOnValue)).getText().trim();
	}
}