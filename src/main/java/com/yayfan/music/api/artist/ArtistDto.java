package com.yayfan.music.api.artist;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class ArtistDto {
    Integer id;
    String artistName;
}
