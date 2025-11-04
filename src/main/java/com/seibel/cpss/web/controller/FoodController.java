package com.seibel.cpss.web.controller;

import com.seibel.cpss.common.domain.Food;
import com.seibel.cpss.service.FoodService;
import com.seibel.cpss.service.FlavorService;
import com.seibel.cpss.service.NutritionService;
import com.seibel.cpss.service.ServingService;
import com.seibel.cpss.web.request.RequestFoodCreate;
import com.seibel.cpss.web.request.RequestFoodUpdate;
import com.seibel.cpss.web.response.ResponseFood;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@Validated
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final FlavorService flavorService;
    private final NutritionService nutritionService;
    private final ServingService servingService;
    private final FoodConverter converter;

    @PostMapping("/")
    public ResponseFood create(@RequestBody RequestFoodCreate request) {
        Food created = foodService.create(converter.toDomain(request));
        return converter.toResponse(created);
    }

    @PutMapping("/{extid}")
    public ResponseFood update(@PathVariable String extid, @RequestBody RequestFoodUpdate request) {
        converter.validateUpdateRequest(request);
        Food updated = foodService.update(extid, converter.toDomain(extid, request));
        return converter.toResponse(updated);
    }

    @GetMapping("/")
    public List<ResponseFood> getAll() {
        return converter.toResponse(foodService.findAll());
    }

    @GetMapping("/{extid}")
    public ResponseFood getByExtid(@PathVariable String extid) {
        return converter.toResponse(foodService.findByExtid(extid));
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        foodService.delete(extid);
        return ResponseEntity.noContent().build();
    }
}

// package-private converter
@org.springframework.stereotype.Component
@RequiredArgsConstructor
class FoodConverter {

    private final FlavorService flavorService;
    private final NutritionService nutritionService;
    private final ServingService servingService;
    private final FlavorConverter flavorConverter;
    private final NutritionConverter nutritionConverter;
    private final ServingConverter servingConverter;

    Food toDomain(RequestFoodCreate request) {
        return Food.builder()
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .subcategory(request.getSubcategory())
                .description(request.getDescription())
                .notes(request.getNotes())
                .flavor(request.getFlavorExtid() != null ? flavorService.findByExtid(request.getFlavorExtid()) : null)
                .nutrition(request.getNutritionExtid() != null ? nutritionService.findByExtid(request.getNutritionExtid()) : null)
                .serving(request.getServingExtid() != null ? servingService.findByExtid(request.getServingExtid()) : null)
                .build();
    }

    Food toDomain(String extid, RequestFoodUpdate request) {
        return Food.builder()
                .extid(extid)
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .subcategory(request.getSubcategory())
                .description(request.getDescription())
                .notes(request.getNotes())
                .flavor(request.getFlavorExtid() != null ? flavorService.findByExtid(request.getFlavorExtid()) : null)
                .nutrition(request.getNutritionExtid() != null ? nutritionService.findByExtid(request.getNutritionExtid()) : null)
                .serving(request.getServingExtid() != null ? servingService.findByExtid(request.getServingExtid()) : null)
                .build();
    }

    ResponseFood toResponse(Food item) {
        return ResponseFood.builder()
                .extid(item.getExtid())
                .code(item.getCode())
                .name(item.getName())
                .category(item.getCategory())
                .subcategory(item.getSubcategory())
                .description(item.getDescription())
                .notes(item.getNotes())
                .flavor(item.getFlavor() != null ? flavorConverter.toResponse(item.getFlavor()) : null)
                .nutrition(item.getNutrition() != null ? nutritionConverter.toResponse(item.getNutrition()) : null)
                .serving(item.getServing() != null ? servingConverter.toResponse(item.getServing()) : null)
                .build();
    }

    List<ResponseFood> toResponse(List<Food> items) {
        return items.stream().map(this::toResponse).toList();
    }

    void validateUpdateRequest(RequestFoodUpdate request) {
        if (request.getCode() == null &&
                request.getName() == null &&
                request.getCategory() == null &&
                request.getSubcategory() == null &&
                request.getDescription() == null &&
                request.getNotes() == null &&
                request.getFlavorExtid() == null &&
                request.getNutritionExtid() == null &&
                request.getServingExtid() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update.");
        }
    }
}
