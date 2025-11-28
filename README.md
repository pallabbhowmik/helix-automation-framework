# Helix Automation Framework

A production-ready, enterprise-grade test automation framework for web and API testing, built with industry best practices.

## 🚀 Features

### Core Capabilities
- **Web UI Testing**: Selenium WebDriver 4.35 with Page Object Model
- **API Testing**: REST Assured for API validation
- **Data-Driven Testing**: JSON/CSV support with TestNG DataProvider
- **Enhanced Reporting**: Allure reports with screenshots and detailed logs
- **Parallel Execution**: TestNG parallel test execution support
- **Retry Mechanism**: Automatic retry for flaky tests
- **CI/CD Ready**: Jenkins pipeline with Allure integration

### Framework Enhancements
- ✅ **ThreadLocal WebDriver** for parallel execution safety
- ✅ **Enhanced Wait Mechanisms** with explicit waits and retry logic
- ✅ **Automatic Screenshot Capture** on test failures
- ✅ **Structured Logging** with SLF4J and Log4j2
- ✅ **Test Data Management** with centralized JSON/CSV files
- ✅ **Stale Element Retry** built into BasePage
- ✅ **Configuration Hierarchy** (System Props → Env Vars → .env.config → classpath)

---

## 📋 Target Application

**PassTheNote** (https://www.passthenote.com) - A full-stack web application used as the testbed for this framework.

---

## 🏗️ Project Structure

```
src/
├── main/java/com/helix/automation/framework/
│   ├── config/         # ConfigManager - environment & configuration
│   ├── core/           # BasePage, DriverManager, WebDriverFactory, TestListener, RetryAnalyzer
│   ├── pages/          # Page Object Model classes (LoginPage, NotesPage, etc.)
│   ├── api/            # API clients and models
│   ├── flows/          # Business flows combining page actions
│   └── utils/          # TestDataProvider, ScreenshotUtil
│
├── test/java/com/helix/automation/tests/
│   ├── ui/             # UI test suites (NotesTests, AuthTests, etc.)
│   ├── api/            # API test suites
│   └── integration/    # Cross-layer integration tests
│
└── test/resources/
    ├── testdata/       # JSON/CSV test data files
    ├── config-*.properties  # Environment-specific configs
    └── testng.xml      # TestNG suite configuration
```

---

## ⚙️ Configuration

Configuration is loaded with the following precedence (highest → lowest):

1. **System Properties** (`-Dkey=value`)
   ```bash
   ./gradlew test -Denv=qa -Dbrowser=chrome -Dheadless=true
   ```

2. **Environment Variables**
   ```bash
   export ENV=qa
   export BASE_URL=https://www.passthenote.com
   export USERNAME=testuser@example.com
   export PASSWORD=Test@1234
   ```

3. **`.env.config` file** (project root)
   ```properties
   ENV=dev
   BASE_URL=https://www.passthenote.com
   USERNAME=testuser@example.com
   PASSWORD=Test@1234
   TIMEOUT=10
   ```

4. **Classpath properties** (`src/test/resources/config-<env>.properties`)

### Configuration Files
- `.env.example` - Template with placeholders
- `.env.config` - Local configuration (gitignored)
- `config-dev.properties` - Development environment defaults
- `config-qa.properties` - QA environment defaults

---

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Gradle 7.x or higher
- Chrome browser (for default WebDriver)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd passthenote-automation
   ```

2. **Create local configuration**
   ```bash
   cp .env.example .env.config
   # Edit .env.config with your credentials
   ```

3. **Download dependencies**
   ```bash
   ./gradlew build --refresh-dependencies
   ```

### Running Tests

**Compile without running tests:**
```bash
./gradlew clean build -x test
```

**Run all tests:**
```bash
./gradlew clean test
```

**Run with specific browser:**
```bash
./gradlew test -Dbrowser=chrome
./gradlew test -Dbrowser=firefox
./gradlew test -Dbrowser=edge
```

**Run in headless mode:**
```bash
./gradlew test -Dheadless=true
```

**Run specific test class:**
```bash
./gradlew test --tests NotesTests
./gradlew test --tests AuthTests
```

**Run API tests only:**
```bash
./gradlew test -Dgroups=api
```

**Run with retry enabled:**
```bash
./gradlew test -Dtest.retry.count=3
```

**Run with custom timeout:**
```bash
./gradlew test -DTIMEOUT=15
```

---

## 📊 Reporting

### Allure Reports

**Generate and view Allure report:**
```bash
./gradlew allure:serve
```

**Generate Allure report only:**
```bash
./gradlew allure:report
```

Reports include:
- Test execution timeline
- Screenshots on failure
- Error messages and stack traces
- Test duration and status
- Retry attempts

### Screenshots

Screenshots are automatically captured on test failure and saved to:
- `build/screenshots/` - File system
- Allure report - Embedded in report

---

## 📝 Writing Tests

### UI Test Example

```java
package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.LoginPage;
import com.helix.automation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.enterEmail("testuser@example.com");
        loginPage.enterPassword("Test@1234");
        loginPage.clickSignIn();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("/app"));
    }
}
```

### Data-Driven Test Example

```java
@DataProvider(name = "validUsers")
public Object[][] validUsersData() {
    UserData data = TestDataProvider.readJson("testdata/users.json", UserData.class);
    return TestDataProvider.toDataProviderArray(data.getValidUsers());
}

@Test(dataProvider = "validUsers")
public void testLoginWithValidUser(User user) {
    LoginPage loginPage = new LoginPage();
    loginPage.open();
    loginPage.enterEmail(user.getEmail());
    loginPage.enterPassword(user.getPassword());
    loginPage.clickSignIn();
    
    Assert.assertTrue(driver.getCurrentUrl().contains("/app"));
}
```

### API Test Example

```java
@Test
public void testLoginApiReturnsToken() {
    AuthRequest req = new AuthRequest("testuser@example.com", "Test@1234");
    
    Response response = ApiSpecs.base()
        .body(req)
        .when()
        .post("/auth/login");
    
    Assert.assertEquals(response.getStatusCode(), 200);
    AuthResponse authResponse = response.as(AuthResponse.class);
    Assert.assertNotNull(authResponse.getToken());
}
```

### Page Object Example

```java
public class LoginPage extends BasePage {
    private final By emailInput = By.xpath("//input[@type='email']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By signInButton = By.xpath("//button[contains(.,'Sign In')]");
    
    public LoginPage open() {
        driver.get(ConfigManager.getBaseUrl() + "/auth/login");
        return this;
    }
    
    public void enterEmail(String email) {
        type(emailInput, email);  // Uses enhanced BasePage method
    }
    
    public void enterPassword(String password) {
        type(passwordInput, password);
    }
    
    public void clickSignIn() {
        click(signInButton);  // Includes retry logic
    }
}
```

---

## 🧪 Test Data Management

Test data is stored in `src/test/resources/testdata/` as JSON or CSV files.

**Example: users.json**
```json
{
  "validUsers": [
    {
      "email": "testuser@passthenote.com",
      "password": "Test@1234",
      "fullName": "Test User"
    }
  ],
  "invalidUsers": [
    {
      "email": "invalid@example.com",
      "password": "WrongPassword",
      "expectedError": "Invalid credentials"
    }
  ]
}
```

**Loading test data:**
```java
UserData data = TestDataProvider.readJson("testdata/users.json", UserData.class);
List<User> validUsers = data.getValidUsers();
```

---

## 🔧 Framework Components

### BasePage
Enhanced base class with:
- `waitForVisible(By)` - Wait for element visibility
- `waitForClickable(By)` - Wait for element to be clickable
- `waitForInvisible(By)` - Wait for element to disappear
- `click(By)` - Click with retry logic
- `type(By, String)` - Type with automatic clear and retry
- `getText(By)` - Get text with retry logic
- `isVisible(By)` - Check visibility without throwing exception

### DriverManager
ThreadLocal WebDriver management for parallel execution safety.

### WebDriverFactory
Supports multiple browsers:
- Chrome (default)
- Firefox
- Edge

Configurable via `-Dbrowser=<browser>` or `BROWSER` env var.

### TestListener
Enhanced TestNG listener with:
- Allure integration
- Screenshot capture on failure
- Detailed logging for each test phase
- Test suite summary statistics

### RetryAnalyzer
Automatic retry for flaky tests:
- Configurable retry count (default: 2)
- Logs retry attempts
- Set via `-Dtest.retry.count=<number>`

### TestDataProvider
Utility for data-driven testing:
- `readJson(path, class)` - Read JSON files
- `readCsv(path)` - Read CSV files
- `toDataProviderArray(list)` - Convert to TestNG format

---

## 🔐 Security Best Practices

- ✅ Never commit `.env.config` (gitignored by default)
- ✅ Use environment variables or CI secret stores for credentials
- ✅ Keep `.env.example` as a template only
- ✅ Use Jenkins credentials or vault (HashiCorp, Azure Key Vault) for CI/CD

---

## 🚦 CI/CD Integration

### Jenkins Pipeline

The framework includes a `Jenkinsfile` with:
- Parameterized builds (ENV, BROWSER, RUN_TYPE)
- Allure report generation
- Screenshot archiving
- Test result publishing

**Running in Jenkins:**
```groovy
pipeline {
    parameters {
        choice(name: 'ENV', choices: ['dev', 'qa', 'prod'])
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'])
        choice(name: 'RUN_TYPE', choices: ['all', 'smoke', 'regression'])
    }
    
    stages {
        stage('Test') {
            steps {
                sh "./gradlew clean test -Denv=${ENV} -Dbrowser=${BROWSER}"
            }
        }
    }
}
```

---

## 📚 Dependencies

### Core
- Selenium WebDriver 4.35.0
- TestNG 7.11.0
- WebDriverManager 5.9.2

### API Testing
- REST Assured 5.5.6

### Reporting
- Allure TestNG 2.25.0
- AShot 1.5.4 (screenshots)

### Logging
- SLF4J 2.0.16
- Log4j2 2.24.1

### Test Data
- Jackson Databind 2.18.0 (JSON)
- OpenCSV 5.9 (CSV)

### Unit Testing
- JUnit Jupiter 5.10.1

---

## 🎯 Best Practices Implemented

1. **Page Object Model** - Separation of test logic and page interactions
2. **ThreadLocal WebDriver** - Safe parallel execution
3. **Explicit Waits** - Reliable element interactions
4. **Retry Logic** - Handle stale elements automatically
5. **Data-Driven Testing** - Externalized test data
6. **Allure Reporting** - Rich, visual test reports
7. **Configuration Hierarchy** - Flexible environment management
8. **Structured Logging** - Detailed execution logs
9. **Screenshot on Failure** - Visual debugging aid
10. **CI/CD Ready** - Jenkins integration included

---

## 📖 Additional Documentation

- [Framework Enhancements Walkthrough](file:///C:/Users/palla/.gemini/antigravity/brain/fad167c6-bcca-4290-883a-808cd72d390f/walkthrough.md)
- [Test Case Fixes Summary](file:///C:/Users/palla/.gemini/antigravity/brain/fad167c6-bcca-4290-883a-808cd72d390f/test_fixes_summary.md)

---

## 🤝 Contributing

1. Follow the existing code structure
2. Use BasePage methods for all page interactions
3. Add test data to `testdata/` directory
4. Include Allure annotations for better reporting
5. Write descriptive test names and assertions

---

## 📄 License

MIT License - See LICENSE file for details

---

## 🆘 Troubleshooting

### Tests fail with "Element not found"
- Increase timeout: `-DTIMEOUT=15`
- Check locators in page objects
- Verify application is accessible

### WebDriver issues
- Run: `./gradlew clean build --refresh-dependencies`
- Check browser version compatibility
- Ensure WebDriverManager can download drivers

### Allure report not generating
- Install Allure CLI: `npm install -g allure-commandline`
- Or use Gradle plugin: `./gradlew allure:serve`

### Dependencies not downloading
- Check internet connection
- Clear Gradle cache: `./gradlew clean --refresh-dependencies`
- Check `build.gradle` for correct dependency versions

---

**Version**: 1.0.0  
**Last Updated**: 2025-11-28  
**Maintained By**: Helix Automation Team
