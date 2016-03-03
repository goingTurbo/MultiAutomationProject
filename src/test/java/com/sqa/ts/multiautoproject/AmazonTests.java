package com.sqa.ts.multiautoproject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonTests {

	private WebDriver driver;
	private String url = "http://www.amazon.com";

	@BeforeClass
	public void setup() {
		driver = new FirefoxDriver();
	}

	@Test
	public void amazonAddItem() {
		AmazonReadFile amazonFile = new AmazonReadFile();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		// the following code will log you in to the Amazon account
		driver.findElement(By.id("nav-link-yourAccount")).click();
		driver.findElement(By.id("ap_email")).sendKeys("sqatestemail7@gmail.com");
		driver.findElement(By.id("ap_password")).sendKeys("appletree1");
		driver.findElement(By.id("signInSubmit")).click();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("java programming", Keys.ENTER);
		// this will add the item to the cart
		driver.findElement(By.partialLinkText("Head First Java")).click();
		driver.findElement(By.id("add-to-cart-button")).click();
		// the code below will confirm if there is an item in your cart and once
		// confirmed will delete the item
		String assertCheck = driver.findElement(By.partialLinkText("Proceed to checkout")).getText();
		Assert.assertEquals("Proceed to checkout (1 item)", assertCheck);
		driver.findElement(By.className("a-button-inner")).click();
		driver.findElement(By
				.xpath(".//*[@id='activeCartViewForm']/div[2]/div/div[4]/div[2]/div[1]/div/div/div[2]/div/span[1]/span/input"))
				.click();
	}

	@Test(dependsOnMethods = { "amazonAddItem" })
	public void amazonAddThreeItems() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		// the following code will add 3 items to the users cart
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("LED Flashlight", Keys.ENTER);
		driver.findElement(By.partialLinkText("Xtreme Bright Multipurpose")).click();
		new Select(driver.findElement(By.id("quantity"))).selectByVisibleText("3");
		driver.findElement(By.id("add-to-cart-button")).click();
		// the code confirms if 3 items were added or not and delete them once
		// confirmed
		String assertCheck2 = driver.findElement(By.partialLinkText("Proceed to checkout")).getText();
		Assert.assertEquals("Proceed to checkout (3 items)", assertCheck2);
		driver.findElement(By.className("a-button-inner")).click();
		driver.findElement(By
				.xpath(".//*[@id='activeCartViewForm']/div[2]/div/div[4]/div[2]/div[1]/div/div/div[2]/div/span[1]/span/input"))
				.click();
	}

	@Test(dependsOnMethods = { "amazonAddThreeItems" })
	public void amazonAddTwoDeleteOne() {
		// below will add 2 coffee cups & then confirm with an assert command
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Coffee Mug", Keys.ENTER);
		driver.findElement(By.partialLinkText("Copco 24-Ounce")).click();
		new Select(driver.findElement(By.id("quantity"))).selectByVisibleText("2");
		driver.findElement(By.id("add-to-cart-button")).click();
		String assertCheck3 = driver.findElement(By.partialLinkText("Proceed to checkout")).getText();
		Assert.assertEquals("Proceed to checkout (2 items)", assertCheck3);
		// this would add a different item to the cart and remove the 2 coffee
		// cups
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("bicycle gears WOTOW", Keys.ENTER);
		driver.findElement(By.partialLinkText("WOTOW Bike Chain Repair")).click();
		driver.findElement(By.id("add-to-cart-button")).click();
		driver.findElement(By.className("a-button-inner")).click();
		driver.findElement(By.name("submit.delete.CJNW8P9WBL7F5")).click();
		// this will confirm that only the one item remains
		Assert.assertTrue(driver.getPageSource().contains("Subtotal (1 item):"));
		driver.close();
	}
	//
	// @BeforeMethod
	// @Parameters("browser")
	// public void beforeMethod(String browser) {
	// if (browser.equalsIgnoreCase("chrome")) {
	// System.setProperty("webdriver.chrome.driver",
	// "C:/Users/Trevor/workspace/ClassProject/drivers/chromedriver.exe");
	// driver = new ChromeDriver();
	// } else if (browser.equalsIgnoreCase("firefox")) {
	// driver = new FirefoxDriver();
	// } else if (browser.equalsIgnoreCase("ie")) {
	// System.setProperty("webdriver.ie.driver",
	// "C:/Users/Trevor/workspace/ClassProject/drivers/IEDriverServer.exe");
	// driver = new InternetExplorerDriver();
	// } else if (browser.equalsIgnoreCase("safari")) {
	// driver = new SafariDriver();
	// }
	// }

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
