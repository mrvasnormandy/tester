package com.verticalbytes.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.verticalbytes.core.TestCore;

public class Config {
	private static Config config;
	private final Properties properties;

	
	private Config(Properties properties) {
		this.properties = properties;
	}

	public static Config getInstance() {
		if (config != null)
			return config;
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream(TestCore.PROPERTIES_FOLDER + "/config.properties")) {
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = new Config(props);
		return config;
	}
	
	public String get(String key) {
		return properties.getProperty(key);
	}
}
