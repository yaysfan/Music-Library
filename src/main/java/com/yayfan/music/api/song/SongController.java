package com.yayfan.music.api.song;


import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.artist.ArtistService;
import com.yayfan.music.domain.song.Song;
import com.yayfan.music.domain.song.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongController {
    private final ArtistService artistService;
    private final SongService songService;
    private final SongMapper songMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ARTIST')")
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto  uploadSong(
            @Valid @ModelAttribute NewSongRequestDto request,
            Authentication authentication
    )  {
        Artist artist = artistService.findByUsername(authentication.getName());
        Song song = songService.createSong(songMapper.toNewSongRequest(request), artist);

        return songMapper.toSongDto(song);
    }

    @GetMapping("{songId}")
    public ResponseEntity<InputStreamResource> downloadSong(@PathVariable("songId") Integer id) {
        Song song = songService.findById(id);
        InputStreamResource resource = new InputStreamResource(songService.loadSong(song.getFile()));

        String headerValue = "attachment; filename=\"" + song.getFile() + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SearchedSongDto> searchSongs(@RequestParam(defaultValue = "") String search) {
        List<Song> songs = songService.searchSongs(search);
        return songMapper.toSearchSongDto(songs);
    }

    @GetMapping("/play/{songId}")
    public ResponseEntity<Resource> playSong(@PathVariable("songId") Integer id) {
        Song song = songService.findById(id);
        Resource resource = new InputStreamResource(songService.loadSong(song.getFile()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(resource);
    }
}
