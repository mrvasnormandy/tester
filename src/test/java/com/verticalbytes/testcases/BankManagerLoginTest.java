package com.verticalbytes.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.verticalbytes.core.TestCore;

public class BankManagerLoginTest extends TestCore {

	@Test
	public void loginAsBankManager() throws InterruptedException {
		click(or.getProperty("bmlBtn"));
		Assert.assertTrue(isElementPresent(By.cssSelector(or.getProperty("addCustBtn"))));
	}
}
