package com.chirangv.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Login Page
 */
public class LoginPage extends BasePage {

    // Locators
    private static final String PAGE_HEADING = "text=Sign in to your account";
    private static final String EMAIL_INPUT = "input[type='email'], input[name='email']";
    private static final String PASSWORD_INPUT = "input[type='password'], input[name='password']";
    private static final String SIGN_IN_BUTTON = "button:has-text('Sign in')";
    private static final String SIGNUP_LINK = "text=Sign up";
    private static final String GOOGLE_BUTTON = "text=Google";
    private static final String BACK_TO_HOME_LINK = "a:has-text('Back to Home')";
    private static final String ERROR_MESSAGE = ".error-message, [role='alert']";

    public LoginPage(Page page) {
        super(page);
    }

    /**
     * Navigate to Login Page
     */
    public LoginPage navigateToLogin(String baseUrl) {
        page.navigate(baseUrl + "/login");
        waitForPageLoad();
        logger.info("Navigated to Login Page");
        return this;
    }

    /**
     * Verify if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isTextPresent("Sign in to your account");
    }

    /**
     * Enter email
     */
    public LoginPage enterEmail(String email) {
        fillInput(EMAIL_INPUT, email);
        return this;
    }

    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        fillInput(PASSWORD_INPUT, password);
        return this;
    }

    /**
     * Click Sign In button
     */
    public void clickSignIn() {
        clickElement(SIGN_IN_BUTTON);
        logger.info("Clicked Sign In button");
    }

    /**
     * Perform login with credentials
     */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        logger.info("Performed login with email: " + email);
    }

    /**
     * Check if email field is visible
     */
    public boolean isEmailFieldVisible() {
        return isElementVisible(EMAIL_INPUT);
    }

    /**
     * Check if password field is visible
     */
    public boolean isPasswordFieldVisible() {
        return isElementVisible(PASSWORD_INPUT);
    }

    /**
     * Check if Sign In button is visible
     */
    public boolean isSignInButtonVisible() {
        return isElementVisible(SIGN_IN_BUTTON);
    }

    /**
     * Check if Sign Up link is visible
     */
    public boolean isSignUpLinkVisible() {
        return isTextPresent("Sign up");
    }

    /**
     * Check if Google sign-in option is visible
     */
    public boolean isGoogleSignInVisible() {
        return isTextPresent("Google");
    }

    /**
     * Click on Sign Up link
     */
    public void clickSignUpLink() {
        clickElement(SIGNUP_LINK);
        logger.info("Clicked Sign Up link");
    }

    /**
     * Click on Back to Home link
     */
    public HomePage clickBackToHome() {
        clickElement(BACK_TO_HOME_LINK);
        logger.info("Clicked Back to Home link");
        return new HomePage(page);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementVisible(ERROR_MESSAGE);
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return getElementText(ERROR_MESSAGE);
        }
        return "";
    }

    /**
     * Verify all login form elements are present
     */
    public boolean areAllLoginElementsPresent() {
        return isEmailFieldVisible() &&
                isPasswordFieldVisible() &&
                isSignInButtonVisible() &&
                isSignUpLinkVisible();
    }
}
