package com.yayfan.music.caching.song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongListCacheDto {
    private List<SongCacheDto> songs;
}