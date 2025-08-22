package com.yayfan.music.api.song;

import com.yayfan.music.domain.song.Song;
import lombok.Value;

import java.util.List;

@Value
public class SongDto {
    Integer id;
    String name;
    String genre;
    String file;

    public static SongDto from(Song song) {
        return new SongDto(
                song.getId(),
                song.getName(),
                song.getGenre(),
                song.getFile()
        );
    }

    public static List<SongDto> from(List<Song> songs) {
        return songs.stream()
                .map(SongDto::from)
                .toList();
    }
}
