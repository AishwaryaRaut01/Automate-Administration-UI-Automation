package Utilities;

import org.openqa.selenium.WebDriver;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;


public class AccessibilityUtil {

    public static boolean checkAccessibility(WebDriver driver) {

        System.out.println("[INFO] Running accessibility scan...");
        
        //can be tested for particular level(a,aa) and verions(wcag 2.2 or 2.1) -> Results results = new AxeBuilder().withTags(Arrays.asList("wcag22aa")).analyze(driver);
        //also, can skip specific rule if we want -> Results results = new AxeBuilder().disableRules(Arrays.asList("region")).analyze(driver);

        AxeBuilder axeBuilder = new AxeBuilder(); //Instantiates `new AxeBuilder()` Deque accessibility library wrapper

        Results results = axeBuilder.analyze(driver); //Injects axe.js(axe.min.js) into the running browser via Selenium

        if (results.getViolations().isEmpty()) {   //Checking for absolute success

            ExtentReportListener.pass("No accessibility violations found.");

            return true;
        }
        
        //runs when getViolations().isEmpty() is false
        ExtentReportListener.info(
                "Accessibility violations found: "
                + results.getViolations().size()
        );

        for (Rule violation : results.getViolations()) {  //Processing and Extracting Violations
        	//these predefined getters methods retrieve from axe engine (AxeBuilder)
            String message =
                    "Violation: " + violation.getId() //violation.getId() -> Pulls the unique programmatic code name (e.g., "page-has-heading-one")
                    + " | Impact: " + violation.getImpact() //Pulls the severity rating (e.g., "critical", "serious", "moderate", "minor")
                    + " | Description: " + violation.getDescription(); //Pulls the human-readable explanation of what is broken
            
           // System.out.println("[FAIL] " + message);
            ExtentReportListener.info(message);
            System.out.println("Official Guideline Help URL: " + violation.getHelpUrl()); // Direct link to fix documentation
        }

        return false;
    }
}
