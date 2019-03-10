package com.verticalbytes.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public enum Browser {
	CHROME {
		@Override
		public WebDriver getWebDriver() {
			return new ChromeDriver();
		}

		@Override
		public void init() {
			System.setProperty("webdriver.chrome.driver", EXECUTABLES_FOLDER + "/chromedriver.exe");
		}

		@Override
		public String toString() {
			return "Chrome";
		}
	},
	FIREFOX {
		@Override
		public WebDriver getWebDriver() {
			return new FirefoxDriver();
		}

		@Override
		public void init() {
			System.setProperty("webdriver.gecko.driver", EXECUTABLES_FOLDER + "/geckodriver.exe");
		}

		@Override
		public String toString() {
			return "Firefox";
		}
	},
	IE {
		@Override
		public WebDriver getWebDriver() {
			return new InternetExplorerDriver();
		}

		@Override
		public void init() {
			System.setProperty("webdriver.ie.driver", EXECUTABLES_FOLDER + "/IEDriverServer.exe");
		}

		@Override
		public String toString() {
			return "Internet Explorer";
		}
	};
	private static final String EXECUTABLES_FOLDER = System.getProperty("user.dir") + "/src/test/resources/executables";

	public abstract void init();

	public abstract WebDriver getWebDriver();
}
