package com.yayfan.music.domain.playlist;

import com.yayfan.music.domain.song.Song;
import com.yayfan.music.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Playlist {
    private final Integer id;
    private final String name;
    private final User user;
    private final List<Song> songs;
}