package com.seibel.cpss.web.controller;

import com.seibel.cpss.common.domain.Flavor;
import com.seibel.cpss.service.FlavorService;
import com.seibel.cpss.web.request.RequestFlavorCreate;
import com.seibel.cpss.web.request.RequestFlavorUpdate;
import com.seibel.cpss.web.response.ResponseFlavor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flavor")
@Validated
@RequiredArgsConstructor
public class FlavorController {

    private final FlavorService flavorService;
    private final FlavorConverter converter;

    @PostMapping("/")
    public ResponseFlavor create(@RequestBody RequestFlavorCreate request) {
        Flavor created = flavorService.create(converter.toDomain(request));
        return converter.toResponse(created);
    }

    @PutMapping("/{extid}")
    public ResponseFlavor update(@PathVariable String extid, @RequestBody RequestFlavorUpdate request) {
        converter.validateUpdateRequest(request);
        Flavor updated = flavorService.update(extid, converter.toDomain(extid, request));
        return converter.toResponse(updated);
    }

    @GetMapping("/")
    public List<ResponseFlavor> getAll() {
        return converter.toResponse(flavorService.findAll());
    }

    @GetMapping("/{extid}")
    public ResponseFlavor getByExtid(@PathVariable String extid) {
        return converter.toResponse(flavorService.findByExtid(extid));
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        flavorService.delete(extid);
        return ResponseEntity.noContent().build();
    }
}

@org.springframework.stereotype.Component
class FlavorConverter {

    Flavor toDomain(RequestFlavorCreate request) {
        return Flavor.builder()
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .subcategory(request.getSubcategory())
                .description(request.getDescription())
                .howtouse(request.getHowtouse())
                .crunch(request.getCrunch())
                .punch(request.getPunch())
                .sweet(request.getSweet())
                .savory(request.getSavory())
                .build();
    }

    Flavor toDomain(String extid, RequestFlavorUpdate request) {
        return Flavor.builder()
                .extid(extid)
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .subcategory(request.getSubcategory())
                .description(request.getDescription())
                .howtouse(request.getHowtouse())
                .crunch(request.getCrunch())
                .punch(request.getPunch())
                .sweet(request.getSweet())
                .savory(request.getSavory())
                .build();
    }

    ResponseFlavor toResponse(Flavor item) {
        return ResponseFlavor.builder()
                .extid(item.getExtid())
                .code(item.getCode())
                .name(item.getName())
                .category(item.getCategory())
                .subcategory(item.getSubcategory())
                .description(item.getDescription())
                .howtouse(item.getHowtouse())
                .crunch(item.getCrunch())
                .punch(item.getPunch())
                .sweet(item.getSweet())
                .savory(item.getSavory())
                .build();
    }

    List<ResponseFlavor> toResponse(List<Flavor> items) {
        return items.stream().map(this::toResponse).toList();
    }

    void validateUpdateRequest(RequestFlavorUpdate request) {
        if (request.getCode() == null &&
                request.getName() == null &&
                request.getCategory() == null &&
                request.getSubcategory() == null &&
                request.getDescription() == null &&
                request.getHowtouse() == null &&
                request.getCrunch() == null &&
                request.getPunch() == null &&
                request.getSweet() == null &&
                request.getSavory() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update.");
        }
    }
}
