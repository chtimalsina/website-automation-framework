package com.chirangv.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration and test data
 */
public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties;
    private static JsonObject testData;

    static {
        loadProperties();
        loadTestData();
    }

    /**
     * Load properties from config.properties file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                return;
            }
            properties.load(input);
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading configuration: ", e);
        }
    }

    /**
     * Load test data from JSON file
     */
    private static void loadTestData() {
        try (FileReader reader = new FileReader("src/test/resources/testdata/test-data.json")) {
            Gson gson = new Gson();
            testData = gson.fromJson(reader, JsonObject.class);
            logger.info("Test data loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading test data: ", e);
        }
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get test data JSON object
     */
    public static JsonObject getTestData() {
        return testData;
    }

    /**
     * Get base URL
     */
    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }

    /**
     * Get browser type
     */
    public static String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Check if headless mode is enabled
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    /**
     * Get default timeout
     */
    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout"));
    }
}
