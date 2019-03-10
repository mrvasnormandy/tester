package com.verticalbytes.listeners;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.verticalbytes.core.TestCore;
import com.verticalbytes.utilities.TestScreenshotter;
import com.verticalbytes.utilities.email.Email;
import com.verticalbytes.utilities.email.Emailer;
import com.verticalbytes.utilities.email.SSLEmailer;
import com.verticalbytes.utilities.email.TestEmailer;

public class CustomListener extends TestCore implements ITestListener, ISuiteListener {
	@Override
	public void onTestFailure(ITestResult result) {
		File f = null;
		try {
			f = screenshotter.captureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		test.fail(result.getName().toUpperCase() + " failed with exception: " + result.getThrowable());
		try {
			test.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(f.getName()).build());
			test.addScreenCaptureFromPath(f.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		rep.flush();
	}

	@Override
	public void onFinish(ISuite suite) {
		try {
			Email email = Email.builder()//
					.to(new String[] { TestCore.config.getProperty("email.to.username") })//
					.subject(suite.getName() + " Test Suite Results")//
					.body(config.getProperty("jenkins.reports.url"))//
					.build();
			new TestEmailer().send(email);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ITestContext context) {

	}

	@Override
	public void onTestStart(ITestResult result) {
		test = rep.createTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.pass(result.getName() + " passed");
		rep.flush();
	}
}