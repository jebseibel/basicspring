package com.seibel.basicspring.web.controller;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
import com.seibel.basicspring.web.request.RequestProfileCreate;
import com.seibel.basicspring.web.request.RequestProfileUpdate;
import com.seibel.basicspring.web.response.ResponseProfile;
import com.seibel.basicspring.web.service.ProfileWebService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class ProfileController {

    private final ProfileWebService profileWebService;

    public ProfileController(ProfileWebService profileWebService) {
        this.profileWebService = profileWebService;
    }

    @GetMapping
    public List<ResponseProfile> getAll() throws DatabaseRetrievalFailureException {
        List<Profile> result = profileWebService.getAll();
        return toResponse(result);
    }

    @GetMapping("/{extid}")
    public ResponseProfile getByExtid(@PathVariable String extid) {
        Profile item = profileWebService.getByExtid(extid);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found.");
        }
        return toResponse(item);
    }

    @PostMapping
    public ResponseProfile create(@Valid @RequestBody RequestProfileCreate request) {
        Profile item = Profile.builder()
                .nickname(request.getNickname())
                .fullname(request.getFullname())
                .build();
        Profile result = profileWebService.create(item);
        return toResponse(result);
    }

    @PutMapping("/{extid}")
    public ResponseProfile update(@PathVariable String extid, @Valid @RequestBody RequestProfileUpdate request) {
        validateUpdateRequest(request);

        Profile item = Profile.builder()
                .nickname(request.getNickname())
                .fullname(request.getFullname())
                .build();
        Profile result = profileWebService.update(extid, item);
        return toResponse(result);
    }

    @DeleteMapping("/{extid}")
    public ResponseEntity<Void> delete(@PathVariable String extid) {
        boolean deleted = profileWebService.delete(extid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found or already deleted.");
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one field must be provided for update.");
        }
    }
}
