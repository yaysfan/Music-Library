package com.yayfan.music.api.song;

import com.yayfan.music.api.song.NewSongRequestDto;
import com.yayfan.music.api.song.SearchedSongDto;
import com.yayfan.music.domain.song.NewSongRequest;
import com.yayfan.music.domain.song.Song;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {
    SearchedSongDto toSearchSongDto(Song song);

    List<SearchedSongDto> toSearchSongDto(List<Song> songs);

    NewSongRequest toNewSongRequest(NewSongRequestDto dto);
}