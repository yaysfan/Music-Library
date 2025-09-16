package com.yayfan.music.persistence.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Integer> {
    List<PlaylistEntity> findByUserId(Integer userId);

    @Query("SELECT p FROM PlaylistEntity p " +
            "LEFT JOIN FETCH p.songs ps " +
            "LEFT JOIN FETCH ps.song s " +
            "LEFT JOIN FETCH s.artist a " +
            "LEFT JOIN FETCH a.user u " +
            "WHERE p.id = :playlistId")
    Optional<PlaylistEntity> findByIdWithSongs(Integer playlistId);
}