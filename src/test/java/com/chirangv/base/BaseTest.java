package com.chirangv.base;

import com.chirangv.utils.ConfigReader;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

/**
 * Base test class that sets up and tears down Playwright browser instances
 * Includes Allure reporting integration
 */
public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setUpClass() {
        logger.info("Setting up Playwright and Browser");
        playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(ConfigReader.isHeadless())
                .setSlowMo(Integer.parseInt(ConfigReader.getProperty("slowMo")));

        String browserType = ConfigReader.getBrowser();
        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;
            default:
                browser = playwright.chromium().launch(launchOptions);
        }

        logger.info("Browser launched: " + browserType);
    }

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up browser context and page");

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(
                        Integer.parseInt(ConfigReader.getProperty("viewport.width")),
                        Integer.parseInt(ConfigReader.getProperty("viewport.height")));

        // Enable video recording on failure
        if (Boolean.parseBoolean(ConfigReader.getProperty("videoOnFailure"))) {
            contextOptions.setRecordVideoDir(Paths.get("target/videos/"));
        }

        context = browser.newContext(contextOptions);
        page = context.newPage();

        // Set default timeout
        page.setDefaultTimeout(ConfigReader.getTimeout());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Attach screenshot to Allure report on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshotToAllure(result.getName());
        }

        logger.info("Closing page and context");
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
    }

    @AfterClass
    public void tearDownClass() {
        logger.info("Closing browser and Playwright");
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    /**
     * Take screenshot on test failure
     */
    protected void takeScreenshot(String testName) {
        if (Boolean.parseBoolean(ConfigReader.getProperty("screenshotOnFailure"))) {
            String screenshotPath = "target/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            logger.info("Screenshot saved: " + screenshotPath);
        }
    }

    /**
     * Attach screenshot to Allure report
     */
    protected void attachScreenshotToAllure(String testName) {
        try {
            byte[] screenshot = page.screenshot();
            Allure.addAttachment(testName + " - Screenshot", "image/png",
                    new ByteArrayInputStream(screenshot), "png");
            logger.info("Screenshot attached to Allure report for test: " + testName);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Allure: " + e.getMessage());
        }
    }

    /**
     * Navigate to base URL
     */
    protected void navigateToBaseUrl() {
        page.navigate(ConfigReader.getBaseUrl());
        logger.info("Navigated to: " + ConfigReader.getBaseUrl());
    }
}
