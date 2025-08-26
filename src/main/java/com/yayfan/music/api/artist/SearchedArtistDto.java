package com.yayfan.music.api.artist;

import com.yayfan.music.api.song.SongDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import java.util.List;

@RequiredArgsConstructor
@Value
public class SearchedArtistDto {
    Integer id;
    String artistName;
    List<SongDto> songs;
}