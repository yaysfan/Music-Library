package com.yayfan.music.api.song;

import com.yayfan.music.api.artist.ArtistDto;
import lombok.Value;

@Value
public class SearchedSongDto {
    Integer id;
    String name;
    String genre;
    ArtistDto artist;
}
