package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Nutrition;
import com.seibel.basicspring.service.NutritionService;
import com.seibel.basicspring.web.request.RequestNutritionCreate;
import com.seibel.basicspring.web.request.RequestNutritionUpdate;
import com.seibel.basicspring.web.response.ResponseNutrition;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
@Validated
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionService service;
    private final NutritionConverter converter;

    @GetMapping("/")
    public List<ResponseNutrition> getAll() {
        return converter.toResponse(service.findAll());
    }

    @GetMapping("/{extid}")
    public ResponseNutrition getByExtid(@PathVariable String extid) {
        return converter.toResponse(service.findByExtid(extid));
    }

    @PostMapping("/")
    public ResponseNutrition create(@Valid @RequestBody RequestNutritionCreate request) {
        Nutrition created = service.create(converter.toDomain(request));
        return converter.toResponse(created);
    }

    @PutMapping("/{extid}")
    public ResponseNutrition update(@PathVariable String extid,
                                    @Valid @RequestBody RequestNutritionUpdate request) {
        converter.validateUpdateRequest(request);
        Nutrition updated = service.update(extid, converter.toDomain(extid, request));
        return converter.toResponse(updated);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        service.delete(extid);
        return ResponseEntity.noContent().build();
    }
}

// package-private converter class
@Component
class NutritionConverter {

    public ResponseNutrition toResponse(Nutrition domain) {
        return ResponseNutrition.builder()
                .extid(domain.getExtid())
                .code(domain.getCode())
                .name(domain.getName())
                .category(domain.getCategory())
                .subcategory(domain.getSubcategory())
                .description(domain.getDescription())
                .carbohydrate(domain.getCarbohydrate())
                .fat(domain.getFat())
                .protein(domain.getProtein())
                .sugar(domain.getSugar())
                .build();
    }

    public List<ResponseNutrition> toResponse(List<Nutrition> domains) {
        return domains.stream().map(this::toResponse).toList();
    }

    public Nutrition toDomain(RequestNutritionCreate request) {
        return Nutrition.builder()
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .subcategory(request.getSubcategory())
                .description(request.getDescription())
                .carbohydrate(request.getCarbohydrate())
                .fat(request.getFat())
                .protein(request.getProtein())
                .sugar(request.getSugar())
                .build();
    }

    public Nutrition toDomain(String extid, RequestNutritionUpdate request) {
        return Nutrition.builder()
                .extid(extid)
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .subcategory(request.getSubcategory())
                .description(request.getDescription())
                .carbohydrate(request.getCarbohydrate())
                .fat(request.getFat())
                .protein(request.getProtein())
                .sugar(request.getSugar())
                .build();
    }

    public void validateUpdateRequest(RequestNutritionUpdate request) {
        if (request.getCode() == null && request.getName() == null && request.getCategory() == null &&
                request.getSubcategory() == null && request.getDescription() == null &&
                request.getCarbohydrate() == null && request.getFat() == null &&
                request.getProtein() == null && request.getSugar() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update.");
        }
    }
}
