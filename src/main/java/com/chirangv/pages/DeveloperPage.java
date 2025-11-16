package com.chirangv.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Developer Page
 */
public class DeveloperPage extends BasePage {

    // Locators
    private static final String PAGE_HEADING = "h1:has-text('Developer Portfolio')";
    private static final String TECHNICAL_EXPERTISE = "text=Technical Expertise";
    private static final String FULL_STACK_DEV = "text=Full-Stack Development";
    private static final String AI_CHATBOT = "text=AI & Chatbot Integration";
    private static final String DATABASE_DESIGN = "text=Database Design & Optimization";
    private static final String API_DEVELOPMENT = "text=API Development & Integration";
    private static final String SKILLS_SECTION = "text=Skills & Technologies";
    private static final String FRONTEND_SECTION = "text=Frontend";
    private static final String BACKEND_SECTION = "text=Backend";
    private static final String DEVOPS_SECTION = "text=DevOps & Tools";
    private static final String PROJECTS_LINK = "a:has-text('My Projects')";
    private static final String DOCUMENTATION_LINK = "a:has-text('Documentation')";
    private static final String TUTORIALS_LINK = "a:has-text('Tutorials')";
    private static final String LOGIN_TO_CONNECT = "a:has-text('Login to Connect')";

    public DeveloperPage(Page page) {
        super(page);
    }

    /**
     * Navigate to Developer Page
     */
    public DeveloperPage navigateToDeveloper(String baseUrl) {
        page.navigate(baseUrl + "/developer");
        waitForPageLoad();
        logger.info("Navigated to Developer Page");
        return this;
    }

    /**
     * Verify if developer page is displayed
     */
    public boolean isDeveloperPageDisplayed() {
        return isTextPresent("Developer Portfolio");
    }

    /**
     * Verify Technical Expertise section is visible
     */
    public boolean isTechnicalExpertiseSectionVisible() {
        return isTextPresent("Technical Expertise");
    }

    /**
     * Verify Full-Stack Development is mentioned
     */
    public boolean isFullStackDevMentioned() {
        return isTextPresent("Full-Stack Development");
    }

    /**
     * Verify AI & Chatbot Integration is mentioned
     */
    public boolean isAIChatbotMentioned() {
        return isTextPresent("AI & Chatbot Integration");
    }

    /**
     * Verify Database Design & Optimization is mentioned
     */
    public boolean isDatabaseDesignMentioned() {
        return isTextPresent("Database Design & Optimization");
    }

    /**
     * Verify API Development is mentioned
     */
    public boolean isAPIDevMentioned() {
        return isTextPresent("API Development & Integration");
    }

    /**
     * Verify Skills & Technologies section is visible
     */
    public boolean isSkillsSectionVisible() {
        return isTextPresent("Skills & Technologies");
    }

    /**
     * Verify Frontend section is visible
     */
    public boolean isFrontendSectionVisible() {
        return isTextPresent("Frontend");
    }

    /**
     * Verify Backend section is visible
     */
    public boolean isBackendSectionVisible() {
        return isTextPresent("Backend");
    }

    /**
     * Verify DevOps section is visible
     */
    public boolean isDevOpsSectionVisible() {
        return isTextPresent("DevOps & Tools");
    }

    /**
     * Verify Developer Resources section is visible
     */
    public boolean isDeveloperResourcesVisible() {
        return isTextPresent("Developer Resources");
    }

    /**
     * Click on My Projects link
     */
    public void clickMyProjects() {
        clickElement(PROJECTS_LINK);
        logger.info("Clicked My Projects link");
    }

    /**
     * Click on Documentation link
     */
    public void clickDocumentation() {
        clickElement(DOCUMENTATION_LINK);
        logger.info("Clicked Documentation link");
    }

    /**
     * Click on Tutorials link
     */
    public void clickTutorials() {
        clickElement(TUTORIALS_LINK);
        logger.info("Clicked Tutorials link");
    }

    /**
     * Click on Login to Connect button
     */
    public LoginPage clickLoginToConnect() {
        clickElement(LOGIN_TO_CONNECT);
        logger.info("Clicked Login to Connect");
        return new LoginPage(page);
    }

    /**
     * Verify all main sections are visible
     */
    public boolean areAllMainSectionsVisible() {
        return isTechnicalExpertiseSectionVisible() &&
                isSkillsSectionVisible() &&
                isDeveloperResourcesVisible();
    }

    /**
     * Verify all expertise areas are mentioned
     */
    public boolean areAllExpertiseAreasMentioned() {
        return isFullStackDevMentioned() &&
                isAIChatbotMentioned() &&
                isDatabaseDesignMentioned() &&
                isAPIDevMentioned();
    }

    /**
     * Verify all skill sections are present
     */
    public boolean areAllSkillSectionsPresent() {
        return isFrontendSectionVisible() &&
                isBackendSectionVisible() &&
                isDevOpsSectionVisible();
    }
}
