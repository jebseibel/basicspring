package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.common.exceptions.ResourceNotFoundException;
import com.seibel.basicspring.common.exceptions.ServiceException;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.db.service.CompanyDbService;
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
public class CompanyService extends BaseService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "code", "createdAt", "updatedAt");

    private final CompanyDbService companyDbService;

    public CompanyService(CompanyDbService companyDbService) {
        this.companyDbService = companyDbService;
        this.thisName = "Company";
    }

    @Transactional
    public Company create(Company item) {
        requireNonNull(item, "Company");
        log.info("create(): {}", item);

        try {
            return companyDbService.create(item.getCode(), item.getName(), item.getDescription());
        } catch (DatabaseFailureException e) {
            log.error("Failed to create company: {}", item.getCode(), e);
            throw new ServiceException("Unable to create company", e);
        }
    }

    @Transactional
    public Company update(String extid, Company item) {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Company");
        log.info("update(): extid={}, {}", extid, item);

        try {
            Company updated = companyDbService.update(extid, item.getCode(), item.getName(), item.getDescription());
            if (updated == null) {
                throw new ResourceNotFoundException("Company", extid);
            }
            return updated;
        } catch (DatabaseFailureException e) {
            log.error("Failed to update company: {}", extid, e);
            throw new ServiceException("Unable to update company", e);
        }
    }

    @Transactional
    public boolean delete(String extid) {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);

        try {
            return companyDbService.delete(extid);
        } catch (DatabaseFailureException e) {
            log.error("Failed to delete company: {}", extid, e);
            throw new ServiceException("Unable to delete company", e);
        }
    }

    public Company findByExtid(String extid) {
        requireNonBlank(extid, "extid");
        log.info("findByExtid(): extid={}", extid);

        try {
            Company company = companyDbService.findByExtid(extid);
            if (company == null) {
                throw new ResourceNotFoundException("Company", extid);
            }
            return company;
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve company: {}", extid, e);
            throw new ServiceException("Unable to retrieve company", e);
        }
    }

    public List<Company> findAll() {
        log.info("findAll()");

        try {
            return companyDbService.findAll();
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve all companies", e);
            throw new ServiceException("Unable to retrieve companies", e);
        }
    }

    public Page<Company> findAll(Pageable pageable, ActiveEnum activeEnum) {
        Pageable safe = enforceCapsAndWhitelist(pageable);
        log.info("findAll(pageable): page={}, size={}, sort={}", safe.getPageNumber(), safe.getPageSize(), safe.getSort());
        try {
            if (activeEnum == null) {
                return companyDbService.findAll(safe);
            }
            return companyDbService.findByActive(activeEnum, safe);
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve companies (paged)", e);
            throw new ServiceException("Unable to retrieve companies", e);
        }
    }

    public List<Company> findByActive(ActiveEnum activeEnum) {
        requireNonNull(activeEnum, "activeEnum");
        log.info("findByActive(): activeEnum={}", activeEnum);

        try {
            return companyDbService.findByActive(activeEnum);
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve companies by active status: {}", activeEnum, e);
            throw new ServiceException("Unable to retrieve companies", e);
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
            // If client requested only invalid fields, fall back to name ASC
            safeSort = Sort.by(Sort.Order.asc("name"));
        }
        return PageRequest.of(pageable.getPageNumber(), size, safeSort);
    }
}