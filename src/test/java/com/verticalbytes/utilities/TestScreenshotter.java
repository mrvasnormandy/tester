package com.verticalbytes.utilities;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;
import com.verticalbytes.core.TestCore;

public class TestScreenshotter implements Screenshotter {
	private static final String SCREENSHOT_FOLDER = System.getProperty("user.dir") + "/target/surefire-reports/html/";

	private static Logger log = LogManager.getLogger();
	
	@Override
	public File captureScreenshot() throws IOException {
		log.info("Capturing screenshot...");
		TakesScreenshot ts = (TakesScreenshot) TestCore.driver;
		File f = ts.getScreenshotAs(OutputType.FILE);
		log.info("Captured screenshot");
		File to = new File(SCREENSHOT_FOLDER + generateScreenshotName());
		log.info("Saving screenshot...");
		Files.copy(f, to);
		log.info("Saved screenshot");
		return to;
	}

	private static String generateScreenshotName() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss-SSS")) + "-"
				+ TestCore.test.getModel().getName() + ".jpg";
	}
}
