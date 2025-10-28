package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
import com.seibel.basicspring.web.request.RequestCompanyCreate;
import com.seibel.basicspring.web.request.RequestCompanyUpdate;
import com.seibel.basicspring.web.response.ResponseCompany;
import com.seibel.basicspring.web.service.CompanyWebService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@Validated  // Enable method-level validation
public class CompanyController {

    private final CompanyWebService companyWebService;

    public CompanyController(CompanyWebService companyWebService) {
        this.companyWebService = companyWebService;
    }

    @GetMapping
    public List<ResponseCompany> getAll() throws DatabaseRetrievalFailureException {
        List<Company> result = companyWebService.getAll();
        return toResponse(result);
    }

    @GetMapping("/{extid}")
    public ResponseCompany getByExtid(@PathVariable String extid) {
        Company item = companyWebService.getByExtid(extid);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.");
        }
        return toResponse(item);
    }

    @PostMapping
    public ResponseCompany create(@Valid @RequestBody RequestCompanyCreate request) {
        Company item = Company.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Company result = companyWebService.create(item);
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
        Company result = companyWebService.update(extid, item);
        return toResponse(result);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        boolean deleted = companyWebService.delete(extid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found or already deleted.");
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one field must be provided for update.");
        }
    }
}
