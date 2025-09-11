package com.yayfan.music.api.playlist;

import com.yayfan.music.domain.playlist.Playlist;
import com.yayfan.music.domain.playlist.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistMapper playlistMapper;

    @GetMapping
    public List<PlaylistDto> getMyPlaylists(Authentication authentication) {
        List<Playlist> playlists = playlistService.findMyPlaylists(authentication.getName());
        return playlistMapper.toDto(playlists);
    }

    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(@Valid @RequestBody CreatePlaylistRequestDto request, Authentication authentication) {
        Playlist createdPlaylist = playlistService.createPlaylist(request.name(), authentication.getName());
        return new ResponseEntity<>(playlistMapper.toDto(createdPlaylist), HttpStatus.CREATED);
    }

    @GetMapping("/{playlistId}")
    public PlaylistDto getPlaylistById(@PathVariable Integer playlistId, Authentication authentication) {
        Playlist playlist = playlistService.getPlaylistById(playlistId, authentication.getName());
        return playlistMapper.toDto(playlist);
    }

    @PostMapping("/{playlistId}/songs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addSongToPlaylist(@PathVariable Integer playlistId,
                                  @Valid @RequestBody AddSongToPlaylistRequestDto request,
                                  Authentication authentication) {
        playlistService.addSongToPlaylist(playlistId, request.songId(), authentication.getName());
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSongFromPlaylist(@PathVariable Integer playlistId,
                                       @PathVariable Integer songId,
                                       Authentication authentication) {
        playlistService.removeSongFromPlaylist(playlistId, songId, authentication.getName());
    }

    @DeleteMapping("/{playlistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@PathVariable Integer playlistId, Authentication authentication) {
        playlistService.deletePlaylist(playlistId, authentication.getName());
    }

}