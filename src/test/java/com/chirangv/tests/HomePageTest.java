package com.chirangv.tests;

import com.chirangv.base.BaseTest;
import com.chirangv.pages.HomePage;
import com.chirangv.utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Home Page functionality
 */
@Epic("Website Testing")
@Feature("Home Page")
public class HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUpHomePage() {
        homePage = new HomePage(page);
    }

    @Test(priority = 1, description = "Verify Home Page loads successfully")
    @Description("Validate that the home page loads correctly with all essential elements visible")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Page Load Validation")
    public void testHomePageLoads() {
        logger.info("Starting test: Verify Home Page loads successfully");

        homePage.navigateToHome(ConfigReader.getBaseUrl());

        Assert.assertTrue(homePage.isHeaderVisible(), "Header should be visible");
        Assert.assertTrue(page.url().contains("chirangv.com"), "URL should contain chirangv.com");

        logger.info("Home Page loaded successfully");
    }

    @Test(priority = 2, description = "Verify all main sections are visible on Home Page")
    @Description("Verify that all main content sections (Join My Circle, About Me, Who I Am, What I Do) are displayed")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Content Visibility")
    public void testAllMainSectionsVisible() {
        logger.info("Starting test: Verify all main sections are visible");

        homePage.navigateToHome(ConfigReader.getBaseUrl());

        Assert.assertTrue(homePage.isJoinCircleSectionVisible(), "'Join My Circle' section should be visible");
        Assert.assertTrue(homePage.isAboutMeSectionVisible(), "'About Me' section should be visible");
        Assert.assertTrue(homePage.isWhoIAmSectionVisible(), "'Who I Am' section should be visible");
        Assert.assertTrue(homePage.isWhatIDoSectionVisible(), "'What I Do' section should be visible");

        logger.info("All main sections are visible");
    }

    @Test(priority = 3, description = "Verify navigation to Login page from Home page")
    @Description("Validate that clicking the Login link navigates to the login page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Navigation Flow")
    public void testNavigationToLoginPage() {
        logger.info("Starting test: Verify navigation to Login page");

        homePage.navigateToHome(ConfigReader.getBaseUrl());
        homePage.clickLoginLink();

        Assert.assertTrue(page.url().contains("/login"), "URL should contain /login");

        logger.info("Successfully navigated to Login page");
    }

    @Test(priority = 4, description = "Verify navigation to Developer page from Home page")
    @Description("Validate that clicking the Developer link navigates to the developer portfolio page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Navigation Flow")
    public void testNavigationToDeveloperPage() {
        logger.info("Starting test: Verify navigation to Developer page");

        homePage.navigateToHome(ConfigReader.getBaseUrl());
        homePage.clickDeveloperLink();

        Assert.assertTrue(page.url().contains("/developer"), "URL should contain /developer");

        logger.info("Successfully navigated to Developer page");
    }

    @Test(priority = 5, description = "Verify welcome message on Home page")
    public void testWelcomeMessage() {
        logger.info("Starting test: Verify welcome message");

        homePage.navigateToHome(ConfigReader.getBaseUrl());
        String welcomeMessage = homePage.getWelcomeMessage();

        Assert.assertTrue(welcomeMessage.contains("Chiran"), "Welcome message should contain 'Chiran'");

        logger.info("Welcome message verified: " + welcomeMessage);
    }

    @Test(priority = 6, description = "Verify page title")
    public void testPageTitle() {
        logger.info("Starting test: Verify page title");

        homePage.navigateToHome(ConfigReader.getBaseUrl());
        String pageTitle = homePage.getPageTitle();

        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");

        logger.info("Page title: " + pageTitle);
    }
}
