package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Profile;
import com.seibel.cpss.database.db.entity.ProfileDb;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfileMapperTest {

    private ProfileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProfileMapper();
    }

    @Test
    void toModel_shouldMapAllFields() {
        // Arrange
        ProfileDb db = DomainBuilderDatabase.getProfileDb();

        // Act
        Profile domain = mapper.toModel(db);

        // Assert
        assertNotNull(domain);
        assertEquals(db.getExtid(), domain.getExtid());
        assertEquals(db.getNickname(), domain.getNickname());
        assertEquals(db.getFullname(), domain.getFullname());
        assertEquals(db.getCreatedAt(), domain.getCreatedAt());
        assertEquals(db.getUpdatedAt(), domain.getUpdatedAt());
        assertEquals(db.getDeletedAt(), domain.getDeletedAt());
        assertEquals(db.getActive(), domain.getActive());
    }

    @Test
    void toDb_shouldMapAllFields() {
        // Arrange
        Profile domain = DomainBuilderDatabase.getProfile();

        // Act
        ProfileDb db = mapper.toDb(domain);

        // Assert
        assertNotNull(db);
        assertEquals(domain.getExtid(), db.getExtid());
        assertEquals(domain.getNickname(), db.getNickname());
        assertEquals(domain.getFullname(), db.getFullname());
        assertEquals(domain.getCreatedAt(), db.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), db.getUpdatedAt());
        assertEquals(domain.getDeletedAt(), db.getDeletedAt());
        assertEquals(domain.getActive(), db.getActive());
    }

    @Test
    void toModelList_shouldMapAllItems() {
        // Arrange
        ProfileDb db1 = DomainBuilderDatabase.getProfileDb();
        ProfileDb db2 = DomainBuilderDatabase.getProfileDb();
        List<ProfileDb> dbList = Arrays.asList(db1, db2);

        // Act
        List<Profile> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(2, domainList.size());
        assertEquals(db1.getExtid(), domainList.get(0).getExtid());
        assertEquals(db2.getExtid(), domainList.get(1).getExtid());
    }

    @Test
    void toModelList_shouldHandleEmptyList() {
        // Arrange
        List<ProfileDb> dbList = Arrays.asList();

        // Act
        List<Profile> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toModelList_shouldHandleNullList() {
        // Act
        List<Profile> domainList = mapper.toModelList(null);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toDbList_shouldMapAllItems() {
        // Arrange
        Profile domain1 = DomainBuilderDatabase.getProfile();
        Profile domain2 = DomainBuilderDatabase.getProfile();
        List<Profile> domainList = Arrays.asList(domain1, domain2);

        // Act
        List<ProfileDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(2, dbList.size());
        assertEquals(domain1.getExtid(), dbList.get(0).getExtid());
        assertEquals(domain2.getExtid(), dbList.get(1).getExtid());
    }

    @Test
    void toDbList_shouldHandleEmptyList() {
        // Arrange
        List<Profile> domainList = Arrays.asList();

        // Act
        List<ProfileDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }

    @Test
    void toDbList_shouldHandleNullList() {
        // Act
        List<ProfileDb> dbList = mapper.toDbList(null);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }
}
