package com.yayfan.music.api.song;

import com.yayfan.music.api.artist.ArtistDto;
import com.yayfan.music.domain.song.Song;
import lombok.Value;

import java.util.List;

@Value
public class SearchedSongDto {
    Integer id;
    String name;
    String genre;
    ArtistDto artist;

    public static SearchedSongDto from(Song song) {
        return new SearchedSongDto(
                song.getId(),
                song.getName(),
                song.getGenre(),
                ArtistDto.from(song.getArtist())
        );
    }

    public static List<SearchedSongDto> from(List<Song> songs) {
        return songs.stream()
                .map(SearchedSongDto::from)
                .toList();
    }
}
