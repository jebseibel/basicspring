package com.seibel.cpss.web.controller;

import com.seibel.cpss.common.domain.Food;
import com.seibel.cpss.service.FoodService;
import com.seibel.cpss.web.request.RequestSaladBuild;
import com.seibel.cpss.web.response.ResponseFlavor;
import com.seibel.cpss.web.response.ResponseNutrition;
import com.seibel.cpss.web.response.ResponseSalad;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/salad")
@Validated
@RequiredArgsConstructor
public class SaladController {

    private final FoodService foodService;
    private final FlavorConverter flavorConverter;
    private final NutritionConverter nutritionConverter;

    @PostMapping("/build")
    public ResponseSalad buildSalad(@RequestBody RequestSaladBuild request) {
        List<ResponseSalad.SaladIngredientDetail> ingredientDetails = new ArrayList<>();

        int totalCarbs = 0;
        int totalFat = 0;
        int totalProtein = 0;
        int totalSugar = 0;

        int sumCrunch = 0;
        int sumPunch = 0;
        int sumSweet = 0;
        int sumSavory = 0;
        int flavorCount = 0;

        for (RequestSaladBuild.SaladIngredient ingredient : request.getIngredients()) {
            Food food = foodService.findByExtid(ingredient.getFoodExtid());

            ResponseNutrition nutrition = food.getNutrition() != null
                ? nutritionConverter.toResponse(food.getNutrition())
                : null;

            ResponseFlavor flavor = food.getFlavor() != null
                ? flavorConverter.toResponse(food.getFlavor())
                : null;

            // Aggregate nutrition based on quantity
            if (nutrition != null) {
                totalCarbs += (int) (nutrition.getCarbohydrate() * ingredient.getQuantity());
                totalFat += (int) (nutrition.getFat() * ingredient.getQuantity());
                totalProtein += (int) (nutrition.getProtein() * ingredient.getQuantity());
                totalSugar += (int) (nutrition.getSugar() * ingredient.getQuantity());
            }

            // Aggregate flavor (will be averaged later)
            if (flavor != null) {
                sumCrunch += flavor.getCrunch();
                sumPunch += flavor.getPunch();
                sumSweet += flavor.getSweet();
                sumSavory += flavor.getSavory();
                flavorCount++;
            }

            ingredientDetails.add(ResponseSalad.SaladIngredientDetail.builder()
                    .foodExtid(food.getExtid())
                    .foodName(food.getName())
                    .quantity(ingredient.getQuantity())
                    .nutrition(nutrition)
                    .flavor(flavor)
                    .build());
        }

        // Calculate average flavor
        ResponseSalad.AggregatedFlavor averageFlavor = null;
        if (flavorCount > 0) {
            averageFlavor = ResponseSalad.AggregatedFlavor.builder()
                    .averageCrunch(sumCrunch / flavorCount)
                    .averagePunch(sumPunch / flavorCount)
                    .averageSweet(sumSweet / flavorCount)
                    .averageSavory(sumSavory / flavorCount)
                    .build();
        }

        ResponseSalad.AggregatedNutrition totalNutrition = ResponseSalad.AggregatedNutrition.builder()
                .totalCarbohydrate(totalCarbs)
                .totalFat(totalFat)
                .totalProtein(totalProtein)
                .totalSugar(totalSugar)
                .build();

        return ResponseSalad.builder()
                .ingredients(ingredientDetails)
                .totalNutrition(totalNutrition)
                .averageFlavor(averageFlavor)
                .build();
    }
}
