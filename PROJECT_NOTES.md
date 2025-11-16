# 📚 Comprehensive Project Notes - TestNG + Playwright Automation Framework

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture & Design Patterns](#architecture--design-patterns)
3. [Project Structure Explained](#project-structure-explained)
4. [Core Components Deep Dive](#core-components-deep-dive)
5. [Test Data Management](#test-data-management)
6. [Configuration Management](#configuration-management)
7. [Running Tests](#running-tests)
8. [Allure Reporting](#allure-reporting)
9. [How to Modify for Similar Projects](#how-to-modify-for-similar-projects)
10. [Best Practices](#best-practices)
11. [Troubleshooting Guide](#troubleshooting-guide)
12. [Advanced Topics](#advanced-topics)

---

## Project Overview

### What is This Project?

This is a **web automation testing framework** built using:

- **Java** - Programming language
- **Maven** - Build and dependency management tool
- **TestNG** - Testing framework for organizing and running tests
- **Playwright** - Modern web automation library (alternative to Selenium)
- **Allure** - Beautiful test reporting framework
- **Page Object Model (POM)** - Design pattern for maintainable test code

### Why These Technologies?

#### Java

- **Industry standard** for enterprise applications
- **Strongly typed** - catches errors at compile time
- **Rich ecosystem** of testing libraries
- **Cross-platform** - runs on Windows, Mac, Linux

#### Maven

- **Dependency management** - automatically downloads libraries
- **Build automation** - compiles code, runs tests, generates reports
- **Standardized structure** - everyone uses the same folder layout
- **Plugin ecosystem** - extend functionality easily

#### TestNG (vs JUnit)

- **Better annotations** - `@BeforeClass`, `@BeforeMethod`, etc.
- **Data-driven testing** - easy to use with JSON/Excel
- **Test grouping** - run specific test categories
- **Parallel execution** - run tests simultaneously
- **Better reporting** - built-in HTML reports

#### Playwright (vs Selenium)

- **Faster** - uses modern browser automation APIs
- **Auto-wait** - waits for elements automatically
- **Multiple browsers** - Chromium, Firefox, WebKit (Safari)
- **Better reliability** - fewer flaky tests
- **Modern API** - cleaner, more intuitive code

#### Page Object Model

- **Separation of concerns** - page elements separate from test logic
- **Reusability** - use same page objects in multiple tests
- **Maintainability** - change UI selectors in one place
- **Readability** - tests read like plain English

---

## Architecture & Design Patterns

### 1. Three-Layer Architecture

```
┌─────────────────────────────────────────┐
│         TEST LAYER (tests/)             │
│  - HomePageTest.java                    │
│  - LoginPageTest.java                   │
│  - DeveloperPageTest.java               │
│  Contains: Test methods, assertions,    │
│            test data usage               │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│       PAGE OBJECT LAYER (pages/)        │
│  - BasePage.java                        │
│  - HomePage.java                        │
│  - LoginPage.java                       │
│  Contains: Page elements, actions,      │
│            navigation methods            │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│      UTILITY LAYER (utils/, base/)      │
│  - ConfigReader.java                    │
│  - BaseTest.java                        │
│  Contains: Configuration, browser setup,│
│            common utilities              │
└─────────────────────────────────────────┘
```

### 2. Page Object Model (POM) Pattern

**Problem**: Tests directly interact with web elements, making code hard to maintain.

**Solution**: Create a separate class for each page with:

- **Locators** - How to find elements (stored as constants)
- **Actions** - Methods to interact with the page (click, type, etc.)
- **Verifications** - Methods to check page state

**Example**:

```java
// ❌ BAD: Without POM
@Test
public void testLogin() {
    page.locator("input[type='email']").fill("test@example.com");
    page.locator("input[type='password']").fill("password");
    page.locator("button:has-text('Sign in')").click();
}

// ✅ GOOD: With POM
@Test
public void testLogin() {
    loginPage.login("test@example.com", "password");
}
```

### 3. Data-Driven Testing

**Concept**: Separate test data from test logic

**Benefits**:

- Run same test with different data
- Easy to add new test cases
- Non-programmers can update test data

**Implementation**:

```
Test Data (JSON) → ConfigReader → Test Method
```

---

## Project Structure Explained

### Complete Directory Tree

```
testAutomationPersonalWebsite/
│
├── .github/
│   └── copilot-instructions.md          # GitHub Copilot workspace instructions
│
├── .vscode/
│   └── tasks.json                       # VS Code tasks for running tests
│
├── src/
│   ├── main/                            # Production code (not test code)
│   │   └── java/
│   │       └── com/chirangv/
│   │           ├── pages/               # Page Object Model classes
│   │           │   ├── BasePage.java    # Parent class for all pages
│   │           │   ├── HomePage.java    # Home page interactions
│   │           │   ├── LoginPage.java   # Login page interactions
│   │           │   └── DeveloperPage.java
│   │           │
│   │           └── utils/               # Utility classes
│   │               └── ConfigReader.java # Reads config & test data
│   │
│   └── test/                            # Test code
│       ├── java/
│       │   └── com/chirangv/
│       │       ├── base/
│       │       │   └── BaseTest.java    # Setup/teardown for all tests
│       │       │
│       │       └── tests/               # Actual test classes
│       │           ├── HomePageTest.java
│       │           ├── LoginPageTest.java
│       │           └── DeveloperPageTest.java
│       │
│       └── resources/                   # Test resources (data, config)
│           ├── config.properties        # Test configuration
│           └── testdata/
│               └── test-data.json       # Test data in JSON format
│
├── target/                              # Generated by Maven (auto-created)
│   ├── classes/                         # Compiled production code
│   ├── test-classes/                    # Compiled test code
│   ├── surefire-reports/                # TestNG reports
│   ├── allure-results/                  # Raw Allure data
│   ├── screenshots/                     # Failure screenshots
│   └── videos/                          # Test recordings
│
├── pom.xml                              # Maven configuration file
├── testng.xml                           # TestNG suite configuration
├── README.md                            # Quick start guide
├── PROJECT_NOTES.md                     # This detailed guide
└── .gitignore                           # Git ignore rules
```

### Why This Structure?

#### `src/main/java` vs `src/test/java`

- **main**: Reusable code (Page Objects, Utilities)
- **test**: Test-specific code (Test classes, BaseTest)
- **Maven standard**: All Java projects follow this

#### Package Naming: `com.chirangv`

- **Convention**: Reverse domain name
- **Purpose**: Avoid naming conflicts with other libraries
- **Example**: `com.google`, `org.apache`, `com.yourcompany`

#### Separation of Concerns

- **pages/**: "What elements exist and how to interact with them"
- **tests/**: "What to test and what results to expect"
- **utils/**: "How to read configuration and data"
- **base/**: "How to set up the browser and test environment"

---

## Core Components Deep Dive

### 1. BasePage.java - The Foundation

**Purpose**: Common functionality for all page objects

**Key Concepts**:

```java
public class BasePage {
    protected Page page;  // Playwright page object

    // Constructor - every page gets a Playwright Page
    public BasePage(Page page) {
        this.page = page;
    }

    // Common methods all pages can use
    public String getPageTitle() { ... }
    public boolean isElementVisible(String selector) { ... }
    public void clickElement(String selector) { ... }
}
```

**Why `protected`?**

- Child classes (HomePage, LoginPage) can access `page`
- Other classes cannot (encapsulation)

**Inheritance**:

```
BasePage (parent)
    ↓
├── HomePage (child)
├── LoginPage (child)
└── DeveloperPage (child)
```

### 2. Page Object Classes (HomePage, LoginPage, etc.)

**Anatomy of a Page Object**:

```java
public class LoginPage extends BasePage {

    // 1. LOCATORS - How to find elements
    private static final String EMAIL_INPUT = "input[type='email']";
    private static final String PASSWORD_INPUT = "input[type='password']";
    private static final String SIGN_IN_BUTTON = "button:has-text('Sign in')";

    // 2. CONSTRUCTOR - Initialize with page
    public LoginPage(Page page) {
        super(page);  // Call parent constructor
    }

    // 3. NAVIGATION - Go to this page
    public LoginPage navigateToLogin(String baseUrl) {
        page.navigate(baseUrl + "/login");
        return this;  // For method chaining
    }

    // 4. ACTIONS - Do things on the page
    public LoginPage enterEmail(String email) {
        fillInput(EMAIL_INPUT, email);
        return this;  // For method chaining
    }

    // 5. COMPOSITE ACTIONS - Multiple steps
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    // 6. VERIFICATIONS - Check page state
    public boolean isLoginPageDisplayed() {
        return isTextPresent("Sign in to your account");
    }
}
```

**Locator Strategies**:

| Strategy        | Example                    | When to Use                      |
| --------------- | -------------------------- | -------------------------------- |
| CSS Selector    | `input[type='email']`      | Fast, widely supported           |
| Text Content    | `text=Sign in`             | When text is unique              |
| Combined        | `button:has-text('Login')` | Specific elements                |
| Data Attributes | `[data-testid='submit']`   | Most reliable (add to your HTML) |

**Method Chaining**:

```java
// Instead of:
loginPage.enterEmail("test@example.com");
loginPage.enterPassword("password123");
loginPage.clickSignIn();

// You can write:
loginPage
    .enterEmail("test@example.com")
    .enterPassword("password123")
    .clickSignIn();
```

### 3. BaseTest.java - Test Setup & Teardown

**Test Lifecycle**:

```
@BeforeClass (once per test class)
    ↓ Create Playwright, launch browser

@BeforeMethod (before each test method)
    ↓ Create new browser context & page

@Test (actual test)
    ↓ Your test code runs here

@AfterMethod (after each test method)
    ↓ Close page & context, take screenshot if failed

@AfterClass (once per test class)
    ↓ Close browser & Playwright
```

**Why Multiple Contexts?**

- **Isolation**: Each test starts fresh
- **Parallel execution**: Tests don't interfere
- **Clean state**: No cookies/cache from previous test

**Example Flow**:

```java
// BeforeClass runs ONCE
setUpClass() {
    playwright = Playwright.create();    // Start Playwright
    browser = playwright.chromium().launch();  // Open browser
}

// BeforeMethod runs BEFORE EACH test
setUp() {
    context = browser.newContext();  // New isolated context
    page = context.newPage();        // New tab
}

// AfterMethod runs AFTER EACH test
tearDown() {
    page.close();      // Close tab
    context.close();   // Close context
}

// AfterClass runs ONCE
tearDownClass() {
    browser.close();     // Close browser
    playwright.close();  // Stop Playwright
}
```

### 4. ConfigReader.java - Configuration Management

**Purpose**: Single source of truth for configuration

**How It Works**:

```java
public class ConfigReader {
    private static Properties properties;    // Key-value pairs
    private static JsonObject testData;      // Test data

    static {
        loadProperties();   // Runs once when class is loaded
        loadTestData();
    }

    // Get any property
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Convenience methods
    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }
}
```

**Static vs Instance**:

- **Static**: Shared across all instances, loaded once
- **Instance**: Each object has its own copy

**Usage**:

```java
// Anywhere in your code:
String url = ConfigReader.getBaseUrl();
JsonObject data = ConfigReader.getTestData();
```

---

## Test Data Management

### JSON Structure Explained

**Why JSON?**

- Human-readable
- Easy to edit
- Native support in Java (Gson library)
- Hierarchical structure

**Our test-data.json Structure**:

```json
{
  "testUsers": [
    // Array of user objects
    {
      "userType": "validUser", // Identifier
      "name": "John Doe",
      "email": "johndoe@example.com",
      "username": "johndoe",
      "password": "Test@123456",
      "phone": "+1-234-567-8900"
    }
  ],

  "loginCredentials": [
    // Different scenarios
    {
      "email": "test@example.com",
      "password": "ValidPass@123",
      "expectedResult": "success" // Expected outcome
    }
  ],

  "pageValidations": {
    // Nested object
    "homePage": {
      "url": "https://dev.chirangv.com/",
      "title": "Hello! I'm Chiran",
      "expectedElements": [
        // Array of strings
        "Join My Circle",
        "A Little About Me"
      ]
    }
  }
}
```

### Using Test Data in Tests

**Example 1: Simple Data Access**

```java
@Test
public void testWithUserData() {
    // Get the entire test data object
    JsonObject testData = ConfigReader.getTestData();

    // Get the testUsers array
    JsonArray users = testData.getAsJsonArray("testUsers");

    // Get first user object
    JsonObject user = users.get(0).getAsJsonObject();

    // Get individual fields
    String email = user.get("email").getAsString();
    String password = user.get("password").getAsString();

    // Use in test
    loginPage.login(email, password);
}
```

**Example 2: Finding Specific Data**

```java
@Test
public void testWithValidUser() {
    JsonArray users = ConfigReader.getTestData()
        .getAsJsonArray("testUsers");

    // Find user by type
    JsonObject validUser = null;
    for (int i = 0; i < users.size(); i++) {
        JsonObject user = users.get(i).getAsJsonObject();
        if ("validUser".equals(user.get("userType").getAsString())) {
            validUser = user;
            break;
        }
    }

    // Use the found user
    if (validUser != null) {
        loginPage.login(
            validUser.get("email").getAsString(),
            validUser.get("password").getAsString()
        );
    }
}
```

**Example 3: Data-Driven Testing**

```java
@Test
public void testMultipleLogins() {
    JsonArray credentials = ConfigReader.getTestData()
        .getAsJsonArray("loginCredentials");

    // Test each credential
    for (int i = 0; i < credentials.size(); i++) {
        JsonObject cred = credentials.get(i).getAsJsonObject();

        String email = cred.get("email").getAsString();
        String password = cred.get("password").getAsString();
        String expected = cred.get("expectedResult").getAsString();

        // Perform login
        loginPage.login(email, password);

        // Verify based on expected result
        if ("success".equals(expected)) {
            // Assert successful login
        } else {
            // Assert login failed
        }
    }
}
```

---

## Configuration Management

### config.properties Explained

**Format**: `key=value` pairs

```properties
# Application URL
baseUrl=https://dev.chirangv.com

# Browser Configuration
browser=chromium              # Options: chromium, firefox, webkit
headless=false                # true = no visible browser window
slowMo=0                      # Delay in ms between actions (for debugging)

# Timeouts
timeout=30000                 # Default timeout (30 seconds)

# Screenshots & Videos
screenshotOnFailure=true      # Take screenshot when test fails
videoOnFailure=true           # Record video when test fails

# Viewport Size
viewport.width=1920           # Browser window width
viewport.height=1080          # Browser window height
```

### Common Configuration Scenarios

**1. Run in Headless Mode (CI/CD)**

```properties
headless=true
browser=chromium
```

**2. Debug Mode (See What Happens)**

```properties
headless=false
slowMo=500                    # Half-second delay between actions
```

**3. Test on Different Browsers**

```properties
browser=firefox               # Or webkit for Safari-like
```

**4. Mobile Viewport Testing**

```properties
viewport.width=375            # iPhone size
viewport.height=812
```

---

## Running Tests

### Maven Commands Explained

**Basic Structure**:

```bash
mvn [lifecycle-phase] [options]
```

**Common Commands**:

| Command            | What It Does               | When to Use               |
| ------------------ | -------------------------- | ------------------------- |
| `mvn clean`        | Deletes `target/` folder   | Start fresh               |
| `mvn compile`      | Compiles `src/main/java`   | Check for syntax errors   |
| `mvn test-compile` | Compiles `src/test/java`   | Check test code           |
| `mvn test`         | Runs all tests             | Run tests                 |
| `mvn clean test`   | Clean + compile + test     | Most common               |
| `mvn install`      | Build + save to local repo | Share with other projects |

**Run Specific Tests**:

```bash
# One test class
mvn test -Dtest=HomePageTest

# One test method
mvn test -Dtest=HomePageTest#testPageLoads

# Multiple classes
mvn test -Dtest=HomePageTest,LoginPageTest

# Pattern matching
mvn test -Dtest=*PageTest
```

**With Options**:

```bash
# Skip compilation (faster if code unchanged)
mvn test -DskipCompile

# Skip tests but compile them
mvn test -DskipTests

# Verbose output
mvn test -X

# Quiet output
mvn test -q
```

### VS Code Tasks

**Using Tasks**:

1. Press `Cmd+Shift+P` (Mac) or `Ctrl+Shift+P` (Windows/Linux)
2. Type "Tasks: Run Task"
3. Select your task

**Available Tasks**:

- **Run All Tests**: Full test suite
- **Run Home Page Tests**: Only home page tests
- **Run Login Page Tests**: Only login tests
- **Run Developer Page Tests**: Only developer page tests
- **Install Playwright Browsers**: One-time browser setup
- **Clean and Install**: Clean build
- **Generate Allure Report**: Create visual report
- **Run Tests and Generate Report**: Test + Report in one command

---

## Allure Reporting

### What is Allure?

Allure is a **modern, beautiful test reporting framework** that generates:

- ✅ **Visual dashboards** with graphs and charts
- 📊 **Detailed test results** with steps and screenshots
- 📈 **Trend analysis** over time
- 🔍 **Failure analysis** with stack traces
- 📸 **Screenshot attachments** on failures

### How Allure Works

```
Test Execution
    ↓
TestNG creates test results
    ↓
Allure listener captures data
    ↓
Data saved to allure-results/
    ↓
Allure generates HTML report
    ↓
Report served in browser
```

### Generating Allure Reports

**Method 1: Maven Command (Recommended)**

```bash
# Run tests and open report
mvn clean test allure:serve

# Just generate report (after tests already run)
mvn allure:serve
```

**Method 2: VS Code Task**

1. `Cmd+Shift+P` → Tasks: Run Task
2. Select "Run Tests and Generate Report"

**Method 3: Generate Static Report**

```bash
# Generate report files
mvn allure:report

# Report saved to: target/site/allure-maven-plugin/
# Open index.html in browser
```

### Understanding Allure Reports

**Dashboard Overview**:

- **Total tests**: Passed, Failed, Broken, Skipped
- **Success rate**: Percentage passed
- **Duration**: How long tests took
- **Trends**: Compare with previous runs

**Test Details Page**:

- **Steps**: Each action in the test
- **Screenshots**: Attached on failure
- **Logs**: Console output
- **Parameters**: Test data used
- **Stack trace**: Error details

### Adding Allure Annotations

**Enhance Reports with Descriptions**:

```java
import io.qameta.allure.*;

@Epic("Website Testing")
@Feature("Login Functionality")
public class LoginPageTest extends BaseTest {

    @Test
    @Description("Verify that user can login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Authentication")
    public void testValidLogin() {
        // Test code
    }

    @Test
    @Description("Verify error message for invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidLogin() {
        // Test code
    }
}
```

**Adding Steps**:

```java
@Test
public void testLogin() {
    step("Navigate to login page", () -> {
        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
    });

    step("Enter credentials", () -> {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("password123");
    });

    step("Click sign in", () -> {
        loginPage.clickSignIn();
    });
}
```

**Attaching Information**:

```java
import io.qameta.allure.Allure;

@Test
public void testWithAttachment() {
    Allure.addAttachment("Test Data", "application/json",
        "{\"user\":\"test@example.com\"}");

    // Screenshot is automatically attached on failure
}
```

---

## How to Modify for Similar Projects

### Adapting This Framework

This framework can be adapted for **any web application**. Here's how:

### Step 1: Update Configuration

**config.properties**:

```properties
# Change to your application URL
baseUrl=https://your-website.com

# Adjust as needed
browser=chromium
headless=false
```

### Step 2: Create Page Objects for Your Pages

**Example: Creating a New Page Object**

1. **Analyze your page** (e.g., Registration Page)

   - What elements exist? (inputs, buttons, links)
   - What actions can users perform?
   - What validations are needed?

2. **Create the Page Object**:

```java
package com.yourcompany.pages;

import com.microsoft.playwright.Page;

public class RegistrationPage extends BasePage {

    // 1. DEFINE LOCATORS
    private static final String FIRST_NAME_INPUT = "input[name='firstName']";
    private static final String LAST_NAME_INPUT = "input[name='lastName']";
    private static final String EMAIL_INPUT = "input[type='email']";
    private static final String PASSWORD_INPUT = "input[type='password']";
    private static final String REGISTER_BUTTON = "button:has-text('Register')";
    private static final String SUCCESS_MESSAGE = ".success-message";

    // 2. CONSTRUCTOR
    public RegistrationPage(Page page) {
        super(page);
    }

    // 3. NAVIGATION
    public RegistrationPage navigateToRegistration(String baseUrl) {
        page.navigate(baseUrl + "/register");
        waitForPageLoad();
        return this;
    }

    // 4. ACTIONS
    public RegistrationPage enterFirstName(String firstName) {
        fillInput(FIRST_NAME_INPUT, firstName);
        return this;
    }

    public RegistrationPage enterLastName(String lastName) {
        fillInput(LAST_NAME_INPUT, lastName);
        return this;
    }

    public RegistrationPage enterEmail(String email) {
        fillInput(EMAIL_INPUT, email);
        return this;
    }

    public RegistrationPage enterPassword(String password) {
        fillInput(PASSWORD_INPUT, password);
        return this;
    }

    public void clickRegister() {
        clickElement(REGISTER_BUTTON);
    }

    // 5. COMPOSITE ACTION
    public void register(String firstName, String lastName,
                        String email, String password) {
        enterFirstName(firstName)
            .enterLastName(lastName)
            .enterEmail(email)
            .enterPassword(password)
            .clickRegister();
    }

    // 6. VERIFICATIONS
    public boolean isSuccessMessageDisplayed() {
        return isElementVisible(SUCCESS_MESSAGE);
    }

    public String getSuccessMessage() {
        return getElementText(SUCCESS_MESSAGE);
    }
}
```

### Step 3: Create Test Data

**test-data.json**:

```json
{
  "registrationUsers": [
    {
      "userType": "valid",
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "SecurePass123!",
      "expectedResult": "success"
    },
    {
      "userType": "invalidEmail",
      "firstName": "Jane",
      "lastName": "Smith",
      "email": "invalid-email",
      "password": "SecurePass123!",
      "expectedResult": "validation_error"
    }
  ]
}
```

### Step 4: Create Test Class

```java
package com.yourcompany.tests;

import com.yourcompany.base.BaseTest;
import com.yourcompany.pages.RegistrationPage;
import com.yourcompany.utils.ConfigReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.*;

@Epic("User Management")
@Feature("User Registration")
public class RegistrationPageTest extends BaseTest {

    private RegistrationPage registrationPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        registrationPage = new RegistrationPage(page);
    }

    @Test
    @Description("Verify successful user registration with valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulRegistration() {
        // Get test data
        JsonArray users = ConfigReader.getTestData()
            .getAsJsonArray("registrationUsers");
        JsonObject validUser = users.get(0).getAsJsonObject();

        // Navigate to registration
        registrationPage.navigateToRegistration(ConfigReader.getBaseUrl());

        // Perform registration
        registrationPage.register(
            validUser.get("firstName").getAsString(),
            validUser.get("lastName").getAsString(),
            validUser.get("email").getAsString(),
            validUser.get("password").getAsString()
        );

        // Verify success
        Assert.assertTrue(registrationPage.isSuccessMessageDisplayed(),
            "Success message should be displayed");
    }
}
```

### Step 5: Update TestNG Suite

**testng.xml**:

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Your Application Test Suite" verbose="1">
    <test name="All Tests" preserve-order="true">
        <classes>
            <class name="com.yourcompany.tests.HomePageTest"/>
            <class name="com.yourcompany.tests.LoginPageTest"/>
            <class name="com.yourcompany.tests.RegistrationPageTest"/>
            <!-- Add more test classes here -->
        </classes>
    </test>
</suite>
```

### Common Modifications

#### 1. Testing Different Environments

**Create Multiple Config Files**:

```
config.properties           # Default
config-staging.properties   # Staging environment
config-production.properties # Production
```

**Load Based on Environment**:

```java
String env = System.getProperty("env", "default");
String configFile = "config" +
    (env.equals("default") ? "" : "-" + env) + ".properties";
```

**Run with Environment**:

```bash
mvn test -Denv=staging
```

#### 2. Adding API Testing

**Add REST Assured Dependency** (pom.xml):

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.2</version>
</dependency>
```

**Create API Helper**:

```java
public class APIHelper {
    public static Response get(String endpoint) {
        return RestAssured.given()
            .baseUri(ConfigReader.getBaseUrl())
            .when()
            .get(endpoint);
    }
}
```

#### 3. Database Validation

**Add JDBC Dependency**:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

**Create DB Utility**:

```java
public class DatabaseHelper {
    public static Connection getConnection() {
        return DriverManager.getConnection(
            ConfigReader.getProperty("db.url"),
            ConfigReader.getProperty("db.user"),
            ConfigReader.getProperty("db.password")
        );
    }
}
```

#### 4. Parallel Test Execution

**Update testng.xml**:

```xml
<suite name="Parallel Suite" parallel="tests" thread-count="3">
    <test name="Chrome Tests">
        <parameter name="browser" value="chromium"/>
        <classes>
            <class name="com.yourcompany.tests.HomePageTest"/>
        </classes>
    </test>

    <test name="Firefox Tests">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="com.yourcompany.tests.HomePageTest"/>
        </classes>
    </test>
</suite>
```

**Update BaseTest**:

```java
@Parameters({"browser"})
@BeforeClass
public void setUpClass(String browserName) {
    // Use browserName parameter instead of config
}
```

---

## Best Practices

### 1. Naming Conventions

**Test Methods**:

```java
// ✅ GOOD: Describes what is being tested
testLoginWithValidCredentials()
testEmptyEmailShowsValidationError()
testNavigationToHomePage()

// ❌ BAD: Vague, unclear
test1()
checkLogin()
verify()
```

**Page Objects**:

```java
// ✅ GOOD: Matches page name
HomePage, LoginPage, DashboardPage

// ❌ BAD: Generic, confusing
Page1, TestPage, MyPage
```

**Locators**:

```java
// ✅ GOOD: Describes element
private static final String EMAIL_INPUT = "input[type='email']";
private static final String SUBMIT_BUTTON = "button[type='submit']";

// ❌ BAD: Meaningless
private static final String INPUT1 = "input[type='email']";
private static final String BTN = "button";
```

### 2. Test Independence

**Each test should**:

- ✅ Run independently
- ✅ Not depend on other tests
- ✅ Set up its own data
- ✅ Clean up after itself

```java
// ✅ GOOD: Self-contained
@Test
public void testLogin() {
    loginPage.navigateToLogin(ConfigReader.getBaseUrl());
    loginPage.login("test@example.com", "password");
    Assert.assertTrue(dashboardPage.isDisplayed());
}

// ❌ BAD: Depends on previous test
@Test
public void testLogout() {
    // Assumes already logged in from previous test!
    dashboardPage.clickLogout();
}
```

### 3. Assertions

**Use Descriptive Messages**:

```java
// ✅ GOOD: Clear failure message
Assert.assertTrue(loginPage.isErrorDisplayed(),
    "Error message should be displayed for invalid credentials");

// ❌ BAD: No context
Assert.assertTrue(loginPage.isErrorDisplayed());
```

**Assert One Thing**:

```java
// ✅ GOOD: Separate assertions
@Test
public void testLoginForm() {
    Assert.assertTrue(loginPage.isEmailFieldVisible(),
        "Email field should be visible");
    Assert.assertTrue(loginPage.isPasswordFieldVisible(),
        "Password field should be visible");
}

// ❌ BAD: Combined assertion
@Test
public void testLoginForm() {
    Assert.assertTrue(
        loginPage.isEmailFieldVisible() &&
        loginPage.isPasswordFieldVisible()
    );  // Which one failed?
}
```

### 4. Waits (Handling Timing Issues)

**Playwright Auto-Waits** (already built-in):

- Waits for element to be visible
- Waits for element to be enabled
- Waits for animations to finish

**Explicit Waits When Needed**:

```java
// Wait for specific condition
page.waitForCondition(() ->
    page.locator(".success").isVisible()
);

// Wait for navigation
page.waitForNavigation(() -> {
    loginPage.clickSignIn();
});

// Wait for load state
page.waitForLoadState(LoadState.NETWORKIDLE);
```

**❌ Avoid Thread.sleep()**:

```java
// BAD: Fixed wait, wastes time
Thread.sleep(5000);

// GOOD: Wait for condition
page.waitForSelector(".element",
    new Page.WaitForSelectorOptions().setTimeout(5000));
```

### 5. Error Handling

```java
@Test
public void testWithErrorHandling() {
    try {
        loginPage.navigateToLogin(ConfigReader.getBaseUrl());
        loginPage.login("test@example.com", "password");

        Assert.assertTrue(dashboardPage.isDisplayed());
    } catch (Exception e) {
        // Take screenshot
        takeScreenshot("testLogin_failure");

        // Log error
        logger.error("Test failed: " + e.getMessage());

        // Re-throw to fail test
        throw e;
    }
}
```

### 6. Code Organization

**Keep Tests Readable**:

```java
// ✅ GOOD: Clear, reads like English
@Test
public void testSuccessfulLogin() {
    // Arrange
    String email = "test@example.com";
    String password = "password123";

    // Act
    loginPage.navigateToLogin(ConfigReader.getBaseUrl());
    loginPage.login(email, password);

    // Assert
    Assert.assertTrue(dashboardPage.isDisplayed());
}
```

**Group Related Tests**:

```java
@Test(groups = {"smoke"})
public void testCriticalPath() { }

@Test(groups = {"regression"})
public void testEdgeCase() { }

// Run specific group
// mvn test -Dgroups=smoke
```

---

## Troubleshooting Guide

### Common Issues & Solutions

#### 1. Playwright Browsers Not Installed

**Error**:

```
Playwright browser not found
```

**Solution**:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

#### 2. Element Not Found

**Error**:

```
Timeout 30000ms exceeded waiting for selector
```

**Solutions**:

**a) Check if selector is correct**:

```java
// Use Playwright Inspector to find correct selector
page.pause();  // Opens inspector
```

**b) Wait for page to load**:

```java
page.waitForLoadState();
```

**c) Increase timeout**:

```java
page.locator(selector).click(
    new Locator.ClickOptions().setTimeout(60000)
);
```

**d) Use more specific selector**:

```java
// Instead of: "button"
// Use: "button:has-text('Sign in')"
```

#### 3. Tests Pass Locally But Fail in CI/CD

**Common Causes**:

- Different viewport size
- Headless vs headed mode
- Timing issues (slower CI environment)

**Solutions**:

**a) Run in headless mode locally**:

```properties
headless=true
```

**b) Increase timeouts for CI**:

```java
if (System.getenv("CI") != null) {
    timeout = 60000;  // 60 seconds in CI
}
```

**c) Use consistent viewport**:

```properties
viewport.width=1920
viewport.height=1080
```

#### 4. Flaky Tests (Pass/Fail Randomly)

**Causes**:

- Timing issues
- Test dependencies
- External dependencies (API, database)

**Solutions**:

**a) Add explicit waits**:

```java
page.waitForSelector(".element");
```

**b) Check for element state**:

```java
page.locator(".button").click(
    new Locator.ClickOptions()
        .setForce(false)  // Wait for actionability
);
```

**c) Isolate tests**:

```java
@BeforeMethod
public void clearState() {
    // Clear cookies, storage, etc.
    context.clearCookies();
}
```

#### 5. Out of Memory Error

**Error**:

```
java.lang.OutOfMemoryError: Java heap space
```

**Solution**:

**Increase heap size** (pom.xml):

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>-Xmx2048m -XX:MaxPermSize=512m</argLine>
    </configuration>
</plugin>
```

#### 6. Compilation Errors After Adding Allure

**Error**:

```
Cannot resolve symbol 'Allure'
```

**Solution**:

```bash
# Re-download dependencies
mvn clean install

# If still fails, delete .m2 cache
rm -rf ~/.m2/repository/io/qameta/allure
mvn clean install
```

---

## Advanced Topics

### 1. Cross-Browser Testing

**Setup**:

```java
public enum BrowserType {
    CHROMIUM, FIREFOX, WEBKIT
}

@Parameters("browser")
@BeforeClass
public void setUpClass(String browserType) {
    playwright = Playwright.create();

    switch (BrowserType.valueOf(browserType.toUpperCase())) {
        case FIREFOX:
            browser = playwright.firefox().launch();
            break;
        case WEBKIT:
            browser = playwright.webkit().launch();
            break;
        default:
            browser = playwright.chromium().launch();
    }
}
```

**TestNG XML**:

```xml
<suite name="Cross-Browser Suite" parallel="tests">
    <test name="Chrome Tests">
        <parameter name="browser" value="chromium"/>
        <classes>
            <class name="com.chirangv.tests.HomePageTest"/>
        </classes>
    </test>

    <test name="Firefox Tests">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="com.chirangv.tests.HomePageTest"/>
        </classes>
    </test>

    <test name="Safari Tests">
        <parameter name="browser" value="webkit"/>
        <classes>
            <class name="com.chirangv.tests.HomePageTest"/>
        </classes>
    </test>
</suite>
```

### 2. Mobile Testing

**Emulate Mobile Devices**:

```java
@BeforeMethod
public void setUp() {
    // iPhone 12 Pro
    context = browser.newContext(
        new Browser.NewContextOptions()
            .setDeviceScaleFactor(3.0)
            .setHasTouch(true)
            .setViewportSize(390, 844)
            .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)")
    );

    page = context.newPage();
}
```

**Playwright Device Descriptors**:

```java
import com.microsoft.playwright.options.Device;

context = browser.newContext(
    new Browser.NewContextOptions(Device.IPHONE_12_PRO)
);
```

### 3. Network Interception

**Mock API Responses**:

```java
page.route("**/api/users", route -> {
    route.fulfill(new Route.FulfillOptions()
        .setStatus(200)
        .setContentType("application/json")
        .setBody("{\"name\":\"Test User\"}")
    );
});
```

**Block Resources** (faster tests):

```java
page.route("**/*.{png,jpg,jpeg}", route -> route.abort());
```

### 4. Video Recording

**Already configured in BaseTest**, but you can customize:

```java
context = browser.newContext(
    new Browser.NewContextOptions()
        .setRecordVideoDir(Paths.get("videos/"))
        .setRecordVideoSize(1280, 720)
);

// Video saved when context closes
context.close();
```

### 5. Accessibility Testing

**Add Axe-Core**:

```xml
<dependency>
    <groupId>com.deque.html.axe-core</groupId>
    <artifactId>playwright</artifactId>
    <version>4.8.0</version>
</dependency>
```

**Test Accessibility**:

```java
@Test
public void testAccessibility() {
    page.navigate("https://example.com");

    AxeResults results = new AxeBuilder(page).analyze();

    Assert.assertEquals(0, results.getViolations().size(),
        "Page should have no accessibility violations");
}
```

### 6. Visual Regression Testing

**Take Baseline Screenshot**:

```java
page.screenshot(new Page.ScreenshotOptions()
    .setPath(Paths.get("baseline/homepage.png"))
    .setFullPage(true)
);
```

**Compare with Playwright**:

```java
@Test
public void testVisualRegression() {
    page.navigate("https://example.com");

    // Playwright compares screenshot automatically
    assertThat(page).hasScreenshot("homepage.png");
}
```

---

## Glossary

| Term                        | Definition                                               |
| --------------------------- | -------------------------------------------------------- |
| **Annotation**              | Java metadata (e.g., `@Test`, `@BeforeMethod`)           |
| **Assertion**               | Check if expected = actual (e.g., `Assert.assertTrue()`) |
| **Browser Context**         | Isolated browser session (like incognito mode)           |
| **CSS Selector**            | Way to find HTML elements (e.g., `button.submit`)        |
| **Data-Driven Testing**     | Same test with different data                            |
| **Dependency**              | External library your project uses                       |
| **JSON**                    | Text format for structured data                          |
| **Locator**                 | How to find an element on a page                         |
| **Maven**                   | Build automation tool for Java                           |
| **Page Object Model (POM)** | Design pattern separating pages from tests               |
| **Playwright**              | Modern browser automation library                        |
| **Property**                | Configuration key-value pair                             |
| **Test Suite**              | Collection of tests                                      |
| **TestNG**                  | Testing framework for Java                               |
| **XPath**                   | Another way to find elements (not used here)             |

---

## Quick Reference

### Essential Commands

```bash
# Install dependencies
mvn clean install

# Run all tests
mvn clean test

# Run specific test
mvn test -Dtest=HomePageTest

# Generate Allure report
mvn allure:serve

# Install browsers (first time only)
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### File Locations

- **Config**: `src/test/resources/config.properties`
- **Test Data**: `src/test/resources/testdata/test-data.json`
- **Page Objects**: `src/main/java/com/chirangv/pages/`
- **Tests**: `src/test/java/com/chirangv/tests/`
- **Reports**: `target/allure-results/`

### Adding a New Page

1. Create `NewPage.java` in `pages/`
2. Extend `BasePage`
3. Define locators as constants
4. Add action methods
5. Create `NewPageTest.java` in `tests/`
6. Extend `BaseTest`
7. Add test methods with `@Test`

### Getting Help

- **Playwright Docs**: https://playwright.dev/java/
- **TestNG Docs**: https://testng.org/doc/
- **Allure Docs**: https://docs.qameta.io/allure/
- **Maven Docs**: https://maven.apache.org/

---

## Conclusion

This framework provides a **solid foundation** for web automation testing. Key takeaways:

✅ **Modular Design**: Easy to maintain and extend  
✅ **Data-Driven**: Separate data from logic  
✅ **Configurable**: Adapt to different environments  
✅ **Comprehensive Reporting**: Beautiful Allure reports  
✅ **Best Practices**: Industry-standard patterns

**Next Steps**:

1. Run the existing tests
2. Examine the Allure report
3. Create a new page object for your application
4. Write tests for new functionality
5. Integrate with CI/CD

Happy Testing! 🚀
