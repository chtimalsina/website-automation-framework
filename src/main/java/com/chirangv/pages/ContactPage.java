package com.chirangv.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Contact/Message Page
 */
public class ContactPage extends BasePage {

    // Locators
    private static final String MESSAGE_TEXTAREA = "textarea[name='message'], textarea[placeholder*='message'], textarea";
    private static final String NAME_INPUT = "input[name='name'], input[placeholder*='name']";
    private static final String EMAIL_INPUT = "input[name='email'], input[type='email'], input[placeholder*='email']";
    private static final String PHONE_INPUT = "input[name='phone'], input[type='tel'], input[placeholder*='phone']";
    private static final String SEND_BUTTON = "button:has-text('Send'), button[type='submit']";
    private static final String SUCCESS_MESSAGE = "text=success, text=sent, text=thank you";
    private static final String CONTACT_FORM = "form";

    public ContactPage(Page page) {
        super(page);
    }

    /**
     * Navigate to Contact Page or Message Form
     */
    public ContactPage navigateToContact(String baseUrl) {
        // Assuming contact is accessible after login or on a specific page
        page.navigate(baseUrl + "/contact");
        waitForPageLoad();
        logger.info("Navigated to Contact Page");
        return this;
    }

    /**
     * Check if contact form is visible
     */
    public boolean isContactFormVisible() {
        return isElementVisible(CONTACT_FORM);
    }

    /**
     * Fill in the contact form
     */
    public ContactPage fillContactForm(String name, String email, String phone, String message) {
        if (isElementVisible(NAME_INPUT)) {
            fillInput(NAME_INPUT, name);
            logger.info("Filled name: " + name);
        }

        if (isElementVisible(EMAIL_INPUT)) {
            fillInput(EMAIL_INPUT, email);
            logger.info("Filled email: " + email);
        }

        if (isElementVisible(PHONE_INPUT)) {
            fillInput(PHONE_INPUT, phone);
            logger.info("Filled phone: " + phone);
        }

        fillInput(MESSAGE_TEXTAREA, message);
        logger.info("Filled message");

        return this;
    }

    /**
     * Fill and send message
     */
    public ContactPage sendMessage(String name, String email, String phone, String message) {
        fillContactForm(name, email, phone, message);
        clickElement(SEND_BUTTON);
        logger.info("Clicked Send button");
        return this;
    }

    /**
     * Verify success message is displayed
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            // Wait a bit for success message to appear
            page.waitForTimeout(2000);
            return isTextPresent("success") || isTextPresent("sent") || isTextPresent("thank");
        } catch (Exception e) {
            logger.warn("Success message not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get success message text
     */
    public String getSuccessMessage() {
        try {
            return page.locator(SUCCESS_MESSAGE).first().textContent();
        } catch (Exception e) {
            return "";
        }
    }
}
