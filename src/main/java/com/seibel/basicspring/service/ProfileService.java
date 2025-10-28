package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.service.ProfileDbService;
import com.seibel.basicspring.database.database.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
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

    public Profile create(Profile item) throws DatabaseFailureException, DatabaseRetrievalFailureException {
        requireNonNull(item, "Profile");
        log.info("create(): {}", item);
        return profileDbService.create(item.getNickname(), item.getFullname());
    }

    public Profile update(String extid, Profile item)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Profile");

        log.info("update(): extid={}, origin={}, {}", extid, item);

        return profileDbService.update(extid, item.getNickname(), item.getFullname());
    }

    public boolean delete(String extid)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);
        return profileDbService.delete(extid);
    }

    public Profile findByExtid(String extid) throws DatabaseRetrievalFailureException {
        requireNonBlank(extid, "extid");
        log.info("findByExtid(): extid={}", extid);
        return profileDbService.findByExtid(extid);
    }

    public List<Profile> findAll() throws DatabaseRetrievalFailureException {
        log.info("findAll()");
        return profileDbService.findAll();
    }

    public List<Profile> findByActive(ActiveEnum activeEnum) throws DatabaseRetrievalFailureException {
        requireNonNull(activeEnum, "activeEnum");
        log.info("findByActive(): activeEnum={}", activeEnum);
        return profileDbService.findByActive(activeEnum);
    }
}
