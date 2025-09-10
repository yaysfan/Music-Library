package com.yayfan.music.api.playlist;

import com.yayfan.music.api.song.SongMapper;
import com.yayfan.music.domain.playlist.Playlist;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = {SongMapper.class})
public interface PlaylistMapper {
    PlaylistDto toDto(Playlist playlist);

    List<PlaylistDto> toDto(List<Playlist> playlists);
}
