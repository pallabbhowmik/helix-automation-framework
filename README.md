Helix Automation Framework

Helix is a modular UI + API automation framework built to test and explore automation workflows against PassTheNote — an AI-assisted engineering playground featuring authentication, note-taking, and e-commerce workflows.

🔗 App Under Test: https://www.passthenote.com

🔗 Repo: https://github.com/pallabbhowmik/helix-automation-framework

Helix is designed to demonstrate:

Modern automation architecture

AI-assisted development validation

Real-world SDET problem solving

End-to-end testing across UI, API, and integration layers

🧱 Architecture Overview

Helix follows a clean layered design:

src/main/java/com/helix/automation/framework
 ├── api/        # RestAssured request models, response DTOs, base specifications
 ├── config/     # Env + property resolution (.env, sys props, config files)
 ├── core/       # Driver lifecycle, BaseTest, retry & screenshot listeners
 ├── flows/      # High-level reusable business flows (Login, Notes, Checkout)
 ├── pages/      # Page Object Model (UI locators + interactions)
 └── utils/      # Helpers (waits, test data, random generation)

src/test/java/com/helix/automation/tests
 ├── ui/         # Selenium UI test suites
 ├── api/        # API-only validation tests
 └── integration/# API-seeded data validated via UI


This structure ensures:

Tests remain thin and readable

UI/API logic is reusable and centralized

Future modules can be added without breaking existing tests

⚙️ Configuration Strategy

Helix resolves configuration in the following priority:

System Properties (-Denv=dev -Dbrowser=chrome)

Environment Variables

.env.config (developer overrides — not committed)

Classpath Properties (config-<env>.properties)

Create a local .env.config based on the sample:

BASE_URL=https://www.passthenote.com
API_BASE_URL=https://www.passthenote.com/api
USERNAME=<your email>
PASSWORD=<your password>


Credentials are never committed into the repo.

▶️ Running Tests
Build without tests:
./gradlew clean build -x test

Run full regression:
./gradlew clean test -Denv=dev -Dbrowser=chrome

Run only API tests:
./gradlew clean test -Denv=dev -Dgroups=api

Run only UI tests:
./gradlew clean test -Denv=dev -Dgroups=ui

🧪 Sample Test Patterns
UI Example
LoginPage login = new LoginPage();
login.open();
login.loginValidUser();

assertTrue(login.isDashboardDisplayed(), "User should land on dashboard after login");

API Example
AuthRequest request = new AuthRequest(USERNAME, PASSWORD);

AuthResponse response = ApiSpecs.base()
    .body(request)
    .post("/auth/login")
    .then()
    .statusCode(200)
    .extract()
    .as(AuthResponse.class);

assertNotNull(response.getToken());

🧬 Integration Example (Planned)

Create data via API → verify via UI
(Used for notes, orders, and shared collaboration tests)

🛠 CI/CD Compatibility

A template Jenkinsfile is included to support:

Parameterized execution (env, browser, test groups)

Headless browser execution

Artifact capture (screenshots + reports)

Support for GitHub Actions + Allure Reports is on the roadmap.

📍 Roadmap
Status	Feature
✔	Hybrid UI + API automation baseline
🔄	Parallel execution w/ ThreadLocal driver
🔄	Allure reporting & video capture
🔄	Integration flows (API-seeded data + UI verification)
⏳	Mobile automation (optional future module)
📄 License

Distributed under the MIT License. See LICENSE for details.

👤 Maintainer

Pallab Bhowmik
Senior SDET | QA Engineering | AI-Assisted Dev Testing

⭐ If this project helps you learn or test automation, feel free to fork, improve, and contribute.