package com.yayfan.music.persistence.playlist;

import com.yayfan.music.domain.playlist.Playlist;
import com.yayfan.music.persistence.song.SongEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SongEntityMapper.class})
public interface PlaylistEntityMapper {

    @Mapping(target = "songs", ignore = true)
    Playlist toDomain(PlaylistEntity entity);

    @Mapping(source = "songs", target = "songs")
    Playlist toDomainWithSongs(PlaylistEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "songs", ignore = true)
    PlaylistEntity toEntity(Playlist playlist);
}