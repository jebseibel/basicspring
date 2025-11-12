package com.seibel.cpss.loader;

import com.seibel.cpss.common.domain.Flavor;
import com.seibel.cpss.common.domain.Food;
import com.seibel.cpss.common.domain.Nutrition;
import com.seibel.cpss.common.domain.Serving;
import com.seibel.cpss.database.db.entity.FlavorDb;
import com.seibel.cpss.database.db.entity.FoodDb;
import com.seibel.cpss.database.db.entity.NutritionDb;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.database.db.repository.FlavorRepository;
import com.seibel.cpss.database.db.repository.FoodRepository;
import com.seibel.cpss.database.db.repository.NutritionRepository;
import com.seibel.cpss.database.db.repository.ServingRepository;
import com.seibel.cpss.database.db.service.FlavorDbService;
import com.seibel.cpss.database.db.service.FoodDbService;
import com.seibel.cpss.database.db.service.NutritionDbService;
import com.seibel.cpss.database.db.service.ServingDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data loader that reads CSV files and loads data through DbService layer.
 * Runs on application startup and only loads if tables are empty.
 */
@Slf4j
@Component
@Order(1) // Run early in startup sequence
public class DataLoader implements CommandLineRunner {

    private final FoodDbService foodDbService;
    private final FlavorDbService flavorDbService;
    private final NutritionDbService nutritionDbService;
    private final ServingDbService servingDbService;

    // Repositories needed for linking relationships
    private final FoodRepository foodRepository;
    private final FlavorRepository flavorRepository;
    private final NutritionRepository nutritionRepository;
    private final ServingRepository servingRepository;

    private static final String DATA_PATH = "db/data/";

    // Category list for organizing CSV files
    private static final List<String> CATEGORIES = Arrays.asList(
            "cheese",
            "dried-crunch",
            "dried-fruit",
            "fresh-fruit",
            "nuts",
            "vegetables"
    );

    public DataLoader(FoodDbService foodDbService,
                      FlavorDbService flavorDbService,
                      NutritionDbService nutritionDbService,
                      ServingDbService servingDbService,
                      FoodRepository foodRepository,
                      FlavorRepository flavorRepository,
                      NutritionRepository nutritionRepository,
                      ServingRepository servingRepository) {
        this.foodDbService = foodDbService;
        this.flavorDbService = flavorDbService;
        this.nutritionDbService = nutritionDbService;
        this.servingDbService = servingDbService;
        this.foodRepository = foodRepository;
        this.flavorRepository = flavorRepository;
        this.nutritionRepository = nutritionRepository;
        this.servingRepository = servingRepository;
    }

    @Override
    public void run(String... args) {
        log.info("=== Starting Data Loader ===");

        try {
            // Check if data already exists
            long foodCount = foodRepository.count();
            if (foodCount > 0) {
                log.info("Data already loaded ({} food items exist). Skipping data load.", foodCount);
                return;
            }

            log.info("No existing data found. Loading from CSV files...");

            // Load in order: Flavor -> Serving -> Nutrition -> Food -> Link relationships
            loadFlavors();
            loadServings();
            loadNutrition();
            loadFoods();
            linkFoodRelationships();

            log.info("=== Data Loading Complete ===");
            logSummary();

        } catch (Exception e) {
            log.error("FATAL: Data loading failed", e);
            throw new RuntimeException("Failed to load initial data", e);
        }
    }

    private void loadFlavors() throws IOException {
        log.info("Loading Flavors...");
        int count = 0;

        for (String category : CATEGORIES) {
            String filePath = DATA_PATH + "20-flavor-" + category + ".csv";
            List<Map<String, String>> records = CsvParser.parse(filePath);

            for (Map<String, String> record : records) {
                Flavor flavor = new Flavor();
                // Note: code is provided in CSV but will be auto-generated if null/empty
                flavor.setCode(record.get("code"));
                flavor.setName(record.get("name"));
                flavor.setDescription(record.get("description"));
                flavor.setNotes(record.get("notes"));
                flavor.setHowtouse(record.get("howtouse"));
                flavor.setCrunch(parseInteger(record.get("crunch")));
                flavor.setPunch(parseInteger(record.get("punch")));
                flavor.setSweet(parseInteger(record.get("sweet")));
                flavor.setSavory(parseInteger(record.get("savory")));

                flavorDbService.create(flavor);
                count++;
            }
        }

        log.info("Loaded {} flavor profiles", count);
    }

    private void loadServings() throws IOException {
        log.info("Loading Servings...");
        int count = 0;

        for (String category : CATEGORIES) {
            String filePath = DATA_PATH + "30-serving-" + category + ".csv";
            List<Map<String, String>> records = CsvParser.parse(filePath);

            for (Map<String, String> record : records) {
                Serving serving = new Serving();
                serving.setCode(record.get("code"));
                serving.setName(record.get("name"));
                serving.setDescription(record.get("description"));
                serving.setNotes(record.get("notes"));
                serving.setCup(parseInteger(record.get("cup")));
                serving.setQuarter(parseInteger(record.get("quarter")));
                serving.setTablespoon(parseInteger(record.get("tablespoon")));
                serving.setTeaspoon(parseInteger(record.get("teaspoon")));
                serving.setGram(parseInteger(record.get("gram")));

                servingDbService.create(serving);
                count++;
            }
        }

        log.info("Loaded {} serving profiles", count);
    }

    private void loadNutrition() throws IOException {
        log.info("Loading Nutrition...");
        int count = 0;

        for (String category : CATEGORIES) {
            String filePath = DATA_PATH + "40-nutrition-" + category + ".csv";
            List<Map<String, String>> records = CsvParser.parse(filePath);

            for (Map<String, String> record : records) {
                Nutrition nutrition = new Nutrition();
                nutrition.setCode(record.get("code"));
                nutrition.setName(record.get("name"));
                nutrition.setDescription(record.get("description"));
                nutrition.setNotes(record.get("notes"));
                nutrition.setCarbohydrate(parseInteger(record.get("carbohydrate")));
                nutrition.setFat(parseInteger(record.get("fat")));
                nutrition.setProtein(parseInteger(record.get("protein")));
                nutrition.setSugar(parseInteger(record.get("sugar")));

                nutritionDbService.create(nutrition);
                count++;
            }
        }

        log.info("Loaded {} nutrition profiles", count);
    }

    private void loadFoods() throws IOException {
        log.info("Loading Foods...");
        int count = 0;

        for (String category : CATEGORIES) {
            String filePath = DATA_PATH + "10-food-" + category + ".csv";
            List<Map<String, String>> records = CsvParser.parse(filePath);

            for (Map<String, String> record : records) {
                Food food = new Food();
                // Code will be auto-generated by FoodDbService
                food.setName(record.get("name"));
                food.setCategory(record.get("category"));
                food.setSubcategory(record.get("subcategory"));
                food.setDescription(record.get("description"));
                food.setNotes(record.get("notes"));
                food.setFoundation(parseBoolean(record.get("foundation")));
                food.setMixable(parseBoolean(record.get("mixable")));

                foodDbService.create(food);
                count++;
            }
        }

        log.info("Loaded {} food items", count);
    }

    /**
     * Links Food entities with their corresponding Flavor, Nutrition, and Serving entities
     * by matching on the 'name' field.
     */
    private void linkFoodRelationships() {
        log.info("Linking Food relationships...");

        List<FoodDb> allFoods = foodRepository.findAll();
        int linkedFlavors = 0;
        int linkedServings = 0;
        int linkedNutrition = 0;

        for (FoodDb food : allFoods) {
            boolean updated = false;

            // Link Flavor by name
            Optional<FlavorDb> flavor = flavorRepository.findByName(food.getName());
            if (flavor.isPresent()) {
                food.setFlavor(flavor.get());
                linkedFlavors++;
                updated = true;
            } else {
                log.warn("No flavor profile found for food: {}", food.getName());
            }

            // Link Serving by name
            Optional<ServingDb> serving = servingRepository.findByName(food.getName());
            if (serving.isPresent()) {
                food.setServing(serving.get());
                linkedServings++;
                updated = true;
            } else {
                log.warn("No serving profile found for food: {}", food.getName());
            }

            // Link Nutrition by name
            Optional<NutritionDb> nutrition = nutritionRepository.findByName(food.getName());
            if (nutrition.isPresent()) {
                food.setNutrition(nutrition.get());
                linkedNutrition++;
                updated = true;
            } else {
                log.warn("No nutrition profile found for food: {}", food.getName());
            }

            if (updated) {
                foodRepository.save(food);
            }
        }

        log.info("Linked {} flavors, {} servings, {} nutrition profiles to food items",
                linkedFlavors, linkedServings, linkedNutrition);
    }

    private void logSummary() {
        long foods = foodRepository.count();
        long flavors = flavorRepository.count();
        long servings = servingRepository.count();
        long nutrition = nutritionRepository.count();

        log.info("=== Data Load Summary ===");
        log.info("Foods:     {}", foods);
        log.info("Flavors:   {}", flavors);
        log.info("Servings:  {}", servings);
        log.info("Nutrition: {}", nutrition);
        log.info("========================");
    }

    // Utility parsing methods

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("Failed to parse integer: {}", value);
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return Boolean.parseBoolean(value.trim());
    }
}
