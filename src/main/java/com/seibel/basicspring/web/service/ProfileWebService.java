package com.seibel.basicspring.web.service;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.database.database.db.mapper.ProfileMapper;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
import com.seibel.basicspring.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is primarily to deal with business logic.
 */
@Slf4j
@Service
public class ProfileWebService {

    ProfileService profileService;
    ProfileMapper profileMapper;

    public ProfileWebService(ProfileService profileService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    public Profile create(Profile item) {
        log.info("create(): {}", item);
        try {
            return profileService.create(item);
        } catch (Exception e) {
            log.error("create() failed: {}", e.getMessage(), e);
            return null;
        }
    }

    public Profile update(String extid, Profile item) {
        log.info("update(): extid={}, item={}", extid, item);
        try {
            return profileService.update(extid, item);
        } catch (Exception e) {
            log.error("update() failed for extid={}: {}", extid, e.getMessage(), e);
            return null;
        }
    }

    public Profile getByExtid(String extid) {
        log.info("getByExtid(): extid={}", extid);
        try {
            return profileService.findByExtid(extid);
        } catch (Exception e) {
            log.error("getByExtid() failed for extid={}: {}", extid, e.getMessage(), e);
            return null;
        }
    }

    public boolean delete(String extid) {
        log.info("delete(): extid={}", extid);
        try {
            return profileService.delete(extid);
        } catch (Exception e){
            log.error("delete() failed for extid={}: {}", extid, e.getMessage(), e);
            return false;
        }
    }

    public List<Profile> getAll() throws DatabaseRetrievalFailureException {
        log.info("getAll()");
        List<Profile> result = profileService.findAll();
        log.info("getAll() returned [{}] items", result.size());
        return result;
    }
}
