package com.seibel.cpss.importdata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cpss.import.csv.enabled", havingValue = "true")
public class CsvImportRunner implements CommandLineRunner {

    private final CsvImportService csvImportService;
    private final SqlFileGenerator sqlFileGenerator;

    private static final String IMPORT_DATA_DIR = "src/main/resources/import/data";
    private static final String SQL_OUTPUT_DIR = "src/main/resources/db/changelog/changes/sql";

    @Override
    public void run(String... args) throws Exception {
        log.info("CSV Import Runner started");
        log.info("Looking for CSV files in: {}", IMPORT_DATA_DIR);

        Path dataDir = Paths.get(IMPORT_DATA_DIR);
        if (!Files.exists(dataDir)) {
            log.warn("Import data directory does not exist: {}", IMPORT_DATA_DIR);
            log.info("Please create the directory and add CSV files to import");
            return;
        }

        // Import in order: nutrition -> flavor -> serving -> food
        // This ensures foreign key references are available

        importAndGenerate("nutrition", "nutrition.csv", "nutrition_import.sql");
        importAndGenerate("flavor", "flavor.csv", "flavor_import.sql");
        importAndGenerate("serving", "serving.csv", "serving_import.sql");
        importAndGenerate("food", "food.csv", "food_import.sql");

        log.info("CSV Import Runner completed");
    }

    private void importAndGenerate(String type, String csvFileName, String sqlFileName) {
        Path csvPath = Paths.get(IMPORT_DATA_DIR, csvFileName);

        if (!Files.exists(csvPath)) {
            log.info("Skipping {}: file not found at {}", type, csvPath);
            return;
        }

        try {
            log.info("========================================");
            log.info("Processing {} import", type);
            log.info("========================================");

            // Import from CSV
            ImportResult result = switch (type) {
                case "nutrition" -> csvImportService.importNutrition(csvPath.toString());
                case "flavor" -> csvImportService.importFlavor(csvPath.toString());
                case "serving" -> csvImportService.importServing(csvPath.toString());
                case "food" -> csvImportService.importFood(csvPath.toString());
                default -> throw new IllegalArgumentException("Unknown import type: " + type);
            };

            // Print results
            if (result.isSuccess()) {
                log.info("✓ Successfully imported {} {} records", result.getRecordsImported(), type);

                // Generate SQL file
                Path sqlPath = Paths.get(SQL_OUTPUT_DIR, sqlFileName);
                switch (type) {
                    case "nutrition" -> sqlFileGenerator.generateNutritionSql(sqlPath.toString(), null);
                    case "flavor" -> sqlFileGenerator.generateFlavorSql(sqlPath.toString(), null);
                    case "serving" -> sqlFileGenerator.generateServingSql(sqlPath.toString(), null);
                    case "food" -> sqlFileGenerator.generateFoodSql(sqlPath.toString(), null);
                }

                log.info("✓ Generated SQL file: {}", sqlPath);
            } else {
                log.error("✗ Failed to import {}", type);
                log.error("Errors found:");
                result.getErrors().forEach(error -> log.error("  - {}", error));
            }

        } catch (Exception e) {
            log.error("Error processing {} import: {}", type, e.getMessage(), e);
        }
    }
}
