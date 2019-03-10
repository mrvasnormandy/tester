package com.verticalbytes.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.verticalbytes.core.TestCore;

public class Or {
	private static Or or;
	private final Properties properties;

	private Or(Properties properties) {
		this.properties = properties;
	}

	public static Or getInstance() {
		if (or != null)
			return or;
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream(TestCore.PROPERTIES_FOLDER + "/OR.properties")) {
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		or = new Or(props);
		return or;
	}

	public String get(String key) {
		return properties.getProperty(key);
	}
}
