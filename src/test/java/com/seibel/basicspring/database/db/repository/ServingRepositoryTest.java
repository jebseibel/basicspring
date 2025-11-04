package com.seibel.basicspring.database.db.repository;

import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.ServingDb;
import com.seibel.basicspring.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServingRepositoryTest {

    @Autowired
    private ServingRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void findByExtid_shouldReturnServing_whenExists() {
        // Arrange
        ServingDb serving = DomainBuilderDatabase.getServingDb();
        repository.save(serving);

        // Act
        Optional<ServingDb> result = repository.findByExtid(serving.getExtid());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(serving.getExtid(), result.get().getExtid());
        assertEquals(serving.getCode(), result.get().getCode());
    }

    @Test
    void findByExtid_shouldReturnEmpty_whenNotExists() {
        // Act
        Optional<ServingDb> result = repository.findByExtid("nonexistent-extid");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findByActive_shouldReturnActiveOnly() {
        // Arrange
        ServingDb active1 = DomainBuilderDatabase.getServingDb();
        active1.setActive(ActiveEnum.ACTIVE);
        ServingDb active2 = DomainBuilderDatabase.getServingDb();
        active2.setActive(ActiveEnum.ACTIVE);
        ServingDb inactive = DomainBuilderDatabase.getServingDb();
        inactive.setActive(ActiveEnum.INACTIVE);

        repository.save(active1);
        repository.save(active2);
        repository.save(inactive);

        // Act
        List<ServingDb> result = repository.findByActive(ActiveEnum.ACTIVE);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getActive() == ActiveEnum.ACTIVE));
    }

    @Test
    void existsByExtid_shouldReturnTrue_whenExists() {
        // Arrange
        ServingDb serving = DomainBuilderDatabase.getServingDb();
        repository.save(serving);

        // Act
        boolean result = repository.existsByExtid(serving.getExtid());

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
    void findByCode_shouldReturnServing_whenExists() {
        // Arrange
        ServingDb serving = DomainBuilderDatabase.getServingDb();
        repository.save(serving);

        // Act
        Optional<ServingDb> result = repository.findByCode(serving.getCode());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(serving.getCode(), result.get().getCode());
    }

    @Test
    void findByName_shouldReturnServing_whenExists() {
        // Arrange
        ServingDb serving = DomainBuilderDatabase.getServingDb();
        repository.save(serving);

        // Act
        Optional<ServingDb> result = repository.findByName(serving.getName());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(serving.getName(), result.get().getName());
    }

    @Test
    void save_shouldPersistServing() {
        // Arrange
        ServingDb serving = DomainBuilderDatabase.getServingDb();

        // Act
        ServingDb saved = repository.save(serving);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(serving.getExtid(), saved.getExtid());
    }

    @Test
    void findAll_shouldReturnAllServings() {
        // Arrange
        ServingDb serving1 = DomainBuilderDatabase.getServingDb();
        ServingDb serving2 = DomainBuilderDatabase.getServingDb();
        repository.save(serving1);
        repository.save(serving2);

        // Act
        List<ServingDb> result = (List<ServingDb>) repository.findAll();

        // Assert
        assertEquals(2, result.size());
    }
}
