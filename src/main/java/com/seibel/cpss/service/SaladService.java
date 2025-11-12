package com.seibel.cpss.service;

import com.seibel.cpss.common.domain.Salad;
import com.seibel.cpss.common.exceptions.ResourceNotFoundException;
import com.seibel.cpss.common.exceptions.ServiceException;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.service.SaladDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SaladService extends BaseService {

    private final SaladDbService dbService;

    public SaladService(SaladDbService dbService) {
        super(Salad.class.getSimpleName());
        this.dbService = dbService;
    }

    @Transactional
    public Salad create(Salad salad) {
        requireNonNull(salad, "Salad");
        requireNonBlank(salad.getName(), "name");
        requireNonBlank(salad.getUserExtid(), "userExtid");
        log.info("create(): {}", salad.getName());

        try {
            return dbService.create(salad);
        } catch (DatabaseFailureException e) {
            log.error("Failed to create salad: {}", salad.getName(), e);
            throw new ServiceException("Unable to create salad", e);
        }
    }

    @Transactional
    public Salad update(String extid, Salad salad) {
        requireNonBlank(extid, "extid");
        requireNonNull(salad, "Salad");
        log.info("update(): extid={}, {}", extid, salad.getName());

        try {
            Salad updated = dbService.update(extid, salad);
            if (updated == null) {
                throw new ResourceNotFoundException("Salad", extid);
            }
            return updated;
        } catch (DatabaseFailureException e) {
            log.error("Failed to update salad: {}", extid, e);
            throw new ServiceException("Unable to update salad", e);
        }
    }

    @Transactional
    public void delete(String extid) {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);

        try {
            dbService.delete(extid);
        } catch (DatabaseFailureException e) {
            log.error("Failed to delete salad: {}", extid, e);
            throw new ServiceException("Unable to delete salad", e);
        }
    }

    public Salad findByExtid(String extid) {
        requireNonBlank(extid, "extid");
        log.debug("findByExtid(): extid={}", extid);

        try {
            Salad salad = dbService.findByExtid(extid);
            if (salad == null) {
                throw new ResourceNotFoundException("Salad", extid);
            }
            return salad;
        } catch (DatabaseFailureException e) {
            log.error("Failed to find salad: {}", extid, e);
            throw new ServiceException("Unable to find salad", e);
        }
    }

    public List<Salad> findByUserExtid(String userExtid) {
        requireNonBlank(userExtid, "userExtid");
        log.debug("findByUserExtid(): userExtid={}", userExtid);

        try {
            return dbService.findByUserExtid(userExtid);
        } catch (DatabaseFailureException e) {
            log.error("Failed to find salads for user: {}", userExtid, e);
            throw new ServiceException("Unable to find salads for user", e);
        }
    }

    public List<Salad> findAll() {
        log.debug("findAll()");

        try {
            return dbService.findAll();
        } catch (DatabaseFailureException e) {
            log.error("Failed to retrieve all salads", e);
            throw new ServiceException("Unable to retrieve salads", e);
        }
    }
}
