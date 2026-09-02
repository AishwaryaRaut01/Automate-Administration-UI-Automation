package Utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListener implements ITestListener {

    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
    	
    	String reportPath = System.getProperty("user.dir")+ "/Reports/AutomationReport.html";
        sparkReporter = new ExtentSparkReporter(reportPath);

        System.out.println("[INFO] Report path: " + reportPath);

        sparkReporter = new ExtentSparkReporter(reportPath);
        
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("UI Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("OS", "Windows 11");
        extent.setSystemInfo("Browser", "Chrome");
    }

    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getName());
        info("Starting execution of test: " + result.getName());
        //test.log(Status.INFO, "Starting execution of test case: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

//        test.log(Status.PASS,"Test case passed: " + result.getName());
    	  pass("Test case passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        fail("Test case failed: " + result.getName());
        fail(result.getThrowable().toString());
    	
//    	test.log(Status.FAIL,"Test case failed: " + result.getName());
//        test.log(Status.FAIL,result.getThrowable());

        try {

            WebDriver activeDriver = Base.BrowserSetup.driver;

            if (activeDriver != null) {
            	
            	// 1. Setup the physical screenshot path inside your Eclipse Project folder
                String projectPath = System.getProperty("user.dir");
                String screenshotFolderName = "/screenshots/";
                String screenshotName = result.getName() + "_" + System.currentTimeMillis() + ".png";
                String absoluteFilePath = projectPath + screenshotFolderName + screenshotName;

                // 2. Ensure the screenshots directory exists in Eclipse
                File directory = new File(projectPath + screenshotFolderName);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 3. Capture the physical file object and save it to Eclipse workspace
                TakesScreenshot screenshotter = (TakesScreenshot) activeDriver;
                File sourceFile = screenshotter.getScreenshotAs(OutputType.FILE);
                File destinationFile = new File(absoluteFilePath);
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("[INFO] Screenshot saved in Eclipse at: " + absoluteFilePath);

                // 4. Capture the Base64 String and attach it to Extent Report
                String base64Screenshot = screenshotter.getScreenshotAs(OutputType.BASE64);
            	
               // String base64Screenshot = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.BASE64);

                test.fail(
                        "Failure Screenshot",
                        com.aventstack.extentreports.MediaEntityBuilder
                                .createScreenCaptureFromBase64String(base64Screenshot).build());
                
                System.out.println("[INFO] Screenshot captured successfully.");
                
            }

        } catch (Exception e) {

            test.log(Status.WARNING,
                    "Unable to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    	 warning("Test case skipped: " + result.getName());
      //  test.log(Status.SKIP, "Test case skipped: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

 // Helper Methods for -> Console + Extent 
    public static void info(String message) {
        System.out.println("[INFO] " + message);
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public static void pass(String message) {
        System.out.println("[PASS] " + message);
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    public static void fail(String message) {
        System.out.println("[FAIL] " + message);
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }

    public static void warning(String message) {
        System.out.println("[WARNING] " + message);
        if (test != null) {
            test.log(Status.WARNING, message);
        }
    }
}