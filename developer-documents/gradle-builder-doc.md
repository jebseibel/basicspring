# High-Level Requirements Document

## Project Goal
Generate complete Spring Boot CRUD boilerplate code from a simple Java Domain object definition.

So I am writing lots and lots of the same kind of code in my Java, Spring, Gradle projects. I repeat this pattern over and over. I also have request objects/response objects, Domain objects in a common area and Entity objects in the database area. I have IntelliJ Ultimate installed with gradle.


## Generation Scope
- **Input**: Single Java Domain object (simple POJO)
- **Output**: Complete layered architecture (8 files per entity)
- **Mode**: One entity at a time

## Generated Artifacts (per entity)

### 1. **Entity Layer** (`database.entity` package)
- `{Entity}Entity.java` - JPA entity with annotations

### 2. **Domain Layer** (`common.domain` package)
- `{Entity}.java` - Business domain object (input source)

### 3. **DTO Layer** (`common.dto` package)
- `{Entity}Request.java` - Incoming data (with validation annotations)
- `{Entity}Response.java` - Outgoing data

### 4. **Mapper** (`common.mapper` package)
- `{Entity}Mapper.java` - Conversions between Entity ↔ Domain ↔ DTOs

### 5. **Repository Layer** (`database.repository` package)
- `{Entity}Repository.java` - Spring Data JPA interface

### 6. **Database Service Layer** (`database.service` package)
- `{Entity}DbService.java` - Database operations

### 7. **Business Service Layer** (`service` package)
- `{Entity}Service.java` - Business logic with full CRUD

### 8. **Controller Layer** (`controller` package)
- `{Entity}Controller.java` - REST endpoints

## Key Features
- ✅ Fully implemented CRUD operations (no TODOs)
- ✅ Validation annotations on Request DTOs
- ✅ Custom Mapper pattern for object conversions
- ✅ Lombok annotations throughout
- ✅ Standard REST practices
- ⚠️  Simple entities only (no relationships initially)
- ⚠️  No database constraints (for now)

## Implementation Approach
- **Technology**: Gradle custom task or buildSrc plugin
- **Generation**: Command/task per entity
- **Framework**: Spring Boot + Gradle

---

**Next Steps:**
1. Review sample objects to understand exact patterns
2. Define input format (Domain object structure)
3. Implement generator code