package com.seibel.basicspring.web.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseSalad {
    private List<SaladIngredientDetail> ingredients;
    private AggregatedNutrition totalNutrition;
    private AggregatedFlavor averageFlavor;

    @Data
    @Builder
    public static class SaladIngredientDetail {
        private String foodExtid;
        private String foodName;
        private Double quantity;
        private ResponseNutrition nutrition;
        private ResponseFlavor flavor;
    }

    @Data
    @Builder
    public static class AggregatedNutrition {
        private Integer totalCarbohydrate;
        private Integer totalFat;
        private Integer totalProtein;
        private Integer totalSugar;
    }

    @Data
    @Builder
    public static class AggregatedFlavor {
        private Integer averageCrunch;
        private Integer averagePunch;
        private Integer averageSweet;
        private Integer averageSavory;
    }
}
