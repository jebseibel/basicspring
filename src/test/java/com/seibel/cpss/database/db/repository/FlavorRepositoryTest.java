package com.seibel.cpss.database.db.repository;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.FlavorDb;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
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
class FlavorRepositoryTest {

    @Autowired
    private FlavorRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void findByExtid_shouldReturnFlavor_whenExists() {
        // Arrange
        FlavorDb flavor = DomainBuilderDatabase.getFlavorDb();
        repository.save(flavor);

        // Act
        Optional<FlavorDb> result = repository.findByExtid(flavor.getExtid());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(flavor.getExtid(), result.get().getExtid());
        assertEquals(flavor.getCode(), result.get().getCode());
    }

    @Test
    void findByExtid_shouldReturnEmpty_whenNotExists() {
        // Act
        Optional<FlavorDb> result = repository.findByExtid("nonexistent-extid");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findByActive_shouldReturnActiveOnly() {
        // Arrange
        FlavorDb active1 = DomainBuilderDatabase.getFlavorDb();
        active1.setActive(ActiveEnum.ACTIVE);
        FlavorDb active2 = DomainBuilderDatabase.getFlavorDb();
        active2.setActive(ActiveEnum.ACTIVE);
        FlavorDb inactive = DomainBuilderDatabase.getFlavorDb();
        inactive.setActive(ActiveEnum.INACTIVE);

        repository.save(active1);
        repository.save(active2);
        repository.save(inactive);

        // Act
        List<FlavorDb> result = repository.findByActive(ActiveEnum.ACTIVE);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getActive() == ActiveEnum.ACTIVE));
    }

    @Test
    void existsByExtid_shouldReturnTrue_whenExists() {
        // Arrange
        FlavorDb flavor = DomainBuilderDatabase.getFlavorDb();
        repository.save(flavor);

        // Act
        boolean result = repository.existsByExtid(flavor.getExtid());

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
    void findByCode_shouldReturnFlavor_whenExists() {
        // Arrange
        FlavorDb flavor = DomainBuilderDatabase.getFlavorDb();
        repository.save(flavor);

        // Act
        Optional<FlavorDb> result = repository.findByCode(flavor.getCode());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(flavor.getCode(), result.get().getCode());
    }

    @Test
    void findByName_shouldReturnFlavor_whenExists() {
        // Arrange
        FlavorDb flavor = DomainBuilderDatabase.getFlavorDb();
        repository.save(flavor);

        // Act
        Optional<FlavorDb> result = repository.findByName(flavor.getName());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(flavor.getName(), result.get().getName());
    }

    @Test
    void save_shouldPersistFlavor() {
        // Arrange
        FlavorDb flavor = DomainBuilderDatabase.getFlavorDb();

        // Act
        FlavorDb saved = repository.save(flavor);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(flavor.getExtid(), saved.getExtid());
    }

    @Test
    void findAll_shouldReturnAllFlavors() {
        // Arrange
        FlavorDb flavor1 = DomainBuilderDatabase.getFlavorDb();
        FlavorDb flavor2 = DomainBuilderDatabase.getFlavorDb();
        repository.save(flavor1);
        repository.save(flavor2);

        // Act
        List<FlavorDb> result = (List<FlavorDb>) repository.findAll();

        // Assert
        assertEquals(2, result.size());
    }
}
