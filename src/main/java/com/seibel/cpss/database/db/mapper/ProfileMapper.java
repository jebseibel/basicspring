package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Profile;
import com.seibel.cpss.database.db.entity.ProfileDb;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class ProfileMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Profile toModel(ProfileDb item) {
        return modelMapper.map(item, Profile.class);
    }

    public ProfileDb toDb(Profile item) {
        return modelMapper.map(item, ProfileDb.class);
    }

    public List<Profile> toModelList(List<ProfileDb> items) {
        if (items == null) return null;
        return items.stream().map(this::toModel).toList();
    }

    public List<ProfileDb> toDbList(List<Profile> items) {
        if (items == null) return null;
        return items.stream().map(this::toDb).toList();
    }
}
