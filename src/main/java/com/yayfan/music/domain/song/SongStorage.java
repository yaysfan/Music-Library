package com.yayfan.music.domain.song;

import com.yayfan.music.domain.artist.Artist;

import java.util.List;
import java.util.Optional;

public interface SongStorage {
    Song save(Song song);

    Optional<Song> findById(Integer id);

    List<Song> search(String search);

    List<Song> findByArtistId(Integer artistId);

    void deleteById(Integer id);
}
