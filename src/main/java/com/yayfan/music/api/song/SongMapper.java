package com.yayfan.music.api.song;

import com.yayfan.music.api.artist.ArtistMapper;
import com.yayfan.music.domain.song.NewSongRequest;
import com.yayfan.music.domain.song.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SongMapper {
    private final ArtistMapper artistMapper;

    public SearchedSongDto toSearchSongDto(Song song) {
        return new SearchedSongDto(
                song.getId(),
                song.getName(),
                song.getGenre(),
                artistMapper.toArtistDto(song.getArtist())
        );
    }

    public List<SearchedSongDto> toSearchSongDto(List<Song> songs) {
        return songs.stream()
                .map(this::toSearchSongDto)
                .toList();
    }

    public SongDto toSongDto(Song song) {
        return new SongDto(
                song.getId(),
                song.getName(),
                song.getGenre(),
                song.getFile()
        );
    }

    public List<SongDto> toSongDto(List<Song> songs) {
        return songs.stream()
                .map(this::toSongDto)
                .toList();
    }

    public NewSongRequest toNewSongRequest(NewSongRequestDto dto) {
        return new NewSongRequest(
                dto.getName(),
                dto.getGenre(),
                dto.getFile()
        );
    }
}
