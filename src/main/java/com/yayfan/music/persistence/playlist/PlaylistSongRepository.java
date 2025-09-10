package com.yayfan.music.persistence.playlist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, Integer> {
}