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

    // 내 플레이리스트 목록 조회
    @GetMapping
    public List<PlaylistDto> getMyPlaylists(Authentication authentication) {
        List<Playlist> playlists = playlistService.findMyPlaylists(authentication.getName());
        return playlistMapper.toDto(playlists);
    }

    // 새 플레이리스트 생성
    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(@Valid @RequestBody CreatePlaylistRequestDto request, Authentication authentication) {
        Playlist createdPlaylist = playlistService.createPlaylist(request.name(), authentication.getName());
        return new ResponseEntity<>(playlistMapper.toDto(createdPlaylist), HttpStatus.CREATED);
    }

    // 특정 플레이리스트 상세 조회
    @GetMapping("/{playlistId}")
    public PlaylistDto getPlaylistById(@PathVariable Integer playlistId, Authentication authentication) {
        return null;
    }

    // 플레이리스트에 노래 추가
    @PostMapping("/{playlistId}/songs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addSongToPlaylist(@PathVariable Integer playlistId,
                                  @Valid @RequestBody AddSongToPlaylistRequestDto request,
                                  Authentication authentication) {
        playlistService.addSongToPlaylist(playlistId, request.songId(), authentication.getName());
    }

    // 플레이리스트에서 노래 삭제
    @DeleteMapping("/{playlistId}/songs/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSongFromPlaylist(@PathVariable Integer playlistId,
                                       @PathVariable Integer songId,
                                       Authentication authentication) {
        playlistService.removeSongFromPlaylist(playlistId, songId, authentication.getName());
    }

    // 플레이리스트 삭제
    @DeleteMapping("/{playlistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@PathVariable Integer playlistId, Authentication authentication) {
        playlistService.deletePlaylist(playlistId, authentication.getName());
    }

}