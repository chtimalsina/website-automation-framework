package com.chirangv.pages;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Page Object class with common functionality
 */
public class BasePage {
    protected Page page;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(Page page) {
        this.page = page;
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return page.title();
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Wait for page to load
     */
    public void waitForPageLoad() {
        page.waitForLoadState();
    }

    /**
     * Check if element is visible
     */
    public boolean isElementVisible(String selector) {
        try {
            return page.locator(selector).isVisible();
        } catch (Exception e) {
            logger.error("Element not found: " + selector);
            return false;
        }
    }

    /**
     * Check if text is present on page
     */
    public boolean isTextPresent(String text) {
        try {
            return page.locator("text=" + text).isVisible();
        } catch (Exception e) {
            logger.error("Text not found: " + text);
            return false;
        }
    }

    /**
     * Click element
     */
    public void clickElement(String selector) {
        page.locator(selector).click();
        logger.info("Clicked element: " + selector);
    }

    /**
     * Fill input field
     */
    public void fillInput(String selector, String value) {
        page.locator(selector).fill(value);
        logger.info("Filled input " + selector + " with value: " + value);
    }

    /**
     * Get element text
     */
    public String getElementText(String selector) {
        return page.locator(selector).textContent();
    }
}
