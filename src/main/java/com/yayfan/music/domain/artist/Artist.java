package com.yayfan.music.domain.artist;


import com.yayfan.music.domain.song.Song;
import com.yayfan.music.domain.user.User;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Artist {
    private final Integer id;
    private final String artistName;
    private final User user;
    private final List<Song> songs;
}
