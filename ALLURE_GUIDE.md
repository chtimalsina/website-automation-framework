# Allure Reporting - Quick Start Guide

## What is Allure?

Allure is a flexible, lightweight test reporting tool that generates beautiful, interactive HTML reports from your test results.

## Features

✅ **Beautiful Dashboard** - Overview of all test results with graphs  
✅ **Detailed Test Reports** - Each test with steps, attachments, and timing  
✅ **Screenshots on Failure** - Automatically attached when tests fail  
✅ **Categorization** - Group tests by features, stories, and severity  
✅ **Trend Analysis** - Compare results across multiple test runs  
✅ **Timeline View** - See execution timeline

## How to Use

### 1. Run Tests and Generate Report

**Option A: Interactive Report (Recommended)**

```bash
mvn clean test allure:serve
```

This will:

1. Clean previous results
2. Run all tests
3. Generate the report
4. Start a local server
5. Open the report in your browser automatically

**Option B: Static Report**

```bash
# Run tests
mvn clean test

# Generate report
mvn allure:report

# Open report manually
open target/site/allure-maven-plugin/index.html
```

### 2. Using VS Code Tasks

1. Press `Cmd+Shift+P` (Mac) or `Ctrl+Shift+P` (Windows)
2. Type "Tasks: Run Task"
3. Select one of:
   - **"Run Tests and Generate Report"** - Runs tests and opens Allure
   - **"Generate Allure Report"** - Just opens report (after tests already run)

## Understanding the Report

### Overview Page

When you open Allure, you'll see:

1. **Total Stats**

   - ✅ Passed: Number of successful tests
   - ❌ Failed: Tests that failed assertions
   - ⚠️ Broken: Tests that threw exceptions
   - ⏭️ Skipped: Tests that were not executed

2. **Severity Distribution**

   - 🔴 Blocker: Critical functionality
   - 🟠 Critical: Important features
   - 🟡 Normal: Standard features
   - 🟢 Minor: Nice-to-have features
   - ⚪ Trivial: Low priority features

3. **Duration Graph**

   - How long each test took

4. **Trend Chart** (after multiple runs)
   - Pass/Fail rates over time

### Suites Page

Shows tests organized by test classes:

- HomePageTest
- LoginPageTest
- DeveloperPageTest

Click any test to see:

- Test name and description
- Execution time
- Status (passed/failed)
- Screenshot (if failed)
- Full error details (if failed)

### Graphs Page

Visual representations:

- **Status Chart**: Pie chart of pass/fail
- **Severity Chart**: Distribution by severity
- **Duration Chart**: How long tests took
- **Categories**: Failure categorization

### Behaviors Page

Tests organized by user stories:

- Epic → Feature → Story → Tests

Example:

```
📁 Website Testing (Epic)
  📁 Home Page (Feature)
    📄 Page Load Validation (Story)
      ✅ testHomePageLoads
    📄 Content Visibility (Story)
      ✅ testAllMainSectionsVisible
    📄 Navigation Flow (Story)
      ✅ testNavigationToLoginPage
      ✅ testNavigationToDeveloperPage
```

### Timeline Page

Shows when each test ran and how long it took.
Useful for:

- Identifying slow tests
- Finding tests that ran in parallel
- Debugging timing issues

## Enhancing Reports with Annotations

### Available Annotations

```java
import io.qameta.allure.*;

@Epic("Website Testing")           // High-level feature group
@Feature("Home Page")               // Specific feature being tested
public class HomePageTest {

    @Test
    @Story("Page Load Validation")  // User story
    @Description("Detailed test description")
    @Severity(SeverityLevel.BLOCKER) // Importance level
    public void testHomePageLoads() {
        // Test code
    }
}
```

### Severity Levels

- **BLOCKER**: Test prevents basic functionality
- **CRITICAL**: Test covers important features
- **NORMAL**: Test covers standard features (default)
- **MINOR**: Test covers minor features
- **TRIVIAL**: Test covers trivial features

### Adding Steps

```java
import static io.qameta.allure.Allure.step;

@Test
public void testLogin() {
    step("Navigate to login page", () -> {
        loginPage.navigateToLogin(baseUrl);
    });

    step("Enter credentials", () -> {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("password");
    });

    step("Click sign in button", () -> {
        loginPage.clickSignIn();
    });

    step("Verify login success", () -> {
        Assert.assertTrue(dashboardPage.isDisplayed());
    });
}
```

### Adding Attachments

Screenshots are automatically attached on failure, but you can add custom attachments:

```java
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

@Test
public void testWithAttachment() {
    // Add text attachment
    Allure.addAttachment("Test Data", "application/json",
        "{\"user\":\"test@example.com\"}", "json");

    // Add screenshot manually
    byte[] screenshot = page.screenshot();
    Allure.addAttachment("Custom Screenshot", "image/png",
        new ByteArrayInputStream(screenshot), "png");
}
```

## Configuration

### allure.properties

Located in: `src/test/resources/allure.properties`

```properties
# Where to save raw results
allure.results.directory=target/allure-results

# Project information
allure.project.name=ChirangV Website Automation Tests
allure.project.author=Chiran GV
```

### Maven Configuration

Already configured in `pom.xml`:

```xml
<properties>
    <allure.version>2.25.0</allure.version>
</properties>

<dependencies>
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-testng</artifactId>
        <version>${allure.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-maven</artifactId>
            <version>2.12.0</version>
        </plugin>
    </plugins>
</build>
```

## Automatic Screenshot on Failure

Already implemented in `BaseTest.java`:

```java
@AfterMethod
public void tearDown(ITestResult result) {
    // Attach screenshot to Allure report on failure
    if (result.getStatus() == ITestResult.FAILURE) {
        attachScreenshotToAllure(result.getName());
    }
    // ... rest of teardown
}

protected void attachScreenshotToAllure(String testName) {
    byte[] screenshot = page.screenshot();
    Allure.addAttachment(testName + " - Screenshot", "image/png",
        new ByteArrayInputStream(screenshot), "png");
}
```

## Tips & Best Practices

### 1. Run Tests Before Important Demos

```bash
mvn clean test allure:serve
```

Have the report ready to show stakeholders.

### 2. Keep History

Allure can show trends if you keep previous results:

```bash
# Copy previous results
cp -r target/allure-results/history target/allure-results-backup/

# Run new tests
mvn clean test

# Copy history back
cp -r target/allure-results-backup/history target/allure-results/

# Generate report with trends
mvn allure:serve
```

### 3. Filter by Severity

In the Allure report, you can filter tests by severity level to focus on critical tests first.

### 4. Share Reports

Generate static report and share the HTML folder:

```bash
mvn allure:report
zip -r allure-report.zip target/site/allure-maven-plugin/
```

### 5. CI/CD Integration

For Jenkins, GitHub Actions, etc.:

```bash
# Generate report
mvn clean test allure:report

# Publish the directory: target/site/allure-maven-plugin/
```

## Troubleshooting

### Report Not Generating

**Problem**: `mvn allure:serve` does nothing

**Solution**:

```bash
# Clear cache
rm -rf target/allure-results
rm -rf .allure

# Reinstall dependencies
mvn clean install

# Try again
mvn clean test allure:serve
```

### No Tests in Report

**Problem**: Report shows 0 tests

**Solutions**:

1. Make sure tests actually ran:

   ```bash
   mvn clean test
   # Look for "Tests run: X" in output
   ```

2. Check allure-results directory:

   ```bash
   ls -la target/allure-results/
   # Should contain JSON files
   ```

3. Ensure allure dependency is in pom.xml

### Screenshots Not Attached

**Problem**: Failed tests don't show screenshots

**Solution**: Check that `BaseTest.tearDown()` has the failure handler:

```java
@AfterMethod
public void tearDown(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
        attachScreenshotToAllure(result.getName());
    }
}
```

## Example Workflow

### Daily Development

```bash
# 1. Make changes to tests
# 2. Run affected tests
mvn test -Dtest=HomePageTest

# 3. Quick check - did it pass?
# 4. If failed, check logs
```

### Before Committing

```bash
# Run all tests with report
mvn clean test allure:serve

# Review report in browser
# Fix any failures
# Commit when all green
```

### Weekly Regression

```bash
# Full test suite
mvn clean test

# Generate comprehensive report
mvn allure:serve

# Review trends
# Identify flaky tests
# Update test suite
```

## Advanced: Categories

Create `src/test/resources/categories.json`:

```json
[
  {
    "name": "Product Defects",
    "matchedStatuses": ["failed"],
    "messageRegex": ".*AssertionError.*"
  },
  {
    "name": "Test Defects",
    "matchedStatuses": ["broken"],
    "messageRegex": ".*Exception.*"
  },
  {
    "name": "Ignored Tests",
    "matchedStatuses": ["skipped"]
  }
]
```

This categorizes failures in the report.

## Learn More

- **Official Docs**: https://docs.qameta.io/allure/
- **Examples**: https://github.com/allure-examples
- **TestNG Integration**: https://docs.qameta.io/allure/#_testng

---

**Happy Testing with Beautiful Reports! 📊✨**
