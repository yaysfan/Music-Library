package com.yayfan.music.persistence.playlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Integer> {
    List<PlaylistEntity> findByUserId(Integer userId);
}