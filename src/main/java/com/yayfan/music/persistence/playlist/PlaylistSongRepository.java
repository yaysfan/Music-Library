package com.yayfan.music.persistence.playlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, Integer> {
    Optional<PlaylistSongEntity> findByPlaylistIdAndSongId(Integer playlistId, Integer songId);
}