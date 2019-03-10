package com.verticalbytes.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent != null)
			return extent;
		ExtentHtmlReporter html = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/target/surefire-reports/html/extent.html");
		extent = new ExtentReports();
		html.loadConfig(System.getProperty("user.dir") + "/src//test/resources/extentconfig/reportsconfig.properties");
		extent.attachReporter(html);
		return extent;
	}
}
