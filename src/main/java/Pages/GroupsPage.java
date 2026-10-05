package Pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Base.BrowserSetup;

public class GroupsPage extends BrowserSetup {
	WebDriver driver;
    WebDriverWait wait;

    public GroupsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // --- Navigation ---
    @FindBy(xpath = "//span[normalize-space()='Administration']")
    private WebElement adminMenu;

    @FindBy(xpath = "//a[@href='/administration/groups']")
    private WebElement groupsSubMenu;

    @FindBy(xpath = "//button[.//span[normalize-space()='Add Group']]")
    private WebElement addGroupButton;

    // --- Input Form Fields ---
    @FindBy(xpath = "//input[@id='Group Name' or @placeholder='Name']")
    private WebElement nameInput;

    // Modified to match the unique ID from your image layout
    @FindBy(xpath = "//textarea[@id='Description' or @placeholder='Description']")
    private WebElement descriptionInput;

    // --- Dynamic Custom Dropdowns (Mantine Inputs) ---
    @FindBy(xpath = "//input[@placeholder='Select Role']")
    private WebElement roleDropdownInput;

    @FindBy(xpath = "//input[@placeholder='Select Member']")
    private WebElement memberDropdownInput;

    // --- Form Action Buttons ---
    @FindBy(xpath = "//button[normalize-space()='Add to List' or .//span[normalize-space()='Add to List']]")
    private WebElement addToListButton;

    @FindBy(xpath = "//button[normalize-space()='Save' or .//span[normalize-space()='Save']]")
    private WebElement saveGroupButton;

    @FindBy(xpath = "//button[normalize-space()='Yes']")
    private WebElement yesGroupButton;

    @FindBy(xpath = "//h5[normalize-space()='Group List']")
    private WebElement groupsListHeading;

    // --- Pagination Locators ---
    @FindBy(xpath = "//button[@aria-label='Go to last page']")
    private WebElement lastPageButton; 

    @FindBy(xpath = "//input[@aria-labelledby='rpp-label' and @aria-haspopup='listbox']")
    private WebElement pageSelectButton;

    @FindBy(xpath = "//div[@role='option' and @value='5']")
    private WebElement pageValue;

    @FindBy(xpath = "(//div[@role='row' and @row-id])[last()]")
    private WebElement lastRow;

    // --- Table Content Locators ---
    @FindBy(xpath = "(//div[@role='row' and @row-id]//div[@role='gridcell' and @col-id='name'])[last()]")
    private WebElement groupNameTableValue; 

    @FindBy(xpath = "(//div[@role='row' and @row-id]//div[@role='gridcell' and @col-id='description'])[last()]")
    private WebElement descriptionTableValue; 

    @FindBy(xpath = "(//div[@role='row' and @row-id]//div[@role='gridcell' and @col-id='created_on'])[last()]")
    private WebElement createdOnTableValue; 

    // --- Action Methods ---
    public void navigateToGroups() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(groupsSubMenu)).click();
    }

    public boolean isAddGroupButtonEnabled() {
        return wait.until(ExpectedConditions.visibilityOf(addGroupButton)).isEnabled();
    }

    public void clickAddGroup() {
        addGroupButton.click();
    }

    public void fillGroupDetails(String name, String desc) {
        wait.until(ExpectedConditions.visibilityOf(nameInput)).clear();
        nameInput.sendKeys(name);
        descriptionInput.clear();
        descriptionInput.sendKeys(desc);
    }

    // GET ACTUAL VALUES FROM UI FIELDS
    public String getUsername() {
        return nameInput.getAttribute("value");
    }

    public String getDescription() {
        return descriptionInput.getAttribute("value");
    }

    public void selectRole(String roleName) throws InterruptedException {
        selectDropdownByVisibleText(roleDropdownInput, roleName);
    }

    public void selectMember(String memberName) throws InterruptedException {
        selectDropdownByVisibleText(memberDropdownInput, memberName);
    }

    public boolean isAddToListButtonEnabled() {
        return addToListButton.isEnabled();
    }

    public void clickAddToList() {
        addToListButton.click();
    }

    public boolean isSaveButtonEnabled() {
        return saveGroupButton.isEnabled();
    }

    public void saveAndConfirm() {
        scrollToElement(saveGroupButton);
        saveGroupButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(yesGroupButton)).click();
    }

    public boolean verifyGroupsListVisible() {
        return wait.until(ExpectedConditions.visibilityOf(groupsListHeading)).isDisplayed();
    }

    // --- Pagination Actions ---
    public void selectPageValue() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(pageSelectButton)).click();
        Thread.sleep(1500);
        wait.until(ExpectedConditions.elementToBeClickable(pageValue)).click();
//        Thread.sleep(1000);
//        wait.until(ExpectedConditions.elementToBeClickable(lastPageButton)).click();
    }

    // --- Grid Table Content Extractors ---
    public String getDisplayedGroupName() throws InterruptedException {
        Thread.sleep(1000);
        scrollToElement(lastRow);
        return wait.until(ExpectedConditions.visibilityOf(groupNameTableValue)).getText().trim();
    }

    public String getDisplayedDescription() {
        return wait.until(ExpectedConditions.visibilityOf(descriptionTableValue)).getText().trim();
    }

    public String getDisplayedCreatedOn() {
        return wait.until(ExpectedConditions.visibilityOf(createdOnTableValue)).getText().trim();
    }
}
