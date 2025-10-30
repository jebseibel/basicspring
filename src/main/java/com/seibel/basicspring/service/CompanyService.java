package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.common.exceptions.ResourceNotFoundException;
import com.seibel.basicspring.common.exceptions.ServiceException;
import com.seibel.basicspring.database.database.db.service.CompanyDbService;
import com.seibel.basicspring.database.database.exception.DatabaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyService extends BaseService {

    private final CompanyDbService companyDbService;

    public CompanyService(CompanyDbService companyDbService) {
        this.companyDbService = companyDbService;
        this.thisName = "Company";
    }

    public Company create(Company item) {
        requireNonNull(item, "Company");
        log.info("create(): {}", item);

        try {
            return companyDbService.create(item.getCode(), item.getName(), item.getDescription());
        } catch (DatabaseException e) {
            log.error("Failed to create company: {}", item.getCode(), e);
            throw new ServiceException("Unable to create company", e);
        }
    }

    public Company update(String extid, Company item) {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Company");
        log.info("update(): extid={}, {}", extid, item);

        try {
            return companyDbService.update(extid, item.getCode(), item.getName(), item.getDescription());
        } catch (DatabaseException e) {
            log.error("Failed to update company: {}", extid, e);
            throw new ServiceException("Unable to update company", e);
        }
    }

    public boolean delete(String extid) {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);

        try {
            return companyDbService.delete(extid);
        } catch (DatabaseException e) {
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
        } catch (DatabaseException e) {
            log.error("Failed to retrieve company: {}", extid, e);
            throw new ServiceException("Unable to retrieve company", e);
        }
    }

    public List<Company> findAll() {
        log.info("findAll()");

        try {
            return companyDbService.findAll();
        } catch (DatabaseException e) {
            log.error("Failed to retrieve all companies", e);
            throw new ServiceException("Unable to retrieve companies", e);
        }
    }

    public List<Company> findByActive(ActiveEnum activeEnum) {
        requireNonNull(activeEnum, "activeEnum");
        log.info("findByActive(): activeEnum={}", activeEnum);

        try {
            return companyDbService.findByActive(activeEnum);
        } catch (DatabaseException e) {
            log.error("Failed to retrieve companies by active status: {}", activeEnum, e);
            throw new ServiceException("Unable to retrieve companies", e);
        }
    }
}