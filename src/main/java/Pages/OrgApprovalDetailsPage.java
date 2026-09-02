package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrgApprovalDetailsPage {

	    WebDriver driver;
	    WebDriverWait wait;

	    public OrgApprovalDetailsPage(WebDriver driver) {

	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	        PageFactory.initElements(driver, this);
	    }

	    // Approval Details tab
	    @FindBy(xpath = "//button[.//span[normalize-space()='Approval Details']]")
	    private WebElement approvalDetailsTab;


	    // Only get Timeline items that actually contain
	    // both Title and Content sections
	    //@FindBy(xpath = "//div[contains(@class,'mantine-Timeline-item') and .//p[@data-size='xs']]")
	    @FindBy(xpath = "//div[contains(@class,'mantine-Timeline-item') and .//div[contains(@class,'mantine-Timeline-itemTitle')] and .//div[contains(@class,'mantine-Timeline-itemContent')]]")
	    private List<WebElement> timelineItems;


	    // Click Approval Details tab
	    public void clickApprovalDetailsTab() {

	        wait.until(
	                ExpectedConditions.elementToBeClickable(approvalDetailsTab)
	        ).click();
	        
	        wait.until(
	                ExpectedConditions.visibilityOfAllElements(timelineItems)
	        );
	    }


	    // Get all complete timeline events
	    public List<WebElement> getTimelineItems() {

	        wait.until(
	                ExpectedConditions.visibilityOfAllElements(timelineItems)
	        );

	        return timelineItems;
	    }


	    // Get User
	    public String getUser(WebElement timelineItem) {

	        return timelineItem.findElement(
	                By.xpath(
	                    ".//div[contains(@class,'mantine-Timeline-itemTitle')]"
	                  + "//div[contains(@class,'mantine-Group-root')]/p[1]"
	                )
	        ).getText().trim();
	    }


	    // Get Action
	    public String getAction(WebElement timelineItem) {

	        return timelineItem.findElement(
	                By.xpath(
	                    ".//div[contains(@class,'mantine-Timeline-itemTitle')]"
	                  + "//div[contains(@class,'mantine-Group-root')]/p[3]"
	                )
	        ).getText().trim();
	    }


	    // Get Timestamp
	    public String getTimestamp(WebElement timelineItem) {

	        return timelineItem.findElement(
	                By.xpath(
	                    ".//div[contains(@class,'mantine-Timeline-itemContent')]"
	                  + "//p[@data-size='xs']"
	                )
	        ).getText().trim();
	    }
	}



