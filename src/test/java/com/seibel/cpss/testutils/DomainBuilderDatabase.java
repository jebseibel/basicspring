package com.seibel.cpss.testutils;

import com.seibel.cpss.common.domain.*;
import com.seibel.cpss.database.db.entity.*;
import com.seibel.cpss.database.db.mapper.*;

import java.util.UUID;

public class DomainBuilderDatabase extends DomainBuilderBase {


    // ///////////////////////////////////////////////////////////////////
    // Company
    public static Company getCompany() {
        CompanyDb item = getCompanyDb();
        return new CompanyMapper().toModel(item);
    }

    public static Company getCompany(CompanyDb item) {
        return new CompanyMapper().toModel(item);
    }

    public static CompanyDb getCompanyDb() {
        return getCompanyDb(null, null, null, null);
    }

    public static CompanyDb getCompanyDb(String code, String name) {
        return getCompanyDb(code, name, null, null);
    }

    public static CompanyDb getCompanyDb(String code, String name, String description, String extid) {
        CompanyDb item = new CompanyDb();
        item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
        item.setCode(code != null ? code : getCodeRandom("CO_"));
        item.setName(name != null ? name : getNameRandom("Company_"));
        item.setDescription(description != null ? description : getDescriptionRandom("Company Description "));
        setBaseSyncFields(item);
        return item;
    }

    // ///////////////////////////////////////////////////////////////////
    // Serving
    public static Serving getServing() {
        ServingDb item = getServingDb();
        return new ServingMapper().toModel(item);
    }

    public static Serving getServing(ServingDb item) {
        return new ServingMapper().toModel(item);
    }

    public static ServingDb getServingDb() {
        return getServingDb(null, null, null, null, null, null, null, null, null, null);
    }

    public static ServingDb getServingDb(String code, String name) {
        return getServingDb(code, name, null, null, null, null, null, null, null, null);
    }

    public static ServingDb getServingDb(String code, String name, String description,
                                         Integer cup, Integer quarter, Integer tablespoon, Integer teaspoon, Integer gram, String notes, String extid) {
        ServingDb item = new ServingDb();
        item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
        item.setCode(code != null ? code : getCodeRandom("SRV_"));
        item.setName(name != null ? name : getNameRandom("Serving_"));
        item.setDescription(description != null ? description : getDescriptionRandom("Serving Description "));
        item.setNotes(notes != null ? notes : getDescriptionRandom("Serving Notes "));
        item.setCup(cup != null ? cup : 1);
        item.setQuarter(quarter != null ? quarter : 4);
        item.setTablespoon(tablespoon != null ? tablespoon : 16);
        item.setTeaspoon(teaspoon != null ? teaspoon : 48);
        item.setGram(gram != null ? gram : 240);
        setBaseSyncFields(item);
        return item;
    }

    // ///////////////////////////////////////////////////////////////////
    // Nutrition
    public static Nutrition getNutrition() {
        NutritionDb item = getNutritionDb();
        return new NutritionMapper().toModel(item);
    }

    public static Nutrition getNutrition(NutritionDb item) {
        return new NutritionMapper().toModel(item);
    }

    public static NutritionDb getNutritionDb() {
        return getNutritionDb(null, null, null, null, null, null, null, null, null);
    }

    public static NutritionDb getNutritionDb(String code, String name) {
        return getNutritionDb(code, name, null, null, null, null, null, null, null);
    }

    public static NutritionDb getNutritionDb(String code, String name, String description,
                                             Integer carbohydrate, Integer fat, Integer protein, Integer sugar, String notes, String extid) {
        NutritionDb item = new NutritionDb();
        item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
        item.setCode(code != null ? code : getCodeRandom("NTR_"));
        item.setName(name != null ? name : getNameRandom("Nutrition_"));
        item.setDescription(description != null ? description : getDescriptionRandom("Nutrition Description "));
        item.setNotes(notes != null ? notes : getDescriptionRandom("Nutrition Notes "));
        item.setCarbohydrate(carbohydrate != null ? carbohydrate : 30);
        item.setFat(fat != null ? fat : 10);
        item.setProtein(protein != null ? protein : 20);
        item.setSugar(sugar != null ? sugar : 5);
        setBaseSyncFields(item);
        return item;
    }

    // ///////////////////////////////////////////////////////////////////
    // Food
    public static Food getFood() {
        FoodDb item = getFoodDb();
        return getFood(item);
    }

    public static Food getFood(FoodDb item) {
        Food food = new Food();
        food.setExtid(item.getExtid());
        food.setCode(item.getCode());
        food.setName(item.getName());
        food.setCategory(item.getCategory());
        food.setSubcategory(item.getSubcategory());
        food.setDescription(item.getDescription());
        food.setNotes(item.getNotes());
        food.setCreatedAt(item.getCreatedAt());
        food.setUpdatedAt(item.getUpdatedAt());
        food.setDeletedAt(item.getDeletedAt());
        food.setActive(item.getActive());

        // Create simple mock objects for flavor, nutrition, serving
        if (item.getFlavor() != null) {
            food.setFlavor(getFlavor(getFlavorDb(item.getFlavor(), "Flavor_" + item.getFlavor())));
        }
        if (item.getNutrition() != null) {
            food.setNutrition(getNutrition(getNutritionDb(item.getNutrition(), "Nutrition_" + item.getNutrition())));
        }
        if (item.getServing() != null) {
            food.setServing(getServing(getServingDb(item.getServing(), "Serving_" + item.getServing())));
        }

        return food;
    }

    public static FoodDb getFoodDb() {
        return getFoodDb(null, null, null, null, null, null, null, null, null, null);
    }

    public static FoodDb getFoodDb(String code, String name) {
        return getFoodDb(code, name, null, null, null, null, null, null, null, null);
    }

    public static FoodDb getFoodDb(String code, String name, String category, String subcategory, String description,
                                   String flavor, String nutrition, String serving, String notes,
                                   String extid) {
        FoodDb item = new FoodDb();
        item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
        item.setCode(code != null ? code : getCodeRandom("FD_"));
        item.setName(name != null ? name : getNameRandom("Food_"));
        item.setCategory(category != null ? category : getNameRandom("Category_"));
        item.setSubcategory(subcategory != null ? subcategory : getNameRandom("SubCat_"));
        item.setDescription(description != null ? description : getDescriptionRandom("Food Description "));
        item.setNotes(notes != null ? notes : getDescriptionRandom("Food Notes "));
        item.setFlavor(flavor != null ? flavor : getCodeRandom("FLV_"));
        item.setNutrition(nutrition != null ? nutrition : getCodeRandom("NTR_"));
        item.setServing(serving != null ? serving : getCodeRandom("SRV_"));

        setBaseSyncFields(item);
        return item;
    }

    // ///////////////////////////////////////////////////////////////////
    // Flavor
    public static Flavor getFlavor() {
        FlavorDb item = getFlavorDb();
        return new FlavorMapper().toModel(item);
    }

    public static Flavor getFlavor(FlavorDb item) {
        return new FlavorMapper().toModel(item);
    }

    public static FlavorDb getFlavorDb() {
        return getFlavorDb(null, null, null, null, null, null, null, null, null, null);
    }

    public static FlavorDb getFlavorDb(String code, String name) {
        return getFlavorDb(code, name, null, null, null, null, null, null, null, null);
    }

    public static FlavorDb getFlavorDb(String code, String name, String description,
                                       String usage, Integer crunch, Integer punch, Integer sweet, Integer savory, String notes, String extid) {
        FlavorDb item = new FlavorDb();
        item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
        item.setCode(code != null ? code : getCodeRandom("FLV_"));
        item.setName(name != null ? name : getNameRandom("Flavor_"));
        item.setDescription(description != null ? description : getDescriptionRandom("Flavor Description "));
        item.setNotes(notes != null ? notes : getDescriptionRandom("Flavor Notes "));
        item.setHowtouse(usage != null ? usage : getVersionRandom("Usage"));
        item.setCrunch(crunch != null ? crunch : 3);
        item.setPunch(punch != null ? punch : 3);
        item.setSweet(sweet != null ? sweet : 3);
        item.setSavory(savory != null ? savory : 3);
        setBaseSyncFields(item);
        return item;
    }
}

