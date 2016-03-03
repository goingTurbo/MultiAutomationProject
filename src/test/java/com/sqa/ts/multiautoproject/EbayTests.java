package com.sqa.ts.multiautoproject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EbayTests {

	private WebDriver driver;
	private String url = "http://www.ebay.com";

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
	}

	@Test
	public void ebayWatchItem() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		// The following will login the user into their account
		driver.findElement(By.xpath(".//*[@id='gh-ug']/a")).click();
		driver.findElement(By.xpath(".//*[@id='SignInForm']/div[1]/div[2]/div[4]")).sendKeys("sqatestemail7@gmail.com");
		driver.findElement(By.xpath(".//*[@id='SignInForm']/div[1]/div[2]/div[5]")).sendKeys("appletree1");
		driver.findElement(By.id("sgnBt")).click();
		// this will search for the item that we want to watch
		driver.findElement(By.id("gh-ac")).sendKeys("round waffle maker", Keys.ENTER);
		driver.findElement(By.partialLinkText("ALL-CLAD CLASSIC ROUND")).click();
		// this will add the item to the user's watch this
		driver.findElement(By.linkText("Add to watch list")).click();
		driver.findElement(By.id("gh-eb-My")).click();
		// the next two lines will confirm that the item was in fact add to the
		// watch list
		String ebayCheck1 = driver.findElement(By.partialLinkText("ALL-CLAD CLASSIC ROUND")).getText();
		Assert.assertEquals("ALL-CLAD CLASSIC ROUND WAFFLE MAKER 7 SETTINGS ITEM #99012", ebayCheck1);
		driver.findElement(By
				.xpath(".//*[@id='watchlist']/div[2]/div[3]/div/table/tbody/tr/td[2]/div/div[2]/div[4]/div[1]/div/a/span"))
				.click();
		driver.findElement(By.className("p secondary-btn btn btn-s btn-scnd")).click();
	}

	@Test(dependsOnMethods = { "ebayWatchItem" })
	public void ebayTopMenuAdd() {
		driver.get(url);
		// the following 7 lines will go to Sporting Goods and select the
		// "Fitness & Running" from the drop down
		WebElement element = driver.findElement(By.linkText("Sporting Goods"));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		WebElement subElement = driver.findElement(By.linkText("Fitness & Running"));
		action.moveToElement(subElement);
		action.click();
		action.perform();
		// this will select the first image that leads to a category and adds an
		// item from the list
		driver.findElement(By.className("categoryMap1")).click();
		driver.findElement(By.linkText("CFF Pink Kettlebell Wrist Guards")).click();
		driver.findElement(By.id("isCartBtn_btn")).click();
		driver.findElement(By.id("gh-logo")).click();
		// Below will confirm if the items have been added or not with an Assert
		// verification
		String ebayCheck2 = driver.findElement(By.id("gh-cart-n")).getText();
		Assert.assertEquals("1", ebayCheck2);
		driver.findElement(By.id("gh-cart-i")).click();
		driver.findElement(By.className("action actionLink")).click();
	}

	@Test(dependsOnMethods = { "ebayTopMenuAdd" })
	public void ebayAddMessageFolder() {
		driver.get(url);
		// below will take you to the messages section of the account and create
		// a folder called favorites
		WebElement element = driver.findElement(By.linkText("My eBay"));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		WebElement subElement = driver.findElement(By.linkText("Messages"));
		action.moveToElement(subElement);
		action.click();
		action.perform();
		driver.findElement(By.id("adf")).click();
		driver.findElement(By.id("-1_inp_inp")).sendKeys("Favorites");
		driver.findElement(By.id("-1_Save")).click();
		String ebayCheck3 = driver.findElement(By.linkText("Favorites")).getText();
		Assert.assertEquals("Favorites", ebayCheck3);
		driver.close();
	}

	// @BeforeMethod
	// public void beforeMethod() {
	// }
	//
	//
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
