package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.common.exceptions.ResourceNotFoundException;
import com.seibel.basicspring.common.exceptions.ServiceException;
import com.seibel.basicspring.database.database.db.service.ProfileDbService;
import com.seibel.basicspring.database.database.exceptions.DatabaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProfileService extends BaseService {

    private final ProfileDbService profileDbService;

    public ProfileService(ProfileDbService profileDbService) {
        this.profileDbService = profileDbService;
        this.thisName = "Profile";
    }

    public Profile create(Profile item) {
        requireNonNull(item, "Profile");
        log.info("create(): {}", item);

        try {
            return profileDbService.create(item.getNickname(), item.getFullname());
        } catch (DatabaseException e) {
            log.error("Failed to create profile: {}", item.getNickname(), e);
            throw new ServiceException("Unable to create profile", e);
        }
    }

    public Profile update(String extid, Profile item) {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Profile");
        log.info("update(): extid={}, origin={}, {}", extid, item);

        try {
            return profileDbService.update(extid, item.getNickname(), item.getFullname());
        } catch (DatabaseException e) {
            log.error("Failed to update profile: {}", extid, e);
            throw new ServiceException("Unable to update profile", e);
        }
    }

    public boolean delete(String extid) {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);

        try {
            return profileDbService.delete(extid);
        } catch (DatabaseException e) {
            log.error("Failed to delete profile: {}", extid, e);
            throw new ServiceException("Unable to delete profile", e);
        }
    }

    public Profile findByExtid(String extid) {
        requireNonBlank(extid, "extid");
        log.info("findByExtid(): extid={}", extid);

        try {
            Profile profile = profileDbService.findByExtid(extid);
            if (profile == null) {
                throw new ResourceNotFoundException("Profile", extid);
            }
            return profile;
        } catch (DatabaseException e) {
            log.error("Failed to retrieve profile: {}", extid, e);
            throw new ServiceException("Unable to retrieve profile", e);
        }
    }

    public List<Profile> findAll() {
        log.info("findAll()");

        try {
            return profileDbService.findAll();
        } catch (DatabaseException e) {
            log.error("Failed to retrieve all profiles", e);
            throw new ServiceException("Unable to retrieve profiles", e);
        }
    }

    public List<Profile> findByActive(ActiveEnum activeEnum) {
        requireNonNull(activeEnum, "activeEnum");
        log.info("findByActive(): activeEnum={}", activeEnum);

        try {
            return profileDbService.findByActive(activeEnum);
        } catch (DatabaseException e) {
            log.error("Failed to retrieve profiles by active status: {}", activeEnum, e);
            throw new ServiceException("Unable to retrieve profiles", e);
        }
    }
}