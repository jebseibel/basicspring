package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.exceptions.ValidationException;
import com.seibel.basicspring.service.ProfileService;
import com.seibel.basicspring.web.request.RequestProfileCreate;
import com.seibel.basicspring.web.request.RequestProfileUpdate;
import com.seibel.basicspring.web.response.ResponseProfile;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@Validated
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<ResponseProfile> getAll() {
        List<Profile> result = profileService.findAll();
        return toResponse(result);
    }

    @GetMapping("/{extid}")
    public ResponseProfile getByExtid(@PathVariable String extid) {
        Profile item = profileService.findByExtid(extid);
        return toResponse(item);
    }

    @PostMapping
    public ResponseProfile create(@Valid @RequestBody RequestProfileCreate request) {
        Profile item = Profile.builder()
                .nickname(request.getNickname())
                .fullname(request.getFullname())
                .build();
        Profile result = profileService.create(item);
        return toResponse(result);
    }

    @PutMapping("/{extid}")
    public ResponseProfile update(@PathVariable String extid, @Valid @RequestBody RequestProfileUpdate request) {
        validateUpdateRequest(request);

        Profile item = Profile.builder()
                .nickname(request.getNickname())
                .fullname(request.getFullname())
                .build();
        Profile result = profileService.update(extid, item);
        return toResponse(result);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        boolean deleted = profileService.delete(extid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseProfile toResponse(Profile itemDb) {
        return ResponseProfile.builder()
                .extid(itemDb.getExtid())
                .nickname(itemDb.getNickname())
                .fullname(itemDb.getFullname())
                .build();
    }

    private List<ResponseProfile> toResponse(List<Profile> items) {
        return items.stream().map(this::toResponse).toList();
    }

    private void validateUpdateRequest(RequestProfileUpdate request) {
        if (request.getNickname() == null &&
                request.getFullname() == null) {
            throw new ValidationException("At least one field must be provided for update.");
        }
    }
}