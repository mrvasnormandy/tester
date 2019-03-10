package com.verticalbytes.utilities;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

public interface Screenshotter {
	public File captureScreenshot() throws IOException;
}
