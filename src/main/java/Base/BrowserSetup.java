package Base;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ConfigReader;

public class BrowserSetup {

	public static WebDriver driver;
	public Properties prop;
	public WebDriverWait wait;
	
	public void setup() {
    	
		String browserName = ConfigReader.getProperty("browser").toLowerCase().trim();
		String appUrl = ConfigReader.getProperty("url").toLowerCase().trim();
	
        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();  //Creates Chrome browser
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }

       driver.manage().window().maximize();
//      WebDriverWait(driver, java.time.Duration.ofSeconds(20));
		
       driver.get(appUrl);
       wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(20));
       
   }

//	 public void scrollToElement(WebElement element) {
//		 new Actions(driver)
//	        .scrollToElement(element)
//	        .perform();
//	 }
	
	public void scrollToElement(WebElement element) {

	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",element);
	    //interface
	}
	
	public void selectDropdownByVisibleText(WebElement dropdown, String textToSelect) throws InterruptedException {

	    scrollToElement(dropdown);

	    dropdown.click();
	    Thread.sleep(1000);

	    WebElement option = driver.findElement(By.xpath("//div[@role='option' and .//span[normalize-space()='"+ textToSelect + "']]"));
	    option.click();
	}
    
    public void teardown() {
       if (driver != null) {
           driver.quit();
       }
    }

	
}
