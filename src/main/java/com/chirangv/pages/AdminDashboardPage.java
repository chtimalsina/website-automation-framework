package com.chirangv.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Admin Dashboard
 */
public class AdminDashboardPage extends BasePage {

    // Locators
    private static final String DASHBOARD_HEADING = "h1:has-text('Admin'), h1:has-text('Dashboard')";
    private static final String MESSAGES_SECTION = "text=Messages, [data-section='messages']";
    private static final String MESSAGES_TAB = "a:has-text('Messages'), button:has-text('Messages')";
    private static final String MESSAGE_LIST = ".message-list, [data-testid='message-list'], .messages";
    private static final String MESSAGE_ITEM = ".message-item, [data-testid='message-item'], .message";
    private static final String SEARCH_INPUT = "input[type='search'], input[placeholder*='Search']";
    private static final String ADMIN_MENU = "nav, .admin-nav, [role='navigation']";

    public AdminDashboardPage(Page page) {
        super(page);
    }

    /**
     * Navigate to Admin Dashboard
     */
    public AdminDashboardPage navigateToAdminDashboard(String baseUrl) {
        page.navigate(baseUrl + "/admin");
        waitForPageLoad();
        logger.info("Navigated to Admin Dashboard");
        return this;
    }

    /**
     * Verify if admin dashboard is displayed
     */
    public boolean isAdminDashboardDisplayed() {
        return isElementVisible(DASHBOARD_HEADING) || isTextPresent("Admin") || isTextPresent("Dashboard");
    }

    /**
     * Click on Messages tab/section
     */
    public AdminDashboardPage goToMessagesSection() {
        if (isElementVisible(MESSAGES_TAB)) {
            clickElement(MESSAGES_TAB);
            logger.info("Clicked Messages tab");
        }
        return this;
    }

    /**
     * Search for a message by content or sender
     */
    public AdminDashboardPage searchMessage(String searchText) {
        if (isElementVisible(SEARCH_INPUT)) {
            fillInput(SEARCH_INPUT, searchText);
            logger.info("Searched for: " + searchText);
            page.waitForTimeout(1000); // Wait for search results
        }
        return this;
    }

    /**
     * Verify if a message exists in the list
     */
    public boolean isMessageVisible(String messageContent) {
        try {
            page.waitForTimeout(1000);
            String pageContent = page.content().toLowerCase();
            boolean found = pageContent.contains(messageContent.toLowerCase());

            if (found) {
                logger.info("Message found: " + messageContent);
            } else {
                logger.warn("Message not found: " + messageContent);
            }

            return found;
        } catch (Exception e) {
            logger.error("Error checking for message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if message from specific user exists
     */
    public boolean isMessageFromUserVisible(String userName, String messageContent) {
        try {
            page.waitForTimeout(1000);
            String pageContent = page.content().toLowerCase();
            boolean userFound = pageContent.contains(userName.toLowerCase());
            boolean messageFound = pageContent.contains(messageContent.toLowerCase());

            boolean found = userFound && messageFound;

            if (found) {
                logger.info("Message from " + userName + " found with content: " + messageContent);
            } else {
                logger.warn("Message from " + userName + " not found");
            }

            return found;
        } catch (Exception e) {
            logger.error("Error checking for user message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get count of messages
     */
    public int getMessageCount() {
        try {
            if (isElementVisible(MESSAGE_ITEM)) {
                return page.locator(MESSAGE_ITEM).count();
            }
            return 0;
        } catch (Exception e) {
            logger.warn("Could not count messages: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get latest message content
     */
    public String getLatestMessageContent() {
        try {
            if (isElementVisible(MESSAGE_ITEM)) {
                return page.locator(MESSAGE_ITEM).first().textContent();
            }
            return "";
        } catch (Exception e) {
            logger.warn("Could not get latest message: " + e.getMessage());
            return "";
        }
    }
}
