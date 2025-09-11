package com.yayfan.music.domain.playlist;

import com.yayfan.music.domain.user.User;
import com.yayfan.music.domain.user.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {

    private final PlaylistStorage playlistStorage;
    private final UserStorage userStorage;

    public List<Playlist> findMyPlaylists(String username) {
        User user = userStorage.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return playlistStorage.findByUserId(user.getId());
    }

    @Transactional
    public Playlist createPlaylist(String name, String username) {
        User user = userStorage.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Playlist newPlaylist = Playlist.builder()
                .name(name)
                .user(user)
                .build();

        return playlistStorage.save(newPlaylist);
    }

    @Transactional
    public void addSongToPlaylist(Integer playlistId, Integer songId, String username) {
        verifyPlaylistOwner(playlistId, username);
        playlistStorage.addSongToPlaylist(playlistId, songId);
    }

    @Transactional
    public void removeSongFromPlaylist(Integer playlistId, Integer songId, String username) {
        verifyPlaylistOwner(playlistId, username);
        playlistStorage.removeSongFromPlaylist(playlistId, songId);
    }

    @Transactional
    public void deletePlaylist(Integer playlistId, String username) {
        verifyPlaylistOwner(playlistId, username);
        playlistStorage.delete(playlistId);
    }

    private void verifyPlaylistOwner(Integer playlistId, String username) {
        Playlist playlist = playlistStorage.findById(playlistId)
                .orElseThrow(PlaylistNotFoundException::new);

        if (!playlist.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not the owner of this playlist");
        }
    }

    public Playlist getPlaylistById(Integer playlistId, String username) {
        verifyPlaylistOwner(playlistId, username);
        return playlistStorage.findById(playlistId)
                .orElseThrow(PlaylistNotFoundException::new);
    }
}