package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.common.exceptions.ResourceNotFoundException;
import com.seibel.basicspring.common.exceptions.ServiceException;
import com.seibel.basicspring.database.db.service.ProfileDbService;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ProfileService extends BaseService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("nickname", "fullname", "createdAt", "updatedAt");

    private final ProfileDbService profileDbService;

    public ProfileService(ProfileDbService profileDbService) {
        this.profileDbService = profileDbService;
        this.thisName = "Profile";
    }

    @Transactional
    public Profile create(Profile item) {
        requireNonNull(item, "Profile");
        log.info("create(): {}", item);

        try {
            return profileDbService.create(item.getNickname(), item.getFullname());
        } catch (DatabaseFailureException e) {
            log.error("Failed to create profile: {}", item.getNickname(), e);
            throw new ServiceException("Unable to create profile", e);
        }
    }

    @Transactional
    public Profile update(String extid, Profile item) {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Profile");
        log.info("update(): extid={}, origin={}, {}", extid, item);

        try {
            Profile updated = profileDbService.update(extid, item.getNickname(), item.getFullname());
            if (updated == null) {
                throw new ResourceNotFoundException("Profile", extid);
            }
            return updated;
        } catch (DatabaseFailureException e) {
            log.error("Failed to update profile: {}", extid, e);
            throw new ServiceException("Unable to update profile", e);
        }
    }

    @Transactional
    public boolean delete(String extid) {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);

        try {
            return profileDbService.delete(extid);
        } catch (DatabaseFailureException e) {
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
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve profile: {}", extid, e);
            throw new ServiceException("Unable to retrieve profile", e);
        }
    }

    public List<Profile> findAll() {
        log.info("findAll()");

        try {
            return profileDbService.findAll();
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve all profiles", e);
            throw new ServiceException("Unable to retrieve profiles", e);
        }
    }

    public Page<Profile> findAll(Pageable pageable, ActiveEnum activeEnum) {
        Pageable safe = enforceCapsAndWhitelist(pageable);
        log.info("findAll(pageable): page={}, size={}, sort={}", safe.getPageNumber(), safe.getPageSize(), safe.getSort());
        try {
            if (activeEnum == null) {
                return profileDbService.findAll(safe);
            }
            return profileDbService.findByActive(activeEnum, safe);
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve profiles (paged)", e);
            throw new ServiceException("Unable to retrieve profiles", e);
        }
    }

    public List<Profile> findByActive(ActiveEnum activeEnum) {
        requireNonNull(activeEnum, "activeEnum");
        log.info("findByActive(): activeEnum={}", activeEnum);

        try {
            return profileDbService.findByActive(activeEnum);
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve profiles by active status: {}", activeEnum, e);
            throw new ServiceException("Unable to retrieve profiles", e);
        }
    }

    private Pageable enforceCapsAndWhitelist(Pageable pageable) {
        int size = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
        Sort safeSort = pageable.getSort().isUnsorted() ? Sort.unsorted() :
                pageable.getSort().stream()
                        .filter(order -> ALLOWED_SORT_FIELDS.contains(order.getProperty()))
                        .collect(() -> Sort.unsorted(),
                                (acc, order) -> acc.and(Sort.by(order.getDirection(), order.getProperty())),
                                Sort::and);
        if (safeSort.isUnsorted() && pageable.getSort().isSorted()) {
            // If client requested only invalid fields, fall back to nickname ASC
            safeSort = Sort.by(Sort.Order.asc("nickname"));
        }
        return PageRequest.of(pageable.getPageNumber(), size, safeSort);
    }
}