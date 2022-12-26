package com.org.tester.webtest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
	WebDriver wd = null;
	
	
	@BeforeTest
	@Parameters("runmode")
	public void setupbrowser(String mode) {
			
		if(mode.equalsIgnoreCase("local")) {
			WebDriverManager.chromedriver().setup();
			wd = new ChromeDriver();
		}else {
			try {
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName(BrowserType.CHROME);
				wd = new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"), dc);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		wd.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.get("http://www.hyrtutorials.com/p/alertsdemo.html");
		wd.manage().window().maximize();
		
		
	}
	@Test
	public void fillthepage() {
		try {
			By button = By.xpath("//button[@id='promptBox']");
			
			WebElement bt = wd.findElement(button);
			scrollpage(wd,bt);
			Actions act = new Actions(wd);
			act.moveToElement(bt).build().perform();
			bt.click();
			Alert alt = wd.switchTo().alert();
			System.out.println(alt.getText());
			alt.sendKeys("ashok");
			alt.accept();
			wd.switchTo().activeElement();
		
			System.out.println(wd.findElement(By.id("output")).getText());
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	

	public void scrollpage(WebDriver wd, WebElement ele) {
		
		JavascriptExecutor jse =  (JavascriptExecutor)wd;
		jse.executeScript("arguments[0].scrollIntoView();",ele);
		
	}
	
	@Test
	public int addnumbers(By num1,By num2) {
		String no1 = wd.findElement(num1).getText();
		String no2 = wd.findElement(num2).getText();
		if(!no1.isEmpty() && !no2.isEmpty()) {
			return Integer.valueOf(no1)+Integer.valueOf(no2);
		}
		return 0;
	}
	
	@AfterTest
	public void kill() {
		if(wd != null) {
			wd.quit();
		}
	}
	
	
    
}
