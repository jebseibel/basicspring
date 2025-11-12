package com.seibel.cpss.web.controller;

import com.seibel.cpss.common.domain.Food;
import com.seibel.cpss.common.domain.Salad;
import com.seibel.cpss.service.FoodService;
import com.seibel.cpss.service.SaladService;
import com.seibel.cpss.web.request.RequestSaladBuild;
import com.seibel.cpss.web.request.RequestSaladCreate;
import com.seibel.cpss.web.request.RequestSaladUpdate;
import com.seibel.cpss.web.response.ResponseFlavor;
import com.seibel.cpss.web.response.ResponseNutrition;
import com.seibel.cpss.web.response.ResponseSalad;
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
@RequestMapping("/api/salad")
@Validated
@RequiredArgsConstructor
public class SaladController {

    private final FoodService foodService;
    private final SaladService saladService;
    private final FlavorConverter flavorConverter;
    private final NutritionConverter nutritionConverter;
    private final SaladConverter saladConverter;

    @PostMapping
    public ResponseEntity<ResponseSalad> create(@RequestBody RequestSaladCreate request, Authentication authentication) {
        String userExtid = authentication.getName(); // Assuming username is the extid
        Salad salad = saladConverter.toDomain(request, userExtid);
        Salad created = saladService.create(salad);
        return ResponseEntity.status(HttpStatus.CREATED).body(saladConverter.toResponse(created));
    }

    @GetMapping("/{extid}")
    public ResponseSalad getByExtid(@PathVariable String extid) {
        Salad salad = saladService.findByExtid(extid);
        return saladConverter.toResponse(salad);
    }

    @GetMapping("/user/{userExtid}")
    public List<ResponseSalad> getByUserExtid(@PathVariable String userExtid) {
        List<Salad> salads = saladService.findByUserExtid(userExtid);
        return salads.stream()
                .map(saladConverter::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ResponseSalad> getAll() {
        List<Salad> salads = saladService.findAll();
        return salads.stream()
                .map(saladConverter::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{extid}")
    public ResponseSalad update(@PathVariable String extid, @RequestBody RequestSaladUpdate request) {
        Salad salad = saladConverter.toDomain(extid, request);
        Salad updated = saladService.update(extid, salad);
        return saladConverter.toResponse(updated);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        saladService.delete(extid);
        return ResponseEntity.noContent().build();
    }
}

// package-private converter
@org.springframework.stereotype.Component
@RequiredArgsConstructor
class SaladConverter {

    private final FoodService foodService;

    Salad toDomain(RequestSaladCreate request, String userExtid) {
        return Salad.builder()
                .name(request.getName())
                .description(request.getDescription())
                .userExtid(userExtid)
                .build();
    }

    Salad toDomain(String extid, RequestSaladUpdate request) {
        return Salad.builder()
                .extid(extid)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    ResponseSalad toResponse(Salad salad) {
        return ResponseSalad.builder()
                .extid(salad.getExtid())
                .name(salad.getName())
                .description(salad.getDescription())
                .userExtid(salad.getUserExtid())
                .createdAt(salad.getCreatedAt())
                .updatedAt(salad.getUpdatedAt())
                .build();
    }
}
