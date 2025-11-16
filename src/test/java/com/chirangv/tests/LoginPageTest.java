package com.chirangv.tests;

import com.chirangv.base.BaseTest;
import com.chirangv.pages.LoginPage;
import com.chirangv.utils.ConfigReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Login Page functionality
 */
public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(page);
    }

    @Test(priority = 1, description = "Verify Login Page loads successfully")
    public void testLoginPageLoads() {
        logger.info("Starting test: Verify Login Page loads successfully");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        Assert.assertTrue(page.url().contains("/login"), "URL should contain /login");

        logger.info("Login Page loaded successfully");
    }

    @Test(priority = 2, description = "Verify all login form elements are present")
    public void testAllLoginElementsPresent() {
        logger.info("Starting test: Verify all login form elements are present");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());

        Assert.assertTrue(loginPage.isEmailFieldVisible(), "Email field should be visible");
        Assert.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible");
        Assert.assertTrue(loginPage.isSignInButtonVisible(), "Sign In button should be visible");
        Assert.assertTrue(loginPage.isSignUpLinkVisible(), "Sign Up link should be visible");

        logger.info("All login form elements are present");
    }

    @Test(priority = 3, description = "Verify Google sign-in option is available")
    public void testGoogleSignInOption() {
        logger.info("Starting test: Verify Google sign-in option is available");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());

        Assert.assertTrue(loginPage.isGoogleSignInVisible(), "Google sign-in option should be visible");

        logger.info("Google sign-in option is available");
    }

    @Test(priority = 4, description = "Test login with valid credentials from test data")
    public void testLoginWithValidCredentials() {
        logger.info("Starting test: Test login with valid credentials");

        // Get test data
        JsonArray loginCredentials = ConfigReader.getTestData().getAsJsonArray("loginCredentials");
        JsonObject validCredentials = null;

        // Find valid credentials
        for (int i = 0; i < loginCredentials.size(); i++) {
            JsonObject cred = loginCredentials.get(i).getAsJsonObject();
            if ("success".equals(cred.get("expectedResult").getAsString())) {
                validCredentials = cred;
                break;
            }
        }

        Assert.assertNotNull(validCredentials, "Valid credentials should be found in test data");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
        loginPage.login(
                validCredentials.get("email").getAsString(),
                validCredentials.get("password").getAsString());

        // Wait for page to process login
        page.waitForTimeout(2000);

        logger.info("Login attempted with valid credentials");
    }

    @Test(priority = 5, description = "Test login with invalid email format")
    public void testLoginWithInvalidEmail() {
        logger.info("Starting test: Test login with invalid email format");

        // Get test data
        JsonArray loginCredentials = ConfigReader.getTestData().getAsJsonArray("loginCredentials");
        JsonObject invalidEmailCred = null;

        // Find invalid email credentials
        for (int i = 0; i < loginCredentials.size(); i++) {
            JsonObject cred = loginCredentials.get(i).getAsJsonObject();
            if ("validation_error".equals(cred.get("expectedResult").getAsString())) {
                invalidEmailCred = cred;
                break;
            }
        }

        Assert.assertNotNull(invalidEmailCred, "Invalid email credentials should be found in test data");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
        loginPage.enterEmail(invalidEmailCred.get("email").getAsString());
        loginPage.enterPassword(invalidEmailCred.get("password").getAsString());

        // Email field should have validation
        logger.info("Tested login with invalid email format");
    }

    @Test(priority = 6, description = "Test login with empty fields")
    public void testLoginWithEmptyFields() {
        logger.info("Starting test: Test login with empty fields");

        // Get test data
        JsonArray loginCredentials = ConfigReader.getTestData().getAsJsonArray("loginCredentials");
        JsonObject emptyFieldsCred = null;

        // Find empty fields credentials
        for (int i = 0; i < loginCredentials.size(); i++) {
            JsonObject cred = loginCredentials.get(i).getAsJsonObject();
            if ("empty_fields".equals(cred.get("expectedResult").getAsString())) {
                emptyFieldsCred = cred;
                break;
            }
        }

        Assert.assertNotNull(emptyFieldsCred, "Empty fields credentials should be found in test data");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
        loginPage.clickSignIn();

        // Form validation should prevent submission
        logger.info("Tested login with empty fields");
    }

    @Test(priority = 7, description = "Verify navigation back to home from login page")
    public void testBackToHomeNavigation() {
        logger.info("Starting test: Verify navigation back to home");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
        loginPage.clickBackToHome();

        // Wait for navigation
        page.waitForTimeout(1000);

        Assert.assertTrue(page.url().equals(ConfigReader.getBaseUrl()) ||
                page.url().equals(ConfigReader.getBaseUrl() + "/"),
                "Should navigate back to home page");

        logger.info("Successfully navigated back to home");
    }

    @Test(priority = 8, description = "Verify page title on login page")
    public void testLoginPageTitle() {
        logger.info("Starting test: Verify login page title");

        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
        String pageTitle = loginPage.getPageTitle();

        Assert.assertNotNull(pageTitle, "Page title should not be null");

        logger.info("Login page title: " + pageTitle);
    }
}
