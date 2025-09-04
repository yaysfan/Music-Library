package com.yayfan.music.persistence.user;

import com.yayfan.music.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    @Mapping(target = "artist", ignore = true)
    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}
