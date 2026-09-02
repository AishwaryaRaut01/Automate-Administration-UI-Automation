package Pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.BrowserSetup;

public class RolesPage extends BrowserSetup{
    WebDriver driver;
    WebDriverWait wait;

    public RolesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators based on your UI screenshot layout
    @FindBy(xpath = "//span[normalize-space()='Administration']")
    private WebElement adminMenu;

    @FindBy(xpath = "//a[@href='/administration/roles']")
    private WebElement rolesSubMenu;

    @FindBy(xpath = "//button[.//span[normalize-space()='Add Role']]")
    private WebElement addRoleButton;
    
 // ORGANIZATION
    @FindBy(xpath = "//p[normalize-space()='launchpad']")
    WebElement launchpad;

    // Targets the input fields inside the 'Role' container box
    @FindBy(xpath = "//input[@id='Role Name']")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@id='description']")
    private WebElement descriptionInput;

    @FindBy(xpath = "//textarea[@id='Long description']")
    private WebElement longDescriptionInput;

    @FindBy(xpath = "//button[.//span[normalize-space()='Save']]")
    private WebElement saveRoleButton;

    @FindBy(xpath = "//button[normalize-space()='Yes']")
    private WebElement yesRoleButton;

    @FindBy(xpath = "//h3[normalize-space()='Roles List'] | //div[normalize-space()='Roles List']")
    private WebElement rolesListHeading;
    
 // --- Pagination Locators (Your Exact XPaths) ---
    @FindBy(xpath = "//button[@aria-label='Go to last page']")
    private WebElement lastPageButton; 

    @FindBy(xpath = "//input[@aria-labelledby='rpp-label' and @aria-haspopup='listbox']")
    private WebElement pageSelectButton;

    @FindBy(xpath = "//div[@role='option' and @value='5']")
    private WebElement pageValue;

    @FindBy(xpath = "//tr[.//td[@data-last-row='true']]")
    private WebElement lastRow;

    // --- Table Content Locators (Your Exact Data Index Tracking XPaths) ---
    @FindBy(xpath = "//td[@data-last-row='true' and @data-index='2']")
    private WebElement roleNameTableValue; // Maps to your firstNameValue example pattern

    @FindBy(xpath = "//td[@data-last-row='true' and @data-index='3']")
    private WebElement descriptionTableValue; // Maps to your emailValue example pattern

    @FindBy(xpath = "//td[@data-last-row='true' and @data-index='10']")
    private WebElement createdOnTableValue; // Maps to your createdOnValue example pattern

    public void navigateToRoles() {
        adminMenu.click();
        rolesSubMenu.click();
    }

    public boolean isAddRoleButtonEnabled() {
        return addRoleButton.isEnabled();
    }

    public void clickAddRole() {
        addRoleButton.click();
    }

 // ORGANIZATION
    public boolean isLaunchpadVisible() {

        return wait.until(
                ExpectedConditions.visibilityOf(launchpad)
        ).isDisplayed();
    }
    
    public void selectLaunchpad() throws InterruptedException {

        wait.until(
                ExpectedConditions.elementToBeClickable(launchpad)
        ).click();
        Thread.sleep(1000);
    }

 //Add data in input fields  
    public void fillRoleDetails(String name, String desc, String longDesc) {
    	wait.until(ExpectedConditions.visibilityOf(nameInput));
        nameInput.sendKeys(name);       
        descriptionInput.sendKeys(desc);    
        longDescriptionInput.sendKeys(longDesc);
    }

 // GET ACTUAL VALUES FROM UI
 	public String getName() {
 		return nameInput.getAttribute("value");
 	}

 	public String getDescription() {
 		return descriptionInput.getAttribute("value"); //gives actual text typed in this field
 	}

 	public String getLongDescription() {
 		return longDescriptionInput.getAttribute("value");
 	}
 	
//save
    public boolean isSaveButtonEnabled() {
        return saveRoleButton.isEnabled();
    }

    public void saveAndConfirm() {
    	scrollToElement(saveRoleButton);
        saveRoleButton.click();
        
        // Handle the dynamic confirmation overlay prompt click
        yesRoleButton.click();
    }

    public boolean verifyRolesListVisible() {
        return rolesListHeading.isDisplayed();
    }
    
 // --- Pagination Actions ---
    public void selectPageValue() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(pageSelectButton)).click();
        Thread.sleep(1500);
        wait.until(ExpectedConditions.elementToBeClickable(pageValue)).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(lastPageButton)).click();
    }

    // --- Grid Table Value Extraction Getters ---
    public String getDisplayedRoleName() throws InterruptedException {
        Thread.sleep(1000);
        scrollToElement(lastRow);
        return wait.until(ExpectedConditions.visibilityOf(roleNameTableValue)).getText().trim();
    }

    public String getDisplayedDescription() {
        return wait.until(ExpectedConditions.visibilityOf(descriptionTableValue)).getText().trim();
    }

    public String getDisplayedCreatedOn() {
        return wait.until(ExpectedConditions.visibilityOf(createdOnTableValue)).getText().trim();
    }
}
