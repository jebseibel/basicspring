### Project Development Guidelines (basicspring)

#### Build and Configuration
- Java toolchain: Java 21 (configured via Gradle toolchains).
- Build system: Gradle Wrapper (Spring Boot 3.5.7).
- Primary commands:
  - Build (without running tests):
    ```bash
    ./gradlew clean build -x test
    ```
  - Run the application in dev:
    ```bash
    ./gradlew bootRun
    ```
- Environment variables and .env:
  - The project uses `spring-dotenv` so a `.env` file in project root is read automatically in dev.
  - Example `.env` (see `developer-documents/setup-env-and-database.md` for full context):
    ```env
    DB_USERNAME=basicspring_username
    DB_PASSWORD=your_password
    DB_NAME=basicspring
    ```
  - `src/main/resources/application.yml` uses `${DB_*}` placeholders.
- Database & migrations:
  - DB: MySQL (driver `com.mysql.cj.jdbc.Driver`).
  - Migrations: Liquibase (`src/main/resources/db/changelog/db.changelog-master.yaml`).
  - On app start, Liquibase will apply changelogs. For clean local DB, create the database and user as documented in `developer-documents/setup-env-and-database.md`.

#### Testing
- Framework: JUnit 5, Spring Boot Test.
- Gradle config forces JUnit Platform: see `build.gradle` (`tasks.named('test') { useJUnitPlatform() }`).
- Test profiles and config:
  - Spring profile `test-database` is used by DB-related tests via `@ActiveProfiles("test-database")`.
  - Profile-specific properties: `src/test/resources/application-test-database.yml`.
    - Points to `jdbc:mysql://localhost:3306/basicspring`.
    - Liquibase is enabled for tests in this profile.
    - Datasource credentials are read from environment variables with defaults:
      - `BASICSPRING_USERNAME` (default `default_user`)
      - `BASICSPRING_PASSWORD` (default `default_pass`)

- Running tests (selectively vs full suite):
  - Run the entire test suite (requires the project to compile; DB tests will need a running MySQL):
    ```bash
    ./gradlew test
    ```
  - Run a single test class (useful to avoid DB requirements):
    ```bash
    ./gradlew test --tests 'com.seibel.basicspring.sample.SamplePureUnitTest'
    ```
    This is a pure unit test that does not start Spring or require a database.
  - Run tests matching a pattern:
    ```bash
    ./gradlew test --tests '*Sample*'
    ```
  - Run a specific DB integration test (requires MySQL and credentials):
    ```bash
    export BASICSPRING_USERNAME=basicspring_username
    export BASICSPRING_PASSWORD=your_password
    # Ensure MySQL has database `basicspring` created and accessible
    ./gradlew test --tests 'com.seibel.basicspring.database.connection.DbConnectionTest' -Dspring.profiles.active=test-database
    ```

- Verifying example tests:
  - The command below was executed and passed on this codebase during guideline preparation:
    ```bash
    ./gradlew test --tests 'com.seibel.basicspring.sample.SamplePureUnitTest'
    ```

- Adding new tests:
  - Unit tests: place under `src/test/java/...` and avoid Spring annotations if you do not need the context. Keep them pure (no network/DB), fast, and deterministic.
  - Spring tests: use `@SpringBootTest` and optionally `@ActiveProfiles("test-database")` when hitting the database. Prefer repository/service-level tests that rely on Liquibase-managed schema. Keep external requirements explicit (document env vars, required running services).
  - Tips:
    - If a test requires DB, assert that a connection can be obtained early, and fail fast with a clear message if not.
    - When troubleshooting test configs, temporarily raise logging: set `logging.level.org.hibernate.SQL=DEBUG` in the relevant `application-*.yml`.

#### Development/Debugging Notes
- Exceptions:
  - Domain/service layer wraps persistence exceptions in `ServiceException`; database layer throws `DatabaseException` (`com.seibel.basicspring.database.database.exception`). Ensure imports reference the `exception` package (not `exceptions`).
- Lombok:
  - The project uses Lombok. Annotation processing must be enabled in your IDE. Gradle config includes Lombok for compileOnly and annotationProcessor.
- Mapping:
  - Uses ModelMapper (`org.modelmapper:modelmapper`). See mappers in `.../database/db/mapper/`.
- Entities and status:
  - Entities typically extend base types and include `ActiveEnum` for logical delete/activation. Deletions set `deletedAt` and mark inactive, not hard-delete.
- Liquibase changelogs:
  - Seed SQL files live in `src/main/resources/db/changelog/changes/sql/` and are referenced by YAML changesets.
- Bootstrapping hints:
  - If you need to run the app or DB tests quickly without editing OS env, use a `.env` file with `spring-dotenv` (dev runtime), or export `BASICSPRING_USERNAME/PASSWORD` for test profile.
  - If you need to build while DB is down or MySQL isn’t available, skip tests: `./gradlew build -x test`.

#### Quick MySQL Setup Recap
- Create DB and user (see also `developer-documents/setup-env-and-database.md`):
  ```sql
  CREATE DATABASE basicspring;
  CREATE USER 'basicspring_username'@'localhost' IDENTIFIED BY 'your_password';
  GRANT ALL PRIVILEGES ON basicspring.* TO 'basicspring_username'@'localhost';
  FLUSH PRIVILEGES;
  ```
- Then export test credentials:
  ```bash
  export BASICSPRING_USERNAME=basicspring_username
  export BASICSPRING_PASSWORD=your_password
  ```

#### Known Integration Test Pre-requisites
- DB-related tests under `src/test/java/com/seibel/basicspring/database/connection/` require:
  - A running local MySQL on `localhost:3306`.
  - Database `basicspring` exists and is reachable.
  - Environment variables `BASICSPRING_USERNAME` and `BASICSPRING_PASSWORD` set to a valid user.
  - Run with profile `test-database` (tests already annotate with `@ActiveProfiles("test-database")`).

#### Notes on Running the App vs Tests
- App runtime pulls credentials from `.env` via `DB_USERNAME/DB_PASSWORD/DB_NAME` placeholders in `application.yml`.
- Test runtime for DB tests uses `BASICSPRING_USERNAME/BASICSPRING_PASSWORD` from the environment and `application-test-database.yml`.
- Liquibase is enabled in tests; first run will create schema and apply seed data.
