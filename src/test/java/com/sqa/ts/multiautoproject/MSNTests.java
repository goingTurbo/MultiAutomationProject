package com.sqa.ts.multiautoproject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MSNTests {

	private WebDriver driver; // I'm declaring the driver here so it can be used
								// throughout the entire class
	private String url = "http://www.msn.com"; // Creating a String of the URL
												// to be used at the beginning
												// of each test

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
	}

	@Test(priority = 1)
	public void msnCommentsCheck() throws InterruptedException {
		// this will make sure that the page has a chance to fully load
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// this will maximize the screen so it will show all visible elements on
		// the page
		driver.manage().window().maximize();
		driver.get(url);
		// this will close the overlay add for Windows 10 advertisement
		driver.findElement(By.xpath("html/body/div[4]/div/button")).click();
		driver.findElement(By.xpath(".//*[@id='main']/div[5]/ul/li[13]/div/ul/li[1]/a/span[1]")).click();
		// this will grab the # of comments in String form
		String count = driver.findElement(By.id("comment-count")).getText();
		// This will change it into integer from a string
		int commentCount = Integer.parseInt(count);
		// This will let the user know if the comments are between 10 & 50. If
		// it's not within these parameters, the test will fail.
		Assert.assertTrue(10 <= commentCount && commentCount >= 50);
	}

	@Test(priority = 2)
	public void msnDemoIssues() {
		driver.get(url);
		driver.findElement(By.xpath(".//*[@id='main']/div[5]/ul/li[15]/div/h3/a")).click();
		// this will create a List of all elements within the Democrats section.
		List<WebElement> allElements = driver
				.findElements(By.xpath(".//*[@id='main']/div[4]/div/div[1]/div[2]/div/ul"));
		int listSize = allElements.size();
		// this will check if there were 6 elements like there should be under
		// the democrat section
		Assert.assertTrue(listSize == 6);
	}

	@Test(priority = 3)
	public void msnAutoModelLookUp() {
		driver.get(url);
		// the following 3 select statements help select the options from the
		// dropdowns
		new Select(driver.findElement(By.xpath(".//*[@id='researchleadclassified']/div[1]/form/span[1]/select")))
				.selectByVisibleText("New & Used");
		new Select(driver.findElement(By.xpath(".//*[@id='researchleadclassified']/div[1]/form/span[2]/select")))
				.selectByVisibleText("Toyota");
		new Select(driver.findElement(By.xpath(".//*[@id='researchleadclassified']/div[1]/form/span[3]/select")))
				.selectByVisibleText("Corolla");
		driver.findElement(By.id("autos_button")).click();
		// this will confirm if the user is on the right page
		Assert.assertTrue(driver.getPageSource().contains("Toyota Corolla"));
		// after that previous Assert statement, this will check the secondary
		// search function for another car
		new Select(driver.findElement(By.id("findacar-makes"))).selectByVisibleText("BMW");
		new Select(driver.findElement(By.id("findacar-models"))).selectByVisibleText("7 Series");
		driver.findElement(By.xpath(".//*[@id='findacar']/input")).click();
		// this will confirm that the new page is correct
		Assert.assertTrue(driver.getPageSource().contains("BMW 7 Series"));
		driver.close();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
