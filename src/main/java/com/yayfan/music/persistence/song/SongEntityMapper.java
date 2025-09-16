package com.yayfan.music.persistence.song;

import com.yayfan.music.domain.song.Song;
import com.yayfan.music.persistence.artist.ArtistEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = ArtistEntityMapper.class)
public interface SongEntityMapper {

    @Mapping(target = "playlistSongs", ignore = true)
    SongEntity toEntity(Song song);

    @Mapping(target = "artist", ignore = true)
    Song toDomain(SongEntity entity);

    @Named("toDomainWithArtist")
    @Mapping(target = "artist.songs", ignore = true)
    Song toDomainWithArtist(SongEntity entity);
}
