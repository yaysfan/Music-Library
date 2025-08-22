package com.yayfan.music.domain.song;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayfan.music.domain.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Song {
    private final Integer id;
    private final String name;
    private final String file;
    private final String genre;
    private final Artist artist;
}
