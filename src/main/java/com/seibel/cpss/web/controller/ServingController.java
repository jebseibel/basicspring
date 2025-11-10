package com.seibel.cpss.web.controller;

import com.seibel.cpss.common.domain.Serving;
import com.seibel.cpss.service.ServingService;
import com.seibel.cpss.web.request.RequestServingCreate;
import com.seibel.cpss.web.request.RequestServingUpdate;
import com.seibel.cpss.web.response.ResponseServing;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serving")
@Validated
@RequiredArgsConstructor
public class ServingController {

    private final ServingService service;
    private final ServingConverter converter;

    @GetMapping
    public List<ResponseServing> getAll() {
        return converter.toResponse(service.findAll());
    }

    @GetMapping("/{extid}")
    public ResponseServing getByExtid(@PathVariable String extid) {
        return converter.toResponse(service.findByExtid(extid));
    }

    @PostMapping
    public ResponseServing create(@Valid @RequestBody RequestServingCreate request) {
        Serving created = service.create(converter.toDomain(request));
        return converter.toResponse(created);
    }

    @PutMapping("/{extid}")
    public ResponseServing update(@PathVariable String extid,
                                   @Valid @RequestBody RequestServingUpdate request) {
        converter.validateUpdateRequest(request);
        Serving updated = service.update(extid, converter.toDomain(extid, request));
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
class ServingConverter {

    public ResponseServing toResponse(Serving domain) {
        return ResponseServing.builder()
                .extid(domain.getExtid())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .cup(domain.getCup())
                .quarter(domain.getQuarter())
                .tablespoon(domain.getTablespoon())
                .teaspoon(domain.getTeaspoon())
                .gram(domain.getGram())
                .build();
    }

    public List<ResponseServing> toResponse(List<Serving> domains) {
        return domains.stream().map(this::toResponse).toList();
    }

    public Serving toDomain(RequestServingCreate request) {
        return Serving.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .cup(request.getCup())
                .quarter(request.getQuarter())
                .tablespoon(request.getTablespoon())
                .teaspoon(request.getTeaspoon())
                .gram(request.getGram())
                .build();
    }

    public Serving toDomain(String extid, RequestServingUpdate request) {
        return Serving.builder()
                .extid(extid)
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .cup(request.getCup())
                .quarter(request.getQuarter())
                .tablespoon(request.getTablespoon())
                .teaspoon(request.getTeaspoon())
                .gram(request.getGram())
                .build();
    }

    public void validateUpdateRequest(RequestServingUpdate request) {
        if (request.getCode() == null && request.getName() == null &&
                request.getDescription() == null &&
                request.getCup() == null && request.getQuarter() == null &&
                request.getTablespoon() == null && request.getTeaspoon() == null &&
                request.getGram() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update.");
        }
    }
}
