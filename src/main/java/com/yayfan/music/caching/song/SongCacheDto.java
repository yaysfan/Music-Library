package com.yayfan.music.caching.song;

import com.yayfan.music.caching.artist.ArtistCacheDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongCacheDto {
    private Integer id;
    private String name;
    private String file;
    private String genre;
    private ArtistCacheDto artist;
}
