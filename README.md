# ChirangV Website Automation Tests

A comprehensive TestNG + Playwright automation testing framework for [dev.chirangv.com](https://dev.chirangv.com) with beautiful Allure reporting.

> 📚 **New to this project?** Check out [PROJECT_NOTES.md](PROJECT_NOTES.md) for an extensive beginner-friendly guide!

## 📋 Project Overview

This project provides automated tests for the ChirangV personal website, covering:

- Home Page functionality
- Login Page validation
- Developer Portfolio page
- Navigation flows
- Form validations

## 🛠️ Technology Stack

- **Java 11** - Programming language
- **Maven** - Build and dependency management
- **TestNG** - Testing framework
- **Playwright** - Browser automation
- **Gson** - JSON parsing for test data
- **SLF4J** - Logging
- **Allure** - Advanced test reporting with visual dashboards

## 📁 Project Structure

```
testAutomationPersonalWebsite/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/chirangv/
│   │           ├── pages/          # Page Object Model classes
│   │           │   ├── BasePage.java
│   │           │   ├── HomePage.java
│   │           │   ├── LoginPage.java
│   │           │   └── DeveloperPage.java
│   │           └── utils/          # Utility classes
│   │               └── ConfigReader.java
│   └── test/
│       ├── java/
│       │   └── com/chirangv/
│       │       ├── base/           # Base test class
│       │       │   └── BaseTest.java
│       │       └── tests/          # Test classes
│       │           ├── HomePageTest.java
│       │           ├── LoginPageTest.java
│       │           └── DeveloperPageTest.java
│       └── resources/
│           ├── config.properties   # Configuration file
│           └── testdata/
│               └── test-data.json  # Test data
├── testng.xml                      # TestNG suite file
└── pom.xml                         # Maven configuration
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- Internet connection (for Playwright browser downloads)

### Installation

1. Clone the repository or extract the project

2. Install Playwright browsers (first time only):

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

3. Install project dependencies:

```bash
mvn clean install -DskipTests
```

## ▶️ Running Tests

### Run all tests

```bash
mvn clean test
```

### Run specific test class

```bash
mvn test -Dtest=HomePageTest
mvn test -Dtest=LoginPageTest
mvn test -Dtest=DeveloperPageTest
```

### Run with TestNG XML

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Generate and View Allure Report

```bash
# Run tests and open interactive report
mvn clean test allure:serve

# Or generate static report
mvn allure:report
# Report will be in: target/site/allure-maven-plugin/
```

## 📊 Allure Reporting

This project includes **Allure Framework** for beautiful, interactive test reports with:

- ✅ Visual dashboards with graphs and trends
- 📸 Automatic screenshot attachments on test failures
- 📋 Detailed test steps and logs
- 📈 Historical trend analysis
- 🔍 Categorization by features and severity

### Viewing Reports

**Option 1: Interactive Server (Recommended)**

```bash
mvn allure:serve
```

This will:

1. Generate the report
2. Start a local web server
3. Automatically open the report in your browser

**Option 2: Static HTML Report**

```bash
mvn allure:report
open target/site/allure-maven-plugin/index.html
```

**Option 3: VS Code Task**

- Press `Cmd+Shift+P` (Mac) or `Ctrl+Shift+P` (Windows)
- Select "Tasks: Run Task"
- Choose "Run Tests and Generate Report"

### Understanding Allure Reports

The report includes:

- **Overview**: Total tests, pass rate, duration
- **Suites**: Tests organized by test classes
- **Graphs**: Visual representation of results
- **Timeline**: Execution timeline
- **Behaviors**: Tests grouped by features
- **Packages**: Tests organized by Java packages

Each failed test automatically includes:

- Screenshot at the point of failure
- Full stack trace
- Test execution timeline
- Browser console logs (if enabled)

## ⚙️ Configuration

## ⚙️ Configuration

### config.properties

Located at `src/test/resources/config.properties`

```properties
baseUrl=https://dev.chirangv.com
browser=chromium              # Options: chromium, firefox, webkit
headless=false                # true for headless mode
timeout=30000                 # Default timeout in milliseconds
screenshotOnFailure=true      # Capture screenshot on test failure
videoOnFailure=true           # Record video on test failure
slowMo=0                      # Slow down operations (ms)
viewport.width=1920
viewport.height=1080
```

### test-data.json

Located at `src/test/resources/testdata/test-data.json`

Contains:

- Test user data (userType, name, email, phone, username, password)
- Login credentials with expected results
- Navigation links
- Page validation data

## 📊 Test Reports

After test execution, reports are generated in:

- `target/surefire-reports/` - TestNG reports
- `target/screenshots/` - Screenshots on failure
- `target/videos/` - Video recordings on failure

### View HTML Report

Open `target/surefire-reports/index.html` in a browser

## 🧪 Test Coverage

### Home Page Tests (6 tests)

- ✅ Page loads successfully
- ✅ All main sections visible
- ✅ Navigation to Login page
- ✅ Navigation to Developer page
- ✅ Welcome message validation
- ✅ Page title verification

### Login Page Tests (8 tests)

- ✅ Page loads successfully
- ✅ All form elements present
- ✅ Google sign-in option available
- ✅ Login with valid credentials
- ✅ Login with invalid email
- ✅ Login with empty fields
- ✅ Navigation back to home
- ✅ Page title verification

### Developer Page Tests (10 tests)

- ✅ Page loads successfully
- ✅ Technical expertise section
- ✅ All expertise areas mentioned
- ✅ Skills & Technologies section
- ✅ All skill categories present
- ✅ Developer resources section
- ✅ All main sections visible
- ✅ Navigation to login
- ✅ Page title verification
- ✅ Complete skills and expertise

## 🎨 Page Object Model

The framework follows the **Page Object Model (POM)** design pattern:

- **BasePage**: Common methods for all pages
- **HomePage**: Home page elements and actions
- **LoginPage**: Login form interactions
- **DeveloperPage**: Developer portfolio interactions

## 📝 Writing New Tests

> 📖 For detailed instructions on adapting this framework, see [PROJECT_NOTES.md](PROJECT_NOTES.md#how-to-modify-for-similar-projects)

1. Create test data in `test-data.json`
2. Create page object in `src/main/java/com/chirangv/pages/`
3. Create test class in `src/test/java/com/chirangv/tests/`
4. Extend `BaseTest` class
5. Add test to `testng.xml`

Example:

```java
public class NewPageTest extends BaseTest {
    private NewPage newPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        newPage = new NewPage(page);
    }

    @Test
    public void testSomething() {
        newPage.navigateTo(ConfigReader.getBaseUrl());
        Assert.assertTrue(newPage.isVisible());
    }
}
```

## 🔧 Troubleshooting

### Playwright browsers not installed

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Tests failing due to timeout

Increase timeout in `config.properties`:

```properties
timeout=60000
```

### Run in headless mode

```properties
headless=true
```

## 📚 Additional Resources

- [Playwright Java Documentation](https://playwright.dev/java/)
- [TestNG Documentation](https://testng.org/doc/)
- [Maven Documentation](https://maven.apache.org/)

## 👤 Author

Chiran GV

## 📄 License

This project is for testing purposes of dev.chirangv.com

---

**Happy Testing! 🚀**

