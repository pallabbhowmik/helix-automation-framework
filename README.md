# Helix Automation Framework

Overview
- Java + Selenium WebDriver + TestNG + Gradle
- API tests with RestAssured
- Planned CI integration (Jenkins + Allure) — pipeline definition to be added

Target application
- PassTheNote (https://www.passthenote.com) — a full-stack web app used as a testbed for this framework.

Configuration
- The framework loads configuration with this precedence (highest → lowest):
  1. System properties (-Dkey=value) e.g. `-Denv=qa -Dbrowser=chrome`
  2. Environment variables (e.g. ENV, BASE_URL, API_BASE_URL, USERNAME, PASSWORD)
  3. `.env.config` file in the project root (key=value lines)
  4. classpath properties file: `config-<env>.properties` inside resources (fallback values)

Files
- `.env.example` — example env file with placeholders. Copy to `.env.config` and edit for local runs.
- `.gitignore` — ignores local env files and build artifacts by default.
- `Jenkinsfile` — CI job uses `ENV`, `RUN_TYPE` and `BROWSER` parameters and passes them to Gradle as `-D` properties.

How to run
- Quick local compile (skip tests):

```powershell
./gradlew clean build -x test
```

- Run all tests (may start browsers, ensure drivers are configured):

```powershell
./gradlew clean test -Denv=dev -Dbrowser=chrome
```

- Run API-only tests:

```powershell
./gradlew clean test -Denv=dev -Dgroups=api
```

Small, real tests included

The repository includes a small but real test set to prove the framework is usable immediately:
- UI tests: src/test/java/com/helix/automation/tests/ui — several small Selenium-based UI tests
- API tests: src/test/java/com/helix/automation/tests/api — RestAssured tests covering auth endpoints

Example test snippets

UI test (Selenium / TestNG):
```java
// example: open login page and assert welcome header
LoginPage login = new LoginPage();
login.open();
assertTrue(login.isWelcomeBackVisible());
```

API test (RestAssured):
```java
AuthRequest req = new AuthRequest("tester@passthenote.com", "Tester@123");
AuthResponse res = ApiSpecs.base()
  .body(req)
  .when().post("/auth/login")
  .then().statusCode(200)
  .extract().as(AuthResponse.class);
assertNotNull(res.getToken());
```

Recommendations & Next steps
- Keep secrets out of the repo; use `.env.local` or CI secret stores.
- Add credential management (vault/secret store) for real CI.
- Add a small config unit test to validate `ConfigManager` behavior on CI.
 - Keep secrets out of the repo; use `.env.local` or CI secret stores.
 - Use Jenkins credentials or a vault (e.g., HashiCorp Vault, Azure Key Vault) to inject secrets at runtime — avoid storing passwords in files.
 - The project now supports automatic test retries (2 retries) and a TestNG listener that captures screenshots to build/screenshots on failure (test scope).
 - Add a small config unit test to validate `ConfigManager` behavior on CI (already added).
 
Project layout changes made in this session
- main sources now follow the canonical structure under `src/main/java/com/helix/automation/framework`
  - config, core, pages, api, flows, utils are present
- UI tests moved into `src/test/java/com/helix/automation/tests/ui`
- API tests remain under `src/test/java/com/helix/automation/tests/api`
- Integration tests go into `src/test/java/com/helix/automation/tests/integration` (placeholder added)
- Legacy page objects and old tests were moved to `src/legacy_pages` and `src/test/legacy_tests` respectively so the refactor is reversible.

If you want me to remove legacy files permanently, I can delete the `src/legacy_pages` and `src/test/legacy_tests` folders after manual verification.
