package Pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	 WebDriver driver; 
	 WebDriverWait wait;

	    public LoginPage(WebDriver driver) {  //Constructor
	        this.driver = driver; 
	     // We wait up to 20 seconds for the redirect to finish and the logo to load 
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        
	        PageFactory.initElements(driver, this);  
	    }
	  
	@FindBy(xpath = "//input[@id='Email']")
	WebElement username;
	
	@FindBy(xpath = "//input[@id='Password']")
	WebElement password;
	
	@FindBy(xpath = "//span[@class='m_80f1301b mantine-Button-inner']")
	WebElement continuebtn;
	
	@FindBy(xpath = "//*[name()='path' and contains(@d,'m6 6 12 12')]")
	WebElement crosslogo;
	
	@FindBy(xpath = "//span[normalize-space()='Administration']")
	WebElement adminmenu;
	
	
	public void enterUsername(String admin_user) {
		username.sendKeys(admin_user);
	}
	
	public void enterPassword(String admin_pw) {
		password.sendKeys(admin_pw);
	}
	
	public boolean isContinueButtonEnabled() {
        return continuebtn.isEnabled();
    }
	
	public void clickSubmit() {
		continuebtn.click();
	}
	
	public boolean isCrossButtonEnabled() {
        return crosslogo.isEnabled();
    }
	
	public void clickCross() {
		crosslogo.click();
	}
	
	public void adminModule() {
		adminmenu.click();
	}
	
	// Inside your Pages.LoginPage class file
	public void loginToApplication(String admin_user, String admin_pw) throws InterruptedException {
	    
	    // 1. Safe null checks to keep Java from crashing
	    if (admin_user == null) { admin_user = ""; }
	    if (admin_pw == null) { admin_pw = ""; }

	    // 2. Clear fields and type text only if it exists
	    if (!admin_user.isEmpty()) { 
	        username.clear();
	        username.sendKeys(admin_user); 
	    }
	    
	    if (!admin_pw.isEmpty()) { 
	        password.clear();
	        password.sendKeys(admin_pw); 
	    }

	    // 3. Complete the click flow
	    if (isContinueButtonEnabled()) {
	        clickSubmit();
	    
            wait.until(ExpectedConditions.visibilityOf(crosslogo)); // Safely waits for your Page Factory logo element [INDEX]
	    }
	    
	    if (isCrossButtonEnabled()) {
	    	Thread.sleep(3500); 
	    	clickCross();
	    }
	    
	    wait.until(ExpectedConditions.visibilityOf(adminmenu));
   
	    
	}
	
}





