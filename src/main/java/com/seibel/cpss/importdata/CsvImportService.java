package com.seibel.cpss.importdata;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.FlavorDb;
import com.seibel.cpss.database.db.entity.FoodDb;
import com.seibel.cpss.database.db.entity.NutritionDb;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.database.db.repository.FlavorRepository;
import com.seibel.cpss.database.db.repository.FoodRepository;
import com.seibel.cpss.database.db.repository.NutritionRepository;
import com.seibel.cpss.database.db.repository.ServingRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvImportService {

    private final NutritionRepository nutritionRepository;
    private final FlavorRepository flavorRepository;
    private final ServingRepository servingRepository;
    private final FoodRepository foodRepository;
    private final Validator validator;

    /**
     * Import nutrition data from CSV file
     */
    @Transactional
    public ImportResult importNutrition(String filePath) throws IOException {
        log.info("Starting import of nutrition data from: {}", filePath);
        List<NutritionDb> entities = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            int rowNum = 1;
            for (CSVRecord record : csvParser) {
                rowNum++;
                try {
                    NutritionDb entity = parseNutritionRecord(record);

                    // Validate entity
                    Set<ConstraintViolation<NutritionDb>> violations = validator.validate(entity);
                    if (!violations.isEmpty()) {
                        errors.add("Row " + rowNum + ": " + formatViolations(violations));
                        continue;
                    }

                    entities.add(entity);
                } catch (Exception e) {
                    errors.add("Row " + rowNum + ": " + e.getMessage());
                }
            }
        }

        if (!errors.isEmpty()) {
            log.warn("Found {} validation errors during nutrition import", errors.size());
            return ImportResult.withErrors("nutrition", 0, errors);
        }

        // Save all valid entities
        List<NutritionDb> saved = nutritionRepository.saveAll(entities);
        log.info("Successfully imported {} nutrition records", saved.size());

        return ImportResult.success("nutrition", saved.size());
    }

    /**
     * Import flavor data from CSV file
     */
    @Transactional
    public ImportResult importFlavor(String filePath) throws IOException {
        log.info("Starting import of flavor data from: {}", filePath);
        List<FlavorDb> entities = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            int rowNum = 1;
            for (CSVRecord record : csvParser) {
                rowNum++;
                try {
                    FlavorDb entity = parseFlavorRecord(record);

                    Set<ConstraintViolation<FlavorDb>> violations = validator.validate(entity);
                    if (!violations.isEmpty()) {
                        errors.add("Row " + rowNum + ": " + formatViolations(violations));
                        continue;
                    }

                    entities.add(entity);
                } catch (Exception e) {
                    errors.add("Row " + rowNum + ": " + e.getMessage());
                }
            }
        }

        if (!errors.isEmpty()) {
            log.warn("Found {} validation errors during flavor import", errors.size());
            return ImportResult.withErrors("flavor", 0, errors);
        }

        List<FlavorDb> saved = flavorRepository.saveAll(entities);
        log.info("Successfully imported {} flavor records", saved.size());

        return ImportResult.success("flavor", saved.size());
    }

    /**
     * Import serving data from CSV file
     */
    @Transactional
    public ImportResult importServing(String filePath) throws IOException {
        log.info("Starting import of serving data from: {}", filePath);
        List<ServingDb> entities = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            int rowNum = 1;
            for (CSVRecord record : csvParser) {
                rowNum++;
                try {
                    ServingDb entity = parseServingRecord(record);

                    Set<ConstraintViolation<ServingDb>> violations = validator.validate(entity);
                    if (!violations.isEmpty()) {
                        errors.add("Row " + rowNum + ": " + formatViolations(violations));
                        continue;
                    }

                    entities.add(entity);
                } catch (Exception e) {
                    errors.add("Row " + rowNum + ": " + e.getMessage());
                }
            }
        }

        if (!errors.isEmpty()) {
            log.warn("Found {} validation errors during serving import", errors.size());
            return ImportResult.withErrors("serving", 0, errors);
        }

        List<ServingDb> saved = servingRepository.saveAll(entities);
        log.info("Successfully imported {} serving records", saved.size());

        return ImportResult.success("serving", saved.size());
    }

    /**
     * Import food data from CSV file
     */
    @Transactional
    public ImportResult importFood(String filePath) throws IOException {
        log.info("Starting import of food data from: {}", filePath);
        List<FoodDb> entities = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            int rowNum = 1;
            for (CSVRecord record : csvParser) {
                rowNum++;
                try {
                    FoodDb entity = parseFoodRecord(record);

                    Set<ConstraintViolation<FoodDb>> violations = validator.validate(entity);
                    if (!violations.isEmpty()) {
                        errors.add("Row " + rowNum + ": " + formatViolations(violations));
                        continue;
                    }

                    entities.add(entity);
                } catch (Exception e) {
                    errors.add("Row " + rowNum + ": " + e.getMessage());
                }
            }
        }

        if (!errors.isEmpty()) {
            log.warn("Found {} validation errors during food import", errors.size());
            return ImportResult.withErrors("food", 0, errors);
        }

        List<FoodDb> saved = foodRepository.saveAll(entities);
        log.info("Successfully imported {} food records", saved.size());

        return ImportResult.success("food", saved.size());
    }

    // ========== Private Helper Methods ==========

    private NutritionDb parseNutritionRecord(CSVRecord record) {
        NutritionDb entity = new NutritionDb();
        LocalDateTime now = LocalDateTime.now();

        // Generate extid from code
        String code = record.get("code");
        entity.setExtid("nut-" + code.toLowerCase() + "-001");

        entity.setCode(code);
        entity.setName(record.get("name"));
        entity.setCategory(record.get("category"));
        entity.setSubcategory(record.get("subcategory"));
        entity.setDescription(record.get("description"));
        entity.setNotes(getNullableValue(record, "notes"));

        entity.setCarbohydrate(Integer.parseInt(record.get("carbohydrate")));
        entity.setFat(Integer.parseInt(record.get("fat")));
        entity.setProtein(Integer.parseInt(record.get("protein")));
        entity.setSugar(Integer.parseInt(record.get("sugar")));

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(ActiveEnum.ACTIVE);

        return entity;
    }

    private FlavorDb parseFlavorRecord(CSVRecord record) {
        FlavorDb entity = new FlavorDb();
        LocalDateTime now = LocalDateTime.now();

        String code = record.get("code");
        entity.setExtid("flv-" + code.toLowerCase() + "-001");

        entity.setCode(code);
        entity.setName(record.get("name"));
        entity.setCategory(record.get("category"));
        entity.setSubcategory(record.get("subcategory"));
        entity.setDescription(record.get("description"));
        entity.setNotes(getNullableValue(record, "notes"));

        entity.setHowtouse(getNullableValue(record, "howtouse"));
        entity.setCrunch(Integer.parseInt(record.get("crunch")));
        entity.setPunch(Integer.parseInt(record.get("punch")));
        entity.setSweet(Integer.parseInt(record.get("sweet")));
        entity.setSavory(Integer.parseInt(record.get("savory")));

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(ActiveEnum.ACTIVE);

        return entity;
    }

    private ServingDb parseServingRecord(CSVRecord record) {
        ServingDb entity = new ServingDb();
        LocalDateTime now = LocalDateTime.now();

        String code = record.get("code");
        entity.setExtid("srv-" + code.toLowerCase() + "-001");

        entity.setCode(code);
        entity.setName(record.get("name"));
        entity.setCategory(record.get("category"));
        entity.setSubcategory(record.get("subcategory"));
        entity.setDescription(record.get("description"));
        entity.setNotes(getNullableValue(record, "notes"));

        entity.setCup(getNullableInteger(record, "cup"));
        entity.setQuarter(getNullableInteger(record, "quarter"));
        entity.setTablespoon(getNullableInteger(record, "tablespoon"));
        entity.setTeaspoon(getNullableInteger(record, "teaspoon"));
        entity.setGram(getNullableInteger(record, "gram"));

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(ActiveEnum.ACTIVE);

        return entity;
    }

    private FoodDb parseFoodRecord(CSVRecord record) {
        FoodDb entity = new FoodDb();
        LocalDateTime now = LocalDateTime.now();

        String code = record.get("code");
        entity.setExtid("food-" + code.toLowerCase() + "-001");

        entity.setCode(code);
        entity.setName(record.get("name"));
        entity.setCategory(record.get("category"));
        entity.setSubcategory(record.get("subcategory"));
        entity.setDescription(record.get("description"));
        entity.setNotes(getNullableValue(record, "notes"));

        entity.setFlavor(record.get("flavor"));
        entity.setNutrition(record.get("nutrition"));
        entity.setServing(record.get("serving"));

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(ActiveEnum.ACTIVE);

        return entity;
    }

    private String getNullableValue(CSVRecord record, String column) {
        String value = record.get(column);
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    private Integer getNullableInteger(CSVRecord record, String column) {
        String value = getNullableValue(record, column);
        return (value == null) ? null : Integer.parseInt(value);
    }

    private <T> String formatViolations(Set<ConstraintViolation<T>> violations) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
        }
        return sb.toString();
    }
}
