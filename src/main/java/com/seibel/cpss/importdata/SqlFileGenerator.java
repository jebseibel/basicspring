package com.seibel.cpss.importdata;

import com.seibel.cpss.database.db.entity.FlavorDb;
import com.seibel.cpss.database.db.entity.FoodDb;
import com.seibel.cpss.database.db.entity.NutritionDb;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.database.db.repository.FlavorRepository;
import com.seibel.cpss.database.db.repository.FoodRepository;
import com.seibel.cpss.database.db.repository.NutritionRepository;
import com.seibel.cpss.database.db.repository.ServingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqlFileGenerator {

    private final NutritionRepository nutritionRepository;
    private final FlavorRepository flavorRepository;
    private final ServingRepository servingRepository;
    private final FoodRepository foodRepository;

    private static final DateTimeFormatter SQL_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Generate Liquibase SQL file for nutrition data
     */
    public void generateNutritionSql(String outputPath, List<Long> ids) throws IOException {
        log.info("Generating nutrition SQL file: {}", outputPath);

        List<NutritionDb> entities = (ids == null || ids.isEmpty())
                ? nutritionRepository.findAll()
                : nutritionRepository.findAllById(ids);

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("--liquibase formatted sql");
            writer.println();
            writer.println("--changeset load_nutrition_import:1");
            writer.println("INSERT INTO nutrition (");
            writer.println("    id, extid, code, name, category, subcategory, description, notes,");
            writer.println("    carbohydrate, fat, protein, sugar,");
            writer.println("    created_at, updated_at, deleted_at, active");
            writer.println(") VALUES");

            for (int i = 0; i < entities.size(); i++) {
                NutritionDb entity = entities.get(i);
                writer.print(formatNutritionValue(entity));
                if (i < entities.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println(";");
                }
            }
        }

        log.info("Successfully generated nutrition SQL with {} records", entities.size());
    }

    /**
     * Generate Liquibase SQL file for flavor data
     */
    public void generateFlavorSql(String outputPath, List<Long> ids) throws IOException {
        log.info("Generating flavor SQL file: {}", outputPath);

        List<FlavorDb> entities = (ids == null || ids.isEmpty())
                ? flavorRepository.findAll()
                : flavorRepository.findAllById(ids);

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("--liquibase formatted sql");
            writer.println();
            writer.println("--changeset load_flavor_import:1");
            writer.println("INSERT INTO flavor (");
            writer.println("    id, extid, code, name, category, subcategory, description, notes,");
            writer.println("    howtouse, crunch, punch, sweet, savory,");
            writer.println("    created_at, updated_at, deleted_at, active");
            writer.println(") VALUES");

            for (int i = 0; i < entities.size(); i++) {
                FlavorDb entity = entities.get(i);
                writer.print(formatFlavorValue(entity));
                if (i < entities.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println(";");
                }
            }
        }

        log.info("Successfully generated flavor SQL with {} records", entities.size());
    }

    /**
     * Generate Liquibase SQL file for serving data
     */
    public void generateServingSql(String outputPath, List<Long> ids) throws IOException {
        log.info("Generating serving SQL file: {}", outputPath);

        List<ServingDb> entities = (ids == null || ids.isEmpty())
                ? servingRepository.findAll()
                : servingRepository.findAllById(ids);

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("--liquibase formatted sql");
            writer.println();
            writer.println("--changeset load_serving_import:1");
            writer.println("INSERT INTO serving (");
            writer.println("    id, extid, code, name, category, subcategory, description, notes,");
            writer.println("    cup, quarter, tablespoon, teaspoon, gram,");
            writer.println("    created_at, updated_at, deleted_at, active");
            writer.println(") VALUES");

            for (int i = 0; i < entities.size(); i++) {
                ServingDb entity = entities.get(i);
                writer.print(formatServingValue(entity));
                if (i < entities.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println(";");
                }
            }
        }

        log.info("Successfully generated serving SQL with {} records", entities.size());
    }

    /**
     * Generate Liquibase SQL file for food data
     */
    public void generateFoodSql(String outputPath, List<Long> ids) throws IOException {
        log.info("Generating food SQL file: {}", outputPath);

        List<FoodDb> entities = (ids == null || ids.isEmpty())
                ? foodRepository.findAll()
                : foodRepository.findAllById(ids);

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("--liquibase formatted sql");
            writer.println();
            writer.println("--changeset load_food_import:1");
            writer.println("INSERT INTO food (");
            writer.println("    id, extid, code, name, category, subcategory, description, notes,");
            writer.println("    flavor, nutrition, serving,");
            writer.println("    created_at, updated_at, deleted_at, active");
            writer.println(") VALUES");

            for (int i = 0; i < entities.size(); i++) {
                FoodDb entity = entities.get(i);
                writer.print(formatFoodValue(entity));
                if (i < entities.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println(";");
                }
            }
        }

        log.info("Successfully generated food SQL with {} records", entities.size());
    }

    // ========== Private Formatting Methods ==========

    private String formatNutritionValue(NutritionDb entity) {
        return String.format(
                "    (%d, %s, %s, %s, %s, %s, %s, %s,\n     %d, %d, %d, %d,\n     NOW(), NOW(), %s, %d)",
                entity.getId(),
                sqlString(entity.getExtid()),
                sqlString(entity.getCode()),
                sqlString(entity.getName()),
                sqlString(entity.getCategory()),
                sqlString(entity.getSubcategory()),
                sqlString(entity.getDescription()),
                sqlString(entity.getNotes()),
                entity.getCarbohydrate(),
                entity.getFat(),
                entity.getProtein(),
                entity.getSugar(),
                sqlNull(),
                entity.getActive().value
        );
    }

    private String formatFlavorValue(FlavorDb entity) {
        return String.format(
                "    (%d, %s, %s, %s, %s, %s, %s, %s,\n     %s, %d, %d, %d, %d,\n     NOW(), NOW(), %s, %d)",
                entity.getId(),
                sqlString(entity.getExtid()),
                sqlString(entity.getCode()),
                sqlString(entity.getName()),
                sqlString(entity.getCategory()),
                sqlString(entity.getSubcategory()),
                sqlString(entity.getDescription()),
                sqlString(entity.getNotes()),
                sqlString(entity.getHowtouse()),
                entity.getCrunch(),
                entity.getPunch(),
                entity.getSweet(),
                entity.getSavory(),
                sqlNull(),
                entity.getActive().value
        );
    }

    private String formatServingValue(ServingDb entity) {
        return String.format(
                "    (%d, %s, %s, %s, %s, %s, %s, %s,\n     %s, %s, %s, %s, %s,\n     NOW(), NOW(), %s, %d)",
                entity.getId(),
                sqlString(entity.getExtid()),
                sqlString(entity.getCode()),
                sqlString(entity.getName()),
                sqlString(entity.getCategory()),
                sqlString(entity.getSubcategory()),
                sqlString(entity.getDescription()),
                sqlString(entity.getNotes()),
                sqlInteger(entity.getCup()),
                sqlInteger(entity.getQuarter()),
                sqlInteger(entity.getTablespoon()),
                sqlInteger(entity.getTeaspoon()),
                sqlInteger(entity.getGram()),
                sqlNull(),
                entity.getActive().value
        );
    }

    private String formatFoodValue(FoodDb entity) {
        return String.format(
                "    (%d, %s, %s, %s, %s, %s, %s, %s,\n     %s, %s, %s,\n     NOW(), NOW(), %s, %d)",
                entity.getId(),
                sqlString(entity.getExtid()),
                sqlString(entity.getCode()),
                sqlString(entity.getName()),
                sqlString(entity.getCategory()),
                sqlString(entity.getSubcategory()),
                sqlString(entity.getDescription()),
                sqlString(entity.getNotes()),
                sqlString(entity.getFlavor()),
                sqlString(entity.getNutrition()),
                sqlString(entity.getServing()),
                sqlNull(),
                entity.getActive().value
        );
    }

    private String sqlString(String value) {
        if (value == null) {
            return "NULL";
        }
        // Escape single quotes by doubling them
        String escaped = value.replace("'", "''");
        return "'" + escaped + "'";
    }

    private String sqlInteger(Integer value) {
        return (value == null) ? "NULL" : value.toString();
    }

    private String sqlNull() {
        return "NULL";
    }
}
