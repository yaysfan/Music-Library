package com.yayfan.music.api.artist;

import com.yayfan.music.api.song.SearchedSongDto;
import com.yayfan.music.api.song.SongMapper;
import com.yayfan.music.domain.artist.ArtistService;
import com.yayfan.music.domain.song.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistMapper mapper;
    private final ArtistService service;
    private final SongMapper songMapper;
    private final SongService songService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SearchedArtistDto> searchArtists(@RequestParam(defaultValue = "") String search) {
        return mapper.toSearchedArtistDto(service.searchArtists(search));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{artistId}")
    public void deleteArtist(@PathVariable("artistId") Integer id) {
        service.deleteArtist(id);
    }

    @GetMapping("{artistId}/songs")
    public List<SearchedSongDto> findSongsByArtistId(@PathVariable("artistId") Integer artistId) {
        return songMapper.toSearchSongDto(songService.findSongsByArtistId(artistId));
    }
}
