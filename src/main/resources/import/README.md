# CSV Import Tool

This tool allows you to import data from CSV files into the database and generate Liquibase SQL migration files.

## Overview

The CSV import process:
1. Reads CSV files from `src/main/resources/import/data/`
2. Validates data using JPA entity validation
3. Imports validated data into the local database
4. Generates Liquibase SQL files in `src/main/resources/db/changelog/changes/sql/`

## Usage

### Step 1: Prepare CSV Files

Copy CSV template files from `templates/` directory and fill them with your data:

```bash
cp templates/nutrition-template.csv data/nutrition.csv
cp templates/flavor-template.csv data/flavor.csv
cp templates/serving-template.csv data/serving.csv
cp templates/food-template.csv data/food.csv
```

### Step 2: Fill CSV Files

Edit the CSV files in `data/` directory with your actual data. See templates for column format.

**Important notes:**
- Do NOT include `id` column - it's auto-generated
- `extid` is auto-generated based on code
- All required fields must be filled
- Flavor ratings (crunch, punch, sweet, savory) must be 1-5
- Follow existing naming conventions for extid references in food.csv

### Step 3: Enable Import

Add this property to your application configuration or pass as argument:

```bash
./gradlew bootRun --args='--cpss.import.csv.enabled=true'
```

Or add to `application.yml`:
```yaml
cpss:
  import:
    csv:
      enabled: true
```

### Step 4: Run Application

```bash
./gradlew bootRun
```

The import will run on startup and:
- Import data in order: nutrition → flavor → serving → food
- Validate all data using entity constraints
- Report any errors found
- Generate SQL files for successful imports

### Step 5: Review Generated SQL

Check the generated SQL files in:
```
src/main/resources/db/changelog/changes/sql/
  - nutrition_import.sql
  - flavor_import.sql
  - serving_import.sql
  - food_import.sql
```

### Step 6: Add to Liquibase Changelog (Production)

To use in production, add the generated SQL files to your Liquibase master changelog:

Edit `src/main/resources/db/changelog/db.changelog-master.yaml` and add:

```yaml
  - include:
      file: db/changelog/changes/sql/nutrition_import.sql
  - include:
      file: db/changelog/changes/sql/flavor_import.sql
  - include:
      file: db/changelog/changes/sql/serving_import.sql
  - include:
      file: db/changelog/changes/sql/food_import.sql
```

## CSV File Formats

### nutrition.csv
```csv
code,name,category,subcategory,description,notes,carbohydrate,fat,protein,sugar
```

### flavor.csv
```csv
code,name,category,subcategory,description,notes,howtouse,crunch,punch,sweet,savory
```

### serving.csv
```csv
code,name,category,subcategory,description,notes,cup,quarter,tablespoon,teaspoon,gram
```

### food.csv
```csv
code,name,category,subcategory,description,notes,flavor,nutrition,serving
```

## Field Descriptions

### Common Fields (all tables)
- **code**: Unique 8-character code (e.g., "ROMAINE")
- **name**: Display name (max 32 chars)
- **category**: Main category (max 32 chars)
- **subcategory**: Subcategory (max 32 chars)
- **description**: Description (text)
- **notes**: Additional notes (text, optional)

### Nutrition Specific
- **carbohydrate**: Carbs in grams (integer)
- **fat**: Fat in grams (integer)
- **protein**: Protein in grams (integer)
- **sugar**: Sugar in grams (integer)

### Flavor Specific
- **howtouse**: Usage suggestion (max 16 chars, optional)
- **crunch**: Crunch rating 1-5 (integer)
- **punch**: Punch rating 1-5 (integer)
- **sweet**: Sweet rating 1-5 (integer)
- **savory**: Savory rating 1-5 (integer)

### Serving Specific
- **cup**: Servings per cup (integer, optional)
- **quarter**: Servings per 1/4 cup (integer, optional)
- **tablespoon**: Servings per tablespoon (integer, optional)
- **teaspoon**: Servings per teaspoon (integer, optional)
- **gram**: Grams per serving (integer, optional)

### Food Specific
- **flavor**: Reference to flavor extid (e.g., "flv-romaine-001")
- **nutrition**: Reference to nutrition extid (e.g., "nut-romaine-001")
- **serving**: Reference to serving extid (e.g., "srv-romaine-001")

## Troubleshooting

### Validation Errors

If you see validation errors, check:
- All required fields are filled
- Ratings are between 1-5
- References in food.csv match existing extids
- String lengths don't exceed limits
- Integer fields contain valid numbers

### File Not Found

Ensure CSV files are in the correct location:
```
src/main/resources/import/data/
```

### Database Errors

Check:
- Database is running
- Connection settings in application.yml
- No duplicate codes or names

## Example Workflow

```bash
# 1. Copy templates
cd src/main/resources/import
cp templates/nutrition-template.csv data/nutrition.csv
cp templates/flavor-template.csv data/flavor.csv
cp templates/serving-template.csv data/serving.csv

# 2. Edit CSV files with your data
# (Use your favorite editor)

# 3. Run import
cd ../../../../..
./gradlew bootRun --args='--cpss.import.csv.enabled=true'

# 4. Check logs for success/errors

# 5. Review generated SQL files
ls -la src/main/resources/db/changelog/changes/sql/*_import.sql

# 6. Commit SQL files to version control
git add src/main/resources/db/changelog/changes/sql/*_import.sql
git commit -m "Add imported food data"
```

## Notes

- Import is disabled by default (safe for production)
- Import order matters: nutrition, flavor, serving must be imported before food
- Generated SQL files use NOW() for timestamps
- Auto-generated IDs will differ between environments
- Use extid for references, not id
