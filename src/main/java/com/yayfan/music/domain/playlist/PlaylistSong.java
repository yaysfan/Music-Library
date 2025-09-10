package com.yayfan.music.domain.playlist;

import com.yayfan.music.domain.song.Song;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistSong {
    private final Integer id;
    private final Playlist playlist;
    private final Song song;
}