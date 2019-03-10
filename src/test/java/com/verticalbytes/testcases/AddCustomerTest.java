package com.verticalbytes.testcases;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.verticalbytes.core.TestCore;

public class AddCustomerTest extends TestCore {
	@Test
	public void addCustomer() {
		click(or.getProperty("addCustBtn"));
		type(or.getProperty("firstname"), "Hello");
		type(or.getProperty("lastname"), "World");
		type(or.getProperty("postcode"), "W1W1W");
		click(or.getProperty("addbtn"));
		driver.switchTo().alert().accept();
	}
}
