package com.seibel.cpss.database.db.service;

import com.seibel.cpss.common.domain.Profile;
import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.ProfileDb;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.mapper.ProfileMapper;
import com.seibel.cpss.database.db.repository.ProfileRepository;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileDbServiceTest {

    @Mock
    private ProfileRepository repository;

    @Mock
    private ProfileMapper mapper;

    @InjectMocks
    private ProfileDbService service;

    @Test
    void create_shouldGenerateUuidAndSetFields() {
        // Arrange
        String nickname = "testnick";
        String fullname = "Test Full Name";
        ProfileDb savedDb = DomainBuilderDatabase.getProfileDb(nickname, fullname, null);
        Profile expectedDomain = DomainBuilderDatabase.getProfile(savedDb);

        when(repository.save(any(ProfileDb.class))).thenReturn(savedDb);
        when(mapper.toModel(savedDb)).thenReturn(expectedDomain);

        // Act
        Profile result = service.create(nickname, fullname);

        // Assert
        assertNotNull(result);
        ArgumentCaptor<ProfileDb> captor = ArgumentCaptor.forClass(ProfileDb.class);
        verify(repository).save(captor.capture());

        ProfileDb captured = captor.getValue();
        assertNotNull(captured.getExtid());
        assertEquals(nickname, captured.getNickname());
        assertEquals(fullname, captured.getFullname());
        assertEquals(ActiveEnum.ACTIVE, captured.getActive());
        verify(mapper).toModel(savedDb);
    }

    @Test
    void create_shouldThrowException_whenRepositoryFails() {
        // Arrange
        when(repository.save(any(ProfileDb.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(DatabaseFailureException.class, () -> {
            service.create("nick", "Full Name");
        });
    }

    @Test
    void update_shouldUpdateFieldsAndTimestamp() {
        // Arrange
        String extid = "existing-extid";
        String nickname = "updated_nick";
        String fullname = "Updated Full Name";

        ProfileDb existingDb = DomainBuilderDatabase.getProfileDb("old_nick", "Old Name", extid);
        ProfileDb updatedDb = DomainBuilderDatabase.getProfileDb(nickname, fullname, extid);
        Profile expectedDomain = DomainBuilderDatabase.getProfile(updatedDb);

        when(repository.findByExtid(extid)).thenReturn(Optional.of(existingDb));
        when(repository.save(any(ProfileDb.class))).thenReturn(updatedDb);
        when(mapper.toModel(updatedDb)).thenReturn(expectedDomain);

        // Act
        Profile result = service.update(extid, nickname, fullname);

        // Assert
        assertNotNull(result);
        verify(repository).findByExtid(extid);
        verify(repository).save(any(ProfileDb.class));
        verify(mapper).toModel(updatedDb);
    }

    @Test
    void update_shouldReturnNull_whenNotFound() {
        // Arrange
        when(repository.findByExtid("nonexistent")).thenReturn(Optional.empty());

        // Act
        Profile result = service.update("nonexistent", "nick", "name");

        // Assert
        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void delete_shouldSetDeletedAtAndInactive() {
        // Arrange
        String extid = "existing-extid";
        ProfileDb existingDb = DomainBuilderDatabase.getProfileDb("nick", "name", extid);

        when(repository.findByExtid(extid)).thenReturn(Optional.of(existingDb));
        when(repository.save(any(ProfileDb.class))).thenReturn(existingDb);

        // Act
        boolean result = service.delete(extid);

        // Assert
        assertTrue(result);
        ArgumentCaptor<ProfileDb> captor = ArgumentCaptor.forClass(ProfileDb.class);
        verify(repository).save(captor.capture());
        assertEquals(ActiveEnum.INACTIVE, captor.getValue().getActive());
    }

    @Test
    void findByExtid_shouldReturnProfile_whenExists() {
        // Arrange
        String extid = "existing-extid";
        ProfileDb db = DomainBuilderDatabase.getProfileDb("nick", "name", extid);
        Profile expectedDomain = DomainBuilderDatabase.getProfile(db);

        when(repository.findByExtid(extid)).thenReturn(Optional.of(db));
        when(mapper.toModel(db)).thenReturn(expectedDomain);

        // Act
        Profile result = service.findByExtid(extid);

        // Assert
        assertNotNull(result);
        verify(repository).findByExtid(extid);
    }

    @Test
    void findAll_shouldReturnAllProfiles() {
        // Arrange
        ProfileDb db1 = DomainBuilderDatabase.getProfileDb();
        ProfileDb db2 = DomainBuilderDatabase.getProfileDb();
        List<ProfileDb> dbList = Arrays.asList(db1, db2);

        when(repository.findAll()).thenReturn(dbList);
        when(mapper.toModelList(dbList)).thenReturn(Arrays.asList(
                DomainBuilderDatabase.getProfile(db1),
                DomainBuilderDatabase.getProfile(db2)
        ));

        // Act
        List<Profile> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findByActive_shouldReturnFilteredProfiles() {
        // Arrange
        ProfileDb db = DomainBuilderDatabase.getProfileDb();
        db.setActive(ActiveEnum.ACTIVE);
        List<ProfileDb> dbList = Arrays.asList(db);
        Page<ProfileDb> page = new PageImpl<>(dbList);

        when(repository.findByActive(any(ActiveEnum.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toModelList(dbList)).thenReturn(Arrays.asList(DomainBuilderDatabase.getProfile(db)));

        // Act
        List<Profile> result = service.findByActive(ActiveEnum.ACTIVE);

        // Assert
        assertEquals(1, result.size());
    }
}
