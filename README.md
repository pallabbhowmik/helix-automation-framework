# Helix Automation Framework

Helix is a small, practical automation framework for UI and API testing. It is implemented to demonstrate engineering-grade automation patterns while exercising the public PassTheNote sandbox (https://www.passthenote.com).

This repository contains:
- A Page Object Model (POM) layer for UI interactions
- API clients and request/response models (RestAssured)
- Test suites and example end-to-end flows that combine API and UI
- A configuration loader that supports layered overrides (-D system props, environment variables, .env.config, and classpath properties)

Target application
------------------
All tests target the public PassTheNote sandbox at:

https://www.passthenote.com

Important: do not include sub-paths (for example, `/app`) in the base URL configuration — use the exact host above.

Repository layout
-----------------
src/main/java/com/helix/automation/framework
- api/        - RestAssured specifications, clients and DTOs
- config/     - ConfigManager: layered configuration and helpers
- core/       - Driver lifecycle, BasePage, BaseTest, listeners, and utilities
- pages/      - Page objects and UI interaction helpers
- flows/      - Reusable business flows (compose actions across multiple pages)

src/test/java/com/helix/automation/tests
- ui/         - Selenium UI test suites
- api/        - RestAssured API tests
- integration - Tests combining API seeding + UI verification

Configuration
-------------
Configuration is loaded with the following precedence (highest → lowest):
1. System properties (eg. -Dweb.baseUrl)
2. Environment variables
3. `.env.config` at the repository root (not committed in normal workflows; use `.env.example` as a template)
4. Classpath properties (config-<env>.properties)

The default values used by the repository are safe for the public sandbox. Example `.env.config`:

BASE_URL=https://www.passthenote.com
API_BASE_URL=https://www.passthenote.com/api/v1
USERNAME=admin@passthenote.com
PASSWORD=Admin@123

Running tests
-------------
Build project without running tests:
```powershell
./gradlew clean build -x test
```

Run the full test-suite (defaults to env=dev):
```powershell
./gradlew clean test -Denv=dev -Dbrowser=chrome
```

Run only groups (example: api or ui):
```powershell
./gradlew clean test -Denv=dev -Dgroups=api
./gradlew clean test -Denv=dev -Dgroups=ui
```

Design notes & recommended practices
-----------------------------------
- Use stable `data-test` test IDs when available. POMs prefer id/name/css/xpath in fallback order.
- Keep tests deterministic: create and clean up data per test (where possible) to avoid reliance on shared state.
- Chain API calls with UI verifications for reliable end-to-end coverage (seed data via API → assert via UI).
- Avoid committing secrets — put credentials into a local `.env.config` or CI secret store.

Examples
--------
UI example:
```java
LoginPage login = new LoginPage();
login.open();
login.enterEmail(USERNAME);
login.enterPassword(PASSWORD);
login.clickSignIn();
assertTrue(login.isDashboardDisplayed());
```

API example (RestAssured):
```java
AuthRequest req = new AuthRequest(USERNAME, PASSWORD);
AuthResponse res = ApiSpecs.base()
    .body(req)
    .post("/auth/login")
    .then()
    .statusCode(200)
    .extract()
    .as(AuthResponse.class);
assertNotNull(res.getToken());
```

Where we are now
-----------------
- The project demonstrates a POM-based UI test layer, API clients, and a small set of integration tests that show API→UI chaining.
- Tests default to the public PassTheNote sandbox so reviewers can run the suite without running local servers.

Roadmap (short)
----------------
- Strengthen coverage: add E2E commerce flow and extended notes flows
- Improve reporting (Allure) and CI examples (GitHub Actions)
- Add idempotent data cleanup steps and retry tuning for flakiness reduction

Contributing
------------
Contributions are welcome. Please open issues and PRs, follow the code style in `src/main/java`, and keep credentials out of commits.

License
-------
MIT — see LICENSE for details.

Maintainer
----------
Pallab Bhowmik
Senior SDET
Helix Automation Framework

Helix is a modular automation framework for UI and API testing, implemented to test the public PassTheNote sandbox (https://www.passthenote.com). It demonstrates best practices for engineering-grade automation: clear separation of concerns, maintainable page objects, API clients, and cross-layer integration tests.

App under test: https://www.passthenote.com
Repository: https://github.com/pallabbhowmik/helix-automation-framework

🧱 Architecture Overview

Project structure

The project follows a layered design so tests remain concise and maintainable:

src/main/java/com/helix/automation/framework
 ├── api/        # RestAssured request models, response DTOs, base specifications
 ├── config/     # Env + property resolution (.env, sys props, config files)
 ├── core/       # Driver lifecycle, BaseTest, retry & screenshot listeners
 ├── flows/      # High-level reusable business flows (Login, Notes, Checkout)
 ├── pages/      # Page Object Model (UI locators + interactions)
 └── utils/      # Helpers (waits, test data, random generation)

src/test/java/com/helix/automation/tests
 ├── ui/         # Selenium UI test suites
 ├── api/        # RestAssured API test suites
 └── integration/# Tests that combine API seeding + UI verification


This structure ensures:

Tests remain thin and readable

UI/API logic is reusable and centralized

Future modules can be added without breaking existing tests

Configuration

Configuration is loaded using this precedence (highest → lowest):
1. System properties (-D...)
2. Environment variables
3. .env.config in the repository root (developer overrides — NOT committed)
4. Classpath properties (config-<env>.properties)

Sample .env.config (copy from .env.example and update locally):

BASE_URL=https://www.passthenote.com
API_BASE_URL=https://www.passthenote.com/api/v1
USERNAME=<your email>
PASSWORD=<your password>

Note: Use the exact base URL (https://www.passthenote.com). Do not include path segments like /app in BASE_URL.

Credentials must never be committed to source control.

Running tests

Build (skip tests):
```powershell
./gradlew clean build -x test
```

Run all tests (default env=dev):
```powershell
./gradlew clean test -Denv=dev -Dbrowser=chrome
```

Run a subset of tests by groups (example: api or ui):
```powershell
./gradlew clean test -Denv=dev -Dgroups=api
./gradlew clean test -Denv=dev -Dgroups=ui
```

Sample patterns

UI example (page object):
```java
LoginPage login = new LoginPage();
login.open();
login.enterEmail(USERNAME);
login.enterPassword(PASSWORD);
login.clickSignIn();
assertTrue(login.isDashboardDisplayed());
```

API example (RestAssured):
```java
AuthRequest req = new AuthRequest(USERNAME, PASSWORD);
AuthResponse res = ApiSpecs.base()
    .body(req)
    .post("/auth/login")
    .then()
    .statusCode(200)
    .extract()
    .as(AuthResponse.class);
assertNotNull(res.getToken());
```

Integration pattern:
Create test data via the API, then verify it in the UI (e.g., create a note via API and assert it appears under /notes/mine).

CI / CI integration

The repository includes a sample Jenkinsfile to demonstrate a CI pipeline. The test suite supports parameterized runs (env, browser, test groups) and headless execution. Integrations for GitHub Actions and Allure reporting are planned.

Roadmap
- Hybrid UI + API automation baseline (complete)
- Parallel execution with ThreadLocal WebDriver instances (in progress)
- Allure reporting and video capture (planned)
- E2E integration flows that combine API seeding and UI verification (planned)
- Mobile automation (future enhancement)
📄 License

Distributed under the MIT License. See LICENSE for details.

Maintainer

Pallab Bhowmik
Senior SDET | QA Engineering | AI-Assisted Dev Testing

If this project helps you learn automation, please fork, contribute, and share improvements.