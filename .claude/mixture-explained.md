# Mixture - Definition and Requirements

## What is a Mixture?

A Mixture is a weekly batch/blend made from food items that are ground up in a food processor and used as a nutrient supplement.

## Key Characteristics

- **Weekly preparation**: Created once per week as a batch
- **Mixable foods only**: Uses food items from `foodDb` table marked with `mixable = true`
- **Physical processing**: All ingredients are ground up (not pulverized) in a food processor
- **External usage**: The resulting mix is added to foods outside the system (e.g., yogurt, smoothies, etc.)
- **No flavor tracking**: Unlike individual foods, mixtures do not track flavor profiles

## Ingredients & Quantities

- Each mixture contains multiple ingredients (mixable foods)
- Each ingredient has a specific quantity to maximize nutrients
- Quantities reference data from the **nutrition** and **serving** tables

## Implementation Phases

### Phase 1 (Current - Simplified)
- **Quantities measured in tablespoons only**
- Basic ingredient tracking
- No nutrition optimization yet

### Phase 2 (Future)
- Add nutrition optimization logic
- Support additional serving measurements
- Nutrient maximization calculations

---

## Implementation

### 1. Database Layer
- Create `MixtureIngredientDb` entity (join table)
  - Links `MixtureDb` to `FoodDb`
  - Fields: `id`, `mixture_id`, `food_id`, `quantity`, `unit` (hardcoded to "tablespoon" for Phase 1)
  - Inherits from `BaseDb` for soft deletes, timestamps, extid
- Add relationship to `MixtureDb`:
  - `@OneToMany` relationship to list of `MixtureIngredientDb`
  - Cascade operations for create/update/delete
- Create Liquibase migration (`009-mixture-ingredients.yaml`)
  - Create `mixture_ingredient` table
  - Add foreign key constraints to `mixture` and `food` tables

### 2. Repository Layer
- Create `MixtureIngredientRepository` extending `JpaRepository`

### 3. Service Layer
- Update `MixtureDbService` to handle ingredients when creating/updating mixtures
- Create `MixtureIngredientDbService` for CRUD operations on ingredients
- Validate that foods are marked as `mixable = true` before adding to mixture
- Handle cascading deletes for mixture ingredients

### 4. Mapper Layer
- Update `MixtureMapper` to convert ingredients between DB entities and domain models
- Handle bidirectional mapping for nested ingredient lists

### 5. Domain Layer
- Update `Mixture` domain model to include list of `MixtureIngredient` objects
- Create `MixtureIngredient` domain model with food reference and quantity

### 6. API Layer
- Wire up existing `RequestMixtureCreate`/`RequestMixtureUpdate` to save ingredients
- Update `ResponseMixture` to return ingredient lists with food details
- Update `MixtureConverter` to handle ingredient conversion
- Ensure validation enforces at least one ingredient per mixture
