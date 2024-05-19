package Srcpkg;

import org.testng.annotations.Test;
import org.testng.ITestResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Extent_Report 
{
	WebDriver driver;
	
	ExtentHtmlReporter reporter; //look & design
	ExtentTest test; //Updations
	ExtentReports extent; //Add new entries - Tester name, environment details, etc...
	
	@BeforeTest
	public void beforetest()
	{
		reporter = new ExtentHtmlReporter("./reporter/fbreport.html");
		reporter.config().setDocumentTitle("Automation");
		reporter.config().setReportName("Functional Test");
		reporter.config().setTheme(Theme.DARK);
		
		//Add new entry / write
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		
		extent.setSystemInfo("Host name", "Local Host");
		extent.setSystemInfo("OS", "Windows 11");
		extent.setSystemInfo("Tester name", "Amal");
		extent.setSystemInfo("Browser name", "Chrome Driver");
		
		driver = new ChromeDriver();
	}
	
	@BeforeMethod
	public void url()
	{
		driver.get("https://www.facebook.com/");
	}
	
	
	@Test
	public void fbtitleverifiction()
	{
		test = extent.createTest("fbtitleverifiction");
		String exp = "Facebook";
		String actual = driver.getTitle();
		org.testng.Assert.assertEquals(exp, actual);
	}
	
	@Test
	public void logo()
	{
		test = extent.createTest("logo");
		boolean b = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div[1]/div/img")).isDisplayed();
		org.testng.Assert.assertTrue(b);
	}
	
	@AfterTest
	public void teardown()
	{
		extent.flush();
	}

	@AfterMethod
	public void browserclose(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(Status.FAIL, "test case failed is "+result.getName());
			test.log(Status.FAIL, "reason is " +result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			test.log(Status.SKIP, "test case failed is "+result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(Status.PASS, "test case failed is "+result.getName());
		}	
	}
}
