package Reports;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import lib.BrowserDriverUtility;
import lib.ScreenshotUtility;

public class ExtentReportDemo {

	ExtentReports extent;
	ExtentTest logger;
	WebDriver dr;

	@BeforeMethod
	public void setup() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./GeneratedReports/report.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		logger = extent.createTest("Title Verification Test");
	}

	@Test
	public void loginTest() throws IOException {
		dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
				"C:\\Chetan\\Softs\\SeleniumSuite\\WebDrivers\\chromedriver.exe", "http://www.google.com");

		System.out.println("Title is: " + dr.getTitle());
		Assert.assertTrue(dr.getTitle().contains("Google"));
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			String path = ScreenshotUtility.CaptureScreenshot(dr, "MainPage");	
			logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}else {
			String path = ScreenshotUtility.CaptureScreenshot(dr, "MainPage");
			logger.pass("Title verified and it contains \"Google\".", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		extent.flush();
		dr.quit();
	}
}
