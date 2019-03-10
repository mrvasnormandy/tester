package com.verticalbytes.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.verticalbytes.utilities.Browser;
import com.verticalbytes.utilities.ExtentManager;
import com.verticalbytes.utilities.Screenshotter;
import com.verticalbytes.utilities.TestScreenshotter;
import com.verticalbytes.utilities.email.Emailer;

public class TestCore {
	public static final String PROPERTIES_FOLDER = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\properties";
	public static WebDriver driver;
	public static final Properties config = new Properties();
	public static final Properties or = new Properties();
	public static final Logger logger = LogManager.getLogger();
	public static final Screenshotter screenshotter = new TestScreenshotter();
	public static final ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {
		if (driver != null)
			return;
		logger.info("Making preparations...");
		loadPropertyFiles();
		driver = getWebDriver();
		driver.get(config.getProperty("selenium.url.testsiteurl"));
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("selenium.implicit.wait")),
				TimeUnit.SECONDS);
		logger.debug("Preparations complete");
	}

	public void click(String css) {
		test.info("Clicking " + css);
		cssSelector(css).click();
	}

	public void type(String css, String keysToSend) {
		test.info("Typing \"" + keysToSend + "\" into " + css);
		cssSelector(css).sendKeys(keysToSend);
	}

	private WebElement cssSelector(String css) {
		return driver.findElement(By.cssSelector(css));
	}

	private void loadPropertyFiles() {
		logger.info("Loading property files...");
		loadConfigurationPropertiesFile();
		loadOrPropertiesFile();
		logger.info("Loaded property files");
	}

	private void loadOrPropertiesFile() {
		logger.info("Loading or.properties...");
		try (FileInputStream fis = new FileInputStream(PROPERTIES_FOLDER + "/OR.properties");) {
			or.load(fis);
			logger.info("Loaded or.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadConfigurationPropertiesFile() {
		logger.info("Loading config.properties...");
		try (FileInputStream fis = new FileInputStream(PROPERTIES_FOLDER + "/config.properties")) {
			config.load(fis);
			logger.info("Loaded config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private WebDriver getWebDriver() {
		Browser browser = getBrowser();
		browser.init();
		return browser.getWebDriver();
	}

	private Browser getBrowser() {
		String property = System.getenv("browser");
		if (property == null || property.isEmpty())
			property = config.getProperty("selenium.browser");
		logger.info("Loading browser for " + property + "...");
		Browser browser = Browser.valueOf(property.toUpperCase());
		logger.info("Loaded browser " + browser);
		return browser;
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
