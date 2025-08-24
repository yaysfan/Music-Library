package com.yayfan.music.api.artist;

import com.yayfan.music.api.song.SongDto;
import com.yayfan.music.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor
@Value
public class SearchedArtistDto {
    Integer id;
    String artistName;
    List<SongDto> songs;

    public static SearchedArtistDto from(Artist artist) {
        return new SearchedArtistDto(
                artist.getId(),
                artist.getArtistName(),
                SongDto.from(artist.getSongs())
        );
    }

    public static List<SearchedArtistDto> from(List<Artist> artists) {
        return artists.stream()
                .map(SearchedArtistDto::from)
                .toList();
    }
}