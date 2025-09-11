package com.yayfan.music.persistence.playlist;

import com.yayfan.music.domain.playlist.Playlist;
import com.yayfan.music.domain.song.Song;
import com.yayfan.music.persistence.song.SongEntityMapper;
import com.yayfan.music.persistence.user.UserEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SongEntityMapper.class, UserEntityMapper.class})
public interface PlaylistEntityMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "songs", ignore = true)
    PlaylistEntity toEntity(Playlist playlist);


    @Mapping(source = "songs", target = "songs", qualifiedByName = "songsToDomain")
    Playlist toDomainWithSongs(PlaylistEntity entity);

    @Named("songsToDomain")
    default List<Song> songsToDomain(List<PlaylistSongEntity> playlistSongs) {
        if (playlistSongs == null) {
            return null;
        }
        SongEntityMapper songMapper = Mappers.getMapper(SongEntityMapper.class);
        return playlistSongs.stream()
                .map(playlistSong -> songMapper.toDomainWithArtist(playlistSong.getSong()))
                .collect(Collectors.toList());
    }

}