package com.yayfan.music.api.playlist;

import com.yayfan.music.api.song.SearchedSongDto;

import java.util.List;

public record PlaylistDto(
        Integer id,
        String name,
        List<SearchedSongDto> songs
) {
}