# Helix Automation Framework

[![CI status](https://github.com/pallabbhowmik/helix-automation-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/pallabbhowmik/helix-automation-framework/actions)

Helix is a small, practical automation framework for UI and API testing. It demonstrates engineering-grade automation patterns while exercising the public PassTheNote sandbox (https://www.passthenote.com).

Table of contents
-----------------
- [Overview](#helix-automation-framework)
- [Architecture overview](#architecture-overview)
- [Configuration](#configuration)
- [Getting started & running tests](#getting-started--running-tests)
- [Examples](#examples)
- [Roadmap & contributing](#roadmap--contributing)
- [License & maintainer](#license--maintainer)

Overview
--------
Target application: https://www.passthenote.com

Helix focuses on demonstrating a dependable test automation stack using POM (UI), RestAssured (API), and small integration flows that seed data via API then verify in the UI.

Architecture overview
---------------------
The framework is intentionally small and layered so tests remain readable and maintainable. Main modules:

- api — RestAssured request specs, API clients and request/response models (DTOs)
- config — layered configuration loader (system props → env vars → .env.config → classpath)
- core — WebDriver lifecycle, BasePage/BaseTest, listeners and utilities
- pages — Page Object Model classes and stable locators
- flows — reusable business flows that combine pages (e.g. login, checkout)

This structure keeps UI and API logic isolated and reusable, and makes it straightforward to add new E2E flows.

Configuration
-------------
Configuration is resolved in this order (highest → lowest):
1. System properties (eg. `-Dweb.baseUrl`)
2. Environment variables
3. `.env.config` at repository root (local overrides)
4. Classpath properties (`config-<env>.properties`)

Sample `.env.config.example`
----------------------------
Copy this file to `.env.config` locally and update values for your environment (do not commit credentials):

```
# Web UI base URL (host only — no path)
BASE_URL=https://www.passthenote.com

# API gateway base URL used by API tests
API_BASE_URL=https://www.passthenote.com/api/v1

# Credentials for demo/test accounts (use secrets in CI)
USERNAME=admin@passthenote.com
PASSWORD=Admin@123

# Test runner preferences
BROWSER=chrome
RUN_TYPE=FULL
```

Getting started & running tests
------------------------------
Build the project (skip tests):

```powershell
./gradlew clean build -x test
```

Run the full test suite (defaults to `env=dev`):

```powershell
./gradlew clean test -Denv=dev -Dbrowser=chrome
```

Run tests by groups (example: `api`, `ui`):

```powershell
./gradlew clean test -Denv=dev -Dgroups=api
./gradlew clean test -Denv=dev -Dgroups=ui
```

Examples
--------
UI (Page Object Model):

```java
LoginPage login = new LoginPage();
login.open();
login.enterEmail(USERNAME);
login.enterPassword(PASSWORD);
login.clickSignIn();
assertTrue(login.isDashboardDisplayed());
```

API (RestAssured):

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

Long-running / cross-layer patterns
----------------------------------
- Prefer API → UI chaining for reliable E2E flows: seed data using API and verify via UI.
- Keep tests self-contained and clean up resources where possible to avoid shared-state flakiness.

Screenshot
----------
Below is a sample visual showing a successful UI flow or app screenshot. (placeholder image included in `docs/images`)

![passTheNote-sample](docs/images/test-run.svg)

Roadmap & contributing
----------------------
Planned next steps (concise, non-duplicated):

1. Add more E2E flows to cover commerce, notes collaboration and role-based admin flows.
2. Improve report integrations (Allure) and provide CI examples (GitHub Actions) with status badges.
3. Add idempotent cleanup helpers, retry tuning and parallel execution improvements to stabilize runs.

Contributing guidelines
-----------------------
- Open issues or pull requests; keep PRs small and focused.
- Follow the repository code style and existing patterns (POM, flows, config).
- Never commit secrets — use `.env.config` or CI secrets.

License & maintainer
--------------------
MIT — see LICENSE for details.

Maintainer: Pallab Bhowmik — Senior SDET