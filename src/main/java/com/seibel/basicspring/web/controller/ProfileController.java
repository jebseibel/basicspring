package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.common.exceptions.ValidationException;
import com.seibel.basicspring.service.ProfileService;
import com.seibel.basicspring.web.request.RequestProfileCreate;
import com.seibel.basicspring.web.request.RequestProfileUpdate;
import com.seibel.basicspring.web.response.ResponseProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/profile")
@Validated
@Tag(name = "Profile", description = "Profile CRUD endpoints")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileConverter converter = new ProfileConverter();

    @GetMapping
    @Operation(summary = "List profiles (paginated)")
    public Page<ResponseProfile> getAll(
            @ParameterObject @PageableDefault(size = 20, sort = "nickname") Pageable pageable,
            @RequestParam(required = false) ActiveEnum active
    ) {
        return profileService.findAll(pageable, active).map(converter::toResponse);
    }

    @GetMapping("/{extid}")
    @Operation(summary = "Get profile by extid")
    public ResponseProfile getByExtid(@PathVariable String extid) {
        return converter.toResponse(profileService.findByExtid(extid));
    }

    @PostMapping
    @Operation(summary = "Create profile")
    public ResponseEntity<ResponseProfile> create(@Valid @RequestBody RequestProfileCreate request) {
        Profile created = profileService.create(converter.toDomain(request));
        URI location = URI.create("/api/profile/" + created.getExtid());
        return ResponseEntity.created(location).body(converter.toResponse(created));
    }

    @PutMapping("/{extid}")
    @Operation(summary = "Update profile (full or partial)")
    public ResponseProfile update(@PathVariable String extid, @Valid @RequestBody RequestProfileUpdate request) {
        converter.validateUpdateRequest(request);
        Profile updated = profileService.update(extid, converter.toDomain(request));
        return converter.toResponse(updated);
    }

    @PatchMapping("/{extid}")
    @Operation(summary = "Patch profile (partial update)")
    public ResponseProfile patch(@PathVariable String extid, @Valid @RequestBody RequestProfileUpdate request) {
        return update(extid, request);
    }

    @DeleteMapping("/{extid}")
    @Operation(summary = "Delete profile (soft-delete)")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        boolean deleted = profileService.delete(extid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

class ProfileConverter {

    Profile toDomain(RequestProfileCreate request) {
        return Profile.builder()
                .nickname(request.getNickname())
                .fullname(request.getFullname())
                .build();
    }

    Profile toDomain(RequestProfileUpdate request) {
        return Profile.builder()
                .nickname(request.getNickname())
                .fullname(request.getFullname())
                .build();
    }

    ResponseProfile toResponse(Profile item) {
        return ResponseProfile.builder()
                .extid(item.getExtid())
                .nickname(item.getNickname())
                .fullname(item.getFullname())
                .build();
    }

    List<ResponseProfile> toResponse(List<Profile> items) {
        return items.stream().map(this::toResponse).toList();
    }

    void validateUpdateRequest(RequestProfileUpdate request) {
        if (request.getNickname() == null &&
                request.getFullname() == null) {
            throw new ValidationException("At least one field must be provided for update.");
        }
    }
}