package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import atu.alm.wrapper.ALMServiceWrapper;
import atu.alm.wrapper.enums.StatusAs;
import atu.alm.wrapper.exceptions.ALMServiceException;

/**
 * 
 * @author Automation Tester http://automationtestingutilities.blogspot.in/
 */
public class SeleniumALMIntegrationExample {
	WebDriver driver;

	@BeforeClass
	public void init() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void login() throws InterruptedException, ALMServiceException {
		// Open Wordpress App
		driver.get("http://demo.opensourcecms.com/wordpress/wp-login.php");
		// Enter UserName
		Thread.sleep(3000);
		driver.findElement(By.id("user_login")).clear();
		driver.findElement(By.id("user_login")).sendKeys("admin");
		// Enter Password
		driver.findElement(By.id("user_pass")).clear();
		driver.findElement(By.id("user_pass")).sendKeys("demo123");
		// Click on Submit button
		driver.findElement(By.id("wp-submit")).submit();
		updateResults();
		System.out.println("Done Updating Results");
	}

	public void updateResults() throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper(
				"http://localhost:8081/qcbin");
		wrapper.connect("admin", "admin", "DEFAULT", "SampleProject");
		wrapper.updateResult("SampleTestSetFolder\\SubTestSetFolder1",
				"TestSet3", 62, "Login", StatusAs.PASSED);
		wrapper.close();
	}

	@AfterClass
	public void close() {
		driver.quit();
	}
}
