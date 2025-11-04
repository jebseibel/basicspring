package com.seibel.basicspring.database.db.repository;

import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.ProfileDb;
import com.seibel.basicspring.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void findByExtid_shouldReturnProfile_whenExists() {
        // Arrange
        ProfileDb profile = DomainBuilderDatabase.getProfileDb();
        repository.save(profile);

        // Act
        Optional<ProfileDb> result = repository.findByExtid(profile.getExtid());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(profile.getExtid(), result.get().getExtid());
    }

    @Test
    void findByExtid_shouldReturnEmpty_whenNotExists() {
        // Act
        Optional<ProfileDb> result = repository.findByExtid("nonexistent-extid");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findByActive_shouldReturnActiveOnly() {
        // Arrange
        ProfileDb active1 = DomainBuilderDatabase.getProfileDb();
        active1.setActive(ActiveEnum.ACTIVE);
        ProfileDb active2 = DomainBuilderDatabase.getProfileDb();
        active2.setActive(ActiveEnum.ACTIVE);
        ProfileDb inactive = DomainBuilderDatabase.getProfileDb();
        inactive.setActive(ActiveEnum.INACTIVE);

        repository.save(active1);
        repository.save(active2);
        repository.save(inactive);

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProfileDb> result = repository.findByActive(ActiveEnum.ACTIVE, pageable);

        // Assert
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().allMatch(p -> p.getActive() == ActiveEnum.ACTIVE));
    }

    @Test
    void existsByExtid_shouldReturnTrue_whenExists() {
        // Arrange
        ProfileDb profile = DomainBuilderDatabase.getProfileDb();
        repository.save(profile);

        // Act
        boolean result = repository.existsByExtid(profile.getExtid());

        // Assert
        assertTrue(result);
    }

    @Test
    void existsByExtid_shouldReturnFalse_whenNotExists() {
        // Act
        boolean result = repository.existsByExtid("nonexistent-extid");

        // Assert
        assertFalse(result);
    }

    @Test
    void save_shouldPersistProfile() {
        // Arrange
        ProfileDb profile = DomainBuilderDatabase.getProfileDb();

        // Act
        ProfileDb saved = repository.save(profile);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(profile.getExtid(), saved.getExtid());
    }

    @Test
    void findAll_shouldReturnAllProfiles() {
        // Arrange
        ProfileDb profile1 = DomainBuilderDatabase.getProfileDb();
        ProfileDb profile2 = DomainBuilderDatabase.getProfileDb();
        repository.save(profile1);
        repository.save(profile2);

        // Act
        List<ProfileDb> result = (List<ProfileDb>) repository.findAll();

        // Assert
        assertEquals(2, result.size());
    }
}
