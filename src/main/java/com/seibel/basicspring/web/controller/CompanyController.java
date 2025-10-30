package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.common.exceptions.ValidationException;
import com.seibel.basicspring.service.CompanyService;
import com.seibel.basicspring.web.request.RequestCompanyCreate;
import com.seibel.basicspring.web.request.RequestCompanyUpdate;
import com.seibel.basicspring.web.response.ResponseCompany;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@Validated
@Tag(name = "Company", description = "Company CRUD endpoints")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @Operation(summary = "List companies (paginated)")
    public Page<ResponseCompany> getAll(
            @ParameterObject @PageableDefault(size = 20, sort = "name") Pageable pageable,
            @RequestParam(required = false) ActiveEnum active
    ) {
        return companyService.findAll(pageable, active).map(this::toResponse);
    }

    @GetMapping("/{extid}")
    @Operation(summary = "Get company by extid")
    public ResponseCompany getByExtid(@PathVariable String extid) {
        Company item = companyService.findByExtid(extid);
        return toResponse(item);
    }

    @PostMapping
    @Operation(summary = "Create company")
    public ResponseEntity<ResponseCompany> create(@Valid @RequestBody RequestCompanyCreate request) {
        Company item = Company.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Company result = companyService.create(item);
        URI location = URI.create("/api/company/" + result.getExtid());
        return ResponseEntity.created(location).body(toResponse(result));
    }

    @PutMapping("/{extid}")
    @Operation(summary = "Update company (full or partial)")
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

    @PatchMapping("/{extid}")
    @Operation(summary = "Patch company (partial update)")
    public ResponseCompany patch(@PathVariable String extid, @Valid @RequestBody RequestCompanyUpdate request) {
        return update(extid, request);
    }

    @DeleteMapping("/{extid}")
    @Operation(summary = "Delete company (soft-delete)")
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