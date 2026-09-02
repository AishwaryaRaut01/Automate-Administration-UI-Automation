package Pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.BrowserSetup;

public class PermissionsPage extends BrowserSetup{

    WebDriver driver;
    WebDriverWait wait;

    public PermissionsPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // PERMISSIONS MODULE
    @FindBy(xpath = "//span[normalize-space()='Administration']")
    WebElement adminmenu;

    @FindBy(xpath = "//a[@href='/administration/permissions']")
    WebElement permissionsMenu;

    @FindBy(xpath = "//button[.//span[normalize-space()='Add Permission']]")
    WebElement addPermissionButton;
    
    // ORGANIZATION
    @FindBy(xpath = "//p[normalize-space()='launchpad']")
    WebElement launchpad;

    // DROPDOWNS
    @FindBy(xpath = "//input[@placeholder='Select Module']")
    WebElement selectModuleDropdown;

    @FindBy(xpath = "//input[@placeholder='Select Permissions']")
    WebElement selectPermissionsDropdown;

    // SAVE
    @FindBy(xpath = "//button[.//span[normalize-space()='Save']]")
    WebElement saveButton;

    @FindBy(xpath = "//button[.//span[normalize-space()='Yes']]")
    WebElement yesButton;
    
    @FindBy(xpath = "//h5[normalize-space()='Permissions List']")
	WebElement permissionsHeading;

    
    // PERMISSIONS MODULE
    public void moveToPermissions() {

        wait.until(ExpectedConditions.elementToBeClickable(adminmenu)).click();

        wait.until(
        		ExpectedConditions.elementToBeClickable(permissionsMenu)).click();
    }

    // ADD PERMISSION
    public boolean isAddPermissionButtonEnabled() {

        return wait.until(
                ExpectedConditions.visibilityOf(addPermissionButton)).isEnabled();
    }

    public void clickAddPermission() {

        wait.until(
                ExpectedConditions.elementToBeClickable(addPermissionButton)).click();
    }

    // ORGANIZATION
    public boolean isLaunchpadVisible() {

        return wait.until(
                ExpectedConditions.visibilityOf(launchpad)).isDisplayed();
    }

    public void selectLaunchpad() throws InterruptedException {

        wait.until(
                ExpectedConditions.elementToBeClickable(launchpad)).click();
        Thread.sleep(1000);
    }

    public void selectModule(String moduleName) throws InterruptedException {

        selectDropdownByVisibleText(selectModuleDropdown,moduleName);
        Thread.sleep(1000);
    }

    public void selectPermission(String permissionName) throws InterruptedException {

        selectDropdownByVisibleText(selectPermissionsDropdown,permissionName);
        Thread.sleep(1000);
    }

    // SAVE
    public boolean isSaveButtonEnabled() {

        return wait.until(
                ExpectedConditions.visibilityOf(saveButton)).isEnabled();
    }

    public void clickSaveAndConfirm() throws InterruptedException {

        wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)).click();

        wait.until(
                ExpectedConditions.visibilityOf(yesButton));

        if (yesButton.isEnabled()) {

            yesButton.click();
        }
        
        Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOf(permissionsHeading));
    }
}