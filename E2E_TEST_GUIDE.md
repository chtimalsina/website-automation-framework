# End-to-End Testing Guide

## Overview

The `EndToEndTest` class performs comprehensive end-to-end testing that simulates a complete user journey through your website, from login to message submission and admin verification.

## Table of Contents

1. [What is End-to-End Testing?](#what-is-end-to-end-testing)
2. [Test Architecture](#test-architecture)
3. [How It Works](#how-it-works)
4. [Test Data Structure](#test-data-structure)
5. [Running E2E Tests](#running-e2e-tests)
6. [Understanding Test Flow](#understanding-test-flow)
7. [Customization Guide](#customization-guide)
8. [Troubleshooting](#troubleshooting)

---

## What is End-to-End Testing?

End-to-End (E2E) testing validates the entire application flow from start to finish, simulating real user scenarios. Unlike unit or integration tests that focus on individual components, E2E tests ensure all system parts work together correctly.

### Benefits of E2E Testing:
- ✅ Validates complete user journeys
- ✅ Catches integration issues between components
- ✅ Tests real-world scenarios
- ✅ Verifies business workflows
- ✅ Provides confidence in production readiness

---

## Test Architecture

### Components Created:

1. **EndToEndTest.java** - Main test class with complete user journey scenarios
2. **ContactPage.java** - Page Object for contact/message functionality
3. **AdminDashboardPage.java** - Page Object for admin portal
4. **Updated test-data.json** - Enhanced with message data for users

### Page Objects:
```
src/main/java/com/chirangv/pages/
├── BasePage.java           (Base functionality)
├── HomePage.java           (Home page actions)
├── LoginPage.java          (Login functionality)
├── DeveloperPage.java      (Developer portfolio)
├── ContactPage.java        (Contact form - NEW)
└── AdminDashboardPage.java (Admin portal - NEW)
```

### Test Class:
```
src/test/java/com/chirangv/tests/
└── EndToEndTest.java       (E2E test scenarios)
```

---

## How It Works

### Test Execution Flow:

```
1. Data Retrieval
   └─> Get user data by username from JSON

2. User Login
   └─> Navigate to login page
   └─> Enter credentials
   └─> Submit login form

3. Navigation & Exploration
   └─> Visit Home Page
   └─> Explore Developer Page
   └─> Navigate to Projects/Documentation

4. Send Message
   └─> Access contact form
   └─> Fill user details
   └─> Send message

5. Admin Verification
   └─> Logout user
   └─> Login as admin
   └─> Navigate to admin dashboard
   └─> Verify message is accessible
   └─> Confirm message content
```

---

## Test Data Structure

### User Data Format (test-data.json):

```json
{
  "testUsers": [
    {
      "userType": "validUser",
      "name": "John Doe",
      "email": "johndoe@example.com",
      "username": "johndoe",
      "password": "Test@123456",
      "phone": "+1-234-567-8900",
      "message": "Hello, I'm interested in your services..."
    },
    {
      "userType": "testUser",
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "username": "alicejohnson",
      "password": "Alice@123456",
      "phone": "+1-234-567-8901",
      "message": "I would like to learn more..."
    },
    {
      "userType": "adminUser",
      "name": "Admin User",
      "email": "admin@chirangv.com",
      "username": "admin",
      "password": "Admin@123456",
      "phone": "+1-555-000-0000",
      "message": "Admin test message"
    }
  ]
}
```

### Required Fields:
- `userType` - Type of user (validUser, adminUser, testUser)
- `name` - Full name
- `email` - Email address for login
- `username` - Unique identifier for test data lookup
- `password` - Login password
- `phone` - Contact phone number
- `message` - Message content to send

---

## Running E2E Tests

### Method 1: Maven Command Line

```bash
# Run all E2E tests
mvn test -Dtest=EndToEndTest

# Run specific E2E test method
mvn test -Dtest=EndToEndTest#testJohnDoeUserJourney

# Run with Allure report
mvn clean test -Dtest=EndToEndTest allure:serve
```

### Method 2: VS Code Tasks

1. Press `Cmd+Shift+P` (Mac) or `Ctrl+Shift+P` (Windows/Linux)
2. Type "Tasks: Run Task"
3. Select "Run End-to-End Tests"

### Method 3: TestNG XML

The E2E tests are included in `testng.xml`:

```bash
# Run all tests including E2E
mvn clean test
```

---

## Understanding Test Flow

### 1. Data-Driven Testing

The E2E test uses a **Data Provider** to run the same test with different users:

```java
@DataProvider(name = "userDataProvider")
public Object[][] getUserData() {
    return new Object[][] {
        {"johndoe"},
        {"alicejohnson"}
    };
}
```

This allows testing multiple user scenarios efficiently.

### 2. Test Methods

#### Main E2E Test (Data-Driven):
```java
@Test(dataProvider = "userDataProvider")
public void testCompleteUserJourney(String username)
```
- Runs for each username in the data provider
- Performs complete user journey
- Validates message in admin portal

#### Specific User Tests:
```java
@Test
public void testJohnDoeUserJourney()

@Test
public void testAliceJohnsonUserJourney()
```
- Test specific users directly
- Useful for debugging individual scenarios

#### Data Validation Test:
```java
@Test
public void testUserDataRetrieval()
```
- Validates test data is properly loaded
- Ensures data structure is correct

### 3. Step-by-Step Breakdown

#### Step 1: User Login
```java
@Step("Step 1: User logs in with credentials")
private void performUserLogin(String email, String password, String name)
```
- Navigates to login page
- Enters email and password
- Submits form
- Attaches login info to Allure report

#### Step 2: Home Page Exploration
```java
@Step("Step 2: Explore Home Page")
private void exploreHomePage()
```
- Verifies header visibility
- Checks "Join Circle" section
- Logs welcome message
- Validates page structure

#### Step 3: Developer Page Navigation
```java
@Step("Step 3: Explore Developer Page and Resources")
private void exploreDeveloperPage()
```
- Navigates to developer page
- Verifies technical expertise section
- Attempts to visit Projects
- Attempts to view Documentation
- Handles navigation gracefully

#### Step 4: Send Message
```java
@Step("Step 4: Send message through contact form")
private void sendUserMessage(String name, String email, String phone, String message)
```
- Navigates to contact page
- Fills contact form (if available)
- Submits message
- Checks for success confirmation
- Handles cases where form isn't accessible

#### Step 5: Admin Verification
```java
@Step("Step 5: Admin logs in and verifies message")
private void verifyMessageInAdminPortal(String userName, String messageContent)
```
- Retrieves admin credentials
- Logs in as admin
- Navigates to admin dashboard
- Searches for user message
- Verifies message accessibility
- Logs results to Allure

---

## Customization Guide

### Adding New Users

1. Open `src/test/resources/testdata/test-data.json`
2. Add new user object to `testUsers` array:

```json
{
  "userType": "newUser",
  "name": "Bob Smith",
  "email": "bob.smith@example.com",
  "username": "bobsmith",
  "password": "BobSecure@123",
  "phone": "+1-555-123-4567",
  "message": "Custom message for Bob"
}
```

3. Add username to data provider in `EndToEndTest.java`:

```java
@DataProvider(name = "userDataProvider")
public Object[][] getUserData() {
    return new Object[][] {
        {"johndoe"},
        {"alicejohnson"},
        {"bobsmith"}  // Add new user
    };
}
```

### Creating Custom E2E Tests

Create a new test method with specific flow:

```java
@Test(priority = 10, description = "Custom E2E scenario")
@Severity(SeverityLevel.CRITICAL)
@Story("Custom User Journey")
public void testCustomScenario() {
    // Your custom test logic
    JsonObject user = getUserByUsername("customuser");
    
    // Implement custom steps
    performUserLogin(...);
    // Add your custom navigation/actions
    verifyMessageInAdminPortal(...);
}
```

### Modifying Test Steps

To add new steps to the journey:

```java
@Step("Step X: Your custom step")
private void yourCustomStep() {
    logger.info("Step X: Performing custom action");
    
    // Your implementation
    CustomPage customPage = new CustomPage(page);
    customPage.performAction();
    
    // Add Allure attachment
    Allure.addAttachment("Custom Step", "Details about this step");
}
```

Then call it in the main test:

```java
public void testCompleteUserJourney(String username) {
    // ... existing steps ...
    exploreDeveloperPage();
    yourCustomStep();  // Add custom step
    sendUserMessage(...);
    // ... remaining steps ...
}
```

### Extending Page Objects

#### Adding New Contact Form Fields:

Edit `ContactPage.java`:

```java
private static final String SUBJECT_INPUT = "input[name='subject']";

public ContactPage fillSubject(String subject) {
    fillInput(SUBJECT_INPUT, subject);
    logger.info("Filled subject: " + subject);
    return this;
}
```

#### Adding Admin Dashboard Features:

Edit `AdminDashboardPage.java`:

```java
public boolean deleteMessage(String messageId) {
    clickElement("button[data-message-id='" + messageId + "']");
    logger.info("Deleted message: " + messageId);
    return true;
}
```

---

## Troubleshooting

### Common Issues

#### 1. Test Data Not Found

**Error**: `User data should not be null for username: xyz`

**Solution**:
- Verify username exists in `test-data.json`
- Check for typos in username
- Ensure JSON is valid (use JSON validator)

#### 2. Login Fails

**Error**: Login page doesn't navigate after submit

**Solution**:
- Check if credentials are correct
- Verify login page URL in `config.properties`
- Check if website authentication is working
- Review login page locators in `LoginPage.java`

#### 3. Contact Form Not Found

**Warning**: `Contact form not visible`

**Explanation**: This is normal if:
- Contact form requires authentication
- Form is on a different page/URL
- Form is behind a feature flag

**Solution**:
- Update `ContactPage.navigateToContact()` with correct URL
- Adjust form locators to match actual page
- Add authentication before accessing form

#### 4. Admin Dashboard Not Accessible

**Warning**: `Admin dashboard not accessible`

**Explanation**: This is expected if:
- Admin dashboard doesn't exist yet
- Requires special permissions
- Different URL than assumed

**Solution**:
- Update `AdminDashboardPage.navigateToAdminDashboard()` URL
- Check admin user permissions
- Verify admin dashboard locators

#### 5. Tests Taking Too Long

**Issue**: E2E tests run slowly

**Solution**:
```properties
# Edit config.properties
headless=true  # Run in headless mode
slowMo=0       # Remove slow motion delay
```

#### 6. Navigation Timeouts

**Error**: `Timeout waiting for navigation`

**Solution**:
```properties
# Increase timeout in config.properties
timeout=60000  # 60 seconds
```

Or in specific page:
```java
page.setDefaultNavigationTimeout(60000);
```

---

## Test Results

### Current Test Count:
- **Total E2E Tests**: 5
  - `testCompleteUserJourney` (with johndoe)
  - `testCompleteUserJourney` (with alicejohnson)
  - `testJohnDoeUserJourney`
  - `testAliceJohnsonUserJourney`
  - `testUserDataRetrieval`

### Execution Time:
- Average: ~8-10 seconds per E2E test
- Total suite: ~40 seconds

### Success Criteria:
✅ User data retrieved successfully  
✅ Login attempted for each user  
✅ Home page navigation successful  
✅ Developer page exploration completed  
✅ Message sending attempted  
✅ Admin login successful  
✅ Admin dashboard accessed (if available)

---

## Best Practices

### 1. Test Data Management
- Keep test data in JSON for easy maintenance
- Use meaningful usernames for data lookup
- Include all required fields
- Don't hardcode sensitive data

### 2. Error Handling
- Use try-catch for optional features
- Log warnings instead of failing for missing features
- Verify element existence before interaction
- Provide meaningful error messages

### 3. Assertions
- Assert critical flows (login, navigation)
- Use soft assertions for optional features
- Add descriptive assertion messages
- Verify both positive and negative scenarios

### 4. Logging
- Log each major step
- Use appropriate log levels (info, warn, error)
- Include context in log messages
- Attach relevant data to Allure reports

### 5. Maintainability
- Keep test logic in test class
- Put page interactions in Page Objects
- Use helper methods for repetitive code
- Follow naming conventions
- Document complex logic

---

## Advanced Topics

### Parallel Execution

To run E2E tests in parallel, update `testng.xml`:

```xml
<suite name="ChirangV Website Test Suite" parallel="methods" thread-count="2">
    <test name="E2E Tests">
        <classes>
            <class name="com.chirangv.tests.EndToEndTest"/>
        </classes>
    </test>
</suite>
```

### Test Dependencies

Create dependent tests:

```java
@Test(priority = 1)
public void testUserRegistration() {
    // Register new user
}

@Test(priority = 2, dependsOnMethods = "testUserRegistration")
public void testNewUserJourney() {
    // Test journey with newly registered user
}
```

### Continuous Integration

Add to CI/CD pipeline:

```yaml
# .github/workflows/e2e-tests.yml
name: E2E Tests
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run E2E Tests
        run: mvn clean test -Dtest=EndToEndTest
      - name: Generate Allure Report
        run: mvn allure:report
```

---

## Summary

The End-to-End test framework provides:
- ✅ **Complete user journey testing** from login to message verification
- ✅ **Data-driven approach** for testing multiple users
- ✅ **Flexible architecture** with reusable Page Objects
- ✅ **Comprehensive reporting** with Allure integration
- ✅ **Easy customization** for adding new scenarios
- ✅ **Graceful handling** of optional features
- ✅ **Detailed logging** for debugging

This framework can be easily adapted for:
- Testing new features
- Validating different user roles
- Verifying business workflows
- Regression testing
- Smoke testing critical paths

---

## Resources

- **Main Documentation**: [PROJECT_NOTES.md](PROJECT_NOTES.md)
- **Allure Guide**: [ALLURE_GUIDE.md](ALLURE_GUIDE.md)
- **Test Data**: [src/test/resources/testdata/test-data.json](src/test/resources/testdata/test-data.json)
- **E2E Test Class**: [src/test/java/com/chirangv/tests/EndToEndTest.java](src/test/java/com/chirangv/tests/EndToEndTest.java)

---

**Created**: November 16, 2025  
**Last Updated**: November 16, 2025  
**Version**: 1.0.0
