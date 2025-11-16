package com.chirangv.tests;

import com.chirangv.base.BaseTest;
import com.chirangv.pages.DeveloperPage;
import com.chirangv.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Developer Page functionality
 */
public class DeveloperPageTest extends BaseTest {

    private DeveloperPage developerPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        developerPage = new DeveloperPage(page);
    }

    @Test(priority = 1, description = "Verify Developer Page loads successfully")
    public void testDeveloperPageLoads() {
        logger.info("Starting test: Verify Developer Page loads successfully");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.isDeveloperPageDisplayed(), "Developer page should be displayed");
        Assert.assertTrue(page.url().contains("/developer"), "URL should contain /developer");

        logger.info("Developer Page loaded successfully");
    }

    @Test(priority = 2, description = "Verify Technical Expertise section is visible")
    public void testTechnicalExpertiseSection() {
        logger.info("Starting test: Verify Technical Expertise section");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.isTechnicalExpertiseSectionVisible(),
                "Technical Expertise section should be visible");

        logger.info("Technical Expertise section is visible");
    }

    @Test(priority = 3, description = "Verify all expertise areas are mentioned")
    public void testAllExpertiseAreas() {
        logger.info("Starting test: Verify all expertise areas are mentioned");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.isFullStackDevMentioned(),
                "Full-Stack Development should be mentioned");
        Assert.assertTrue(developerPage.isAIChatbotMentioned(),
                "AI & Chatbot Integration should be mentioned");
        Assert.assertTrue(developerPage.isDatabaseDesignMentioned(),
                "Database Design & Optimization should be mentioned");
        Assert.assertTrue(developerPage.isAPIDevMentioned(),
                "API Development & Integration should be mentioned");

        logger.info("All expertise areas are mentioned");
    }

    @Test(priority = 4, description = "Verify Skills & Technologies section")
    public void testSkillsSection() {
        logger.info("Starting test: Verify Skills & Technologies section");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.isSkillsSectionVisible(),
                "Skills & Technologies section should be visible");

        logger.info("Skills & Technologies section is visible");
    }

    @Test(priority = 5, description = "Verify all skill categories are present")
    public void testAllSkillCategories() {
        logger.info("Starting test: Verify all skill categories are present");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.isFrontendSectionVisible(),
                "Frontend section should be visible");
        Assert.assertTrue(developerPage.isBackendSectionVisible(),
                "Backend section should be visible");
        Assert.assertTrue(developerPage.isDevOpsSectionVisible(),
                "DevOps & Tools section should be visible");

        logger.info("All skill categories are present");
    }

    @Test(priority = 6, description = "Verify Developer Resources section")
    public void testDeveloperResourcesSection() {
        logger.info("Starting test: Verify Developer Resources section");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.isDeveloperResourcesVisible(),
                "Developer Resources section should be visible");

        logger.info("Developer Resources section is visible");
    }

    @Test(priority = 7, description = "Verify all main sections are visible")
    public void testAllMainSectionsVisible() {
        logger.info("Starting test: Verify all main sections are visible");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.areAllMainSectionsVisible(),
                "All main sections should be visible");

        logger.info("All main sections are visible");
    }

    @Test(priority = 8, description = "Verify navigation to login from developer page")
    public void testNavigationToLogin() {
        logger.info("Starting test: Verify navigation to login");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());
        developerPage.clickLoginToConnect();

        // Wait for navigation
        page.waitForTimeout(1000);

        Assert.assertTrue(page.url().contains("/login"),
                "Should navigate to login page");

        logger.info("Successfully navigated to login page");
    }

    @Test(priority = 9, description = "Verify page title on developer page")
    public void testDeveloperPageTitle() {
        logger.info("Starting test: Verify developer page title");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());
        String pageTitle = developerPage.getPageTitle();

        Assert.assertNotNull(pageTitle, "Page title should not be null");

        logger.info("Developer page title: " + pageTitle);
    }

    @Test(priority = 10, description = "Verify all expertise areas and skill sections together")
    public void testCompleteSkillsAndExpertise() {
        logger.info("Starting test: Verify complete skills and expertise");

        developerPage.navigateToDeveloper(ConfigReader.getBaseUrl());

        Assert.assertTrue(developerPage.areAllExpertiseAreasMentioned(),
                "All expertise areas should be mentioned");
        Assert.assertTrue(developerPage.areAllSkillSectionsPresent(),
                "All skill sections should be present");

        logger.info("Complete skills and expertise verified");
    }
}
