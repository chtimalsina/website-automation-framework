package com.chirangv.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Home Page
 */
public class HomePage extends BasePage {

    // Locators
    private static final String HEADER = "h1:has-text('Hello! I\\'m Chiran')";
    private static final String JOIN_CIRCLE_SECTION = "text=Join My Circle";
    // More specific locators using href or navigation context
    private static final String LOGIN_LINK = "nav a[href='/login']";
    private static final String DEVELOPER_LINK = "nav a[href='/developer']";
    private static final String ABOUT_SECTION = "text=A Little About Me";
    private static final String WHO_I_AM_SECTION = "text=Who I Am";
    private static final String WHAT_I_DO_SECTION = "text=What I Do";

    public HomePage(Page page) {
        super(page);
    }

    /**
     * Navigate to Home Page
     */
    public HomePage navigateToHome(String baseUrl) {
        page.navigate(baseUrl);
        waitForPageLoad();
        logger.info("Navigated to Home Page: " + baseUrl);
        return this;
    }

    /**
     * Verify if header is visible
     */
    public boolean isHeaderVisible() {
        return isElementVisible(HEADER);
    }

    /**
     * Verify if Join My Circle section is visible
     */
    public boolean isJoinCircleSectionVisible() {
        return isTextPresent("Join My Circle");
    }

    /**
     * Click on Login link
     */
    public LoginPage clickLoginLink() {
        page.click(LOGIN_LINK);
        page.waitForURL("**/login**");
        logger.info("Clicked Login link");
        return new LoginPage(page);
    }

    /**
     * Click on Developer link
     */
    public DeveloperPage clickDeveloperLink() {
        page.click(DEVELOPER_LINK);
        page.waitForURL("**/developer**");
        logger.info("Clicked Developer link");
        return new DeveloperPage(page);
    }

    /**
     * Verify About Me section is visible
     */
    public boolean isAboutMeSectionVisible() {
        return isTextPresent("A Little About Me");
    }

    /**
     * Verify Who I Am section is visible
     */
    public boolean isWhoIAmSectionVisible() {
        return isTextPresent("Who I Am");
    }

    /**
     * Verify What I Do section is visible
     */
    public boolean isWhatIDoSectionVisible() {
        return isTextPresent("What I Do");
    }

    /**
     * Verify all main sections are visible
     */
    public boolean areAllMainSectionsVisible() {
        return isJoinCircleSectionVisible() &&
                isAboutMeSectionVisible() &&
                isWhoIAmSectionVisible() &&
                isWhatIDoSectionVisible();
    }

    /**
     * Get welcome message
     */
    public String getWelcomeMessage() {
        return getElementText(HEADER);
    }
}
