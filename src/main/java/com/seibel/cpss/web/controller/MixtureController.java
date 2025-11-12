package com.seibel.cpss.web.controller;

import com.seibel.cpss.common.domain.Food;
import com.seibel.cpss.common.domain.Mixture;
import com.seibel.cpss.service.FoodService;
import com.seibel.cpss.service.MixtureService;
import com.seibel.cpss.web.request.RequestMixtureCreate;
import com.seibel.cpss.web.request.RequestMixtureUpdate;
import com.seibel.cpss.web.response.ResponseFlavor;
import com.seibel.cpss.web.response.ResponseNutrition;
import com.seibel.cpss.web.response.ResponseMixture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mixture")
@Validated
@RequiredArgsConstructor
public class MixtureController {

    private final FoodService foodService;
    private final MixtureService mixtureService;
    private final FlavorConverter flavorConverter;
    private final NutritionConverter nutritionConverter;
    private final MixtureConverter mixtureConverter;

    @PostMapping
    public ResponseEntity<ResponseMixture> create(@RequestBody RequestMixtureCreate request, Authentication authentication) {
        String userExtid = authentication.getName(); // Assuming username is the extid
        Mixture mixture = mixtureConverter.toDomain(request, userExtid);
        Mixture created = mixtureService.create(mixture);
        return ResponseEntity.status(HttpStatus.CREATED).body(mixtureConverter.toResponse(created));
    }

    @GetMapping("/{extid}")
    public ResponseMixture getByExtid(@PathVariable String extid) {
        Mixture mixture = mixtureService.findByExtid(extid);
        return mixtureConverter.toResponse(mixture);
    }

    @GetMapping("/user/{userExtid}")
    public List<ResponseMixture> getByUserExtid(@PathVariable String userExtid) {
        List<Mixture> mixtures = mixtureService.findByUserExtid(userExtid);
        return mixtures.stream()
                .map(mixtureConverter::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ResponseMixture> getAll() {
        List<Mixture> mixtures = mixtureService.findAll();
        return mixtures.stream()
                .map(mixtureConverter::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{extid}")
    public ResponseMixture update(@PathVariable String extid, @RequestBody RequestMixtureUpdate request) {
        Mixture mixture = mixtureConverter.toDomain(extid, request);
        Mixture updated = mixtureService.update(extid, mixture);
        return mixtureConverter.toResponse(updated);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        mixtureService.delete(extid);
        return ResponseEntity.noContent().build();
    }
}

// package-private converter
@org.springframework.stereotype.Component
@RequiredArgsConstructor
class MixtureConverter {

    private final FoodService foodService;

    Mixture toDomain(RequestMixtureCreate request, String userExtid) {
        Mixture mixture = Mixture.builder()
                .name(request.getName())
                .description(request.getDescription())
                .userExtid(userExtid)
                .build();

        // Convert ingredients
        if (request.getIngredients() != null && !request.getIngredients().isEmpty()) {
            List<com.seibel.cpss.common.domain.MixtureIngredient> ingredients = new ArrayList<>();
            for (RequestMixtureCreate.MixtureIngredientRequest ingredientReq : request.getIngredients()) {
                com.seibel.cpss.common.domain.MixtureIngredient ingredient =
                    com.seibel.cpss.common.domain.MixtureIngredient.builder()
                        .foodExtid(ingredientReq.getFoodExtid())
                        .quantity(1) // Always 1 for Phase 1
                        .unit("tablespoon") // Always tablespoon for Phase 1
                        .build();
                ingredients.add(ingredient);
            }
            mixture.setIngredients(ingredients);
        }

        return mixture;
    }

    Mixture toDomain(String extid, RequestMixtureUpdate request) {
        Mixture mixture = Mixture.builder()
                .extid(extid)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        // Convert ingredients
        if (request.getIngredients() != null && !request.getIngredients().isEmpty()) {
            List<com.seibel.cpss.common.domain.MixtureIngredient> ingredients = new ArrayList<>();
            for (RequestMixtureUpdate.MixtureIngredientRequest ingredientReq : request.getIngredients()) {
                com.seibel.cpss.common.domain.MixtureIngredient ingredient =
                    com.seibel.cpss.common.domain.MixtureIngredient.builder()
                        .foodExtid(ingredientReq.getFoodExtid())
                        .quantity(1) // Always 1 for Phase 1
                        .unit("tablespoon") // Always tablespoon for Phase 1
                        .build();
                ingredients.add(ingredient);
            }
            mixture.setIngredients(ingredients);
        }

        return mixture;
    }

    ResponseMixture toResponse(Mixture mixture) {
        List<ResponseMixture.MixtureIngredientResponse> ingredientResponses = new ArrayList<>();

        if (mixture.getIngredients() != null) {
            for (com.seibel.cpss.common.domain.MixtureIngredient ingredient : mixture.getIngredients()) {
                ResponseMixture.MixtureIngredientResponse ingredientResponse =
                    ResponseMixture.MixtureIngredientResponse.builder()
                        .extid(ingredient.getExtid())
                        .foodExtid(ingredient.getFoodExtid())
                        .foodName(ingredient.getFood() != null ? ingredient.getFood().getName() : null)
                        .quantity(ingredient.getQuantity())
                        .unit(ingredient.getUnit())
                        .build();
                ingredientResponses.add(ingredientResponse);
            }
        }

        return ResponseMixture.builder()
                .extid(mixture.getExtid())
                .name(mixture.getName())
                .description(mixture.getDescription())
                .userExtid(mixture.getUserExtid())
                .ingredients(ingredientResponses)
                .createdAt(mixture.getCreatedAt())
                .updatedAt(mixture.getUpdatedAt())
                .build();
    }
}
