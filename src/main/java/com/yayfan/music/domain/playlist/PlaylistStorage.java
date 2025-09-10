package com.yayfan.music.domain.playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistStorage {
    // 특정 유저의 모든 플레이리스트를 가져오기
    List<Playlist> findByUserId(Integer userId);

    // ID로 특정 플레이리스트 하나를 가져오기 (노래 목록 포함)
    Optional<Playlist> findById(Integer playlistId);

    // 새 플레이리스트를 저장
    Playlist save(Playlist playlist);

    // 플레이리스트에 노래를 추가
    void addSongToPlaylist(Integer playlistId, Integer songId);

    // 플레이리스트에서 노래를 제거
    void removeSongFromPlaylist(Integer playlistId, Integer songId);

    // 플레이리스트를 삭제
    void delete(Integer playlistId);
}