# High-Level Requirements Document

## Project Goal
Generate complete Spring Boot CRUD boilerplate code from a simple Java Domain object definition.

I am writing lots and lots of the same kind of code in my Java, Spring, Gradle projects. I repeat this pattern over and over. I have request objects/response objects, Domain objects in a common area and Entity objects in the database area. I have IntelliJ Ultimate installed with gradle.

## Generation Scope
- **Input**: Single Java Domain object (simple POJO extending BaseDomain)
- **Output**: Complete layered architecture (10 files + 3 test files per entity + builder methods added to shared class)
- **Mode**: One entity at a time

## Generated Artifacts (per entity)

### 1. **Domain Layer** (`com.seibel.basicspring.common.domain`)
- `{Entity}.java` - Business domain object
    - Extends `BaseDomain` (inherits: id, extid, createdAt, updatedAt, deletedAt, active)
    - Uses `@Data`, `@SuperBuilder`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@EqualsAndHashCode(callSuper = true)`
    - Contains only business-specific fields
    - **Input source for generation**

### 2. **Web Layer - Request DTOs** (`com.seibel.basicspring.web.request`)
- `Request{Entity}Create.java` - Create operation DTO
    - Extends `BaseRequest`
    - Uses `@Data`, `@EqualsAndHashCode(callSuper = true)`
    - All fields required with `@NotEmpty` and `@Size` validations
    - Validation messages follow pattern: "The {field} is required."

- `Request{Entity}Update.java` - Update operation DTO
    - Extends `BaseRequest`
    - Uses `@Data`, `@EqualsAndHashCode(callSuper = true)`
    - All fields optional (nullable)
    - Only `@Size` validations (no `@NotEmpty`)

### 3. **Web Layer - Response DTO** (`com.seibel.basicspring.web.response`)
- `Response{Entity}.java` - Outgoing data
    - Uses `@Data`, `@Builder`
    - Contains `extid` plus all business fields
    - No validation annotations

### 4. **Entity Layer** (`com.seibel.basicspring.database.database.db.entity`)
- `{Entity}Db.java` - JPA entity
    - Extends `BaseDb` (inherits: id, extid, createdAt, updatedAt, deletedAt, active with JPA annotations)
    - Uses `@Data`, `@EqualsAndHashCode(callSuper = true)`, `@Entity`, `@Table(name = "{entity_lowercase}")`
    - Has `serialVersionUID` constant
    - All business fields have `@Column` annotations with:
        - `name` in snake_case
        - `length` constraint
        - `nullable` flag
        - `unique` flag (where applicable)

### 5. **Mapper** (`com.seibel.basicspring.database.database.db.mapper`)
- `{Entity}Mapper.java` - Object conversions
    - Uses `@Component`, `@NoArgsConstructor`
    - Contains private `ModelMapper` instance
    - Methods: `toModel()`, `toDb()`, `toModelList()`, `toDbList()`
    - Handles null checks in list methods

### 6. **Repository** (`com.seibel.basicspring.database.database.db.repository`)
- `{Entity}Repository.java` - Data access interface
    - Uses `@Repository`
    - Extends `ListCrudRepository<{Entity}Db, Long>`
    - Standard methods:
        - `Optional<{Entity}Db> findByExtid(String extid)`
        - `List<{Entity}Db> findByActive(ActiveEnum active)`
        - `boolean existsByExtid(String extid)`
        - Additional `findBy{UniqueField}()` methods for unique business fields

### 7. **Database Service** (`com.seibel.basicspring.database.database.db.service`)
- `{Entity}DbService.java` - Database operations layer
    - Uses `@Slf4j`, `@Service`
    - Extends `BaseDbService` (passes entity name "{Entity}Db" to constructor)
    - Constructor-injected repository and mapper
    - **Create method:**
        - Takes business fields as `@NonNull` parameters
        - Generates UUID for extid
        - Sets timestamps (createdAt, updatedAt)
        - Sets `ActiveEnum.ACTIVE`
        - Returns Domain object
    - **Update method:**
        - Takes extid + business fields as parameters
        - Fetches existing record (throws if not found)
        - Updates provided fields
        - Updates `updatedAt` timestamp
        - Returns Domain object
    - **Delete method:**
        - Soft delete: sets `deletedAt` and `ActiveEnum.INACTIVE`
        - Returns boolean
    - **Find methods:**
        - `findByExtid()` - single record
        - `findAll()` - all records
        - `findByActive()` - filtered by active status
    - All methods use `handleException()` for error handling
    - Uses `BaseDbService` logging message helpers

### 8. **Business Service** (`com.seibel.basicspring.service`)
- `{Entity}Service.java` - Business logic layer
    - Uses `@Slf4j`, `@Service`
    - Extends `BaseService` (sets `thisName` to "{Entity}" in constructor)
    - Constructor-injected DbService
    - **Pattern for all methods:**
        - Validate inputs using `requireNonNull()` / `requireNonBlank()`
        - Log operation with `log.info()`
        - Delegate to DbService
        - Accept Domain objects, extract fields for DbService calls
    - **Methods:**
        - `create(Company item)` - validates item, extracts fields
        - `update(String extid, Company item)` - validates extid and item
        - `delete(String extid)` - validates extid
        - `findByExtid(String extid)` - validates extid
        - `findAll()` - no validation
        - `findByActive(ActiveEnum activeEnum)` - validates enum
    - Propagates database exceptions

### 9. **Web Service** (`com.seibel.basicspring.web.service`)
- `{Entity}WebService.java` - Web-specific business logic wrapper
    - Uses `@Slf4j`, `@Service`
    - Constructor-injected business service and mapper
    - Wraps all business service methods with:
        - Logging (info for calls, error for exceptions)
        - Try-catch blocks that return null/false on errors
    - Returns Domain objects (not DTOs)
    - Acts as exception boundary between web and service layers

### 10. **Controller** (`com.seibel.basicspring.web.controller`)
- `{Entity}Controller.java` - REST API endpoints
    - Uses `@RestController`, `@RequestMapping("/api/{entity_lowercase}")`, `@Validated`
    - Constructor-injected web service
    - **Endpoints:**
        - `GET /` - getAll() → List<Response{Entity}>
        - `GET /{extid}` - getByExtid() → Response{Entity}
        - `POST /` - create(@Valid @RequestBody Request{Entity}Create) → Response{Entity}
        - `PUT /{extid}` - update(@PathVariable, @Valid @RequestBody Request{Entity}Update) → Response{Entity}
        - `DELETE /{extid}` - delete(@PathVariable) → ResponseEntity<Void>
    - **Manual DTO mapping:**
        - Private `toResponse(Domain)` method for single object
        - Private `toResponse(List<Domain>)` method for list
        - Manual builder pattern in create/update endpoints
    - **Validation:**
        - `validateUpdateRequest()` ensures at least one field provided
    - **Error handling:**
        - Throws `ResponseStatusException` for NOT_FOUND scenarios
        - Returns proper HTTP status codes

## Test Files (per entity)

### 11. **Mapper Tests** (`test/.../database.database.db.mapper`)
- `{Entity}MapperTest.java` - Unit tests for mapper conversions
    - Uses JUnit 5 (`@Test`, `@BeforeEach`)
    - Tests all four methods: `toModel()`, `toDb()`, `toModelList()`, `toDbList()`
    - Verifies field mapping accuracy
    - Tests null handling in list methods
    - Uses assertions to verify:
        - All fields copied correctly
        - BaseDomain/BaseDb fields mapped
        - Business fields mapped
        - List conversions work correctly
    - **Uses DomainBuilderDatabase helper to create test objects**

### 12. **Repository Tests** (`test/.../database.database.db.repository`)
- `{Entity}RepositoryTest.java` - Integration tests for repository
    - Uses `@DataJpaTest` for JPA testing
    - Uses `@AutoConfigureTestDatabase(replace = Replace.NONE)` for real database testing
    - Uses `@Autowired` for repository injection
    - Tests all custom query methods:
        - `findByExtid()` - positive and negative cases
        - `findByActive()` - ACTIVE and INACTIVE filtering
        - `existsByExtid()` - exists and not exists cases
        - Additional `findBy{UniqueField}()` methods
    - Tests inherited CRUD methods from ListCrudRepository
    - Includes setup/teardown with test data
    - Verifies query correctness and results
    - **Uses DomainBuilderDatabase helper to create test objects**

### 13. **Database Service Tests** (`test/.../database.database.db.service`)
- `{Entity}DbServiceTest.java` - Unit tests for database service
    - Uses JUnit 5 with Mockito (`@ExtendWith(MockitoExtension.class)`)
    - Uses `@Mock` for repository and mapper dependencies
    - Uses `@InjectMocks` for service under test
    - Tests all CRUD operations:
        - **Create:** UUID generation, timestamp setting, ActiveEnum.ACTIVE
        - **Update:** Field updates, updatedAt timestamp, exception handling
        - **Delete:** Soft delete (deletedAt, INACTIVE), no physical delete
        - **FindByExtid:** Success and not found scenarios
        - **FindAll:** List conversion
        - **FindByActive:** Filtering by active status
    - Verifies exception handling:
        - `DatabaseRetrievalFailureException` when record not found
        - `DatabaseFailureException` for other errors
    - Uses `verify()` to ensure repository methods called correctly
    - Uses `assertThrows()` for exception testing
    - Uses `assertEquals()`, `assertNotNull()`, `assertTrue()` for assertions
    - **Uses DomainBuilderDatabase helper to create test objects**

## Test Utility Classes

### DomainBuilderUtils (Base Utility - Shared)
- **Location:** `test/.../testutils/DomainBuilderUtils.java`
- **Purpose:** Provides utility methods for generating random test data strings with specific patterns and size constraints
- **Not generated per entity** - this is a shared utility class

**Constants for field sizes:**
```java
// Standard field sizes
public static final int SIZE_CODE = 8;
public static final int SIZE_NAME = 32;
public static final int SIZE_DESC = 255;
public static final int SIZE_UNIQUE = 64;
public static final int SIZE_LABEL = 32;
public static final int SIZE_VERSION = 16;
public static final int SIZE_STATUS = 32;
public static final int SIZE_RANDOM = 10;

// Base prefixes
public static final String BASE_CODE = "Cod_";
public static final String BASE_NAME = "Nam_";
public static final String BASE_DESC = "Des_";
public static final String BASE_UNIQUE = "Unq_";
public static final String BASE_LABEL = "Lbl_";
public static final String BASE_VERSION = "Ver_";
public static final String BASE_STATUS = "Sta_";

// Minimum suffix lengths (for randomization)
public static final int SUFFIX_MIN_CODE = 4;
public static final int SUFFIX_MIN_NAME = 4;
public static final int SUFFIX_MIN_DESC = 4;
// ... etc for other types
```

**Key Methods (pattern repeated for each field type):**
- `getCodeRandom()` - generates random code with default prefix
- `getCodeRandom(String label)` - generates random code with custom prefix
- `getCodeRandom(String label, String random)` - generates code with custom prefix and suffix
- `getNameRandom()`, `getDescriptionRandom()`, etc. - same pattern for other field types

**Shared utility methods:**
- `buildWithLabel(String label, int maxSize, String random)` - Core builder ensuring max length respected
- `randomString()` / `randomString(int length)` - Generates random alphanumeric strings (uppercase)

**Features:**
- Automatically truncates labels if they exceed max size minus minimum suffix
- Ensures generated strings always fit within database field length constraints
- Adds random suffix (4-8 characters) for uniqueness
- Uses Apache Commons `RandomStringUtils` for generation

### DomainBuilderBase (Abstract Base - Shared)
- **Location:** `test/.../testutils/DomainBuilderBase.java`
- **Purpose:** Abstract base class that extends DomainBuilderUtils and adds BaseDb initialization
- **Not generated per entity** - this is a shared base class

```java
public abstract class DomainBuilderBase extends DomainBuilderUtils {
    protected static void setBaseSyncFields(BaseDb item) {
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setActive(ActiveEnum.ACTIVE);
        item.setDeletedAt(null);
    }
}
```

**Features:**
- Provides consistent initialization for all BaseDb entities
- Sets audit timestamps to current time
- Sets active status to ACTIVE
- Ensures deletedAt is null (for non-deleted test entities)

### 14. **DomainBuilderDatabase** (`test/.../testutils`)
- `DomainBuilderDatabase.java` - Entity-specific test object builder methods
- **Generated with methods for each entity**
- Extends `DomainBuilderBase`

**Per-entity methods pattern (using Company as example):**

```java
// Get Domain object with default values
public static Company getCompany()

// Get Domain object from existing Db entity
public static Company getCompany(CompanyDb item)

// Get Db entity with all defaults
public static CompanyDb getCompanyDb()

// Get Db entity with partial customization
public static CompanyDb getCompanyDb(String code, String name)

// Get Db entity with full customization
public static CompanyDb getCompanyDb(String code, String name, String description, String extid)
```

**Implementation pattern for the full customization method:**
```java
public static CompanyDb getCompanyDb(String code, String name, String description, String extid) {
    CompanyDb item = new CompanyDb();
    item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
    item.setCode(code != null ? code : getCodeRandom("CO_"));
    item.setName(name != null ? name : getNameRandom("Company_"));
    item.setDescription(description != null ? description : getDescriptionRandom("Company Description "));
    setBaseSyncFields(item);
    return item;
}
```

**Features:**
- Multiple overloaded methods per entity for flexibility
- Uses custom prefixes for each entity (e.g., "CO_" for Company code)
- Generates UUID for extid when not provided
- Uses appropriate random generators based on field type
- Calls `setBaseSyncFields()` to initialize BaseDb fields
- Uses mapper to convert Db entities to Domain objects
- All parameters optional via overloading (null = use random default)

**Generation requirements:**
- Generate method set for each entity in the project
- Use entity-specific prefixes (e.g., "CO_" for Company, "PROD_" for Product)
- Match field types to appropriate random generators (code→getCodeRandom, name→getNameRandom, etc.)
- Include overloads for: no params, partial params, all params
- Always include the Domain object conversion methods

## Key Technical Details

### Base Classes
- **BaseDomain** - `@Data`, `@SuperBuilder`, `@NoArgsConstructor`
    - Fields: id (Long), extid (String), createdAt, updatedAt, deletedAt (LocalDateTime), active (ActiveEnum)

- **BaseDb** - `@Data`, `@MappedSuperclass`, implements `Serializable`
    - Same fields as BaseDomain but with full JPA annotations
    - Uses `@Convert(converter = ActiveEnumConverter.class)` for active field

- **BaseRequest** - `@Data`, abstract class (currently empty)

- **BaseService** - Abstract class with validation helpers
    - Methods: `requireNonNull()`, `requireNonBlank()` (with/without field name)
    - Uses `thisName` for error messages

- **BaseDbService** - Abstract class with logging/exception helpers
    - Constructor takes entity name
    - Provides message formatting methods
    - Provides `handleException()` for standardized error handling

### Enums & Converters
- **ActiveEnum** - Integer-backed (ACTIVE=1, INACTIVE=0)
    - Has helper methods: `isActive()`, `isInactive()`
    - Provides `ALLOWED_VALUES` string for validation

- **ActiveEnumConverter** - `@Converter(autoApply = true)`
    - Converts ActiveEnum ↔ Integer for JPA

### Exceptions
All extend `Exception` with simple constructors:
- `DatabaseFailureException` - General database errors (has multiple constructors including cause)
- `DatabaseRetrievalFailureException` - Retrieval failures
- `DatabaseCreateFailureException` - Create failures
- `DatabaseUpdateFailureException` - Update failures
- `DatabaseDeleteFailureException` - Delete failures
- `DatabaseAccessException` - Access errors

### Dependencies
- **ModelMapper** - Used in mapper classes for entity/domain conversions
- **Lombok** - Used throughout for `@Data`, `@Builder`, `@Slf4j`, etc.
- **Spring Data JPA** - `ListCrudRepository` for repositories
- **Jakarta Validation** - `@Valid`, `@NotEmpty`, `@Size` annotations
- **Spring Boot** - Core framework
- **JUnit 5** - Testing framework for unit tests
- **Mockito** - Mocking framework for unit tests
- **Spring Boot Test** - `@DataJpaTest` for repository integration tests
- **Apache Commons Lang3** - `RandomStringUtils` for test data generation

### Naming Conventions
- **Packages:** Standard Java package naming with deep nesting
- **Classes:** {Entity}[Suffix] pattern (e.g., CompanyDb, CompanyService)
- **Database columns:** snake_case (e.g., created_at, deleted_at)
- **Java fields:** camelCase (e.g., createdAt, deletedAt)
- **REST endpoints:** lowercase with hyphens (e.g., /api/company)

### Key Features
- ✅ Fully implemented CRUD operations (no TODOs)
- ✅ Soft deletes (sets deletedAt and INACTIVE status)
- ✅ UUID-based external IDs (extid) for all entities
- ✅ Audit timestamps (createdAt, updatedAt, deletedAt) on all entities
- ✅ Validation annotations on Request DTOs (different for Create vs Update)
- ✅ ModelMapper for entity/domain conversions
- ✅ Manual builder-based mapping in controllers
- ✅ Comprehensive logging at all layers
- ✅ Standardized exception handling with custom exceptions
- ✅ ActiveEnum for soft delete status tracking
- ✅ Lombok annotations throughout for boilerplate reduction
- ✅ Standard REST practices (proper HTTP methods and status codes)
- ✅ Validation helper methods in base classes
- ✅ **Unit and integration tests for Mapper, Repository, and DbService**
- ✅ **Test utility classes for consistent test data generation**
- ⚠️  Simple entities only (no relationships initially)
- ⚠️  Database constraints defined in JPA annotations

## Implementation Approach
- **Technology**: Gradle custom task or buildSrc plugin
- **Generation**: Command/task per entity
- **Input**: Domain object class file
- **Framework**: Spring Boot + Gradle
- **Template Engine**: Consider using FreeMarker or Velocity for code generation
- **Parsing**: Use Java reflection or AST parsing to extract field information from Domain class

## Generation Strategy

### Input Processing
1. Parse the input Domain class file
2. Extract class name (becomes {Entity})
3. Extract business fields (exclude BaseDomain fields)
4. Extract field metadata:
    - Field name
    - Field type
    - Validation requirements (from annotations or inferred from type)
    - JPA constraints (length, nullable, unique)

### Output Generation
For each Domain class input, generate all 13 files plus builder methods:
1. Domain class (already exists - this is input)
2. Request DTOs (Create and Update variants)
3. Response DTO
4. Entity (Db) class
5. Mapper class
6. Repository interface
7. Database Service class
8. Business Service class
9. Web Service class
10. Controller class
11. Mapper Test class
12. Repository Test class
13. Database Service Test class
14. **DomainBuilderDatabase methods** (append to existing shared class)

### Configuration Requirements
- Package base path: `com.seibel.basicspring`
- Entity naming convention
- Field naming conventions (camelCase → snake_case)
- Default field constraints (lengths, nullable, unique)
- REST endpoint path patterns
- Entity-specific prefixes for test data generation

---

## Next Steps
1. ✅ Review sample objects to understand exact patterns - **COMPLETE**
2. Define input format (Domain object structure) - **IN PROGRESS**
3. Choose template engine (FreeMarker, Velocity, or custom)
4. Implement field metadata extraction
5. Create templates for each of the 13 file types + builder methods
6. Implement Gradle task/plugin
7. Add configuration options
8. Test with sample entities
9. Document usage instructions

## Example Domain Input

```java
package com.seibel.basicspring.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseDomain {
    private String code;      // length=8, nullable=false, unique=true
    private String name;      // length=32, nullable=false, unique=true
    private String description; // length=255, nullable=false, unique=false
}
```

**Note:** Field constraints (length, nullable, unique) need to be either:
- Defined in annotations on Domain class
- Configured in a separate mapping file
- Inferred from field types and naming conventions