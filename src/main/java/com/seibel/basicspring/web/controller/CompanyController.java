package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.exceptions.ValidationException;
import com.seibel.basicspring.service.CompanyService;
import com.seibel.basicspring.web.request.RequestCompanyCreate;
import com.seibel.basicspring.web.request.RequestCompanyUpdate;
import com.seibel.basicspring.web.response.ResponseCompany;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@Validated
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<ResponseCompany> getAll() {
        List<Company> result = companyService.findAll();
        return toResponse(result);
    }

    @GetMapping("/{extid}")
    public ResponseCompany getByExtid(@PathVariable String extid) {
        Company item = companyService.findByExtid(extid);
        return toResponse(item);
    }

    @PostMapping
    public ResponseCompany create(@Valid @RequestBody RequestCompanyCreate request) {
        Company item = Company.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Company result = companyService.create(item);
        return toResponse(result);
    }

    @PutMapping("/{extid}")
    public ResponseCompany update(@PathVariable String extid, @Valid @RequestBody RequestCompanyUpdate request) {
        validateUpdateRequest(request);

        Company item = Company.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Company result = companyService.update(extid, item);
        return toResponse(result);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        boolean deleted = companyService.delete(extid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseCompany toResponse(Company itemDb) {
        return ResponseCompany.builder()
                .extid(itemDb.getExtid())
                .code(itemDb.getCode())
                .name(itemDb.getName())
                .description(itemDb.getDescription())
                .build();
    }

    private List<ResponseCompany> toResponse(List<Company> items) {
        return items.stream().map(this::toResponse).toList();
    }

    private void validateUpdateRequest(RequestCompanyUpdate request) {
        if (request.getCode() == null &&
                request.getName() == null &&
                request.getDescription() == null) {
            throw new ValidationException("At least one field must be provided for update.");
        }
    }
}