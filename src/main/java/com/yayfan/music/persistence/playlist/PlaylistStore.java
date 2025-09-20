package com.yayfan.music.persistence.playlist;

import com.yayfan.music.domain.playlist.Playlist;
import com.yayfan.music.domain.playlist.PlaylistStorage;
import com.yayfan.music.persistence.song.SongRepository;
import com.yayfan.music.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlaylistStore implements PlaylistStorage {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final PlaylistEntityMapper playlistEntityMapper;
    private final UserRepository userRepository;


    @Override
    public List<Playlist> findByUserId(Integer userId) {
        return playlistRepository.findByUserId(userId).stream()
                .map(playlistEntityMapper::toDomainWithSongs)
                .toList();
    }

    @Override
    public Optional<Playlist> findById(Integer playlistId) {
        return playlistRepository.findById(playlistId)
                .map(playlistEntityMapper::toDomainWithSongs);
    }

    @Override
    public Playlist save(Playlist playlist) {
        var entity = playlistEntityMapper.toEntity(playlist);
        userRepository.findByUsername(playlist.getUser().getUsername())
                .ifPresent(entity::setUser);
        var savedEntity = playlistRepository.save(entity);
        return playlistEntityMapper.toDomainWithSongs(savedEntity);
    }

    @Override
    public void addSongToPlaylist(Integer playlistId, Integer songId) {
        playlistRepository.findById(playlistId).ifPresent(playlist -> {
            songRepository.findById(songId).ifPresent(song -> {
                PlaylistSongEntity playlistSong = new PlaylistSongEntity();
                playlistSong.setPlaylist(playlist);
                playlistSong.setSong(song);
                playlistSongRepository.save(playlistSong);
            });
        });
    }

    @Override
    public void removeSongFromPlaylist(Integer playlistId, Integer songId) {
        playlistRepository.findById(playlistId).ifPresent(playlistEntity -> {
            playlistEntity.getSongs()
                    .removeIf(playlistSong -> playlistSong.getSong().getId().equals(songId));
        });
    }

    @Override
    public void delete(Integer playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public Optional<Playlist> findByIdWithSongs(Integer playlistId) {
        return playlistRepository.findByIdWithSongs(playlistId)
                .map(playlistEntityMapper::toDomainWithSongs);
    }

}