# Mixture - Definition and Requirements

## What is a Mixture?

A Mixture is a saved recipe for a weekly batch/blend made from food items that are ground up in a food processor and used as a nutrient supplement. Each mixture is like a recipe that can be reused or modified over time.

## Key Characteristics

- **Saved recipes**: Each mixture is a reusable recipe with specific ingredients and quantities
- **Weekly preparation**: Mixtures are typically made once per week as a batch
- **Mixable foods only**: Uses food items from `foodDb` table marked with `mixable = true`
- **Physical processing**: All ingredients are ground up (not pulverized) in a food processor
- **External usage**: The resulting mix is added to foods outside the system (e.g., yogurt, smoothies, etc.)
- **No flavor tracking**: Unlike individual foods, mixtures do not track flavor profiles
- **User-specific**: Each user maintains their own collection of mixture recipes

## Recipe Model

A Mixture is essentially a recipe with:
- **Name and description**: Identifying information for the mixture
- **Ingredient list**: Multiple ingredients, each specifying:
  - Which mixable food to use
  - Amount in grams

**Real-world example:**
```
Mixture: "Seed Mix"
- 15g walnuts
- 10g chia seeds
- 5g pumpkin seeds
```

Users can create multiple mixture recipes with different ingredients and proportions. When they want to adjust quantities or try different combinations, they create a new mixture recipe.

## Measurement Approach

**Grams-based system:**
- All measurements use weight (grams) instead of volume (tablespoons, teaspoons)
- Users measure ingredients with a food scale
- Provides precision and consistency
- Aligns with industry nutrition standards (per 100g)

**Why grams?**
- **Accuracy**: Volume measurements vary based on how ingredients are packed
- **Simplicity**: Direct nutrition calculations without conversion tables
- **Standard**: Industry uses per 100g for all nutrition data
- **Precise**: Especially important for small amounts (e.g., chia seeds)

## Data Architecture

### Food & Nutrition Model

**FoodDb:**
- `nutrition_id` → References NutritionDb
- `typical_serving_grams` → Typical/suggested serving size for UX (e.g., 10g for chia seeds, 30g for walnuts)
- `mixable` → Boolean flag indicating if food can be used in mixtures

**NutritionDb (all values per 100g):**
- **Macronutrients**: carbohydrate, fat, protein, sugar
- **Future micronutrients**: vitamins (A, C, D, etc.), minerals (calcium, iron, etc.)
- All values standardized per 100g for consistent calculations

**Why typical_serving_grams?**
- UX reference: "Most people use 10g of chia seeds"
- Not everyone uses 100g of every food (chia seeds are very small!)
- Provides helpful defaults in the UI
- Users can still enter any amount they want

**Calculation example:**
```
User adds 10g of chia seeds to mixture
Food has typical_serving_grams = 10 (just for reference)
Nutrition is stored per 100g
Calculate: (10g / 100g) × nutrition_values = actual nutrition
```

### Mixture Ingredient Model

**MixtureIngredientDb:**
- `mixture_id` → References MixtureDb
- `food_id` → References FoodDb (must have mixable=true)
- `grams` → Amount in grams (Integer)
- No "unit" field needed - everything is grams

## Future Enhancements

- Nutrition optimization logic
- Nutrient maximization calculations
- Micronutrient tracking (vitamins, minerals)
- Mixture nutrition totals and per-serving calculations

---

## Implementation

### 1. Database Layer

**Food table updates:**
- Add `typical_serving_grams` field (Integer, nullable)
- Keep existing `nutrition_id`, `mixable` fields

**Nutrition table:**
- Keep existing fields: `carbohydrate`, `fat`, `protein`, `sugar` (all per 100g)
- Future: add micronutrient fields (vitamins, minerals)

**MixtureIngredient table:**
- Create `MixtureIngredientDb` entity (join table)
  - Links `MixtureDb` to `FoodDb`
  - Fields: `id`, `mixture_id`, `food_id`, `grams` (Integer)
  - Inherits from `BaseDb` for soft deletes, timestamps, extid
- Add relationship to `MixtureDb`:
  - `@OneToMany` relationship to list of `MixtureIngredientDb`
  - Cascade operations for create/update/delete
- Create Liquibase migration (`009-mixture-ingredients.yaml`)
  - Create `mixture_ingredient` table
  - Add foreign key constraints to `mixture` and `food` tables

**Note:** Serving table is no longer needed - can be deprecated/removed

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
- Create `MixtureIngredient` domain model with:
  - Food reference
  - Grams (Integer)

### 6. API Layer
- Wire up existing `RequestMixtureCreate`/`RequestMixtureUpdate` to save ingredients
- Update `ResponseMixture` to return ingredient lists with food details
- Update `MixtureConverter` to handle ingredient conversion
- Ensure validation enforces at least one ingredient per mixture

---

## Key Architectural Decisions

### 1. Grams-only measurement system
**Decision:** Use grams exclusively; eliminate volume measurements (tablespoons, teaspoons, cups)
**Rationale:**
- Precision and accuracy (volume varies based on packing)
- Simplified data model (no serving conversion table needed)
- Industry standard (all nutrition databases use per 100g)
- Better for small amounts (chia seeds, supplements)

### 2. Unified nutrition table
**Decision:** Keep all nutrition data (macros + future micros) in one table
**Rationale:**
- All nutrition data calculated the same way (per 100g)
- Single source of truth
- Easy to extend (add vitamins, minerals later)
- Clear responsibility and lifecycle

### 3. Typical serving size for UX
**Decision:** Add `typical_serving_grams` to Food table
**Rationale:**
- Different foods have different practical serving sizes (10g chia vs 100g oats)
- Provides helpful defaults/suggestions in UI
- Doesn't constrain calculations (users can enter any amount)
- Separates UX concern from calculation logic

### 4. No serving table
**Decision:** Deprecate/remove the serving conversion table
**Rationale:**
- No longer needed with grams-based system
- Simplifies architecture
- Reduces maintenance burden
- Users will use food scales instead of volume measurements
